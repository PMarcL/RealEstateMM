

$(document).ready(function () {
    getNumberOfSellersWithAProperty();

});

function getNumberOfSellersWithAProperty(){
    $.ajax({
        url: "http://localhost:8080/stats/numberofsellerswithaproperty",
        type: "GET",
        contentType: "application/json",

        success: function (numberOfSeller) {
            var htmlToAppend = "<li class ='stats-list-item'> Number of sellers with at least one on sale property: " + numberOfSeller.numberOfSellerWithAProperty + "</li>";
            $('.stats-list').append(htmlToAppend);

        }
    });
}