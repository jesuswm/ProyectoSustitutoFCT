$(document).ready(function () {
    inicializar();
});
function manejadorRedimension() {
    $("#resultados").height($("body").height() - $("#na").outerHeight()-24);
}
function inicializar() {
    $(".contenedor").hide();
    $(".carga").show();
    var busqueda = buscarParametro("search");
    if (busqueda == null) {
        $("#resultados").append("No hay parametros de busqueda");
    } else {
        while (busqueda.includes("+")) {
            busqueda = busqueda.replace("+", " ");
        }
        busqueda=busqueda.trim();
        $.ajax({
            url: "GetAjax.aspx?op=busqueda&buscado=" + busqueda,
            dataType: "json",
            method: "GET",
            crossDomain: true,
            success: function (result) {
                if ("0".localeCompare(result) == 0) {
                    window.location.href = "Login.aspx";
                } else {
                    var nres = 0;
                    $("#resultados").append("<label style=\"font-weight: 900;\">Resultados de la busqueda</label><br/>");
                    var jsonObject = jQuery.parseJSON(JSON.stringify(result));
                    $.each(jsonObject, function (i, item) {
                        var elDiv = $("<div class=\"resultado\"></div > ");
                        elDiv.append("<a href=\"OtroUsuario.aspx?id=" + item.id + "\">" + item.nombre + "</a> <br/>");
                        elDiv.append(item.email + "<br/>");
                        $("#resultados").append(elDiv);
                        nres++;
                    });
                    if (nres == 0){
                        $("#resultados").append("No se han encontrado usuarios<br/>");
                    }
                }
            }
        });
    }
    $(".carga").hide();
    $(".contenedor").show();
    $(window).on("resize", function () {
        manejadorRedimension();
    })
    manejadorRedimension();
}
function buscarParametro(name) {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results == null) {
        return null;
    }
    return decodeURI(results[1]) || 0;
}
