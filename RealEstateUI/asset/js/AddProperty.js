var loginCookie = new LoginCookie();
var tokenCookie = new TokenCookie();

var propertyAddress;
var propertyDetails;

var Property = Backbone.Model.extend({
    url: 'http://localhost:8080/property/' + tokenCookie.cookie()
})

var EditPropertyView = Backbone.View.extend({
    template : _.template($("#edit_property").html()),

    events: {
        'click .editProperty': 'editProperty'
    },

    editProperty: function(){
        if(propertyFeaturesFormIsValid()) {
            putProperty(propertyDetails);
        }
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
        else if(!fieldIsADouble($('#price'))) {
            $('form .card').attr('style','display:block');
            $('form .card').html("Price field is invalid. Make sure it's a positive numeric value.");
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