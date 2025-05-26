$(document).ready(function () {
  //导航添加阴影
  $('#navbar').css({
    'box-shadow': '0px 0px 25px #6c6c6c',
  });
  //头像旋转360度
  $('.zhu_tou').css({
    'rotate': '360deg',
  });
  //主页框下拉
  $('#kai_shi').animate({
    'height': '+=100px',
    'opacity': 1,
    'backgroundColor': 'rgba(255, 23, 23, 0.5)',
  },500,'easeOutSine');
  /**
   * 可选的
   * easeOutQuad
   * easeOutCubic
   * easeOutQuart
   */
  //主页框的图片
  setTimeout(function () {
    $('#kun').css({
      'transition': 'all .2s ease-in-out',
      'opacity': 1,
    });
    setTimeout(function () {
      $('#kun').css({
        'transition': 'all 0.5s ease-in-out',
      });
    },10);
  },450);
  //3色块
  setTimeout(function () {
    $('#kuai_1').css({
      'transform': 'translateY(-30px)',
      'opacity': 1,
    });
  },800);
  setTimeout(function () {
    $('#kuai_2').css({
      'transform': 'translateY(-60px)',
      'opacity': 1,
    });
  },1000);
  setTimeout(function () {
    $('#kuai_3').css({
      'transform': 'translateY(-10px)',
      'opacity': 1,
    });
  },1200);
});
//注册表单装饰bs图标旋转
$('#aa2').click(function () {
  setTimeout(function () {
    $('#zhu_biao').addClass('animate').one('animationend', function () {
      $(this).removeClass('animate');
    });
  }, 500);
});
//注册表单年龄实时更新
$('#age_z').on('input', function () {
  $('#age_label').text('Your age are: ' + $(this).val());
});


  document.querySelectorAll('.emoji-btn').forEach(el => {
  el.addEventListener('click', () => {
    alert('你点击了表情：' + el.dataset.emoji);
  });
});
// 表情雨：点击按钮后爆出多颗表情，缓慢掉落
document.getElementById('trigger-emoji').addEventListener('click', () => {
  const emojis = ['😊','😂','😍','👍','🎉'];
  for (let i = 0; i < 50; i++) {
    const span = document.createElement('span');
    span.className = 'falling-emoji';
    span.textContent = emojis[Math.floor(Math.random() * emojis.length)];
    span.style.left = Math.random() * 100 + 'vw';
    span.style.fontSize = (Math.random() * 1 + 1) + 'rem';
    span.style.animationDuration = (Math.random() * 3 + 4) + 's';
    document.body.appendChild(span);
    span.addEventListener('animationend', () => span.remove());
  }
});

// 烟花：点击按钮触发浪漫烟花满屏绽放
document.getElementById('trigger-fireworks').addEventListener('click', launchFireworks);

function launchFireworks() {
  // 创建 canvas
  let canvas = document.getElementById('fireworks-canvas');
  if (!canvas) {
    canvas = document.createElement('canvas');
    canvas.id = 'fireworks-canvas';
    document.body.appendChild(canvas);
  }
  const ctx = canvas.getContext('2d');
  canvas.width = innerWidth;
  canvas.height = innerHeight;

  // 生成粒子群
  const particles = [];
  const count = 200;
  const x0 = innerWidth/2, y0 = innerHeight/2;
  for (let i = 0; i < count; i++) {
    const angle = Math.random() * Math.PI * 2;
    const speed = Math.random() * 5 + 2;
    particles.push({
      x: x0, y: y0,
      vx: Math.cos(angle) * speed,
      vy: Math.sin(angle) * speed,
      alpha: 1,
      color: `hsl(${Math.random()*360},80%,60%)`
    });
  }

  // 动画循环
  function animate() {
    ctx.fillStyle = 'rgba(0,0,0,0.1)';
    ctx.fillRect(0, 0, canvas.width, canvas.height);

    particles.forEach(p => {
      p.x += p.vx; p.y += p.vy;
      p.vy += 0.05; // 重力
      p.alpha -= 0.01;
      if (p.alpha <= 0) return;
      ctx.globalAlpha = p.alpha;
      ctx.fillStyle = p.color;
      ctx.beginPath();
      ctx.arc(p.x, p.y, 3, 0, 2*Math.PI);
      ctx.fill();
    });
    ctx.globalAlpha = 1;

    if (particles.some(p => p.alpha > 0)) {
      requestAnimationFrame(animate);
    } else {
      // 清理
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      canvas.remove();
    }
  }
  animate();
}

// 窗口大小变化，更新 canvas
window.addEventListener('resize', () => {
  const c = document.getElementById('fireworks-canvas');
  if (c) {
    c.width = innerWidth; c.height = innerHeight;
  }
});
// 页面 DOM 就绪后，启动表情雨
window.addEventListener('DOMContentLoaded', () => {
  const emojis = ['😊','😂','😍','👍','🎉','😎','🤩','🤔','🙌','🌟'];
  // 每隔 300ms 生成一颗表情
  setInterval(() => {
    const span = document.createElement('span');
    span.className = 'falling-emoji';
    // 随机选 emoji
    span.textContent = emojis[Math.floor(Math.random() * emojis.length)];
    // 随机水平位置
    span.style.left = (Math.random() * 100) + 'vw';
    // 随机大小：1rem~2rem
    const size = Math.random() + 1;
    span.style.fontSize = size + 'rem';
    // 随机动画时长：4s~7s
    span.style.animationDuration = (Math.random() * 3 + 4) + 's';
    document.body.appendChild(span);
    // 动画完成后自动移除
    span.addEventListener('animationend', () => span.remove());
  }, 300);
});


