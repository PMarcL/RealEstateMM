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

$('#propertylist').on('click', '.editProperty', function(){
    var infoContainer = $(this).parent().parent();
    var propertyAddress = {
        "streetAddress": infoContainer.find('.streetAddress').text(),
        "city": infoContainer.find('.city').text(),
        "province": infoContainer.find('.province').text(),
        "zipCode": infoContainer.find('.zipCode').text()
    };

    $.cookie("currentPropertyAddress", JSON.stringify(propertyAddress));
    window.location.href = 'propertyDetails.html';
});

/*
 $(function () {
 $( "#slider-range" ).slider({
 range: true,
 min: 0,
 max: 5000000,
 values: [ 12000, 250000 ],
 slide: function( event, ui ) {
 $( "#amount" ).val( "$" + ui.values[ 0 ] + " - $" + ui.values[ 1 ] );
 }
 });
 $( "#amount" ).val( "$" + $( "#slider-range" ).slider( "values", 0 ) +
 " - $" + $( "#slider-range" ).slider( "values", 1 ) );
 });*/

