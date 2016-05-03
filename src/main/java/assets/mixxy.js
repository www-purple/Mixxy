/**
 * Created by MURDERBUS on 4/7/16.
 */
$(document).ready(function() {
  $('#featured-comics').slick({
    // adaptiveHeight: true,
    dots : true,
    autoplay : true,
    autoplaySpeed : 3000,
    prevArrow : null,
    nextArrow : null
  });
  $('.ui.dropdown').dropdown();

  $('.ui.checkbox').checkbox();

  $('#muro').damuro({
    loadingText : 'This is a customized loading message...',
    sandbox : '/assets/deviantart_muro_sandbox.html',
    autoload : false,
    background : "rgba(255, 255, 255, 1.0)",
    splashCss : {
      color : '#33a'
    }
  }).one('click', function() {
    $(this).damuro().open();
  }).damuro();

});