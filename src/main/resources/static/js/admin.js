// const baseUrl = "https://pintable.co.kr";
const baseUrl = "http://localhost:8080";

window.onload = function () {
    EnterStatus();
    alert("호출!")
}

// 대기자 호출 기능
function callpeople() {
    var getId = localStorage.getItem("storeId");   //데이터를 key로 꺼냄
    const id = JSON.parse(getId);   //문자열을 객체(json)으로 변환
    let username = document.querySelector('#username').innerHTML;
    const api = '/api/storeStatus/call/';

    axios({
        method: "put",
        url: baseUrl + api + id,
        data: JSON.stringify(
            {username: username}
        ),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
    })
        .then(function (response) {
            console.log("");
            console.log("RESPONSE : " + JSON.stringify(response.data));
            alert("고객 호출 !")
            location.reload();
        })
        .catch(function (error) {
            console.log("");
            console.log("ERROR : " + JSON.stringify(error));
            console.log("");
        });
}

// 입장 완료 여부 조회 API 연동
function EnterStatus() {
    var getId = localStorage.getItem("storeId");   //데이터를 key로 꺼냄
    const id = JSON.parse(getId);   //문자열을 객체(json)으로 변환
    const api = '/api/enterStatus/';

    axios({
        method: "get",
        url: baseUrl + api + id,
        data: JSON.stringify(
        ),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
    })
        .then(function (response) {
            console.log("");
            console.log("RESPONSE : " + JSON.stringify(response.data));

            const statusData = JSON.stringify(response.data);
            var status = JSON.parse(statusData);
            if(Object.keys(status).length != 0){

                document.getElementById("waitingNum").innerHTML = status.userid;
                document.getElementById("waitingUserName").innerHTML = status.username;
                document.getElementById("waitingNum").innerHTML = status.waitingStatus;

                console.log(status.userid, status.username, status.waitingStatus);
            }
        })
        .catch(function (error) {
            console.log("");
            console.log("ERROR : " + JSON.stringify(error));
            console.log("");
        });


}

// 대기마감(Limit) 설정 API 연동
function LimitSetup() {
    var getId = localStorage.getItem("storeId");   //데이터를 key로 꺼냄
    const id = JSON.parse(getId);   //문자열을 객체(json)으로 변환
    const limit_number = document.querySelector('.waiting-inputbox').value;

    const api = '/api/storeStatus/limitWaitingCnt/';

    axios({
        method: "put",
        url: baseUrl + api + id,
        data: JSON.stringify(
            {limitWaitingCnt : limit_number }
        ),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
    })
        .then(function (response) {
            console.log("");
            console.log("RESPONSE : " + JSON.stringify(response.data));
            alert("대기 제한 설정 완료!")
        })
        .catch(function (error) {
            console.log("");
            console.log("ERROR : " + JSON.stringify(error));
            console.log("");
        });
}

// 입장 확인
function EnterConfirm() {
    var getId = localStorage.getItem("storeId");   //데이터를 key로 꺼냄
    const id = JSON.parse(getId);   //문자열을 객체(json)으로 변환
    let username = document.querySelector('#username').innerHTML;

    const api = '/api/storeStatus/confirmEnter/' + id;

    axios({
        method: "put",
        url: baseUrl + api,
        data: JSON.stringify(
            {username : username, waitingStatus : 1}
        ),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
    })
        .then(function (response) {
            console.log("");
            console.log("RESPONSE : " + JSON.stringify(response.data));
            alert("손님 입장 완료!!")
        })
        .catch(function (error) {
            console.log("");
            console.log("ERROR : " + JSON.stringify(error));
            console.log("");
        });
}

// 퇴장확인 API 연동
function ExitConfirm(){
    var getId = localStorage.getItem("storeId");   //데이터를 key로 꺼냄
    const id = JSON.parse(getId);   //문자열을 객체(json)으로 변환
    const api = '/api/storeStatus/leave/' + id;

    axios({
        method: "put",
        url: baseUrl + api,
        data: JSON.stringify(
        ),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
    })
        .then(function (response) {
            console.log("");
            console.log("RESPONSE : " + JSON.stringify(response.data));
            alert("손님 퇴장 확인!!")
        })
        .catch(function (error) {
            console.log("");
            console.log("ERROR : " + JSON.stringify(error));
            console.log("");
        });
}