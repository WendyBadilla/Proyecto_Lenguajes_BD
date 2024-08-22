
/* La siguiente función se utiliza para visualizar la imagen seleccionada en la
 * página html donde se desea "cargar" utilizando un llamado "ajax"*/
function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#blah')
                    .attr('src', e.target.result)
                    .height(200);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

$(document).ready(function () {
    $('#filtroForm').on('submit', function (event) {
        event.preventDefault(); // Evita el envío normal del formulario
        cargarLocales();
    });
});

function cargarLocales() {
    var $form = $('#filtroForm');
    $.ajax({
        url: $form.attr('action'),
        type: 'GET',
        data: $form.serialize(),
        success: function (response) {
            $('#resultsBlock').html(response);
        }
    });
}





