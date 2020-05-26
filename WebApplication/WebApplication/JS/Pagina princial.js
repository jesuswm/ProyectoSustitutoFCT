$(document).ready(function () {
    inicializar();
});
function manejadorRedimension() {
    $("#amigos").height($("#amigos").parent().height() - $("#info").outerHeight());
}
function inicializar() {
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
                                var elDiv = $("<div class=\"div-amigo idAmigo"+item.id+"\"></div > ");
                                elDiv.append(item.nombre + "<br/>");
                                elDiv.append(item.email + "<br/>");
                                //elDiv.css({ "padding-bottom": 5, "border-bottom": "solid 1px black" });
                                $("#amigos").append(elDiv);
                            });
                            
                        }
                    }
                });
            }
        }
    });
    $(window).on("resize", function () {
        manejadorRedimension();
    })
    manejadorRedimension();
    //$("#user-info").append("informacion del usuario");
    //$("#user-post").append("posts del usuario");
    //$("#user-request").append("peticiones del usuario");
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