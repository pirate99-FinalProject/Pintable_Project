const baseUrl = "https://pintable.co.kr";
// const baseUrl = "http://localhost:8080";

window.onload = function () {
    EnterStatus();
    getStoreAdminInfo();
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
            alert("고객 호출 !")
            location.reload();
        })
        .catch(function (error) {
        });
}

// 입장 완료 여부 조회 API 연동 (음식점 대기자 리스트 전체)
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
            const statusData = JSON.stringify(response.data);
            var status = JSON.parse(statusData);

            if (Object.keys(status).length != 0) {

                let rows = status;

                for (let i = 0; i < rows.length; i++) {
                    let waitingNum = i + 1;
                    let waitingUserName = rows[i]['username']
                    let waitingStatus = rows[i]['waitingStatus']

                    let temp_html =
                        `<tr>
                            <td class="tg-09md" id="idx">${waitingNum}</td>
                            <td class="tg-09md" id="username">${waitingUserName}</td>
                            <td class="tg-09md" id="waitingStatus">${waitingStatus}</td>
                            <td class="tg-09md">
                                <img id="callUser" src="/css/images/bell.png" width="48" onclick="callpeople('${waitingUserName}')">
                             </td>
                             <td class="tg-09md">
                                <img id="entranceUserCheck" src="/css/images/bell1.png" width="48" onclick="EnterConfirm('${waitingUserName}')">
                             </td>
                             <td class="tg-09md">
                                <img id="workoutUserCheck" src="/css/images/okButton.png" width="48" onclick="ExitConfirm()">
                             </td>
                        </tr>`
                    $('#waitingList').append(temp_html)
                }
            }
        })
        .catch(function (error) {
            console.log("ERROR");
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
            {limitWaitingCnt: limit_number}
        ),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
    })
        .then(function (response) {
            alert("대기 제한 설정 완료!")
        })
        .catch(function (error) {
            console.log("Error")
        });
}

// 입장 확인
function EnterConfirm(waitingUserName) {
    var getId = localStorage.getItem("storeId");   //데이터를 key로 꺼냄
    const id = JSON.parse(getId);   //문자열을 객체(json)으로 변환
    const api = '/api/storeStatus/confirmEnter/' + id;

    axios({
        method: "put",
        url: baseUrl + api,
        data: JSON.stringify(
            {username: waitingUserName, waitingStatus: 1}
        ),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
    })
        .then(function (response) {
            alert("손님 입장 완료!!")
        })
        .catch(function (error) {
            console.log("Error")
        });
}

// 퇴장확인 API 연동
function ExitConfirm() {
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
            alert("손님 퇴장 확인!!")
        })
        .catch(function (error) {
            console.log("Error")
        });
}

function getStoreAdminInfo() {
    var getId = localStorage.getItem("storeId");   //데이터를 key로 꺼냄
    const id = JSON.parse(getId);   //문자열을 객체(json)으로 변환
    const api = '/api/storeAdmin/';

    axios({
        method: "get",
        url: baseUrl + api + id,
        data: JSON.stringify(
        ),
        headers: {
            "Content-Type": "application/json; charset=utf-8"                                                           // responseType: "json" // [응답 데이터 : stream , json]
        },
    })
        .then(function (response) {
            const transferStoreAdmin = JSON.stringify(response.data);
            const storeAdmin = JSON.parse(transferStoreAdmin);
            document.getElementById("storeName").innerHTML = storeAdmin.storeName;
            document.getElementById("totalWaiting").innerHTML = "총 대기팀 수 : " + storeAdmin.numberOfTeamsWaiting + "팀";
            document.getElementById("useCustomer").innerHTML = "이용중인 고객수 : " + storeAdmin.numberOfCustomersInUse + "팀";

        })
        .catch(function (error) {
            console.log("Error")
        });
}