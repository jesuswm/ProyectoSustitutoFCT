$(document).ready(function () {
    inicializar();
});
function manejadorRedimension() {
    $("#cont-form").height($("body").height() - $("#na").outerHeight()+2 );
}

function inicializar() {
    //var altura = $("body").height() - $("#na").outerHeight() * 2 - $("#div-radios").outerHeight() - 24;
    $('#summernote').summernote({
        tooltip: false,
        disableResizeEditor: true,
        placeholder: 'Redacta aqui tu post',
        tabsize: 2,
        height: "500px",
        toolbar: [
            ['font', ['bold', 'underline', 'clear']],
            ['insert', ['link']],
        ]
    });
    $("#botPublicar").click(function () {
        if ($('#summernote').summernote('isEmpty') == false) {
            var radioValue = $("input[name='publico']:checked").val();
            var contenido = $('#summernote').summernote('code');
            contenido = contenido.replace(/"/g, "\\\"");
            contenido = contenido.replace(/&nbsp;/g, " ");
            alert(contenido);
            $.ajax({
                url: "GetAjax.aspx?op=miUsuario",
                dataType: "json",
                method: "GET",
                crossDomain: true,
                success: function (result) {
                    if ("0".localeCompare(result) == 0) {
                        window.location.href = "Login.aspx";
                    } else {
                        $.ajax({
                            url: "GetAjax.aspx?op=CrearPost&contenido=" + contenido + "&privado=" + radioValue,
                            // dataType: "json",
                            method: "GET",
                            crossDomain: true,
                            success: function (result) {
                                if ("1".localeCompare(result) == false) {
                                    window.location.href = "PaginaPrincipal.aspx";
                                } else {
                                    alert("No se ha podido publicar el post");
                                }
                            }
                        });
                    }
                }
            });
        } else {
            alert("Introduzca un contenido en su post");
        }
        return false;
    });
    
    $(window).on("resize", function () {
        manejadorRedimension();
    })
    manejadorRedimension();
}