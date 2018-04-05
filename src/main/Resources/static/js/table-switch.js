// changing tables for experiments
const showCP = function() {
    $('.consent-pending').removeClass('is-hidden');
    $('.reviewed').addClass('is-hidden');
};

const showR = function() {
    $('.consent-pending').addClass('is-hidden');
    $('.reviewed').removeClass('is-hidden');
};

const updateLabel = function() {
    let tName = $('#t-switch').is(':checked') ? 'Reviewed Experiments' : 'Unreviewed Experiments';
    $('.current-table').text(tName);
};

const updatePNo = function() {
    let numSelected = $('input[class=pending]:checked').length;
    $('#p-selected').text(numSelected);
};

const updateRNo = function() {
    let numSelected = $('input[class=rev]:checked').length;
    $('#r-selected').text(numSelected);
};

window.onpageshow = function(event) {
    $('#t-switch').is(':checked') ? showR() : showCP();
};

$(document).ready(function() {
    updateLabel();
    updatePNo();
    updateRNo();

    $('#t-switch').change(function(){
        $(this).is(':checked') ? showR() : showCP();
        updateLabel();
    });

    $('.toggle-p').click(function() {
        let allPending = $('.pending');

        if ($(this).text() === 'Select All')
            allPending.each((_, e) => e.checked = true);

        if ($(this).text() === 'Unselect All')
            allPending.each((_, e) => e.checked = false);

        if ($(this).text() === 'Select All') $(this).text('Unselect All');

        else $(this).text('Select All');

        updatePNo();
    });

    $('.toggle-r').click(function() {
        let allRev = $('.rev');

        if ($(this).text() === 'Select All')
            allRev.each((_, e) => e.checked = true);

        if ($(this).text() === 'Unselect All')
            allRev.each((_, e) => e.checked = false);

        if ($(this).text() === 'Select All') $(this).text('Unselect All');

        else $(this).text('Select All');

        updateRNo();
    });

    $('input[type=checkbox]').change(function() {
        updatePNo();
        updateRNo();
    });
});
