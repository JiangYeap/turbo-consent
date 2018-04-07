// The following code is based off a toggle menu by @Bradcomp
// source: https://gist.github.com/Bradcomp/a9ef2ef322a8e8017443b626208999c1
$(document).ready(function() {
    $('.navbar-burger').click(function() {
        $(this).toggleClass('is-active');
        $('.navbar-menu').toggleClass('is-active');
    });

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

    $('.close-success').click(function() {
        $('.success-flag').addClass('is-hidden');
    });

    $('.close-error').click(function() {
        $('.error-flag').addClass('is-hidden');
    });
});
