var loginCookie = new LoginCookie();

var propertyAddress;
var propertyDetails;

var Property = Backbone.Model.extend({
    url: 'http://localhost:8080/property'
})

var EditPropertyView = Backbone.View.extend({
    template : _.template($("#edit_property").html()),

    events: {
        'click .editProperty': 'editProperty'
    },

    editProperty: function(){
        propertyDetails.propertyFeatures = {
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

        $.ajax({
            url: "http://localhost:8080/property",
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify(propertyDetails),
            dataType: "json",
            success: function () {
                window.location.href = 'index.html';
            },
            error: function (httpRequest, textStatus, errorThrown) {
                $('form .card').attr('style','display:block');
                $('form .card').html(textStatus.responseText);
            }
        });
    },

    render: function() {
        this.$el.html(this.template());
    },
});

var NewPropertyView = Backbone.View.extend({
    template: _.template($("#new_property").html()),

    events: {
        'click .addProperty': 'addProperty'
    },

    addProperty: function() {
        if(isAFieldEmpty())
        {
            $('form .card').attr('style','display:block');
            $('form .card').html("You must fill all fields");
        }
        else {
            propertyAddress = {
                "streetAddress": $('#streetAddress').val(),
                "city": $('#city').val(),
                "province": $('#province').val(),
                "zipCode": $('#zipCode').val()
            };
            propertyDetails = {
                "propertyType": $('#propertyType').val(),
                "propertyAddress": propertyAddress,
                "propertyPrice": $('#price').val(),
                "propertyOwner": loginCookie.cookie(),
                "propertyStatus": "on sale",
                "propertyFeatures": null
            };
            var property = new Property();

            property.save(propertyDetails,{
                success: function(){
                    router.navigate('edit', {trigger:true});
                },
                error: function(httpRequest, textStatus, errorThrown) {
                    $('form .card').attr('style','display:block');
                    $('form .card').html(textStatus.responseText);
                }
            })
        }
    },

    render: function() {
        this.$el.html(this.template());
    }
});

var Router = Backbone.Router.extend({
   routes:{
       "" : "home",
       "edit": "edit"
   }
});

var newPropertyView = new NewPropertyView({el:$("#property_container")});
var editPropertyView = new EditPropertyView({el:$("#property_container")});
var router = new Router;

router.on('route:home', function(){
    newPropertyView.render();
});

router.on('route:edit', function(){
    editPropertyView.render();
});

Backbone.history.start();