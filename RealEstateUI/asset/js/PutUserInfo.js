var change = false;
var tokenCookie = new TokenCookie();

function putUserInfo() {

    var formData = JSON.stringify({
        "firstName": $('#firstName').val(),
        "lastName": $('#lastName').val(),
        "email": $('#email').val(),
        "phoneNumber": $('#phone').val(),
        "pseudonym": $('#username').val(),
        "password" : $('#password').val(),
        "userType" : "BUYER"
    });

    if(isAFieldEmpty())
    {
        $('form .card').attr('style','display:block');
        $('form .card').html("You must fill all fields");
    }
    else
    {
        ajaxPutUser(formData);
        $('form .card').attr('style','display:none');
    }
}

function ajaxPutUser(formData)
{
    $.ajax({
        url: "http://localhost:8080/user/" + tokenCookie.cookie(),
        type: "PUT",
        contentType: "application/json",
        data: formData,
        success: function () {
            window.location.href = 'index.html';
        },
        error: function (httpRequest) {
            $('form .card').attr('style','display:block');
            $('form .card').html("Invalid user information");
        }
    });
}