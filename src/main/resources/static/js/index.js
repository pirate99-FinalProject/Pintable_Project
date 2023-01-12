function handleSearch() {
    const query = document.querySelector('.form-control').value;
    window.location = "/api/searchLocation?storeName=" + query;
}

function onSuccess(position) {
    latitude = position.coords.latitude;
    longitude = position.coords.longitude;
    const storename = document.querySelector('.form-control').value;
    console.log((storename));
    location.href = "api/currentLocation?latitude=" + latitude + "&longitude=" + longitude + "&storeName=" + storename;
}

function onError() {
    alert("위치를 찾을 수 없습니다.");
}
