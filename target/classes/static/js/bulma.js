// The following code is based off a toggle menu by @Bradcomp
// source: https://gist.github.com/Bradcomp/a9ef2ef322a8e8017443b626208999c1


// const closeModal = function() {
//     let modal = document.querySelector('.modal');
//     modal.classList.remove('is-active');
// };
//
// const openModal = function() {
//     let modal = document.querySelector('.modal');
//     modal.classList.add('is-active');
// };

$(document).ready(function() {
    $('.navbar-burger').click(function() {
        $(this).toggleClass('is-active');
        $('.navbar-menu').toggleClass('is-active');
    });

    // (function() {
    //     let burger = document.querySelector('.nav-burger, .burger');
    //     let menu = document.querySelector('.navbar-menu');
    //     burger.addEventListener('click', function() {
    //         burger.classList.toggle('is-active');
    //         menu.classList.toggle('is-active');
    //     });
    // })();

    $('.close-modal').click(function() {
        let modal = $('.modal');
        modal.removeClass('is-active');
    });

    $('.open-modal').click(function() {
        let modal = $('.modal');
        modal.addClass('is-active');
    });

    $('.back').click(function() {
        window.history.back()
    });
});
