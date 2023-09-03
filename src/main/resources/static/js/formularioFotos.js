  // Obtener referencia a los elementos del DOM
  const fileInput = document.getElementById('file-input');
  const imagePreview = document.getElementById('image-preview');

  // Agregar un evento de carga al input de archivo
  fileInput.addEventListener('change', function (event) {
      const file = event.target.files[0]; // Obtener el archivo seleccionado

      // Validar si se seleccionó un archivo y es una imagen
      if (file && file.type.startsWith('image')) {
          const reader = new FileReader();

          // Función que se ejecuta cuando la imagen se ha cargado correctamente
          reader.onload = function (e) {
              imagePreview.src = e.target.result; // Actualizar la fuente de la imagen previa
          };

          // Leer el archivo como una URL de datos
          reader.readAsDataURL(file);
      }
  });