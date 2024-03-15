// Define the URL with the servlet endpoint and the query parameter
//  https://search.imdbot.workers.dev/?q=ram%20lakhan

//  https://search.imdbot.workers.dev/?tt=tt0098168

var api_url="https://search.imdbot.workers.dev/?";
var imdb_id="tt4110568";

async function updateSongsData(title, articleElement){
//var title="the avengers";
title=title.replaceAll(" ","%20");
console.log("Title "+title);

//1st URL fetch
    var url =api_url+"q="+title;
    console.log("fetching url"+url);

  var response = await fetch(url);
  var data = await response.json();
    console.log("Fetched string:", data); // Log the fetched string
	//console.log("Fetched var imdbid", data.getImdbIds(text);
    if (data.ok && data.description) {
		imdb_id=data.description[0]["#IMDB_ID"];
      console.log("Extracted IMDB IDs:", data.description[0]["#IMDB_ID"]);
    }

 var dateElement = articleElement.querySelector('.multiple-line.article-date');
	dateElement.innerHTML =  data.description[0]["#YEAR"];

  var movieTitleElement = articleElement.querySelector('.article-details span a');
	movieTitleElement.innerHTML =  data.description[0]["#TITLE"];   

//2nd URL fetch
    var url =api_url+"tt="+imdb_id;
    //console.log("fetching url"+url);

   response = await fetch(url);
   data = await response.json();
    console.log("Fetched string:", data); // Log the fetched string
	//console.log("Fetched var imdbid", data.getImdbIds(text);

      console.log("Extracted name:", data.short.name);

 var imageElement = articleElement.querySelector('img');
    imageElement.src = data.short.image;

 var descriptionElement = articleElement.querySelector('.tile-text-box p');
	descriptionElement.innerHTML =  data.short.description;

}




 async function updateSongsArticles() {

  // 2. Select all matching elements
  const articleElements = document.querySelectorAll('.article-tile');

  // 3. Loop through each element
  for (const article of articleElements) {
    // 4. Find the text element and extract content
    const articleHeadingElement = article.querySelector('.tile-text-box a .article-title');

    var textContent = articleHeadingElement.innerHTML;

	// Regular expression to match text inside parentheses
const regex = /\(([^)]+)\)/;

// Match the string against the regular expression
const match = textContent.match(regex);

// Extract the substring if a match is found
let subString;
if (match) {
  subString = match[1]; // Access the captured group within the parentheses
} else {
  subString = "No parentheses found"; // Handle cases where no parentheses exist
}

console.log(subString); // Output: Ram Lakhan

      textContent=textContent.replaceAll(subString,"");
      textContent=textContent.replaceAll("(","");
      textContent=textContent.replaceAll(")","");


articleHeadingElement.innerHTML= textContent;
     // console.log("textContent i"+article);


	await updateSongsData(subString,article);
  }
}

// Call the function after the content is loaded (replace with your event listener)
window.addEventListener('DOMContentLoaded', updateSongsArticles);
