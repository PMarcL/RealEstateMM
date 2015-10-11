var loginCookie = new LoginCookie();

function postNewProperty() {
    if(isAFieldEmpty())
    {
        $('form .card').attr('style','display:block');
        $('form .card').html("You must fill all fields");
    }
    else
    {
        ajaxPostProperty();
        $('form .card').attr('style','display:none');
    }
}


function ajaxPostProperty()
{
    var propertyAddress = {
        "streetAddress": $('#streetAddress').val(),
        "city": $('#city').val(),
        "province": $('#province').val(),
        "zipCode": $('#zipCode').val()
    };
    var formData = JSON.stringify({
        "propertyType": $('#propertyType').val(),
        "propertyAddress": propertyAddress,
        "propertyPrice": $('#price').val(),
        "propertyOwner": loginCookie.cookie(),
        "propertyStatus": "on sale",
        "propertyFeatures" : null
    });

    $.ajax({
        url: "http://localhost:8080/property",
        type: "POST",
        contentType: "application/json",
        data: formData,
        dataType: "json",
        success: function () {
            window.location.href = "editProperty.html";
        },
        error: function (httpRequest, textStatus, errorThrown) {
            alert(errorThrown + " fuck");
        }
    });
}