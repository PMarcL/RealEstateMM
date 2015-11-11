$(document).ready(function(){
    getProperties('');

    $('#orderBy').change(function(){
        var queryParam = '?orderBy=' + $(orderBy).val();
        $('#propertylist').empty();
        getProperties(queryParam);
    });
});

function getProperties(param) {
    $.ajax({
        url: "http://localhost:8080/property/search" + param + "/" + new TokenCookie().cookie(),
        type: "GET",
        contentType: "application/json",

        success: function (data, status, httpResponse) {
            var propertiesJSON = JSON.parse(httpResponse.responseText);
            createHtmlPropertyList(propertiesJSON);
        },
        error: function (data, textStatus, xhr) {
            console.log(data.responseText);
        }
    });
}


function createHtmlPropertyList(propertiesJSON) {
    var buffer = "";
    $.each(propertiesJSON, function () {

        buffer = buffer
            + "<li class='propertylist-item'><div class='type'>" + this.propertyType + "</div>"
            + "<div class='price'>" + "C $" +this.propertyPrice + "</div>"
            + "<div class='streetAddress'>" + this.propertyAddress.streetAddress + "</div>"
            + "<div class='city'>" + this.propertyAddress.city + "</div>"
            + "<div class='province'>" + this.propertyAddress.province + "</div>"
            + "<div class='zipCode'>" + this.propertyAddress.zipCode + "</div>"
            + "<div class='status'>" + this.propertyStatus + "</div>"
            + "<div class='owner'>" + this.propertyOwner + "</div>"
            + "<div class='features'>  "
            + "<div class='featureTitle'>Features: </div>"
            +   "<div class='featuresAttribute'> Number of bathrooms : " + this.propertyFeatures.numberOfBathrooms + "</div>"
            +   "<div class='featuresAttribute'> Number of bedrooms : " + this.propertyFeatures.numberOfBedrooms + "</div>"
            +   "<div class='featuresAttribute'> Total number of rooms : " + this.propertyFeatures.totalNumberOfRooms + "</div>"
            +   "<div class='featuresAttribute'> Number of levels : " + this.propertyFeatures.numberOfLevels + "</div>"
            +   "<div class='featuresAttribute'> Lot dimension : " + this.propertyFeatures.lotDimensions + "</div>"
            +   "<div class='featuresAttribute'> Year of construction : " + this.propertyFeatures.yearOfConstruction + "</div>"
            +   "<div class='featuresAttribute'> Living space area : " + this.propertyFeatures.livingSpaceArea + "</div>"
            +   "<div class='featuresAttribute'> Backyard direction : " + this.propertyFeatures.backyardDirection + "</div>"
            +   "<div class='featuresAttribute'> Description : " + this.propertyFeatures.description + "</div>"
            +"</div><br>"
            + "<li>";
        $('#propertylist').html(buffer);
    });
}