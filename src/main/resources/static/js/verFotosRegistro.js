var fileInput = document.getElementById("file-input");
var imagePreview = document.getElementById("image-preview");

fileInput.addEventListener("change", function(event) {
  var file = event.target.files[0];

  if (file) {
    var reader = new FileReader();

    // Verificar el formato del archivo
    if (file.type === "image/jpeg") {
      reader.readAsDataURL(file);

      reader.onload = function(e) {
        imagePreview.src = e.target.result; 
      };
    } else {
      // Mostrar un mensaje de error si el formato no es válido
      alert("El archivo seleccionado no es una imagen JPEG (.jpg).");
    }
  }
});
