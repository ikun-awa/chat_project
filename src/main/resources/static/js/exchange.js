//登录信息处理
(async function () {
  'use strict';
  const deng_ti = document.getElementById('deng_ti');
  deng_ti.querySelectorAll('.needs-validation').forEach(formEl => {
    formEl.addEventListener('submit', async event => {
      event.preventDefault();
      event.stopPropagation();
      if (!formEl.checkValidity()) {
        console.log('非法表单');
      } else {
        $('#spin_d').show();
        try {
          const data = new URLSearchParams(new FormData(formEl));
          const resp = await fetch('/login', { method: 'POST', body: data });
          if (resp.ok) {
            window.location.assign('./lobby/lobby.html');
          } else {
            const msg = await resp.text();
            alert(`登录失败：${msg}`);
          }
        } catch (err) {
          console.error(err);
          alert('网络错误，请稍后再试');
        } finally {
          $('#spin_d').hide();
        }
      }
      formEl.classList.add('was-validated');
    }, false);
  });
})();



/*
(function () {
  'use strict';
  const deng_ti = document.getElementById('deng_ti');
  const forms = deng_ti.querySelectorAll('.needs-validation');
  Array.prototype.slice.call(forms).forEach(function (form) {
    form.addEventListener('submit', async function (event) {

      if (!form.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
        console.log('非法表单');
      }
      else{
        $('#spin_d').css('display', 'inline-block');

        event.preventDefault();
        const form = event.target;
        const data = new URLSearchParams(new FormData(form));
        const resp = await fetch('/login', {
          method: 'POST',
          body: data
        });
        //document.getElementById('result').textContent = await resp.text();
        form.reset();
        console.log('合法表单');

      }

      form.classList.add('was-validated');
    }, false);
  });
})();

 */

//注册信息处理
(function () {
  'use strict';
  // 从整个 document 中选出所有 .needs-validation 表单
  const forms = document.querySelectorAll('form.needs-validation');
  forms.forEach(function (form) {
    form.addEventListener('submit', async function (event) {
      event.preventDefault();
      if (!form.checkValidity()) {
        event.stopPropagation();
        console.log('非法表单');
      } else {
        $('#spin_z').show();
        const data = new URLSearchParams(new FormData(form));
        await fetch('/submit', { method: 'POST', body: data });
        form.reset();
        console.log('合法表单');
        alert('Good boy, you hava successfully registered!');
        setTimeout(() => {
          window.location.assign('./lobby/lobby.html');
        },500);
      }
      form.classList.add('was-validated');
    }, false);
  });
})();

/*
(function () {
  'use strict';
  const zhu_ti = document.getElementById('zhu_ti');
  const forms = zhu_ti.querySelectorAll('.needs-validation');
  Array.prototype.slice.call(forms).forEach(function (form) {
    form.addEventListener('submit', async function (event) {
      event.preventDefault();
      if (!form.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
        console.log('非法表单');
      }
      else{
        $('#spin_z').css('display', 'inline-block');


        const form = event.target;
        const data = new URLSearchParams(new FormData(form));
        const resp = await fetch('/submit', {
          method: 'POST',
          body: data
        });
        //document.getElementById('result').textContent = await resp.text();
        form.reset();
        console.log('合法表单');
        //window.location.assign('./lobby/lobby.html');
      }

      form.classList.add('was-validated');
    }, false);
  });
})();

 */






// 通知角标示例：假设通过 WebSocket 或 AJAX 来更新未读数
function updateNotifyCount(n) {
  const badge = document.getElementById('notify-badge');
  if (n > 0) {
    badge.textContent = n;
    badge.style.display = 'inline-block';
  } else {
    badge.style.display = 'none';
  }
}

// 示例：5 秒后模拟有 3 条新通知
setTimeout(() => updateNotifyCount(3), 5000);


$(function(){
  const $panel = $('#notify-panel');
  const $badge = $('#notify-badge');
  let unread = 0;

  // 点击铃铛，切换面板显示/隐藏
  $('#btn-notify').on('click', function(e){
    e.stopPropagation();
    $panel.toggle();
    if ($panel.is(':visible')) {
      // 打开时标记所有为已读
      $panel.find('.item.unread').removeClass('unread');
      unread = 0;
      $badge.hide();
    }
  });

  // 点击页面任意其他地方，隐藏面板
  $(document).on('click', function(){
    $panel.hide();
  });

  // 模拟接收新消息：2秒后弹一条通知
  setTimeout(function(){
    const $item = $(`
        <div class="item unread">
          <strong>系统</strong>
          <p>你有一条新消息！</p>
          <small class="text-muted">刚刚</small>
        </div>
      `);
    $panel.prepend($item);
    unread++;
    $badge.text(unread).show();
  }, 2000);

  // —— 如果你有真实 WebSocket，把上面 setTimeout 换成：
  // const ws = new WebSocket('wss://你的服务器地址/ws');
  // ws.onmessage = evt => {
  //   const msg = JSON.parse(evt.data);
  //   const $item = $(`
  //     <div class="item unread">
  //       <strong>${msg.from}</strong>
  //       <p>${msg.text}</p>
  //       <small class="text-muted">${msg.time}</small>
  //     </div>
  //   `);
  //   $panel.prepend($item);
  //   unread++;
  //   $badge.text(unread).show();
  // };
});
// 等页面及第三方脚本都加载完再执行
(function() {
  // 拿到 #nav-home 下的链接
  var homeLink = document.querySelector('#nav-home .nav-link');
  if (!homeLink) return;

  homeLink.addEventListener('click', function(e) {
    // 阻止默认的 href 行为
    e.preventDefault();
    // 刷新页面
    window.location.reload();
  });
})();

(function(){
  var home = document.getElementById('homeLink');
  if (!home) return;
  home.addEventListener('click', function(e){
    // 1. 阻止浏览器处理 href="#" 的默认滚动
    // 2. 防止冒泡
    e.preventDefault();
    e.stopPropagation();
    // 刷新页面
    window.location.reload();
  });
})();

$(function(){
  $('#deng_ti').on('submit', function(e){
    e.preventDefault();
    // …校验用户名/密码…
    window.location.href = 'lobby.html';  // 跳到大厅
  });
});
