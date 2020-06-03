$(document).ready(function () {
    $(".contenedor").hide();
    $(".carga").show();
    var idBucado = buscarParametro("id");
    var miId;
    var nombreDueño;
    $.ajax({
        url: "GetAjax.aspx?op=miUsuario",
        dataType: "json",
        method: "GET",
        crossDomain: true,
        success: function (result) {
            if ("0".localeCompare(result) == 0) {
                window.location.href = "Login.aspx";
            } else {
                miId = result.id;
            }
            if (miId == idBucado) {
                window.location.href = "PaginaPrincipal.aspx";
            }
            $.ajax({
                url: "GetAjax.aspx?op=usuarioPorId&idBuscado=" + idBucado,
                dataType: "json",
                method: "GET",
                crossDomain: true,
                success: function (result) {
                    $("#info").append("<label style=\"font-weight: 900;\">Usuario propietario</label><br/>");
                    $("#info").append(result.nombre + "<br/>");
                    $("#info").append(result.email + "<br/>");
                    nombreDueño = result.nombre;
                    $.ajax({
                        url: "GetAjax.aspx?op=comprobarAmistad&idBuscado=" + idBucado,
                        dataType: "json",
                        method: "GET",
                        crossDomain: true,
                        success: function (result) {
                            var btnAmigo = $("<button id=\"btAmistad\">Solicitar amistad</button>");
                            btnAmigo.click(function (e) {
                                solicitarAmistad(idBucado);
                                e.preventDefault();
                            });
                            if (result == "3") {
                                //alert("amigos");
                                btnAmigo.prop('disabled', true);
                                $("#info").append(btnAmigo);
                            }
                            else if (result == "2") {
                                //alert("pendiente");
                                btnAmigo.prop('disabled', true);
                                $("#info").append(btnAmigo);
                            }
                            else{
                                //alert("desconocidos");
                                $("#info").append(btnAmigo);
                            }

                        }
                    });
                }
            });
            $(".contenedor").show();
            $(".carga").hide();
            manejadorRedimension();
        }
    });
    $(window).on("resize", function () {
        manejadorRedimension();
    })
    manejadorRedimension();
    $("#btNewPost").click(function (e) {
        window.location.href = "CrearPost.aspx";
    });
});
function manejadorRedimension() {
    $("#amigos").height($("#amigos").parent().height() - $("#info").outerHeight() - 22);
    $("#posts").height($("#posts").parent().height() - 22);
}
function buscarParametro(name) {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results == null) {
        return null;
    }
    return decodeURI(results[1]) || 0;
}
function solicitarAmistad(idUsuario) {
    
    $.ajax({
        url: "GetAjax.aspx?op=solicitarAmistad&idSolicitado=" + idUsuario,
        dataType: "json",
        method: "GET",
        crossDomain: true,
        success: function (result) {
        }
    });
    $("#btAmistad").prop("disabled", true);
}
