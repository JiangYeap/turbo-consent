if (JSON && JSON.stringify && JSON.parse) var Session = Session || (function() {
    // window object
    var win = window.top || window;

    // session store
    var store = (win.name ? JSON.parse(win.name) : {});

    // save store on page unload
    function Save() {
        win.name = JSON.stringify(store);
    };

    // page unload event
    if (window.addEventListener) window.addEventListener("unload", Save, false);
    else if (window.attachEvent) window.attachEvent("onunload", Save);
    else window.onunload = Save;

    // public methods
    return {
        // set a session variable
        set: function(name, value) {
            store[name] = value;
        },

        // get a session value
        get: function(name) {
            return (store[name] ? store[name] : undefined);
        },

        // clear session
        clear: function() { store = {}; },

        // dump session data
        dump: function() { return JSON.stringify(store); }
    };
})();

// <![CDATA[

// initialize application defaults
var counter = Session.get("counter") || {
    visits: 0,
    time: []
};

// onload
window.onload = function() {
    // update previous visits
    var d = new Date();
    counter.visits++;
    counter.time.push(Pad(d.getHours()) + ":" + Pad(d.getMinutes()) + ":" + Pad(d.getSeconds()));
    if (counter.time.length > 10) counter.time = counter.time.slice(1);

    var modal = document.querySelector('.modal');
    if (counter.visits > 1) modal.classList.remove('is-active');

    // store value in session
    Session.set("counter", counter);
};

// add leading zeros
function Pad(n) {
    n = "00" + n;
    return n.substr(n.length-2);
}
// ]]>

$(document).ready(function () {
    $('button[type=submit]').click(function() {
        checked1 = $('input[type=checkbox]:checked').length;
        checked2 = $('input[type=radio]:checked').length;

        if(!checked1 || !checked2) {
            alert("You must select at least one experiment and exactly one consent option.");
            return false;
        }

    });
});

const showCP = function() {
    $('.consent-pending').removeClass('is-hidden');
    $('.reviewed').addClass('is-hidden');
}

const showR = function() {
    $('.consent-pending').addClass('is-hidden');
    $('.reviewed').removeClass('is-hidden');
}

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

const toggleReviewed = function(source) {
    checkboxes = document.querySelectorAll('.reviewed');
    for (var i = 0, n = checkboxes.length; i<n; i++) checkboxes[i].checked = source.checked;
};