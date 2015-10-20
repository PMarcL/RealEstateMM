

function getUserInfo()
{
    var re = new RegExp(name + "=([^;]+)");
    var value = re.exec(document.cookie);

    $.ajax({
        url: "http://localhost:8080/user/" + value[1],
        type: "GET",
        contentType: "application/json",
        data:
        {
            "Pseudonym": value[1]
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