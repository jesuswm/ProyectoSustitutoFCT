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
            ['color', ['color']],
            ['insert', ['link']],
        ]
    });
    $(window).on("resize", function () {
        manejadorRedimension();
    })
    manejadorRedimension();
}