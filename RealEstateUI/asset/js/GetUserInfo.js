

function getUserInfo()
{
    var loginCookie = new LoginCookie();


    $.ajax({
        url: "http://localhost:8080/user/" + loginCookie.cookie(),
        type: "GET",
        contentType: "application/json",
        data:
        {
            "Pseudonym": loginCookie.cookie()
        },
        success: function (data, status, httpResponse) {

            $('#phone').val(data.phoneNumber);
            $('#email').val(data.emailAddress);
            $('#firstName').val(data.firstName);
            $('#lastName').val(data.lastName);
            $('#username').val(data.pseudonym);
            $('#password').val(data.password);
        },
        error: function (data, textStatus, xhr) {
            $('.card').attr('style','display:block');
            $('.card').html(data.responseText);

        }
    });
}