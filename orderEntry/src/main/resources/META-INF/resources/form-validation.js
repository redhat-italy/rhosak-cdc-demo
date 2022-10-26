(function () {
  'use strict'

  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.querySelectorAll('.needs-validation')
  
  // Loop over them and prevent submission
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        form.classList.remove('has-errors') 
        
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()      
          form.classList.add('has-errors')          
        }

        form.classList.add('was-validated')
      }, false)
    })
})()
