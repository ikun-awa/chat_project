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

  // 一些通用参数
  const EMOJIS = ['😊','😂','😍','👍','🎉','😎','🤩','🤔','🙌','🌟'];

  // —— 表情雨：点击按钮后爆出多颗表情，缓慢掉落 ——
  document.getElementById('trigger-emoji').addEventListener('click', () => {
  for (let i = 0; i < 50; i++) {
  createFallingEmoji(
  EMOJIS[Math.floor(Math.random() * EMOJIS.length)],
  Math.random() * 100 + 'vw',
  (Math.random() + 1) + 'rem',
  (Math.random() * 3 + 4) + 's'
  );
}
});

  // —— 自动表情雨：页面加载后每隔 300ms 一颗 ——
  window.addEventListener('DOMContentLoaded', () => {
  setInterval(() => {
    createFallingEmoji(
      EMOJIS[Math.floor(Math.random() * EMOJIS.length)],
      Math.random() * 100 + 'vw',
      (Math.random() + 1) + 'rem',
      (Math.random() * 3 + 4) + 's'
    );
  }, 300);
});

  // 创建并启动一次表情下落
  function createFallingEmoji(char, left, size, duration) {
  const span = document.createElement('span');
  span.className = 'falling-emoji';
  span.textContent = char;
  span.style.left = left;
  span.style.fontSize = size;
  span.style.animationDuration = duration;
  document.body.appendChild(span);
  span.addEventListener('animationend', () => span.remove());
}

document.getElementById('trigger-fireworks').addEventListener('click', launchFireworks);

function launchFireworks() {
  let canvas = document.getElementById('fireworks-canvas');
  if (!canvas) {
    canvas = document.createElement('canvas');
    canvas.id = 'fireworks-canvas';
    document.body.appendChild(canvas);
  }
  const ctx = canvas.getContext('2d');
  canvas.width = innerWidth;
  canvas.height = innerHeight;

  // 生成粒子
  const particles = [];
  const count = 200;
  const x0 = innerWidth / 2, y0 = innerHeight / 2;
  for (let i = 0; i < count; i++) {
    const angle = Math.random() * Math.PI * 2;
    const speed = Math.random() * 5 + 2;
    particles.push({
      x: x0, y: y0,
      vx: Math.cos(angle) * speed,
      vy: Math.sin(angle) * speed,
      alpha: 1,
      color: `hsl(${Math.random() * 360},80%,60%)`
    });
  }

  // 动画循环：先清空画布，再绘制
  (function animate() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    let alive = false;
    particles.forEach(p => {
      if (p.alpha <= 0) return;
      alive = true;
      p.x += p.vx;
      p.y += p.vy;
      p.vy += 0.05;   // 重力
      p.alpha -= 0.01;

      ctx.globalAlpha = p.alpha;
      ctx.fillStyle = p.color;
      ctx.beginPath();
      ctx.arc(p.x, p.y, 3, 0, 2 * Math.PI);
      ctx.fill();
    });
    ctx.globalAlpha = 1;

    if (alive) {
      requestAnimationFrame(animate);
    } else {
      // 全部粒子消失后清理
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      canvas.remove();
    }
  })();
}

// 窗口大小变化时，同步更新画布尺寸
window.addEventListener('resize', () => {
  const c = document.getElementById('fireworks-canvas');
  if (c) {
    c.width = innerWidth;
    c.height = innerHeight;
  }
});
