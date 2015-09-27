
function getUser()
{
    $.ajax({
        url: "http://localhost:8080/user",
        type: "GET",
        contentType: "application/json",
        data:
        {
          "username": $("#username").val(),
          "password": $("#password").val()
        },
        success: function (data, status, httpResponse) {
            var loginCookie = new LoginCookie();
            loginCookie.setUsernameCookie($('#username').val());
            window.location.href = '/RealEstateUI/index.html';

        },
        error: function (httpRequest) {
            $('.card').attr('style','display:block');
        }
    });
}
