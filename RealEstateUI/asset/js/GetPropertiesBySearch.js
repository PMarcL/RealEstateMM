/**
 * Created by alex on 28/11/15.
 */
$(document).ready(function(){
    getPropertiesBySearch('');

    $('#orderBy').change(function(){
        var queryParam = '?orderBy=' + $(orderBy).val();
        $('#propertylist').empty();
        getPropertiesBySearch(queryParam);
    });
});

function getPropertiesBySearch(param) {
    $.ajax({
        url: "http://localhost:8080/property/" + new TokenCookie().cookie() + "/search" + param,
        type: "GET",
        contentType: "application/json",

        success: function (data, status, httpResponse) {
            var propertiesJSON = JSON.parse(httpResponse.responseText);
            createHtmlPropertyList(propertiesJSON);
        },
        error: function (data) {
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
            + "<div class='see-details'><a class='waves-effect waves-light btn editProperty'>See details</a></div>"
            + "<li>";
        $('#propertylist').html(buffer);
    });
}