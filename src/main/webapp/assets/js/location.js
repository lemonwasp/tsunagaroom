function initMap() {

    const latitude =
        parseFloat(document.getElementById("latitude").value);

    const longitude =
        parseFloat(document.getElementById("longitude").value);

    const address =
        document.getElementById("address").value;

    const position = {
        lat: latitude,
        lng: longitude
    };

    const map = new google.maps.Map(
        document.getElementById("map"),
        {
            center: position,
            zoom: 16
        }
    );

    new google.maps.Marker({
        position: position,
        map: map,
        title: address
    });

    const addressText = document.getElementById("addressText");

    let displayAddress = address;

    displayAddress = displayAddress.replace("区", "区<br>");
    displayAddress = displayAddress.replace("町", "町<br>");

    addressText.innerHTML = displayAddress;
}