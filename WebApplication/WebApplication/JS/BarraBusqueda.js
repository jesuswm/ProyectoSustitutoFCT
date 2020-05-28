$(document).ready(function () {
    $("#form-busqueda").submit(function (event) {
        return buscarUsuario();
    });
});
function buscarUsuario() {
    if ($("#inp-busqueda").val().trim() == "") {
        return false;
    } else {
        true;
    }
}