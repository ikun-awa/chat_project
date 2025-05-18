import { time } from './chat.js';
console.log(time);

/*
$('#member-list li').mouseover(function () {
  $(this).css('background-color', '#ececec');
}).mouseout(function () {
  $(this).css({
    'background-color': '#ffffff',
    'transition': 'all .1s'
  });
});
 */

// ====== 把 splitBtn 改成 settingsBtn ======
const settingsBtn  = document.getElementById('settingsBtn');
const settingsMenu = document.getElementById('chatSettings');

settingsBtn.addEventListener('click', e => {
  e.stopPropagation();
  // 切换显示/隐藏菜单，用 flex 才能让 flex-direction 生效
  settingsMenu.style.display =
    settingsMenu.style.display === 'flex' ? 'none' : 'flex';
});

// 点击页面任意空白处收起菜单
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


// 退出群聊
document.getElementById('leaveGroup').addEventListener('click', e => {
  e.stopPropagation();           // 防止冒泡关闭菜单
  if (!confirm('确定要退出此群聊吗？')) return;

  // 1. 关闭设置菜单
  document.getElementById('chatSettings').style.display = 'none';

  // 2. 用红色提示替换消息区内容
  document.getElementById('message').innerHTML =
    '<div class="left-notice">You have left the group chat</div>';
  setTimeout(() => {
    window.location.href = 'lobby.html';
  },1000);

  // 3. 隐藏输入区并禁用所有按钮
  document.getElementById('ipt_bar').style.display = 'none';
  document.getElementById('sendButton').disabled = true;
  document.getElementById('sendButton').style.cursor = 'not-allowed';
  document.querySelectorAll('.func-btn, #settingsBtn')
    .forEach(btn => { btn.disabled = true; btn.style.opacity = '0.5'; });
});

// 清空聊天记录
document.getElementById('clearHistory').addEventListener('click', e => {
  e.stopPropagation();
  if (!confirm('确定要清空聊天记录吗？此操作不可撤销。')) return;

  // 1. 关闭设置菜单
  document.getElementById('chatSettings').style.display = 'none';

  // 2. 清空消息区并显示提示
  document.getElementById('message').innerHTML =
    '<div class="clear-notice">聊天记录已清空</div>'+
    `<div class="clear-notice">${time}</div>`;

  // 3. 保持输入区可用（如有需求可在此重置输入框）
  // document.getElementById('ipt').value = '';
});
const sendButton = document.getElementById('sendButton');
const inputEl    = document.getElementById('ipt');

sendButton.addEventListener('click', () => {
  const text = inputEl.value.trim();

  // —— ① 空值校验 ——
  if (!text) {
    alert('必须填写内容');
    return;
  }

  // —— ② 你的原始发送逻辑 ——
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
// 假设你在 chat.js 或 chat_function.js 中
$(function(){
  const $btn   = $('#stickerBtn');
  const $panel = $('#stickerPicker');
  const $msg   = $('#message');    // 聊天消息容器
  const $ipt   = $('#ipt');        // 文本输入框

  // 1) 切换面板显示/隐藏
  $btn.on('click', e => {
    e.stopPropagation();
    // 计算面板位置（相对于按钮），如果需要更精确可用 getBoundingClientRect()
    const pos = $btn.position();
    $panel.css({ top: pos.top + $btn.outerHeight() + 5, left: pos.left }).toggle();
  });
  // 点击空白处隐藏面板
  $(document).on('click', () => $panel.hide());
  // 阻止点击面板本身冒泡
  $panel.on('click', e => e.stopPropagation());

  // 2) 点击某个表情包缩略图
  $panel.on('click', '.sticker-thumb', function(){
    const stickerSrc = $(this).attr('src'); // 或者 $(this).data('id')
    sendSticker(stickerSrc);
    $panel.hide();
  });

  // 3) 发送函数：可复用你原来 sendText 的逻辑
  function sendSticker(src){
    // 如果你有后端接口，请在这里发请求；下面演示：直接渲染到页面上
    const $wrap = $('<div class="bubble-c"></div>');
    const $img  = $(`<img src="${src}" alt="[sticker]" style="max-width:120px;">`);
    $wrap.append($img);
    $msg.append($wrap);

    // 滚动到底部（如果有这段逻辑的话）
    $msg.scrollTop($msg[0].scrollHeight);
  }

  // （可选）如果你想把文字和表情都走同一个 sendMessage 函数：
  // function sendSticker(src){
  //   sendMessage({ type:'sticker', content: src });
  // }
});
$(function(){
  // 当 Modal 完全显示后，自动聚焦到输入框
  $('#searchModal').on('shown.bs.modal', function () {
    $('#searchInput').trigger('focus');
    // 清除上次高亮
    $('.bubble, .bubble-other').removeClass('highlight');
    $('#searchResultInfo').hide();
    $('#searchInput').val('');
  });

  // 点击“搜索”按钮或回车
  $('#searchConfirmBtn').on('click', doSearch);
  $('#searchInput').on('keypress', function(e){
    if (e.key === 'Enter') {
      e.preventDefault();
      doSearch();
    }
  });

  function doSearch() {
    const keyword = $('#searchInput').val().trim();
    const $info = $('#searchResultInfo');
    $('.bubble, .bubble-other').removeClass('highlight');

    if (!keyword) {
      $info.text('Please enter a keyword.').show();
      return;
    }

    let found = false;
    $('#message .bubble, #message .bubble-other').each(function(){
      const $msg = $(this);
      if ($msg.text().toLowerCase().includes(keyword.toLowerCase())) {
        $msg.addClass('highlight');
        // 滚动到消息
        $('html, body').animate({
          scrollTop: $msg.closest('#message').offset().top
            + $msg.position().top
            - 100
        }, 300);
        found = true;
        return false;
      }
    });

    if (found) {
      $info.text('Found messages containing "'+keyword+'".').show();
    } else {
      $info.text('No messages found for "'+keyword+'".').show();
    }
  }
});
// 当 Search Modal 被关闭时，清除所有高亮
$('#searchModal').on('hidden.bs.modal', function () {
  $('.bubble, .bubble-other').removeClass('highlight');
  // 隐藏提示文字
  $('#searchResultInfo').hide();
});
// 当 Compose Modal 的 Send 被点击
$('#composeSendBtn').on('click', function() {
  const text = $('#composeInput').val().trim();
  if (!text) return;
  // 复用你原来的 sendMessage 函数，比如：
  sendMessage(text);
  // 关闭 Modal
  $('#composeModal').modal('hide');
  // 清空输入框
  $('#composeInput').val('');
});

// 可选：当 Modal 打开时自动聚焦到文本域
$('#composeModal').on('shown.bs.modal', function() {
  $('#composeInput').trigger('focus');
});

  document.getElementById('splitBtn').addEventListener('click', function() {
  // 跳回首页
  window.location.href = '/wocao/index.html'; // 或者 'index.html'，看你的文件结构
});



