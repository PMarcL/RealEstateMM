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
        "streetNumber": "25",
        "streetName": "bobbt",
        "city": "Quebec",
        "province": "Quebec",
        "zipCode": "G1C7K7"
    };
    var formData = JSON.stringify({
        "propertyType": "House",
        "propertyAddressInformations": propertyAddress,
        "propertyPrice": "20",
        "propertyOwner": loginCookie.cookie(),
        "propertyStatus": "On Sale"
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