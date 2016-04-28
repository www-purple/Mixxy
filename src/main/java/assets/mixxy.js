/**
 * Created by MURDERBUS on 4/7/16.
 */
$(document).ready(function(){
    $('#featured-comics').slick({
        //adaptiveHeight: true,
        dots: true,
        autoplay: true,
        autoplaySpeed: 3000,
        prevArrow: null,
        nextArrow: null
    });
    $('.ui.dropdown')
        .dropdown()
    ;
    $('#muro').damuro({
        sandbox: 'deviantart_muro_sandbox.html',
        background: '/assets/images/rubbellslsss.jpg' 
    });
});