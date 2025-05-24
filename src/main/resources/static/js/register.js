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
          window.location.assign('../lobby/lobby.html');
        },500);
      }
      form.classList.add('was-validated');
    }, false);
  });
})();
