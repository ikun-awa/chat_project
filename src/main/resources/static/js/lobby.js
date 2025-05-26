// lobby.js
$(function() {
  // 1. 获取 JWT
  const token = localStorage.getItem('jwtToken');
  if (!token) {
    // 没登录，直接跳到登录页
    return window.location.assign('/login');
  }

  // 2. 拉取当前用户信息
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
      window.location.assign('/login');
    });

  // 4. 退出登录
  $('#logoutBtn').on('click', () => {
    localStorage.removeItem('jwtToken');
    window.location.assign('/login');
  });
});
