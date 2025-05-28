import { time } from './chat.js';
console.log(time);

// 设置按钮和菜单
const settingsBtn = document.getElementById('settingsBtn');
const settingsMenu = document.getElementById('chatSettings');

settingsBtn.addEventListener('click', e => {
  e.stopPropagation();
  settingsMenu.style.display =
    settingsMenu.style.display === 'flex' ? 'none' : 'flex';
});

document.addEventListener('click', () => {
  settingsMenu.style.display = 'none';
});

// 菜单项回调示例
document.getElementById('renameGroup').addEventListener('click', () => {
  const newName = prompt('Please enter a new group chat name:');
  if (newName) alert('Renamed group chats to:' + newName);
});
document.getElementById('manageMembers').addEventListener('click', () => {
  alert('Open the Manage Members dialog box (example)');
});


// 发送消息逻辑（保持不变）
const sendButton = document.getElementById('sendButton');
const inputEl = document.getElementById('ipt');

// In your send message handler
document.getElementById('sendButton').addEventListener('click', function() {
  const messageInput = document.getElementById('ipt');
  const message = messageInput.value.trim();

  if (!message) {
    // Show empty message modal
    const emptyMessageModal = new bootstrap.Modal(document.getElementById('emptyMessageModal'));
    emptyMessageModal.show();
    return;
  }


  // Proceed with sending message
  // ... your existing send logic ...
});
// 清空聊天记录确认事件
document.getElementById('confirmClearHistory').addEventListener('click', function() {
  document.getElementById('message').innerHTML = '<div class="clear-notice">Chat history cleared</div>';
  const modal = bootstrap.Modal.getInstance(document.getElementById('clearHistoryModal'));
  modal.hide();
});

// 退出群聊确认事件
document.getElementById('quitBtn').addEventListener('click', function() {
  document.getElementById('message').innerHTML = '<div class="left-notice">You have left the group chat</div>';
  document.getElementById('ipt_bar').style.display = 'none';
  document.getElementById('sendButton').disabled = true;
  document.getElementById('sendButton').style.cursor = 'not-allowed';
  document.querySelectorAll('.func-btn, #settingsBtn').forEach(btn => {
    btn.disabled = true;
    btn.style.opacity = '0.5';
  });
  const modal = bootstrap.Modal.getInstance(document.getElementById('quitModal'));
  modal.hide();
});


$(function(){
  $('#renameConfirmBtn').click(function(){
    const newName = $('#renameInput').val().trim();
    if(!newName){
      alert('名称不能为空');
      return;
    }
    $.ajax({
      url: '/api/chat/rename',
      method: 'POST',
      contentType: 'application/json',
      data: JSON.stringify({ chatId: CURRENT_CHAT_ID, name: newName }),
      success(){
        // 更新标题和群聊信息展示
        $('#top_nav h1').text(newName);
        $('#group-info p:contains("名称：")')
          .html('<strong>名称：</strong>' + newName);
        $('#renameModal').modal('hide');
      },
      error(){
        alert('重命名失败，请重试');
      }
    });
  });
});
$(function(){
  const $items = $('#announcement-board .announcement-list li');
  let idx = 0;
  if (!$items.length) return;

  // 显示第一条
  $items.eq(0).show();

  // 每 3 秒切换一次
  setInterval(() => {
    $items.eq(idx)
      .fadeOut(300, function() {
        idx = (idx + 1) % $items.length;
        $items.eq(idx).fadeIn(300);
      });
  }, 3000);
});
//初始id序列
let times = 0;

/*
//初始化时间
const time = new Date().toLocaleTimeString([], {
  hour: '2-digit',
  minute: '2-digit',
  second: '2-digit'
});

 */

//自动换行function
function scrollToBottom() {
  const msgDiv = document.getElementById('message');
  msgDiv.scrollTop = msgDiv.scrollHeight;
}

//自己发消息function
function sendMessage() {
  const msg = $('#ipt').val().trim();
  if (!msg) return;
  times++;

  // 消息容器
  const msgBox = $(`
    <div class="d-flex justify-content-end mb-2"
         id="box${times}"
         style="opacity:0; transform: translateY(20px); transition: all .2s ease-out position: relative;
                padding-right: 80px;">
    </div>
  `);

  // 用户名，绝对定位到头像上方
  const nameEl = $('<div>').text('草泥马').addClass('name-tag');

  // 气泡容器
  const bubbleContainer = $('<div>').addClass('bubble-container bubble-c');

  // 消息气泡
  const bubble = $('<p>')
    .addClass('d-inline-block bg-primary text-white p-3 bubble')
    .text(msg);

  // 时间戳
  const stamp = $('<small>')
    .addClass('bubble-time stamp')
    .text(time);

  // 组装自己气泡元素
  bubble.append(stamp);
  bubbleContainer.append(bubble);
  msgBox.append(nameEl, bubbleContainer);

  // 插入消息列表
  $('#message').append(msgBox);

  // 触发动画
  setTimeout(() => {
    msgBox.css({
      opacity: 1,
      transform: 'translateY(0)',
      transition: 'all .2s ease-out'
    });
    scrollToBottom();
  }, 10);

  //清空消息栏
  $('#ipt').val('').focus();
}

//接受对方信息function
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

//局限function
$(function(){
  //发送按钮
  $('#sendButton').on('click', sendMessage);

  //enter事件
  $('#ipt').on('keypress', e => {
    if (e.which === 13) {
      sendMessage();
      return false;
    }
  });
});

$(function() {
  //自动测试（3秒后显示消息）
  setTimeout(() => {
    receiveMessage('你好，这是一条自动测试消息！');
  }, 1000);
});
