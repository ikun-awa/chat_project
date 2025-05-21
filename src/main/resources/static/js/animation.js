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
