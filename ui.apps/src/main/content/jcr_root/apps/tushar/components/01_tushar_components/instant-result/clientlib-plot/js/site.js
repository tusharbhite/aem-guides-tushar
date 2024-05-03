

    var instantResultDiv=document.getElementById("InstantResultDiv");
	var jsonData=InstantResultDiv.getAttribute("InstantResultJson");
		//alert(jsonData);

function waitForAttribute(selector, attributeName, timeout = 2000) {
  const targetElement = document.querySelector(selector);
  const maxChecks = timeout / 100;
  let checkCount = 0;

  const interval = setInterval(() => {
    checkCount++;
    if (targetElement && targetElement.hasAttribute(attributeName)) {
      clearInterval(interval);
      callback(targetElement.getAttribute(attributeName));
      return;
    }
    if (checkCount >= maxChecks) {
      clearInterval(interval);
      console.error("Timed out waiting for attribute");
    }
  }, 100);
}

// Example usage
const callback = value =>{ //console.log("Element has data-loaded attribute with value:", value);

 var listElement=document.getElementById("InstantResultList");
	var jsonArray=JSON.parse(value);
	//alert(jsonArray[0].pageTitle);
    //alert(jsonArray[0].pagePath);
    //alert(jsonArray[0].primaryImage);

    var tempItemsHolder="";

    for (let i = 0; i < jsonArray.length; i++) {
  		//copyItems.push(jsonArray[i]);
		if(jsonArray[i].primaryImage!="NA")
    	tempItemsHolder+=`  
    						<li><div class="parentofSIbling">
    							<div class="sibling1"><img class="" loading="lazy" src="`+jsonArray[i].primaryImage+`" style="max-width: 50px;" /></div>
        					  <div class="sibling2"><h3 class=" name"><a href="`+jsonArray[i].pagePath+`.html">`+jsonArray[i].pageTitle+`</a></h3>
          					  <p class="born hideElement">`+jsonArray[i].pagePath+`</p></div></div>
        					</li>

            			  `;

	}

   listElement.innerHTML =tempItemsHolder;
/*
    var options = {
      valueNames: [ 'name', 'born' ], page: 3, pagination: true
    };

    var userList = new List('users', options);
*/

  var options = {
  valueNames: ['name','born'],
  page: 5,
  pagination: true
    };

    var userList = new List('users', options);



}
waitForAttribute('#InstantResultDiv', 'InstantResultJson', callback);


//





