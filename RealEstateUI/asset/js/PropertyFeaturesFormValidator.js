
function propertyFeaturesFormIsValid() {
    if(isAFieldEmpty())
    {
        $('form .card').attr('style','display:block');
        $('form .card').html("You must fill all fields");
        return false;
    }
    else if(!fieldIsAInt($('#bathrooms'))){
        $('form .card').attr('style','display:block');
        $('form .card').html("Bathroom field is invalid. Make sure its a positive number.");
        return false;
    }
    else if(!fieldIsAInt($('#bedrooms'))){
        $('form .card').attr('style','display:block');
        $('form .card').html("Bedroom field is invalid. Make sure its a positive number.");
        return false;
    }
    else if(!fieldIsAInt($('#totalNumberRooms'))){
        $('form .card').attr('style','display:block');
        $('form .card').html("Total number of rooms field is invalid. Make sure its a positive number.");
        return false;
    }
    else if(!totalNumberOfRoomsIsValid($('#bedrooms'), $('#bathrooms'), $('#totalNumberRooms'))){
        $('form .card').attr('style','display:block');
        $('form .card').html("Total number of rooms field is invalid. Make sure it's greater or equal" +
            " than the sum of the number of bedrooms and the number of bathrooms.");
        return false;
    }
    else if(!fieldIsAInt($('#levels'))){
        $('form .card').attr('style','display:block');
        $('form .card').html("Levels field is invalid. Make sure its a positive number.");
        return false;
    }
    else if(!fieldIsADouble($('#lotDimensions'))){
        $('form .card').attr('style','display:block');
        $('form .card').html("Lot dimension field is invalid. Make sure its a positive number.");
        return false;
    }
    else if(!fieldIsAInt($('#yearOfConstruction'))){
        $('form .card').attr('style','display:block');
        $('form .card').html("Year of construction field is invalid. Make sure its a valid year.");
        return false;
    }
    else if(!fieldIsADouble($('#livingSpaceArea'))){
        $('form .card').attr('style','display:block');
        $('form .card').html("Living space area field is invalid. Make sure its a positive number.");
        return false;
    }
    return true;
};
