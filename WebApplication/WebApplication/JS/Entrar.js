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
    alert(pass + " " + email);
    $.ajax({
        url: "GetAjax.aspx?op=login&pass=" + pass + "&email=" + email,
       // dataType: "json",
        method: "GET",
        crossDomain: true,
        success: function (result) {
           alert(result);
        }
    });
}
