
$(document).ready(function(){
    createPriceRangeSlider();
});

function doubleSlider(idSlider, inputMax, inputStep){

    var slider = document.getElementById(idSlider);
    noUiSlider.create(slider, {
        start: (0),
        connect: false,
        step: inputStep,
        range: {
            'min': 0,
            'max': inputMax
        }
    });
    return slider;
}

function updateSlider(sliderVar, varInputMin) {
// When the slider value changes, update the input and span
    sliderVar.noUiSlider.on('update', function (values) {
        varInputMin.value = Math.round(values[0]);

    });
// When the input changes, set the slider value
    varInputMin.addEventListener('change', function () {
        sliderVar.noUiSlider.set(this.value);
    });
}


function createPriceRangeSlider(){
    var slider = $('#double-slider')[0];
    noUiSlider.create(slider, {
        start: [0, 1000000],
        connect: true,
        step: 1000,
        range: {
            'min': 0,
            'max': 5000000
        }
    });

    var snapValues = [
        $('#price-min')[0],
        $('#price-max')[0]
    ];

    slider.noUiSlider.on('update', function( values, handle ) {
        snapValues[handle].innerHTML = values[handle];
    });
}