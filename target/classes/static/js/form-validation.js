// prevents illegal submit
$(document).ready(function() {
    $('button[type=submit]').click(function() {
        checked = $('input[type=radio]:checked').length;

        if(!checked) {
            alert("You must select a consent option to submit.");
            return false;
        }
    });
});
