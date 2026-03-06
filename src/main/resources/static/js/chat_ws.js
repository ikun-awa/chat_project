function formatTimestamp(ts) {
  if (!ts) {
    return new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' });
  }
  const d = new Date(ts);
  if (Number.isNaN(d.getTime())) {
    return String(ts);
  }
  return d.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' });
}

function escapeHtml(text) {
  return $('<div/>').text(text).html();
}

function receiveMessage(content, sender = '对方', timestamp) {
  const safeContent = escapeHtml(content || '');
  const safeSender = escapeHtml(sender || '匿名');
  const timeText = formatTimestamp(timestamp);

  const msgBox = $(
    `<div class="d-flex justify-content-start mb-2 receive-message-container"
         style="opacity:0; transform: translateY(20px); transition: all .2s ease-out; position: relative; padding-left: 80px;">
      <div class="name-tag-other">${safeSender}</div>
      <div class="bubble-container bubble-c-other">
        <p class="d-inline-block p-3 bubble-other">${safeContent}<small class="bubble-time stamp-other">${timeText}</small></p>
      </div>
    </div>`
  );

  $('#message').append(msgBox);

  setTimeout(() => {
    msgBox.css({ opacity: 1, transform: 'translateY(0)' });
    scrollToBottom();
  }, 10);
}

let stompClient = null;
const token = localStorage.getItem('jwtToken');
const currentGroupId = Number(new URLSearchParams(window.location.search).get('groupId') || '1');

function scrollToBottom() {
  const msgDiv = document.getElementById('message');
  if (msgDiv) {
    msgDiv.scrollTop = msgDiv.scrollHeight;
  }
}

async function loadHistory() {
  const res = await fetch(`/api/chat/history?roomId=${currentGroupId}`, {
    headers: token ? { Authorization: 'Bearer ' + token } : {}
  });
  if (!res.ok) {
    throw new Error('加载历史消息失败: ' + res.status);
  }

  const history = await res.json();
  $('#message').empty();
  history.forEach(msg => receiveMessage(msg.content, msg.sender, msg.timestamp));
}

function connect() {
  const socket = new SockJS('/ws-chat');
  stompClient = Stomp.over(socket);
  stompClient.debug = null;

  stompClient.connect({}, () => {
    stompClient.subscribe('/topic/group.' + currentGroupId, payload => {
      const msg = JSON.parse(payload.body);
      receiveMessage(msg.content, msg.sender, msg.timestamp);
    });
  }, error => {
    console.error('STOMP error', error);
  });
}

function sendWsMessage() {
  const input = document.getElementById('ipt');
  const content = input.value.trim();
  if (!content || !stompClient) return;

  const username = localStorage.getItem('username') || '匿名';
  const chatMsg = { roomId: currentGroupId, sender: username, content };
  stompClient.send('/app/chat.sendMessage/' + currentGroupId, {}, JSON.stringify(chatMsg));
  input.value = '';
  input.focus();
}

document.addEventListener('DOMContentLoaded', async () => {
  try {
    await loadHistory();
  } catch (e) {
    console.error(e);
  }

  connect();

  document.getElementById('sendButton').addEventListener('click', sendWsMessage);
  document.getElementById('ipt').addEventListener('keypress', e => {
    if (e.key === 'Enter') {
      sendWsMessage();
      e.preventDefault();
    }
  });
});

$('#logoutBtn').on('click', () => {
  localStorage.removeItem('jwtToken');
  localStorage.removeItem('username');
  window.location.assign('/');
});
