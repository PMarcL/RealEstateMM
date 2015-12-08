
$('.saveSearchBtn').click(function(){
    var data = buildSearchParam();
    PostSearchParameters(data);
});

function buildSearchParam(){
    var propertyTypesArray = new Array;
    var form =  document.getElementById("advancedForm");
    for (var i = 0; i < form.elements.length; i++) {
        if (form.elements[i].type == 'checkbox') {
            if (form.elements[i].checked == true) {
                if(form.elements[i].className == "propertylistType"){
                    propertyTypesArray.push(form.elements[i].value.toString());
                }

            }
        }
    }
    return {
        'name': $('#searchName').val(),
        'orderBy': $('#orderBy').val(),
        'propertyTypes': propertyTypesArray,
        'minNumBedrooms': valueInputBedroomMin.value,
        'minNumBathrooms': valueInputBathroomMin.value
    };
}

function PostSearchParameters(data) {
    $.ajax({
        url: "http://localhost:8080/search/" + new TokenCookie().cookie(),
        type: 'POST',
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: 'json',
        success: function(){
            console.log('search params posted');
        },
        error: function(){
            console.log('save search error');
        }
    });
}