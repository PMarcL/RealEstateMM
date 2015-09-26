
function getUser()
{
    $.ajax({
        url: "http://localhost:8080/user?pseudonym=" + $("#username").val() + "&password=" + $("#password").val(),
        type: "GET",
        contentType: "application/json",
        success: function (data, status, httpResponse) {
            alert("success");
        },
        error: function (httpRequest) {
            alert("failed");
        }
    });
}
