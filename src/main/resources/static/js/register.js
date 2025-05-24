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

const usernameInput = document.getElementById('name_z');
const feedback = document.getElementById('usernameFeedback');

usernameInput.addEventListener('blur', async () => {
  const username = usernameInput.value.trim();
  if (!username) return;
  const resp = await fetch(`/check-username?username=${encodeURIComponent(username)}`);
  const {exists} = await resp.json();
  if (exists) {
    feedback.textContent = '用户名已被占用';
    usernameInput.classList.add('is-invalid');
  } else {
    feedback.textContent = '用户名可用';
    usernameInput.classList.remove('is-invalid');
    usernameInput.classList.add('is-valid');
  }
});

//年龄更新
$('#age_z').on('input', function () {
  $('#age_label').text('Your age are: ' + $(this).val());
});

//密码切换
const passwordInput = document.getElementById('pass_z');
const toggleBtn     = document.getElementById('pass_z_toggle');
toggleBtn.addEventListener('click', () => {
  const isHidden = passwordInput.type === 'password';
  passwordInput.type = isHidden ? 'text' : 'password';
  toggleBtn.innerHTML = isHidden
    //睁眼
    ? `<svg xmlns="http://www.w3.org/2000/svg" width="1.3em" height="1.3em" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
        <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z"/>
        <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0"/>
       </svg>`
    //闭眼
    : `<svg xmlns="http://www.w3.org/2000/svg" width="1.3em" height="1.3em" fill="currentColor" class="bi bi-eye-slash" viewBox="0 0 16 16">
        <path d="M13.359 11.238C15.06 9.72 16 8 16 8s-3-5.5-8-5.5a7 7 0 0 0-2.79.588l.77.771A6 6 0 0 1 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755q-.247.248-.517.486z"/>
        <path d="M11.297 9.176a3.5 3.5 0 0 0-4.474-4.474l.823.823a2.5 2.5 0 0 1 2.829 2.829zm-2.943 1.299.822.822a3.5 3.5 0 0 1-4.474-4.474l.823.823a2.5 2.5 0 0 0 2.829 2.829"/>
        <path d="M3.35 5.47q-.27.24-.518.487A13 13 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7 7 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12z"/>
       </svg>`;
});

