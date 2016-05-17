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
        splashText: "Splash!",
        savingText: "Saving!",
        splashCss: {
        	color: "black"
        },
        loadingCss: {
        	color: "red"
        },
        savingCss: {
        	color: "orange"
        }
        // width: "100%",
        // height: "38rem"
        })
    .damuro()
    // The .damuro() object is a promise, so lets bind a done() and fail() handler.
    .done(function (data) {
            if (data.image && !/\'/.test(data.image)) {
               $('#muroimage').prop('value', data.image);
               console.log(data.image);
            }

        })
    .fail(function (data) {
            if (data.error) {
                // Something failed in saving the image.
                $('body').append('<p>All aboard the fail whale: ' + data.error + '.</p>');
            } else {
                // The user pressed "done" without making any changes.
                $('body').append("<p>Be that way then, don't edit anything.</p>");
            }
        });
});