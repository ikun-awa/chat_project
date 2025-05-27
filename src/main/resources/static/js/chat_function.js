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
  const newName = prompt('请输入新的群聊名称：');
  if (newName) alert('已将群聊重命名为：' + newName);
});
document.getElementById('manageMembers').addEventListener('click', () => {
  alert('打开“管理成员”对话框（示例）');
});


// 发送消息逻辑（保持不变）
const sendButton = document.getElementById('sendButton');
const inputEl = document.getElementById('ipt');

sendButton.addEventListener('click', () => {
  const text = inputEl.value.trim();
  if (!text) {
    alert('必须填写内容');
    return;
  }
  appendMyMessage(text);
  inputEl.value = '';
});

inputEl.addEventListener('keydown', e => {
  if (e.key === 'Enter') {
    const text = inputEl.value.trim();
    if (!text) {
      alert('必须填写内容');
      return;
    }
    appendMyMessage(text);
    inputEl.value = '';
  }
});
// 清空聊天记录确认事件
document.getElementById('confirmClearHistory').addEventListener('click', function() {
  document.getElementById('message').innerHTML = '<div class="clear-notice">聊天记录已清空</div>';
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
