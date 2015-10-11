var loginCookie = new LoginCookie();

function editProperty() {
    if(isAFieldEmpty())
    {
        $('form .card').attr('style','display:block');
        $('form .card').html("You must fill all fields");
    }
    else
    {
        ajaxPutProperty();
        $('form .card').attr('style','display:none');
    }
}

function ajaxPutProperty() {
    var propertyAddress = {
        "streetAddress": $('#streetAddress').val(),
        "city": $('#city').val(),
        "province": $('#province').val(),
        "zipCode": $('#zipCode').val()
    };

    var propertyFeatures = {
        "numberOfBathrooms": $('#bathrooms').val(),
        "numberOfBedrooms": $('#bedrooms').val(),
        "totalNumberOfRooms": $('#totalNumberRooms').val(),
        "numberOfLevels": $('#levels').val(),
        "lotDimensions": $('#lotDimensions').val(),
        "yearOfConstruction": $('#yearOfConstruction').val(),
        "livingSpaceArea": $('#livingSpaceArea').val(),
        "backyardDirection": $('#backyardFaces').val(),
        "description": $('#description').val()
    };

    var formData = JSON.stringify({
        "propertyType": $('#propertyType').val(),
        "propertyAddress": propertyAddress,
        "propertyPrice": $('#price').val(),
        "propertyOwner": loginCookie.cookie(),
        "propertyStatus": "on sale",
        "propertyFeatures" : propertyFeatures
    });

    $.ajax({
        url: "http://localhost:8080/property",
        type: "PUT",
        contentType: "application/json",
        data: formData,
        dataType: "json",
        success: function () {
            window.location.href = "index.html";
        },
        error: function (httpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}