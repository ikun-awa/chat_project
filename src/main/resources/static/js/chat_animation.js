$('#settingsBtn').on('click', function(e) {
  $(this).css({
    'transform': 'scale(1.3)',
  });
  console.log("a");
  setTimeout(function() {
    $('#settingsBtn').css({
      'transform': 'scale(1)',
    });
    console.log("b");
  },150);
  console.log("c");
});


