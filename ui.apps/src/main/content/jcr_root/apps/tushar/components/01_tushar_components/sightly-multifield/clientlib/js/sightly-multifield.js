
function openCity(evt, cityName) {
  var i, tabcontent, tablinks;
  var parentDiv = evt.target.closest(".parent-div-tab");
  //console.log(parentDiv.className)
    //var bElement = parentDiv.previousElementSibling;
  // Find the 3 div elements with class 'tabcontent' inside the b element
  //var tabcontent = bElement.querySelectorAll("div.tabcontent");

  tabcontent = parentDiv.querySelectorAll(".tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }

  tablinks = parentDiv.querySelectorAll(".tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  parentDiv.querySelector("#"+cityName).style.display = "block";
  evt.currentTarget.className += " active";
}

document.addEventListener('DOMContentLoaded', function() {
var buttons = document.querySelectorAll('.first-button-link');
buttons.forEach(function(button) {
  button.click();
});


});


