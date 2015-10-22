
function putProperty(propertyDetails) {
    propertyDetails.propertyFeatures = {
        "numberOfBathrooms" : $('#bathrooms').val(),
        "numberOfBedrooms" : $('#bedrooms').val(),
        "totalNumberOfRooms" : $('#totalNumberRooms').val(),
        "numberOfLevels" : $('#levels').val(),
        "lotDimensions" : $('#lotDimensions').val(),
        "yearOfConstruction": $('#yearOfConstruction').val(),
        "livingSpaceArea": $('#livingSpaceArea').val(),
        "backyardDirection": $('#backyardFaces').val(),
        "description" : $('#description').val()
    };
    $.ajax({
        url: "http://localhost:8080/property",
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(propertyDetails),
        dataType: "json",
        success: function () {
            window.location.href = 'index.html';
        },
        error: function (httpRequest, textStatus, errorThrown) {
            $('form .card').attr('style','display:block');
            $('form .card').html(textStatus.responseText);
        }
    });
}
