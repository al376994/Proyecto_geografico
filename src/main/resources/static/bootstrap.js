const acordion = document.getElementsByClassName('accordion-item');
for (i=0; i<acordion.length; i++) {
  acordion[i].addEventListener('click', function () {
    this.children[0].classList.toggle('collapsed')
	this.children[1].classList.toggle('collapse')
  })
}