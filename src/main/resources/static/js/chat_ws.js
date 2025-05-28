import { sendMessage, receiveMessage, scrollToBottom } from './chat_function.js';

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
