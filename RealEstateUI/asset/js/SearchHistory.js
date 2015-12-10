$(document).ready(function(){
    GetSearchParameterNames();
});

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

$('#previousSearch').change(function(){
    GetSearchParameter($('#previousSearch').val());
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
            GetSearchParameterNames();
            Materialize.toast('Search parameters saved in your search history.', 4000);
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
        dataType: 'json',
        success: function(){
            GetSearchParameterNames();
            Materialize.toast('Search parameters removed from your search history.', 4000);
            $('#searchName').val('');
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
            updateSearchesHistory(data);
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
            updateSearchParametersField(data);
        },
        error: function(){
            console.log('getSearch error');
        }
    });
}

function updateSearchesHistory(data){
    $('.searchOption').remove();
    for(var i = 0; i < data.length; i++) {
        $('#previousSearch').append('<option value="' + data[i] + '" class="searchOption">' + data[i] + '</option>')
    }
}

function updateSearchParametersField(data){
    $('#searchName').val(data.name);

    if(data.orderBy != 'null'){
        $('#orderBy').val(data.orderBy);
    }
    sliderBedroom.noUiSlider.set(data.minNumBedrooms);
    sliderBathroom.noUiSlider.set(data.minNumBathrooms);
    $('#double-slider')[0].noUiSlider.set([data.minPrice, data.maxPrice]);

    for(var i = 0; i < data.propertyTypes.length; i++) {
        if(data.propertyTypes[i] == 'farm'){
            $('#farmCheckbox').prop('checked', true);
        } else if(data.propertyTypes[i] == 'house'){
            $('#HouseCheckbox').prop('checked', true);
        } else if(data.propertyTypes[i] == 'commercial'){
            $('#CommercialCheckbox').prop('checked', true);
        } else if(data.propertyTypes[i] == 'land'){
            $('#LandCheckbox').prop('checked', true);
        } else if(data.propertyTypes[i] == 'multiplex'){
            $('#MultiplexCheckbox').prop('checked', true);
        }
    }

    GetPropertiesWithForm();
}