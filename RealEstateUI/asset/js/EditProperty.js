$(document).ready(getOwnersProperties());

var EditPropertyView = Backbone.View.extend({

    template : _.template($("#edit_property").html()),

    events: {
        'click .editProperty': 'editProperty'
    },

    initialize: function(options) {
        _.extend(this, _.pick(options, 'propertyDetails'));
    },

    editProperty: function(){
        if(propertyFeaturesFormIsValid()) {
            putProperty(this.propertyDetails);
        }
    },

    render: function() {
        this.$el.html(this.template(this.propertyDetails));
    },
});

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