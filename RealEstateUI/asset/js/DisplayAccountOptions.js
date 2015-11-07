var userCookie = new LoginCookie();
var accountCookie = new AccountTypeCookie();
var tokenCookie = new TokenCookie();

$(document).ready(displayUserOptions());


function displayUserOptions() {
    loggedInDisplay();
    accountTypeOptions();

}

function accountTypeOptions() {
    var accountType = accountCookie.cookie();

    if (accountType == 'buyer') {
        toggleElementVisibilityWithClass('buyer-option','inline-block');
        toggleElementVisibilityWithClass('seller-option','none');
    }

    else if (accountType == 'seller') {
        toggleElementVisibilityWithClass('buyer-option','none');
        toggleElementVisibilityWithClass('seller-option','inline-block');
    }
    else {
        toggleElementVisibilityWithClass('buyer-option','none');
        toggleElementVisibilityWithClass('seller-option','none');
    }
}

function loggedInDisplay() {
    if (userCookie.isUserPresent()) {
        $('.login').attr("style", "display:none");
        $('.signup').attr("style", "display:none");
        $('.signout').attr("style", "display:inline-block");
    }
    else {
        $('.login').attr("style", "display:inline-block");
        $('.signup').attr("style", "display:inline-block");
        $('.signout').attr("style", "display:none");
    }
}

function signout() {
    userCookie.delete();
    accountCookie.delete();
    $.ajax({
        url: "http://localhost:8080/user/" + tokenCookie.cookie(),
        type: "POST",
        contentType: "application/json",
        dataType: "json"
    });
    displayUserOptions();
    window.location.href = "index.html";
}

function toggleElementVisibilityWithClass(elemClass, visibility) {

    var elements = new Array();
    elements = document.getElementsByClassName(elemClass);
    for (var i = 0; i < elements.length; i += 1) {
        elements[i].style.display = visibility;
    }
}
