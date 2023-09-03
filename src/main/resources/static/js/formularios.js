//// JS DEL MODAL ////

var modal = document.getElementById("myModal");
var btn = document.getElementById("myButton");
var span = document.getElementsByClassName("close")[0];

btn.onclick = function() {

    modal.style.display = "block";
}


window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
  
}

function closeModal() {
    modal.style.display = "none";
  }


  function validarFormulario() {
    var inputs = document.querySelectorAll('#form input[required]');
    for (var i = 0; i < inputs.length; i++) {
      if (!inputs[i].value) {
        return false; // Si algún campo requerido está vacío, retorna falso
      }
    }
    return true; // Si todos los campos requeridos están completos, retorna verdadero
  }