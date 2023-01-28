const baseUrl = "https://pintable.co.kr";
// const baseUrl = "http://localhost:8080";

function adminLogin() {
    const inputId = document.querySelector('#id').value;
    const storeId = inputId.replace("\"", "");
    const api = '/admin';

    if(inputId == ""){
        alert("스토어 아이디가 입력되지 않았습니다.");
    }else {
        localStorage.setItem("storeId", JSON.stringify(storeId));
        location.href=baseUrl+api;
    }
}
