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
    $('.ui.checkbox')
        .checkbox()
    ;
});