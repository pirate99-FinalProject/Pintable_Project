// const baseUrl = "https://pintable.co.kr";
const baseUrl = "http://localhost:8080";
let code = "";
var isCertification = false;

window.onload = function () {
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
    location.href = "api/currentLocation?latitude=" + latitude + "&longitude=" + longitude + "&storeName=" + storename;
}

function onError() {
    alert("위치를 찾을 수 없습니다.");
}

// 웨이팅 요청 기능 숨기기
function waiting() {
    const requestWaiting = document.getElementById('signin');

    if (requestWaiting.style.display !== 'none') {
        requestWaiting.style.display = 'none';
    } else {
        requestWaiting.style.display = 'block';
    }
}

// 내 상태 조회 기능 입력 박스 숨기기 기능
function status() {
    const statusConfirm = document.getElementById('statusConfirm');

    if (statusConfirm.style.display !== 'none') {
        statusConfirm.style.display = 'none';
    } else {
        statusConfirm.style.display = 'block';
    }
}

// 회원가입 기능
function signIn() {
    let username = document.querySelector('.userId').value;
    let password = document.querySelector('.password').value;
    let email = document.querySelector('.email').value;
    const api = '/api/signup';

    if(isCertification == true) {
        axios({
            method: "post",
            url: baseUrl + api,
            data: JSON.stringify(
                {username: username, password: password, email:email }
            ),
            headers: {
                "Content-Type": "application/json; charset=utf-8"
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
    } else {
        alert("사용자 이메일 인증이 되지 않았습니다. 인증확인을 눌러주세요!")
    }


}

// 웨이팅 등록 기능
function callWaiting() {
    let id = document.getElementById("id").innerHTML                                                            // 선택한 점포의 Store Id값을 받아온다.
    let username = document.querySelector('.userId').value;                                                      // 사용자가 Input Box에 입력한 username값(=UserId)를 받아온다
    const api = '/api/waitingList/';

    axios({
        method: "post",
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
            const requestWaiting = document.getElementById('signin');                                           // 입력받은 회원정보로 회원가입을 하고,
            requestWaiting.style.display = 'none';                                                                       // 해당 Form을 숨김처리한다.
            alert("웨이팅 등록 성공!")
        })
        .catch(function (error) {
            console.log("");
            console.log("ERROR : " + JSON.stringify(error));
            console.log("");
        });
}

// 내 웨이팅 현황 확인 기능
function mystatus() {
    let id = document.getElementById("id").innerHTML                                                            // 선택한 점포의 Store Id값을 받아온다.
    let username = document.querySelector('.userId1').value;                                                     // 사용자가 Input Box에 입력한 username값(=UserId)를 받아온다
    const api = '/api/waitingList/myTurn/';

    axios({
        method: "post",
        url: baseUrl + api + id,
        data: JSON.stringify(
            {username: username}
        ),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
    })
        .then(function (response) {
            const transferStatus = JSON.stringify(response.data);                                                        // response값을 가져와서 transferStatus에 저장
            var status = JSON.parse(transferStatus);                                                                     // string 객체를 json 객체로 변환

            document.getElementById("myStatusShow").innerHTML = "내 웨이팅 현황 : " + status.myTurn;               // 웨이팅 현황 출력

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

// 점포상태 확인 기능
function storeStatus() {
    let id = document.getElementById("id").innerHTML
    const api = '/api/waitingList/';

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

// 인증메일 전송 기능
function sendMail() {
    let email = document.querySelector('.email').value;                                                         // 사용자가 입력한 이메일 주소 값 저장
    const api = '/api/login/mailConfirm/';                                                                              // 요청 API


    if (email == "") {                                                                                                  // 이메일이 입력되지 않았을 경우 알럿 생성
        alert("메일 주소가 입력되지 않았습니다.");
    } else {
        axios({
            method: "post",
            url: baseUrl + api,
            data: JSON.stringify(
                {email: email}                                                                                    // Post 방식으로 API 호출, Json 방식으로 전달
            ),
            headers: {
                "Content-Type": "application/json; charset=utf-8"                                                       // responseType: "json" // [응답 데이터 : stream , json]
            },
        })
            .then(function (response) {                                                                                 // 성공했을 때
                alert("인증번호가 전송되었습니다. 메일을 확인해주세요!")
                code = JSON.stringify(response.data);                                                                   // 이메일로 전송한 코드 저장
            })
            .catch(function (error) {
                console.log("ERROR : " + JSON.stringify(error));
            });
    }
}

// 메일 인증코드 유효성 검사 기능
function checkCode() {
    invailedCode = code.replace(/\"/gi, "");                                                      // 전송된 이메일 코드(Json.stringfy로 Data 받아올 시 " " 제거하여 가져옴)
    let emailCode = document.querySelector(".code").value;                                                      // 사용자가 입력한 코드

    if (emailCode == invailedCode ) {                                                                                    // 사용자가 입력한 코드와 발송된 코드가 동일할 경우
        isCertification = true;                                                                                         // isCertification true로 변경 (= 미인증시// 웨이팅 신청 불가하게 하도록 위함)
        const emailcheck = document.getElementById('emailcheck');
        emailcheck.style.display = 'none';
        const emailcheckConfirm = document.getElementById('emailcheckConfirm');
        emailcheckConfirm.style.display = 'block';
        alert("확인 되었습니다.");
    } else {
        isCertification = false;                                                                                        // 일치하지 않을 경우 isCertification = false
        alert("코드가 일치하지 않습니다.");
    }
}


// 블로그 리뷰 확인 기능
function storeReview(storeId) {
    let id = storeId;
    const api = '/api/review/';

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
            console.log("");
            console.log("RESPONSE : " + JSON.stringify(response.data));

            const transeBlog = JSON.stringify(response.data);
            var blogList = JSON.parse(transeBlog);
            const blogNum = blogList.length;

            const reviewList = document.getElementsByClassName('reviewList')[0];

            for (let i = 0; i < blogNum; i++) {
                const { content } = blogList[i];

                const review = document.createElement('div');
                review.classList.add('review');

                const img = document.createElement('img');
                img.src = '/css/images/blogger.png';

                const p = document.createElement('p');
                p.classList.add(`blogReview${i}`);

                const reviewContent = document.createTextNode(content);
                p.appendChild(reviewContent);

                review.appendChild(img);
                review.appendChild(p);

                reviewList.appendChild(review);
            }
        })
        .catch(function (error) {
            console.log("");
            console.log("ERROR : " + JSON.stringify(error));
        });
}



