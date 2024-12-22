//alert("clientlib-movies-called");

//Related Items JS
window.addEventListener('load', function() {
  // Get the value of the movieJson attribute from the div
  const movieJsonDiv = document.getElementById('movieJsonDiv');
  if (movieJsonDiv != null)
  {
  const relatedItemsDiv = document.getElementById('relatedItemsDiv');
 
  const movieJsonString = movieJsonDiv.getAttribute('moviejson');
  
  // Parse the JSON string into a JavaScript object
  const jsonObject = JSON.parse(movieJsonString);
	//alert(jsonObject)

  // Access and print the value of jsonObject.short.name
  const shortName = jsonObject.short.name;
  //alert("Short Name:"+ jsonObject.short.name.toString());
                //edgesArray=jsonResponse?.main?.moreLikeThisTitles?.edges[0]?.node?.titleText?.text ?: "NA"
			// Extract edges array (handling potential errors)
            let edgesArray = jsonObject?.main?.moreLikeThisTitles?.edges || [];
            
            // Check if edges exist and have elements
            if (edgesArray.length > 0) {
            	var tempItemsHolder="";
              //console.log(edgesArray); // Print the entire edges array
               for (let i = 0; i < edgesArray.length; i++) {
                /*  console.log(edgesArray[i].node.titleText.text);
                  console.log(edgesArray[i].node.primaryImage.url);
                  console.log(edgesArray[i].node.ratingsSummary.aggregateRating);
                  console.log(edgesArray[i].node.id);
                  console.log(edgesArray[i].node.ratingsSummary.voteCount);
                */
            var classToHide;
            if(i>=4){
				classToHide=" hideElement secondaryItems";
            }
            else{
            classToHide="";
            }
					tempItemsHolder+=`  <div class="col`+classToHide+`">
                                            <div class="card h-100">
                								<div style="height: 250px;overflow: hidden;">
                                              <img src="`+edgesArray[i].node.primaryImage.url+`" class="card-img-top" alt="..."></div>
                                              <div class="card-body">
                                                <h5 class="card-title">`+edgesArray[i].node.titleText.text+`</h5>
                                                <p class="card-text"></p>
                                              </div>
                                              <div class="card-footer">
                <small class="text-muted">Rating: `+edgesArray[i].node.ratingsSummary.aggregateRating+` /10 </small>
                                              </div>
                                            </div>
                                          </div>

            						 `;
                }
            //console.log(tempItemsHolder);
            //console.log(relatedItemsDiv.getAttribute('id'));
            relatedItemsDiv.innerHTML =tempItemsHolder;

            } else {
              console.log("No edges found");
            }

    const viewallrelateditems = document.getElementById("viewallrelateditems");
	const relatedItemsBody = document.getElementById("related-items");

            viewallrelateditems.addEventListener("click", function() {

				const elements = document.querySelectorAll('.secondaryItems');
                    elements.forEach((element) => {
                        if(element.classList.contains('hideElement')){
                            element.classList.remove('hideElement');
                    }else {
                            element.classList.add('hideElement');
                    }
                });
				const buttonText = viewallrelateditems.innerText;
            	viewallrelateditems.innerText = buttonText === 'View All Reated Items' ? 'View Less Related Items' : 'View All Reated Items';
				relatedItemsBody.scrollIntoView();	

            });

	 const viewallcast = document.getElementById("viewallcast");
    const castBody = document.getElementById("movie-cast-id");


	viewallcast.addEventListener("click", function() {

				const elements = document.querySelectorAll('.secondarycast');
                    elements.forEach((element) => {
                        if(element.classList.contains('hideElement')){
                            element.classList.remove('hideElement');
                    }else {
                            element.classList.add('hideElement');
                    }
                });
				const buttonText = viewallcast.innerText;
            	viewallcast.innerText = buttonText === 'View Full Cast' ? 'View Less Cast' : 'View Full Cast';
				castBody.scrollIntoView();	

            });
          }
});
