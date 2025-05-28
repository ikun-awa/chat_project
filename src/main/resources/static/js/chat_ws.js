function receiveMessage(content, sender = '对方') {
  times++;

  // 消息容器（左侧布局）
  const msgBox = $(`
    <div class="d-flex justify-content-start mb-2 receive-message-container"
         id="box${times}"
         class="name_tag"
         style="opacity:0; transform: translateY(20px); transition: all .2s ease-out;
                    position: relative; padding-left: 80px;">
    </div>
  `);

  // 用户名标签
  const nameEl = $('<div>')
    .text(sender)
    .addClass('name-tag-other');

  // 气泡容器（左侧间距）
  const bubbleContainer = $('<div>').addClass('bubble-container bubble-c-other');

  // 消息气泡（灰色）
  const bubble = $('<p>')
    .addClass('d-inline-block p-3 bubble-other')
    .text(content);

  // 时间戳（左侧对齐）
  const stamp = $('<small>')
    .addClass('bubble-time stamp-other')
    .text(time);

  //组装元素结构
  bubble.append(stamp);
  bubbleContainer.append(bubble);
  msgBox.append(nameEl, bubbleContainer);

  // 插入消息列表
  $('#message').append(msgBox);

  // 触发动画
  setTimeout(() => {
    msgBox.css({
      opacity: 1,
      transform: 'translateY(0)'
    });
    scrollToBottom();
  }, 10); // 增加10ms延迟确保渲染
}



let stompClient = null;
let currentGroupId = new URLSearchParams(window.location.search).get('groupId') || '1';

// 建立连接并订阅
function connect() {
  const socket = new SockJS('/ws-chat');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, frame => {
    console.log('Connected: ' + frame);
    // 订阅该群组的消息主题
    stompClient.subscribe('/topic/group.' + currentGroupId, payload => {
      const msg = JSON.parse(payload.body);
      receiveMessage(msg.content, msg.sender, msg.timestamp);
    });
  }, error => {
    console.error('STOMP error', error);
  });
}

// 发送消息
function sendWsMessage() {
  const content = document.getElementById('ipt').value.trim();
  if (!content) return;
  const username = localStorage.getItem('username') || '匿名';
  const chatMsg = { groupId: currentGroupId, sender: username, content };
  stompClient.send('/app/chat.sendMessage/' + currentGroupId, {}, JSON.stringify(chatMsg));
  // 清空并聚焦
  document.getElementById('ipt').value = '';
}

// 绑定按钮和回车
document.addEventListener('DOMContentLoaded', () => {
  connect();
  document.getElementById('sendButton').addEventListener('click', sendWsMessage);
  document.getElementById('ipt').addEventListener('keypress', e => {
    if (e.key === 'Enter') { sendWsMessage(); e.preventDefault(); }
  });
});

$('#logoutBtn').on('click', () => {
  localStorage.removeItem('jwtToken');
  window.location.assign('/');
});
