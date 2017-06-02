import Weather from "./weather";
import Map from "./map";

$(function() {
    let weather = new Weather();
    let map = new Map();

    $("#search-city").click(() => {
        let newCity = $("#input-city").val();
        weather.send(newCity);
    });
    $(window).keydown(() => {
        let newCity = $("#input-city").val();
        weather.send(newCity);
    });
    weather.send(weather.city);

});
