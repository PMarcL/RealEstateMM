


function postUser()
{
    var formData = {pseudonym:"testuser",firstName:"John",lastName:"Bartholomew",email:"johnb@example.com",phoneNumber:"418-666-6666"};

    $.ajax({
        url : "localhost:8080/user",
        type: "POST",
        contentType: "application/json",
        data : formData,
        dataType:"json",
        success: function()
        {
            alert("yay");
        },
        error: function (httpRequest, textStatus, errorThrown)
        {
            alert(errorThrown + " fuck");
        }
    });
}