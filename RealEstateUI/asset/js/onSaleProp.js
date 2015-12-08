
$(document).ready(function(){

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
        //varInputMax.value = Math.round(values[1]);

    });
// When the input changes, set the slider value
    varInputMin.addEventListener('change', function () {
        sliderVar.noUiSlider.set(this.value);
    });
}

