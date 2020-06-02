$(document).ready(function () {
    inicializar();
});
function manejadorRedimension() {
    $("#cont-form").height($("body").height() - $("#info").outerHeight()-13);
}
function inicializar() {
    $(".carga").show();
    $.ajax({
        url: "GetAjax.aspx?op=miUsuario",
        dataType: "json",
        method: "GET",
        crossDomain: true,
        success: function (result) {
            if ("0".localeCompare(result) == 0) {
                window.location.href = "Login.aspx";
            } else {
                //var jsonObject = jQuery.parseJSON(JSON.stringify(result));
                $("#info").append("<label style=\"font-weight: 900;\">Tu usuario</label><br/>");
                $("#info").append(result.nombre + "<br/>");
                $("#info").append(result.email + "<br/>");
                manejadorRedimension();
            }
        }
    });
    $("#botCancelar").click(function () {
        window.location.href = "PaginaPrincipal.aspx";
    });
    $("#botCambiar").click(function () {
        cambiar();
    });
    $(".carga").hide();
    $(window).on("resize", function () {
        manejadorRedimension();
    })
}
function cambiar() {
    $("#error").hide();
    var nombre = $("#impnombre").val();
    var email = $("#impemail").val();
    var nuevaContraseña = $("#impNewPass").val();
    var nuevaContraseñaConf = $("#impNewPassConf").val();
    var contraseñaActual = $("#impPass").val();
    var urlpeticion = "GetAjax.aspx?op=modificarUsuario"
    if (nombre.trim() != "") {
        urlpeticion = urlpeticion + "&nombre=" + nombre.trim();
    }
    if (email.trim() != "") {
        urlpeticion = urlpeticion + "&email=" + email.trim();
    }
    if (nuevaContraseña != "") {
        urlpeticion = urlpeticion + "&newPass=" + nuevaContraseña;
    }
    if (contraseñaActual != "") {
        urlpeticion = urlpeticion + "&OldPass=" + contraseñaActual;
    }
    //alert(urlpeticion);
    if (nuevaContraseña == nuevaContraseñaConf) {
        $.ajax({
            url: urlpeticion,
            // dataType: "json",
            method: "GET",
            crossDomain: true,
            success: function (result) {
                if ("1".localeCompare(result) == false) {
                    window.location.href = "PaginaPrincipal.aspx";
                } else {
                    $("#error").html("No se ha podido modificar el usuario. La contrasela introducida no es correcta o el email ya esta en uso");
                    $("#error").show();
                    manejadorRedimension();
                }
            }
        });
    } else {
        $("#error").html("Los valores de nueva contraseña y confirmar nueva contraseña no coinciden");
        $("#error").show();
    }
    return false;
}