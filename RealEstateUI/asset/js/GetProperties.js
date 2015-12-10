$(document).ready(function(){
    getProperties('');

    $('#orderBy').change(function(){
        GetPropertiesWithForm();
    });

});

function getProperties(param) {
    $.ajax({
        url: "http://localhost:8080/property/" + new TokenCookie().cookie() + "/search?" + param,
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
    $('#propertylist').empty();
    if(propertiesJSON.length <= 0){
        $('#noPropertiesReturned').show();
    } else {
        $('#noPropertiesReturned').hide();
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

function GetPropertiesWithForm(){
    var form =  document.getElementById("advancedForm");
    var orderByValue = document.getElementById("orderBy");
    var strUser = orderByValue.options[orderByValue.selectedIndex].value;

    var queryParamSearchForm = '';
    for (var i = 0; i < form.elements.length; i++) {
        if (form.elements[i].type == 'checkbox') {
            if (form.elements[i].checked == true) {
                if(form.elements[i].className == "propertylistType"){
                    queryParamSearchForm += '&propertyTypes=' + form.elements[i].value.toString();
                }

            }
        }
    }
    queryParamSearchForm += '&minNumBedrooms=' + valueInputBedroomMin.value;
    queryParamSearchForm += '&minNumBathrooms=' + valueInputBathroomMin.value;
    queryParamSearchForm +='&priceMax=' + $('#price-max').html().split(".")[0];
    queryParamSearchForm +='&priceMin=' + $('#price-min').html().split(".")[0];
    if(strUser.length !== 0){
        queryParamSearchForm += '&orderBy=' + strUser;
    }

    getProperties(queryParamSearchForm);
}


