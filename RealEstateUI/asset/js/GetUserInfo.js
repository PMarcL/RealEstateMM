
function getUserInfo()
{
    var tokenCookie = new TokenCookie();

    $.ajax({
        url: "http://localhost:8080/user/" + tokenCookie.cookie(),
        type: "GET",
        contentType: "application/json",
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