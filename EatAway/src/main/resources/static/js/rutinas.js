
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

document.addEventListener('DOMContentLoaded', function () {
    // Obtén el modal y su contenido
    var modal = document.getElementById('editarResena');
    var calificacionSelect = modal.querySelector('#calificacion');
    var comentarioTextarea = modal.querySelector('textarea[name="comentario"]');
    var idResenaInput = modal.querySelector('input[name="idResena"]');

    // Función para actualizar el modal
    function actualizarModal(event) {
        var button = event.relatedTarget; // Botón que abrió el modal
        var idResena = button.getAttribute('data-id-resena');
        var calificacion = button.getAttribute('data-calificacion');
        var comentario = button.getAttribute('data-comentario');

        // Actualiza los campos del modal
        idResenaInput.value = idResena;
        calificacionSelect.value = calificacion;
        comentarioTextarea.value = comentario;
    }

    // Añade el evento al abrir el modal
    modal.addEventListener('show.bs.modal', actualizarModal);
});


