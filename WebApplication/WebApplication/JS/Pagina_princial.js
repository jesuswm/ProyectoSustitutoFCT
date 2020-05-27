$(document).ready(function () {
    inicializar();
});
function manejadorRedimension() {
    $("#amigos").height($("#amigos").parent().height() - $("#info").outerHeight()-22);
    $("#posts").height($("#posts").parent().height() - $("#divNewPost").outerHeight() - 22);
    $("#peticiones").height($("#peticiones").parent().height()- 22);
}
function inicializar() {
    $("body").hide();
    var miNombre;
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
                $("#info").append("<a href=\"#\">Editar perfil</a><br/>");
                $("#info").append("<a id=\"cerrar\" href=\"#\">Cerrar sesión</a><br/>");
                miNombre = result.nombre;
                //$("#info").append("<hr/>");
                $("#cerrar").click(function (e) {
                    cerrarSesion();
                    e.preventDefault();
                });
                $.ajax({
                    url: "GetAjax.aspx?op=amigos",
                    dataType: "json",
                    method: "GET",
                    crossDomain: true,
                    success: function (result) {
                        if ("0".localeCompare(result) == 0) {
                            window.location.href = "Login.aspx";
                        } else {                           
                            $("#amigos").append("<label style=\"font-weight: 900;\">Tus amigos</label><br/>");
                            var jsonObject = jQuery.parseJSON(JSON.stringify(result));
                            $.each(jsonObject, function (i, item) {
                                var elDiv = $("<div class=\"div-amigo idAmigo" + item.id + "\"></div > ");
                                elDiv.append("<a href=\"Buscar.aspx?id=" + item.id + "\">"+item.nombre+"</a> <br/>");
                                elDiv.append(item.email + "<br/>");
                                $("#amigos").append(elDiv);
                            });
                        }
                        $.ajax({
                            url: "GetAjax.aspx?op=misPost",
                            dataType: "json",
                            method: "GET",
                            crossDomain: true,
                            success: function (result) {
                                if ("0".localeCompare(result) == 0) {
                                    window.location.href = "Login.aspx";
                                } else {
                                    $("#posts").append("<div style=\"font-weight: 900;text-align: center;\">Tus posts</div><br/>");
                                    var jsonObject = jQuery.parseJSON(JSON.stringify(result));
                                    $.each(jsonObject, function (i, item) {
                                        var elDiv = $("<div class=\"post\"></div > ");
                                        var elDiv2 = $("<div class=\"postFecha\"></div > ");
                                        var elDivComentarios = $("<div class=\"comentarios\"></div > ");
                                        elDiv2.append(" <label>Fecha de publicación: " + item.fecha.substring(0, item.fecha.length - 1) + "</label><br>");
                                        elDiv.append(elDiv2);
                                        elDiv.append("Autor: " + miNombre + "</label><br>");
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
                                                            elDivfechacom.append("Fecha comentario: "+item.fecha.substring(0, item2.fecha.length - 1));
                                                            elDivcomentario.append(elDivfechacom);
                                                            elDivcomentario.append("<a href=\"Buscar.aspx?id=" + item2.idCreador + "\">" + item2.autor + "</a> <br/>");
                                                            elDivcomentario.append(item2.contenido + "<br/>");
                                                            elDivComentarios.append(elDivcomentario);
                                                    })
                                                }
                                            }
                                        });
                                        elDiv.append(elDivComentarios);
                                        $("#posts").append(elDiv);
                                    });
                                    $.ajax({
                                        url: "GetAjax.aspx?op=peticiones",
                                        dataType: "json",
                                        method: "GET",
                                        crossDomain: true,
                                        success: function (result) {
                                            if ("0".localeCompare(result) == 0) {
                                                window.location.href = "Login.aspx";
                                            } else {
                                                $("#peticiones").append("<div style=\"font-weight: 900;text-align: center;\">Peticiones pendientes</div><br/>");
                                                var jsonObject = jQuery.parseJSON(JSON.stringify(result));
                                                $.each(jsonObject, function (i, item) {
                                                    var elDivPeticion = $("<div class=\"peticion\"></div >");
                                                    var btnAceptar = $("<button class=\"btnAceptar\">Aceptar</button>");
                                                    var btnRechazer = $("<button class=\"btnrechazar\">rechazar</button>");
                                                    elDivPeticion.append("El usuario " + item.nombreSolicitante + " quiere ser tu amigo<br/>");
                                                    btnAceptar.click(function (e) {
                                                        //alert("Aceptar"+ item.id);
                                                        contestarPeticion(item.id, "true");
                                                        e.preventDefault();
                                                    });
                                                    btnRechazer.click(function (e) {
                                                        //alert("Rechazar" + item.id);
                                                        contestarPeticion(item.id, "false");
                                                        e.preventDefault();
                                                    });

                                                    elDivPeticion.append(btnAceptar);
                                                    elDivPeticion.append("<br/>");
                                                    elDivPeticion.append(btnRechazer);
                                                    elDivPeticion.append("<br/>");
                                                    $("#peticiones").append(elDivPeticion);;
                                                })
                                            }
                                            $("body").show();
                                            manejadorRedimension();
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            }
        }
    });
    $(window).on("resize", function () {
        manejadorRedimension();
    })
    manejadorRedimension();
}
function contestarPeticion(idPeticion,aceptar) {
    $.ajax({
        url: "GetAjax.aspx?op=contestarPeticion&idPeticion=" + idPeticion + "&aceptar=" + aceptar,
        method: "GET",
        crossDomain: true,
        success: function (result) {
            if ("1".localeCompare(result) == 0) {
                window.location.href = "PaginaPrincipal.aspx";
            } else {
                window.location.href = "Login.aspx";
            } 
        }
    });
}
function cerrarSesion() {
    $.ajax({
        url: "GetAjax.aspx?op=cerrar",
        method: "GET",
        crossDomain: true,
        success: function (result) {
            if ("1".localeCompare(result) == 0) {
                window.location.href = "Login.aspx";
            } 
        }
    });
}