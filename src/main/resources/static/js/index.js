window.onload=function(){
    const requestWaiting = document.getElementById('signin');
    requestWaiting.style.display = 'none';

    const statusConfirm = document.getElementById('statusConfirm');
    statusConfirm.style.display = 'none';
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

function status() {
    const statusConfirm = document.getElementById('statusConfirm');

    if(statusConfirm.style.display !== 'none') {
        statusConfirm.style.display = 'none';
    }
    else {
        statusConfirm.style.display = 'block';
    }
}


function signIn() {
    let username = document.querySelector('.userId').value;
    let password = document.querySelector('.password').value;
    const url = 'http://3.36.123.179:8080/api/signup/';

    axios({
        method: "post",                                                                     // [요청 타입]
        url: url,                                                                      // [요청 주소]
        data: JSON.stringify(
            {username: username, password:password}
        ),                                                                                  // [요청 데이터]
        headers: {                                                                          // [요청 헤더]
            "Content-Type": "application/json; charset=utf-8"                               // responseType: "json" // [응답 데이터 : stream , json]
        },
    })
        .then(function (response) {
            console.log("");
            console.log("RESPONSE : " + JSON.stringify(response.data));
            alert("로그인 성공, confirm 버튼을 눌러주세요!")
        })
        .catch(function (error) {
            console.log("");
            console.log("ERROR : " + JSON.stringify(error));
            console.log("");
        });
}

function callWaiting() {
    let id = document.getElementById("id").innerHTML
    let username = document.querySelector('.userId').value;
    const url = 'http://3.36.123.179:8080/api/waitingList/';

    axios({
        method: "post",                                                                     // [요청 타입]
        url: url + id,                                                                      // [요청 주소]
        data: JSON.stringify(
            {username: username}
        ),                                                                                  // [요청 데이터]
        headers: {                                                                          // [요청 헤더]
            "Content-Type": "application/json; charset=utf-8"                               // responseType: "json" // [응답 데이터 : stream , json]
        },
    })
        .then(function (response) {
            console.log("");
            console.log("RESPONSE : " + JSON.stringify(response.data));
            const requestWaiting = document.getElementById('signin');
            requestWaiting.style.display = 'none';
            alert("웨이팅 등록 성공!")
        })
        .catch(function (error) {
            console.log("");
            console.log("ERROR : " + JSON.stringify(error));
            console.log("");
        });
}


function mystatus() {
    let id = document.getElementById("id").innerHTML
    let username = document.querySelector('.userId1').value;
    const url = 'http://3.36.123.179:8080/api/waitingList/myTurn/';

    axios({
        method: "post",                                                                     // [요청 타입]
        url: url + id,                                                                      // [요청 주소]
        data: JSON.stringify(
            {username: username}
        ),                                                                                  // [요청 데이터]
        headers: {                                                                          // [요청 헤더]
            "Content-Type": "application/json; charset=utf-8"                               // responseType: "json" // [응답 데이터 : stream , json]
        },
    })
        .then(function (response) {
            console.log("");
            console.log("RESPONSE : " + JSON.stringify(response.data));
            const transferStatus = JSON.stringify(response.data);
            var status = JSON.parse(transferStatus);
            document.getElementById("storeStatusShow").innerHTML = "대기 현황 : " + status.totalWaitingCnt;
            document.getElementById("myStatusShow").innerHTML = "내 웨이팅 현황 : " + status.myTurn;
            const storeStatusShow = document.getElementById('statusConfirm');
            storeStatusShow.style.display = 'none';
            alert("상태 조회 완료!")
        })
        .catch(function (error) {
            console.log("");
            console.log("ERROR : " + JSON.stringify(error));
            console.log("");
        });
}

function storeStatus() {
    let id = document.getElementById("id").innerHTML
    const url = 'http://3.36.123.179:8080/api/waitingList/';

    axios({
        method: "get",                                                                     // [요청 타입]
        url: url + id,                                                                      // [요청 주소]
        data: JSON.stringify(
        ),                                                                                  // [요청 데이터]
        headers: {                                                                          // [요청 헤더]
            "Content-Type": "application/json; charset=utf-8"                               // responseType: "json" // [응답 데이터 : stream , json]
        },
    })
        .then(function (response) {
            console.log("");
            console.log("RESPONSE : " + JSON.stringify(response.data));
            const transferStatus = JSON.stringify(response.data);
            var status = JSON.parse(transferStatus);
            alert("내 상태 조회 완료!")
            document.getElementById("myStatusShow").innerHTML = "대기 현황 : " + status.waitingStatus;

        })
        .catch(function (error) {
            console.log("");
            console.log("ERROR : " + JSON.stringify(error));
            console.log("");
        });
}