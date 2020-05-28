$(document).ready(function () {
    inicializar();
});
function inicializar() {
    $("form").submit(registrarse);
}
function registrarse() {
    $("#btsub").prop('disabled', true);
    var nombre = $("#nombre").val();
    var pass = $("#pass").val();
    var confpass = $("#confpass").val();
    var email = $("#email").val();
    if (nombre.trim() != "" && email.trim() != "") {
        if (pass == confpass) {
            $.ajax({
                url: "GetAjax.aspx?op=registro&nombre=" + nombre + "&pass=" + pass + "&email=" + email,
                // dataType: "json",
                method: "GET",
                crossDomain: true,
                success: function (result) {
                    if ("1".localeCompare(result) == false) {
                        $.ajax({
                            url: "GetAjax.aspx?op=login&pass=" + pass + "&email=" + email,
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
                        window.location.href = "Login.aspx";
                    } else {
                        $("#error").html("El campo email introducido ya esta en uso en otra cuenta");
                        $("#error").show();
                    }
                }
            });
        } else {
            $("#error").html("Los campos de contraseña y confirmar contraseña no coinciden");
            $("#error").show();
        }
    } else {
        $("#error").html("No puede dejar campos en blanco");
        $("#error").show();
    }
    $("#btsub").prop('disabled', false);
    return false;
    
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