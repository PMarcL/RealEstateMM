function postUser() {
    var formData = JSON.stringify({
        "pseudonym": $('#username').val(),
        "password" : $('#password').val(),
        "firstName": $('#firstName').val(),
        "lastName": $('#lastName').val(),
        "email": $('#email').val(),
        "phoneNumber": $('#phone').val(),
        "userType" : $('select').val().toUpperCase()
    });
    if(isAFieldEmpty())
    {
        $('form .card').attr('style','display:block');
        $('form .card').html("You must fill all fields");
    }
    else
    {
        ajaxPostUser(formData);
        $('form .card').attr('style','display:none');
    }
}

function ajaxPostUser(formData)
{
    $.ajax({
        url: "http://localhost:8080/user",
        type: "POST",
        contentType: "application/json",
        data: formData,
        dataType: "json",
        success: function (data, status, httpResponse) {
            window.location.href = 'confirmMail.html';
        },
        error: function (httpRequest) {
            $('form .card').attr('style','display:block');
            $('form .card').html("Invalid user information");
        }
    });
}

