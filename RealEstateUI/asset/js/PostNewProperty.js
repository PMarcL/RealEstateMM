var loginCookie = new LoginCookie();

var Property = Backbone.Model.extend({
    url: 'http://localhost:8080/property'
})

var EditPropertyView = Backbone.View.extend({
    template : _.template($("#edit_property").html()),

    events: {
        'click .editProperty': 'editProperty'
    },

    editProperty: function(){
        console.log("hello");
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
            var propertyAddress = {
                "streetAddress": $('#streetAddress').val(),
                "city": $('#city').val(),
                "province": $('#province').val(),
                "zipCode": $('#zipCode').val()
            };
            var propertyDetails = {
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
                error: function() {
                    console.log("Add property error");
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

var newPropertyView = new NewPropertyView({el:$("#new_property_container")});
var editPropertyView = new EditPropertyView({el:$("#edit_property_container")});
var router = new Router;

router.on('route:home', function(){
    newPropertyView.render();
});

router.on('route:edit', function(){
    editPropertyView.render();
})

Backbone.history.start();









function postNewProperty() {
    if(isAFieldEmpty())
    {
        $('form .card').attr('style','display:block');
        $('form .card').html("You must fill all fields");
    }
    else
    {
        ajaxPostProperty();
        $('form .card').attr('style','display:none');
    }
}

function ajaxPostProperty()
{
    var propertyAddress = {
        "streetAddress": $('#streetAddress').val(),
        "city": $('#city').val(),
        "province": $('#province').val(),
        "zipCode": $('#zipCode').val()
    };
    var formData = JSON.stringify({
        "propertyType": $('#propertyType').val(),
        "propertyAddress": propertyAddress,
        "propertyPrice": $('#price').val(),
        "propertyOwner": loginCookie.cookie(),
        "propertyStatus": "on sale",
        "propertyFeatures" : null
    });

    $.ajax({
        url: "http://localhost:8080/property",
        type: "POST",
        contentType: "application/json",
        data: formData,
        dataType: "json",
        success: function () {
            var editView = new EditView({el:$("#edit_property_container")});
            var property = new Property({id: propertyAddress});
            editView.initialize();
        },
        error: function (httpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}

