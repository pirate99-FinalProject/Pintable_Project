window.onload=function(){
    const requestWaiting = document.getElementById('signin');
    requestWaiting.style.display = 'none';

    const cancleWaiting = document.getElementById('waitingCancle');
    cancleWaiting.style.display = 'none';

    const statusConfirm = document.getElementById('statusConfirm');
    statusConfirm.style.display = 'none';

    const myStatusShow = document.getElementById('myStatusShow');
    myStatusShow.style.display = 'none'
}


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

function waiting() {
    const requestWaiting = document.getElementById('signin');

    if(requestWaiting.style.display !== 'none') {
        requestWaiting.style.display = 'none';
    }
    else {
        requestWaiting.style.display = 'block';
    }
}



function cancle() {
    const cancleWaiting = document.getElementById('waitingCancle');

    if(cancleWaiting.style.display !== 'none') {
        cancleWaiting.style.display = 'none';
    }
    else {
        cancleWaiting.style.display = 'block';
    }
}

function status() {
    const statusConfirm = document.getElementById('statusConfirm');

    if(statusConfirm.style.display !== 'none') {
        statusConfirm.style.display = 'none';
    }
    else {
        statusConfirm.style.display = 'block';
    }
}

function showStatus(){
    const myStatusShow = document.getElementById('myStatusShow');
    myStatusShow.style.display = 'block';
}

