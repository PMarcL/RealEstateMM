function postUser() {
    var formData = JSON.stringify({
        "pseudonym": $('#username').val(),
        "firstName": $('#firstName').val(),
        "lastName": $('#lastName').val(),
        "email": $('#email').val(),
        "phoneNumber": $('#phone').val()
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

function isAFieldEmpty()
{
    var isAFieldEmpty = false;
    $('form').find(":input").each(function () {
        if ($(this).val() === "")
        {
            isAFieldEmpty = true;
        }
    });
    if($('select').val() === null)
    {
        isAFieldEmpty = true;
    }
    return isAFieldEmpty;
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
            document.cookie = "realestateUser=" + $('#username').val();
            document.cookie = "accountType=" + $('select').val();
            window.location.href = '/RealEstateUI/index.html';
        },
        error: function (httpRequest) {
            $('form .card').attr('style','display:block');
            $('form .card').html("Invalid user information");
        }
    });
}

