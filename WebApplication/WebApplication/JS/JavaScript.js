$(document).ready(function(){
    inicializar();
});
function inicializar() {
    $("button").submit(function () {
        //$.ajax({
        //    url: "http://localhost:8080/RestProyect/rest/Usuarios",
        //    dataType: "json",
        //    method: "GET",
        //    crossDomain: true,
        //    success: function (result) {
        //        $("#div1").html(JSON.stringify(result));
        //    }
        //});
        $.ajax({
            url: "GetAjax.aspx?op=login&u=jandro&p=jandro",
            dataType: "json",
            method: "GET",
            crossDomain: true,
            success: function (result) {
                $("#div1").html(JSON.stringify(result));
            }
        });
    });
}
