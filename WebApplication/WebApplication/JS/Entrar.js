$(document).ready(function () {
    inicializar();
});
function inicializar() {
    $("#btnEntrar").click(function(e) {
        entrar();
        e.preventDefault();
    });
}
function entrar() {
    var pass = $("#pass").val();
    var email = $("#email").val();
    $.ajax({
        url: "GetAjax.aspx?op=login&pass=" + pass + "&email=" + email,
       // dataType: "json",
        method: "GET",
        crossDomain: true,
        success: function (result) {
            if ("1".localeCompare(result) == 0) {
                window.location.href = "PaginaPrincipal.aspx";
            } else {
                $("#error").html("No se ha encontrado el usuario");
                $("#error").show();
            }
        }
    });
}
