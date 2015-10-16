$(document).ready(getProperties());

var EditPropertyView = Backbone.View.extend({

    template : _.template($("#edit_property").html()),

    events: {
        'click .editProperty': 'editProperty'
    },

    initialize: function(options) {
        _.extend(this, _.pick(options, 'propertyDetails'));
    },

    editProperty: function(){
        this.propertyDetails.propertyFeatures = {
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
        console.log(JSON.stringify(this.propertyDetails));
        $.ajax({
            url: "http://localhost:8080/property",
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify(this.propertyDetails),
            dataType: "json",
            success: function () {
                window.location.href = 'index.html';
            },
            error: function (httpRequest, textStatus, errorThrown) {
                $('form .card').attr('style','display:block');
                $('form .card').html(httpRequest.responseText);
            }
        });
    },

    render: function() {
        this.$el.html(this.template(this.propertyDetails));
    },
});

function getProperties() {
    $.ajax({
        url: "http://localhost:8080/property/" + new LoginCookie().cookie(),
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

        buffer+= "<li class='ownerpropertylist-item'><div class='type'>" + this.propertyType + "</div>"
            + "<div>C $ <span class='price'>" +this.propertyPrice + "</span></div>"
            + "<div class='streetAddress'>" + this.propertyAddress.streetAddress + "</div>"
            + "<div class='city'>" + this.propertyAddress.city + "</div>"
            + "<div class='province'>" + this.propertyAddress.province + "</div>"
            + "<div class='zipCode'>" + this.propertyAddress.zipCode + "</div>"
            + "<div class='status'>" + this.propertyStatus + "</div>"
            + "<div class='owner'>" + this.propertyOwner + "<br></div>"
            + "<div class='features'> Features: "
            +   "<div class='featuresAttribute'> Number of bathrooms : <span class='bathrooms'>" + this.propertyFeatures.numberOfBathrooms + "</span></div>"
            +   "<div class='featuresAttribute'> Number of bedrooms : <span class='bedrooms'>" + this.propertyFeatures.numberOfBedrooms + "</span></div>"
            +   "<div class='featuresAttribute'> Total number of rooms : <span class='totalRooms'>" + this.propertyFeatures.totalNumberOfRooms + "</span></div>"
            +   "<div class='featuresAttribute'> Number of levels :<span class='levels'> " + this.propertyFeatures.numberOfLevels + "</span></div>"
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

$('#properties-container').on('click', '.editProperty', function(){
    var infoContainer = $(this).parent().parent();

    var propertyAddress = {
        "streetAddress": infoContainer.find('.streetAddress').text(),
        "city": infoContainer.find('.city').text(),
        "province": infoContainer.find('.province').text(),
        "zipCode": infoContainer.find('.zipCode').text()
    };

    var propertyFeatures = {
        "numberOfBathrooms" : infoContainer.find('.bathrooms').text(),
        "numberOfBedrooms" : infoContainer.find('.bedrooms').text(),
        "totalNumberOfRooms" : infoContainer.find('.totalRooms').text(),
        "numberOfLevels" : infoContainer.find('.levels').text(),
        "lotDimensions" : infoContainer.find('.lotDimensions').text(),
        "yearOfConstruction": infoContainer.find('.yearOfConstruction').text(),
        "livingSpaceArea": infoContainer.find('.livingSpaceArea').text(),
        "backyardDirection": infoContainer.find('.backyardDirection').text(),
        "description" : infoContainer.find('.description').text()
    };

    var propertyDetails = {
        "propertyType": infoContainer.find('.type').text(),
        "propertyAddress": propertyAddress,
        "propertyPrice": infoContainer.find('.price').text(),
        "propertyOwner": new LoginCookie().cookie(),
        "propertyStatus": infoContainer.find('.status').text(),
        "propertyFeatures": propertyFeatures
    };

    $('#properties-container').empty();
    var editPropertyView = new EditPropertyView({el:$("#properties-features-edit"), propertyDetails:propertyDetails});
    editPropertyView.render();
});