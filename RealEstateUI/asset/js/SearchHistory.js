
$('.saveSearchBtn').click(function(){
    if($('#searchName').val() == '') {
        $('.errorSearch').show();
    }
    else {
        $('.errorSearch').hide();
        var data = buildSearchParam();
        PostSearchParameters(data);
    }
});

$('.deleteSearchBtn').click(function(){
    if($('#searchName').val() == '') {
        $('.errorSearch').show();
    }
    else {
        $('.errorSearch').hide();
        DeleteSearchParameters($('#searchName').val());
    }
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
        'minNumBathrooms': valueInputBathroomMin.value,
        'minPrice': $('#price-min').html().substring(0, $('#price-min').html().indexOf(".")),
        'maxPrice': $('#price-max').html().substring(0, $('#price-max').html().indexOf("."))
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

function DeleteSearchParameters(searchName) {
    $.ajax({
        url: "http://localhost:8080/search/" + new TokenCookie().cookie() + "/" + searchName,
        type: 'DELETE',
        contentType: "application/json",
        success: function(){
            console.log('delete params done');
        },
        error: function(){
            console.log('delete search error');
        }
    });
}

function GetSearchParameterNames() {
    $.ajax({
        url: "http://localhost:8080/search/" + new TokenCookie().cookie(),
        type: 'GET',
        contentType: "application/json",
        success: function(data){
            console.log('getSearches done : ' + data);
        },
        error: function(){
            console.log('getSearches error');
        }
    });
}

function GetSearchParameter(name) {
    $.ajax({
        url: "http://localhost:8080/search/" + new TokenCookie().cookie() + "/" + name,
        type: 'GET',
        contentType: "application/json",
        success: function(data){
            console.log('getSearch done : ' + data);
        },
        error: function(){
            console.log('getSearch error');
        }
    });
}