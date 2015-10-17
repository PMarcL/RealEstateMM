function isAFieldEmpty()
{
    var isAFieldEmpty = false;
    $('form').find(":input").each(function () {
        if ($(this).val() === "")
        {
            isAFieldEmpty = true;
        }
    });
    if($('select').val() === null)
    {
        isAFieldEmpty = true;
    }
    return isAFieldEmpty;
}

function fieldIsADouble(field){
    return $.isNumeric(field.val());
}

function fieldIsAInt(field) {
    return /^\d+$/.test(field.val());
}

function totalNumberOfRoomsIsValid(bedrooms, bathrooms, totalrooms) {
    var bedroomsNumber = parseInt(bedrooms.val());
    var bathroomsNumber = parseInt(bathrooms.val());
    var totalRoomsNumber = parseInt(totalrooms.val());
    return totalRoomsNumber >= bedroomsNumber + bathroomsNumber;
}