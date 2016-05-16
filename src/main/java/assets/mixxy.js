/**
 * Little JS stuff for Mixxy
 * Created by Clearydude on 4/7/16.
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

    $('.ui.dropdown').dropdown({
            allowAdditions: true
    });

    $('.ui.checkbox')
        .checkbox()
    ;

    $('.ui.accordion')
        .accordion()
    ;
    
    $('#muro').damuro({
        loadingText: 'Loading Muro',
        sandbox:     '/assets/deviantart_muro_sandbox.html',
        autoload: true,
        background: "rgba(255, 255, 255, 1.0)",
        // width: "100%",
        // height: "38rem"
        })
    .one('click', function () {
        $(this).damuro().open();
    })
    .query('image', null, function (data) {
        if (data.image) {
            // Send it to my server as an autosave.
        }
    });
});