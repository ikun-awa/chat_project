import { receiveMessage } from './chat.js';

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

fetch('/api/auth/me', {
  headers: { 'Authorization': 'Bearer ' + token }
})
  .then(res => {
    if (!res.ok) throw new Error('无法获取用户信息');
    return res.json();
  })
  .then(user => {
    // 3. 填充下拉菜单
    $('#usernameDisplay').text(user.username);
    $('#userMeta').text(`${user.gender} · ${user.age} 岁`);
    // 如果后端返回 avatar URL：
    if (user.avatarUrl) {
      $('#userAvatar').attr('src', user.avatarUrl);
    }
  })
  .catch(err => {
    console.error(err);
    // token 过期或无效，跳登录
    localStorage.removeItem('jwtToken');
    window.location.assign('/');
  });

$('#logoutBtn').on('click', () => {
  localStorage.removeItem('jwtToken');
  window.location.assign('/');
});
