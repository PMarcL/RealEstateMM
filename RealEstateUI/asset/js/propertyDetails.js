$(document).ready(function(){
    getProperty();
});

function getProperty(param) {
    $.ajax({
        url: "http://localhost:8080/property/atAddress/" + new TokenCookie().cookie(),
        type: "POST",
        contentType: "application/json",
        data: $.cookie("currentPropertyAddress"),
        dataType: "json",
        success: function (data, status, httpResponse) {
            var propertyJSON = JSON.parse(httpResponse.responseText);
            createHtmlProperty(propertyJSON);
        },
        error: function (data, textStatus, xhr) {
            console.log(data.responseText);
        }
    });
}

function createHtmlProperty(json) {
    console.log("called");
    var buffer = "";
    buffer = buffer
        + "<h3 class='type'>" + json.propertyType + "</h3>"
        + "<div class='price'>" + "C $" + json.propertyPrice + "</div>"
        + "<div class='streetAddress'>" + json.propertyAddress.streetAddress + "</div>"
        + "<div class='city'>" + json.propertyAddress.city + "</div>"
        + "<div class='province'>" + json.propertyAddress.province + "</div>"
        + "<div class='zipCode'>" + json.propertyAddress.zipCode + "</div>"
        + "<div class='status'>" + json.propertyStatus + "</div>"
        + "<div class='owner'>" + json.propertyOwner + "</div>"
        + "<div class='featureTitle'>Features: </div>"
        +   "<div class='featuresAttribute'> Number of bathrooms : <span class='bathrooms'>" + json.propertyFeatures.numberOfBathrooms + "</span></div>"
        +   "<div class='featuresAttribute'> Number of bedrooms : <span class='bedrooms'>" + json.propertyFeatures.numberOfBedrooms + "</span></div>"
        +   "<div class='featuresAttribute'> Total number of rooms : <span class='totalRooms'>" + json.propertyFeatures.totalNumberOfRooms + "</span></div>"
        +   "<div class='featuresAttribute'> Number of levels :<span class='levels'>" + json.propertyFeatures.numberOfLevels + "</span></div>"
        +   "<div class='featuresAttribute'> Lot dimension : <span class='lotDimensions'>" + json.propertyFeatures.lotDimensions + "</span></div>"
        +   "<div class='featuresAttribute'> Year of construction : <span class='yearOfConstruction'>" + json.propertyFeatures.yearOfConstruction + "<span></div>"
        +   "<div class='featuresAttribute'> Living space area : <span class='livingSpaceArea'>" + json.propertyFeatures.livingSpaceArea + "</span></div>"
        +   "<div class='featuresAttribute'> Backyard direction : <span class='backyardDirection'>" + json.propertyFeatures.backyardDirection + "</span></div>"
        +   "<div class='featuresAttribute'> Description : <span class='description'>" + json.propertyFeatures.description + "</spane></div>"
        +"</div>";

    $('#detailsContainer').html(buffer);
}