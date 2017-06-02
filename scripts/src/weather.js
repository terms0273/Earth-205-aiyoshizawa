export default class Weather{
    constructor(){
        this.city = "Tokyo";
    }
    print(json){
        this.city = json.name;
        $("#city-name").text(this.city);
        $("#weather").text(json.weather[0].main);
        $("#icon").html("<img src='http://openweathermap.org/img/w/"+json.weather[0].icon+".png'>");
    }

    send(cityName){
        let url = "http://api.openweathermap.org/data/2.5/weather?q=" +
        cityName +
        "&appid=9ab6492bf227782c3c7ae7417a624014";

        $.ajax({
            url:url
        }).then((json) =>{
            console.log(json);
            this.print(json);
        },(err) =>{
            console.log(err);
        });
    }
}
