const acordion = document.getElementsByClassName('accordion-button');
for (i=0; i<acordion.length; i++) {
  acordion[i].addEventListener('click', function () {
    this.classList.toggle('collapsed')
	document.getElementById(this.attributes['accordion-target'].value).classList.toggle('collapse');
  })
}