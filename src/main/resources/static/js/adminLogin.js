const baseUrl = "https://pintable.co.kr";
// const baseUrl = "http://localhost:8080";

function adminLogin() {
    const inputId = document.querySelector('#id').value;
    const storeId = inputId.replace("\"", "");
    const api = '/api/storeStatus/'
    const adminapi = '/admin'

    if(inputId == ""){
        alert("스토어 아이디가 입력되지 않았습니다.");
    }else {
            axios({
            method: "get",
            url: baseUrl + api + storeId,
            data: JSON.stringify(
            ),
            headers: {
                "Content-Type": "application/json; charset=utf-8"
            },
        })
            .then(function (response) {
                const transferStatus = JSON.stringify(response.data);                                                        // response값을 가져와서 transferStatus에 저장
                localStorage.setItem("storeId", JSON.stringify(storeId));
                location.href=baseUrl+adminapi;
            })
            .catch(function (error) {
                console.log("ERROR : " + JSON.stringify(error));
                alert("존재하지 않는 스토어ID 입니다.")
            });


    }
}
