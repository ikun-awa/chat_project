document.addEventListener('DOMContentLoaded', () => {
  const trailContainer = document.getElementById('trail-container');
  const particles = [];

  // 监听鼠标移动事件
  document.addEventListener('mousemove', (e) => {
    // 在鼠标位置创建粒子
    createParticle(e.clientX, e.clientY);
  });

  // 创建粒子
  function createParticle(x, y) {
    const particle = document.createElement('div');
    particle.classList.add('particle');
    particle.style.left = `${x}px`;
    particle.style.top = `${y}px`;
    // 使用随机颜色或固定颜色
    particle.style.backgroundColor = getRandomColor();
    trailContainer.appendChild(particle);
    particles.push(particle);

    // 几乎立即给粒子添加淡出动画
    setTimeout(() => {
      particle.classList.add('fade');
      // 动画结束后移除粒子
      particle.addEventListener('animationend', () => {
        particle.remove();
        particles.splice(particles.indexOf(particle), 1);
      });
    }, 100); // 非常短的延迟，几乎立即开始淡出
  }

  // 获取随机颜色
  function getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  }

  // 定期清理，动画结束后粒子会自动移除
  setInterval(() => {
    particles.forEach(particle => {
      if (particle.classList.contains('fade')) {
        particle.remove();
        particles.splice(particles.indexOf(particle), 1);
      }
    });
  }, 1000);
});
