// Define the URL with the servlet endpoint and the query parameter

async function updateSRC(title, imageElement){
//var title="the avengers";
title=title.replaceAll(" ","%20");
//console.log("Title "+title);
    var url ='/bin/movieposter?title='+title;

// Make a GET request to the URL using fetch API
    /*
fetch(url)
  .then(response => response.text()) // Parse the response as text
  .then(data => {
    console.log("Fetched string:", data); // Log the fetched string
    imageElement.src = data;


  })
  .catch(error => {
    console.error("Error:", error); // Handle any errors during the request
  });
*/
    //console.log("fetching url"+url)
  const response = await fetch(url);
  const data = await response.text();
    //console.log("Fetched string:", data); // Log the fetched string

    imageElement.src = data;

}




 async function updateImageUrls() {

  // 2. Select all matching elements
  const slideElements = document.querySelectorAll('.mySlides.fade');

  // 3. Loop through each element
  for (const slide of slideElements) {
    // 4. Find the text element and extract content
    const textElement = slide.querySelector('.text b.movie-title');

    const textContent = textElement.innerHTML;
      //console.log("textContent"+textContent);
    const imageElement = slide.querySelector('img');

	await updateSRC(textContent,imageElement);
  }
}

// Call the function after the content is loaded (replace with your event listener)
window.addEventListener('DOMContentLoaded', updateImageUrls);
