

function getOwnersProperties() {
    $.ajax({
        url: "http://localhost:8080/property/" + new TokenCookie().cookie(),
        type: "GET",
        contentType: "application/json",

        success: function (data, status, httpResponse) {
            var propertiesJSON = JSON.parse(httpResponse.responseText);
            createHtmlEditPropertyList(propertiesJSON);
        },
        error: function (data, textStatus, xhr) {
            alert(data.responseText);
        }
    });
}

function createHtmlEditPropertyList(propertiesJSON) {
    var buffer = "";
    $.each(propertiesJSON, function () {

        buffer+= "<li class='ownerpropertylist-item'><div class='type'>" + this.propertyType + "</div>"
            + "<div>C $ <span class='price'>" +this.propertyPrice + "</span></div>"
            + "<div class='streetAddress'>" + this.propertyAddress.streetAddress + "</div>"
            + "<div class='city'>" + this.propertyAddress.city + "</div>"
            + "<div class='province'>" + this.propertyAddress.province + "</div>"
            + "<div class='zipCode'>" + this.propertyAddress.zipCode + "</div>"
            + "<div class='status'>" + this.propertyStatus + "</div>"
            + "<div class='owner'>" + this.propertyOwner + "<br></div>"
            + "<div class='features'> "
            + "<div class='featureTitle'>Features: </div>"
            +   "<div class='featuresAttribute'> Number of bathrooms : <span class='bathrooms'>" + this.propertyFeatures.numberOfBathrooms + "</span></div>"
            +   "<div class='featuresAttribute'> Number of bedrooms : <span class='bedrooms'>" + this.propertyFeatures.numberOfBedrooms + "</span></div>"
            +   "<div class='featuresAttribute'> Total number of rooms : <span class='totalRooms'>" + this.propertyFeatures.totalNumberOfRooms + "</span></div>"
            +   "<div class='featuresAttribute'> Number of levels :<span class='levels'>" + this.propertyFeatures.numberOfLevels + "</span></div>"
            +   "<div class='featuresAttribute'> Lot dimension : <span class='lotDimensions'>" + this.propertyFeatures.lotDimensions + "</span></div>"
            +   "<div class='featuresAttribute'> Year of construction : <span class='yearOfConstruction'>" + this.propertyFeatures.yearOfConstruction + "<span></div>"
            +   "<div class='featuresAttribute'> Living space area : <span class='livingSpaceArea'>" + this.propertyFeatures.livingSpaceArea + "</span></div>"
            +   "<div class='featuresAttribute'> Backyard direction : <span class='backyardDirection'>" + this.propertyFeatures.backyardDirection + "</span></div>"
            +   "<div class='featuresAttribute'> Description : <span class='description'>" + this.propertyFeatures.description + "</spane></div>"

            +"</div><br>"
            + "<div class='confirm-sale'><a class='waves-effect waves-light btn editProperty' >Edit property</a></div>"
            + "<li>";
        $('#properties-container').html(buffer);
    });
}