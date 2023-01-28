function adminLogin() {
    const inputId = document.querySelector('#id').value;
    const storeId = inputId.replace("\"", "");
    localStorage.setItem("storeId", JSON.stringify(storeId));

    window.location.href = 'http://localhost:8080/';
}