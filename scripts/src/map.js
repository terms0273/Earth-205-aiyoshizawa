import L from "leaflet";
import Weather from "./weather";

export default class Map{
    constructor(){

        this.map = {};
        this.init();
    }
    init() {
       // map要素が無い場合は地図画面ではない

       if (!$("#map")) {
         console.log("this page is not map page");
         return;
       }

       let std = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
         attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
       });

       let chiriin = L.tileLayer('https://cyberjapandata.gsi.go.jp/xyz/std/{z}/{x}/{y}.png', {
         attribution: "<a href='http://portal.cyberjapan.jp/help/termsofuse.html' target='_blank'>国土地理院</a>"
       });

       let pr = L.tileLayer('http://tile.openweathermap.org/map/pressure_new/{z}/{x}/{y}.png?appid=9ab6492bf227782c3c7ae7417a624014')

       this.map = L.map("map",{
           center:[35.69,139.69],
           zoom:16,
           layers:[std]
       });

       let baseMaps = {
           "Mapboc(osm)": std,
           "Mapbox(chiriin)":chiriin
       };

       let overMaps = {
           "Mapboc(pressure)": pr
       };

       L.control.layers(baseMaps,overMaps).addTo(this.map);

       this.map.on("resize",()=>{
           this.map.invalidateSize();
       });
    }

    changeCurrent (lat,lon){
        this.map.setView(new L.LatLng(lat,lon), 16 );
    }

}
