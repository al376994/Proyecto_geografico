const acordion = document.getElementsByClassName('accordion-button');
for (i=0; i<acordion.length; i++) {
  acordion[i].addEventListener('click', function () {
    this.classList.toggle('collapsed')
	document.getElementById(this.attributes['accordion-target'].value).classList.toggle('collapse');
  })
}

function mySubmitButtonOnEnter(button) {
  if(event.keyCode==13) document.getElementById(button).click();
}