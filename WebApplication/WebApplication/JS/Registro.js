$(document).ready(function () {
    inicializar();
});
function inicializar() {
    $("form").submit(registrarse);
}
function registrarse() {
    var nombre = $("#nombre").val();
    var pass = $("#pass").val();
    var confpass = $("#confpass").val();
    var email = $("#email").val();
    if (pass == confpass) {
        $.ajax({
            url: "GetAjax.aspx?op=registro&nombre=" + nombre + "&pass=" + pass + "&email=" + email,
            // dataType: "json",
            method: "GET",
            crossDomain: true,
            success: function (result) {
                if ("1".localeCompare(result) == false) {
                    window.location.href = "Login.aspx";
                } else {
                    alert("Valores introducidos invalidos o ya en uso");
                }
            }
        });
        return false;
    } else {
        alert("Contraseña y confirmar contraseña no coinciden");
        return false;
    }
    
}
//function registrar() {
//    $.ajax({
//        url: "GetAjax.aspx?op=login&u=jandro&p=jandro",
//        dataType: "json",
//        method: "GET",
//        crossDomain: true,
//        success: function (result) {
//            $("#div1").html(JSON.stringify(result));
//        }
//    });
//}