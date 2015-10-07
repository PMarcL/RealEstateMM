$(document).ready(getProperties());

function getProperties() {
    $.ajax({
        url: "http://localhost:8080/property",
        type: "GET",
        contentType: "application/json",

        success: function (data, status, httpResponse) {
            var propertiesJSON = JSON.parse(httpResponse.responseText);
            createHtmlPropertyList(propertiesJSON);
        },
        error: function (data, textStatus, xhr) {
            alert(data.responseText);
        }
    });
}


function createHtmlPropertyList(propertiesJSON) {
    var buffer = "";
    $.each(propertiesJSON, function () {

        buffer+= "<li class='propertylist-item'><div class='type'>" + this.propertyType + "</div>"
            + "<div class='price'>" + "C $" +this.propertyPrice + "</div>"
            + "<div class='streetAddress'>" + this.propertyAddressDTO.streetAddress + "</div>"
            + "<div class='city'>" + this.propertyAddressDTO.city + "</div>"
            + "<div class='province'>" + this.propertyAddressDTO.province + "</div>"
            + "<div class='zipCode'>" + this.propertyAddressDTO.zipCode + "</div>"
            + "<div class='status'>" + this.propertyStatus + "</div>"
            + "<div class='owner'>" + this.propertyOwner + "</div>"
            + "<li>";
        $('#propertylist').html(buffer);
    });
}