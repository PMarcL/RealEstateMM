var loginCookie = new LoginCookie();

function postNewProperty() {

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
            alert("yay");
        },
        error: function (httpRequest, textStatus, errorThrown) {
            alert(errorThrown + " fuck");
        }
    });
}
