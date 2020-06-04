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
                            if ("0".localeCompare(result) == 0) {
                                window.location.href = "PaginaPrincipal.aspx";
                            }
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
                            $.ajax({
                                url: "GetAjax.aspx?op=buscarAmigosOtroUsuario&idBuscado=" + idBucado,
                                dataType: "json",
                                method: "GET",
                                crossDomain: true,
                                success: function (result) {
                                    if ("0".localeCompare(result) == 0) {
                                        window.location.href = "Login.aspx";
                                    } else {
                                        $("#amigos").append("<label style=\"font-weight: 900;\">Amigos de " + nombreDueño + "</label><br/>");
                                        var jsonObject = jQuery.parseJSON(JSON.stringify(result));
                                        $.each(jsonObject, function (i, item) {
                                            var elDiv = $("<div class=\"div-amigo idAmigo" + item.id + "\"></div > ");
                                            elDiv.append("<a href=\"PantallaOtroUsuario.aspx?id=" + item.id + "\">" + item.nombre + "</a> <br/>");
                                            elDiv.append(item.email + "<br/>");
                                            $("#amigos").append(elDiv);
                                        });
                                    }
                                    $.ajax({
                                        url: "GetAjax.aspx?op=posts&idBuscado=" + idBucado,
                                        dataType: "json",
                                        method: "GET",
                                        crossDomain: true,
                                        success: function (result) {
                                            if ("0".localeCompare(result) == 0) {
                                                window.location.href = "Login.aspx";
                                            } else {
                                                $("#posts").append("<div style=\"font-weight: 900;text-align: center;\">Posts de " + nombreDueño + "</div><br/>");
                                                var jsonObject = jQuery.parseJSON(JSON.stringify(result));
                                                $.each(jsonObject, function (i, item) {
                                                    var elDiv = $("<div class=\"post\"></div > ");
                                                    var elDiv2 = $("<div class=\"postFecha\"></div > ");
                                                    var elDivComentarios = $("<div class=\"comentarios\"></div > ");
                                                    elDiv2.append(" <label>Fecha de publicación: " + item.fecha.substring(0, item.fecha.length - 1) + "</label><br>");
                                                    elDiv.append(elDiv2);
                                                    elDiv.append("Autor: " + nombreDueño + "</label><br>");
                                                    elDiv.append(item.contenido + "<br/>");
                                                    //elDiv.css({ "padding-bottom": 5, "border-bottom": "solid 1px black" });
                                                    $.ajax({
                                                        url: "GetAjax.aspx?op=comentarios&idPost=" + item.id,
                                                        dataType: "json",
                                                        method: "GET",
                                                        crossDomain: true,
                                                        success: function (res) {
                                                            if ("0".localeCompare(res) == 0) {
                                                                window.location.href = "Login.aspx";
                                                            } else {
                                                                var jsonObject2 = jQuery.parseJSON(JSON.stringify(res));
                                                                $.each(jsonObject2, function (i2, item2) {
                                                                    var elDivfechacom = $("<div class=\"comFecha\"></div >");
                                                                    var elDivcomentario = $("<div class=\"comentario\"></div >");
                                                                    elDivfechacom.append("Fecha comentario: " + item2.fecha.substring(0, item2.fecha.length - 1));
                                                                    elDivcomentario.append(elDivfechacom);
                                                                    elDivcomentario.append("<a href=\"PantallaOtroUsuario.aspx?id=" + item2.idCreador + "\">" + item2.autor + "</a> <br/>");
                                                                    elDivcomentario.append(item2.contenido + "<br/>");
                                                                    elDivComentarios.append(elDivcomentario);
                                                                })
                                                            }
                                                        }
                                                    });
                                                    elDiv.append("<div class=\"contBtComentar\"><button class=\"btComentar\">Comentar Post</button><div>");
                                                    elDiv.append(elDivComentarios);
                                                    $("#posts").append(elDiv);
                                                });
                                            }
                                            if (miId != idBucado) {
                                                $(".contenedor").show();
                                                $(".carga").hide();
                                            }
                                            manejadorRedimension();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
            
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
