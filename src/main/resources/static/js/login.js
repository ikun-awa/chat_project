//登录处理
(function () {
  'use strict';
  const form = document.getElementById('deng_ti');
  form.addEventListener('submit', async function (event) {
    event.preventDefault();
    event.stopPropagation();
    console.log('提交');
    if (!form.checkValidity()) {
      console.log('非法表单');
    } else {
      console.log('合法');
      const spinner = document.getElementById('spin_z');
      if (spinner) spinner.style.display = 'inline-block';

      try {
        const data = new URLSearchParams(new FormData(form));
        const resp = await fetch('/login', {
          method: 'POST',
          body: data
        });

        if (resp.ok) {
          // 登录成功，跳转大堂
          window.location.assign('../lobby/lobby.html');
        } else {
          // 4. 修正模板字符串
          const msg = await resp.text();
          alert(`登录失败：${msg}`);
        }
      } catch (err) {
        console.error(err);
        alert('网络错误，请稍后再试');
      } finally {
        if (spinner) spinner.style.display = 'none';
      }
    }

    form.classList.add('was-validated');
  }, false);

})();






/*
//登录信息处理
(async function () {
  'use strict';
  const deng_ti = document.getElementById('deng_ti');
  deng_ti.querySelectorAll('.needs-validation').forEach(formEl => {
    formEl.addEventListener('submit', async event => {
      event.preventDefault();
      //event.stopPropagation();
      console.log('提交');
      if (!formEl.checkValidity()) {
        console.log('非法表单');
      } else {
        $('#spin_d').show();
        console.log('合法')
        try {
          const data = new URLSearchParams(new FormData(formEl));
          const resp = await fetch('/login', { method: 'POST', body: data });
          if (resp.ok) {
            window.location.assign('../lobby/lobby.html');
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


 */
