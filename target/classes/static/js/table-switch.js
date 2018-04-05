// changing tables for experiments
const showCP = function() {
    $('.consent-pending').removeClass('is-hidden');
    $('.reviewed').addClass('is-hidden');
};

const showR = function() {
    $('.consent-pending').addClass('is-hidden');
    $('.reviewed').removeClass('is-hidden');
};

const updatePNo = function() {
    var numSelected = $('input[class=pending]:checked').length;
    $('#p-selected').text(numSelected);
};

const updateRNo = function() {
    var numSelected = $('input[class=rev]:checked').length;
    $('#r-selected').text(numSelected);
};

$('.toggle-p').click(function() {
    if ($(this).text() === 'Select All') {
        $('.pending').each(function (_, e) {
            e.checked = true;
        })
    }

    if ($(this).text() === 'Unselect All') {
        $('.pending').each(function (_, e) {
            e.checked = false;
        })
    }

    if ($(this).text() === 'Select All') $(this).text('Unselect All');
    else $(this).text('Select All');

    updatePNo();
});

$('.toggle-r').click(function() {
    if ($(this).text() === 'Select All') {
        $('.rev').each(function (_, e) {
            e.checked = true;
        })
    }

    if ($(this).text() === 'Unselect All') {
        $('.rev').each(function (_, e) {
            e.checked = false;
        })
    }

    if ($(this).text() === 'Select All') $(this).text('Unselect All');
    else $(this).text('Select All');

    updateRNo();
});

$('input[type=checkbox]').change(function() {
    updatePNo();
    updateRNo();
});