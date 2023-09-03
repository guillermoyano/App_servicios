// document.getElementById("openModal").addEventListener("click", function() {
//     document.getElementById("myModal").style.display = "block";
//   });
  
//   document.getElementsByClassName("close")[0].addEventListener("click", function() {
//     document.getElementById("myModal").style.display = "none";
//   });

// Obtener el modal y el botón de cerrar
var modal = document.getElementById("myModal");
var closeBtn = document.getElementsByClassName("close")[0];

// Abrir el modal al hacer clic en el botón
document.getElementById("openModal").addEventListener("click", function() {
  modal.style.display = "block";
});

// Cerrar el modal al hacer clic en la X o fuera del modal
closeBtn.addEventListener("click", function() {
  modal.style.display = "none";
});

window.addEventListener("click", function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
});
