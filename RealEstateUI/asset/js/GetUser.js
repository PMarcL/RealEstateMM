
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

            var accountTypeCookie = new AccountTypeCookie();
            var usertype = JSON.parse(httpResponse.responseText).userType;
            accountTypeCookie.setAccountTypeCookie(usertype);

            var tokenCookie = new TokenCookie();
            var userToken = JSON.parse(httpResponse.responseText).token;
            tokenCookie.setTokenCookie(userToken);

            window.location.href = 'index.html';
        },
        error: function (data, textStatus, xhr) {
            $('.card').attr('style','display:block');
            $('.card').html(data.responseText);
        }
    });
}
