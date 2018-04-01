// The following code is based off a toggle menu by @Bradcomp
// source: https://gist.github.com/Bradcomp/a9ef2ef322a8e8017443b626208999c1
(function() {
    var burger = document.querySelector('.nav-burger, .burger');
    var menu = document.querySelector('.navbar-menu');
    burger.addEventListener('click', function() {
        burger.classList.toggle('is-active');
        menu.classList.toggle('is-active');
    });
})();

const closeModal = function() {
    var modal = document.querySelector('.modal');
    modal.classList.remove('is-active');
};

const openModal = function() {
    var modal = document.querySelector('.modal');
    modal.classList.add('is-active');
};