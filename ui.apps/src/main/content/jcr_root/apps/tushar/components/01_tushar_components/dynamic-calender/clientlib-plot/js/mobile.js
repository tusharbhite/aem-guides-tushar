window.addEventListener('load', function() {
  const calenderDiv = document.getElementById('calenderDiv');

  var calenderJsonString = calenderDiv.getAttribute('calenderJson');
  // Parse the JSON string into a JavaScript object
    calenderJsonString=calenderJsonString.replaceAll("\r","")
  var jsonObject = JSON.parse(calenderJsonString);


  // calenderDiv = document.getElementById('calenderDiv');
  const parentOfRows = document.getElementById('parentOfRows');

  const allElements = document.querySelectorAll('.calenderDiv #table-body .row');
    allElements.forEach((element) => {
		console.debug(element)
    });
	createWeek(new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() ),"neutral");


	var prevButtonMob = document.getElementById("prev-week");
    var nextButtonMob = document.getElementById("next-week");
    var currButtonMob = document.getElementById("curr-week");

    currButtonMob.onclick = function changeMonthPrev() {


       currentDate =  new Date();
       createWeek(currentDate, "neutral");
        console.log("curr clicked");
         //updateEventsForFullMonth();
    }

    prevButtonMob.onclick = function changeMonthPrev() {
         	var parentRow = document.getElementById("parentOfRows");
    var sundayDay=parentRow.getAttribute("sundayDay");
    var sundayMonth=parentRow.getAttribute("sundayMonth");
	var sundayYear=parentRow.getAttribute("sundayYear");
	const sundayDateDOM = new Date(sundayYear+"-"+ sundayMonth+"-"+sundayDay);


       currentDate = sundayDateDOM;
       createWeek(currentDate, "left");
        console.log("prev clicked");
         //updateEventsForFullMonth();
    }
    nextButtonMob.onclick = function changeMonthNext() {
         	var parentRow = document.getElementById("parentOfRows");
    var sundayDay=parentRow.getAttribute("sundayDay");
    var sundayMonth=parentRow.getAttribute("sundayMonth");
	var sundayYear=parentRow.getAttribute("sundayYear");
	const sundayDateDOM = new Date(sundayYear+"-"+ sundayMonth+"-"+sundayDay);


       currentDate = sundayDateDOM;
       createWeek(currentDate, "right");
         //updateEventsForFullMonth();
    }


});

function createWeek(date, side) {
var newDate;
    if(side=="left"){
		newDate = new Date(date.getTime());
	  	newDate.setDate(newDate.getDate() - 7);
		date=newDate;

    }
   else if(side=="right"){
       	newDate = new Date(date.getTime());
	  	newDate.setDate(newDate.getDate() + 8);
		date=newDate;

    }
     if(side=="neutral"){
		date= new Date();
    }



// Example usage:
const today = date
const sundayDate = getSundayOfWeek(today);
 var parentRow = document.getElementById("parentOfRows");
    parentRow.setAttribute("sundayDay",sundayDate.getDate());
    parentRow.setAttribute("sundayMonth",sundayDate.getMonth() + 1);
	parentRow.setAttribute("sundayYear",sundayDate.getFullYear());


//console.log("Today's date:", today);
//console.log("Sunday of this week:", sundayDate);



const week = getWeekDays(today);

//console.log("Week starting from this Sunday:", week);

// Accessing individual dates in the array (optional)
     var monthName = currentDate.toLocaleString("en-US", {
          month: "long"
       });
document.getElementById("mobileCalenderLabel").innerHTML =monthName+" " +sundayDate.getFullYear();


parentOfRows.innerHTML="";
    var tempRowsHolder="";
    var tableheader = document.getElementById("table-header");
        var curmonth= tableheader.getAttribute("month");
            var currDate= today;	


for (i = 0; i < week.length; i++) {
//console.log(getDayName(week[i])+"="+week[i].getDate()+"/"+monthName+"/"+week[i].getFullYear());
//Sunday=7/April/2024

tempRowsHolder+=`
				        <div class="col col-par">  
                            <div class="row row-cols-2"  day="`+week[i].getDate()+`">
                                 <div class="col-4"><span class="stacked-spans date-span-mob fw-bold">`+monthName+" "+week[i].getDate()+`</span><span class="stacked-spans day-span-mob">`+getDayName(week[i])+`</span></div>
                                 <div class="col-8 eventDiv" ><span class="stacked-spans event1span"></span><span class="stacked-spans event2span"></span></div>
                            </div>
                        </div><br/>
				`;

} 
parentOfRows.innerHTML=tempRowsHolder;
updateWeekEvents(date)
}

function updateWeekEvents(currDate){

		var appendCount=0;
    	var curmonth=currDate.getMonth() + 1;

        //var tableheader = document.getElementById("table-header");
    var parentRow = document.getElementById("parentOfRows");
    var curmonth=parentRow.getAttribute("sundayMonth");

       // console.log("curmonth"+ typeof curmonth);
        const allElements = document.querySelectorAll('#parentOfRows .col .row');

        // Loop through each element and print its text content
        allElements.forEach((element) => {
          var appendCount=0;
            if(appendCount<2){
              const day = element.getAttribute('day'); // Extract text content with trimming
              //console.log(`Element: ${text}`);
                for(let i=0; i<jsonObject.length; i++){
                    var dateString = jsonObject[i].releaseDate
                    var dateObject = new Date(parseInt(dateString.split("-")[2]), parseInt(dateString.split("-")[1]) - 1, parseInt(dateString.split("-")[0]));
                    var jsonMonth=dateObject.getMonth() + 1
                    jsonMonth=jsonMonth.toString()
                    var jsonDay=dateObject.getDate().toString();
                    //console.log(jsonMonth+" "+curmonth);
                    if(jsonMonth.indexOf('0')>=0 && jsonMonth!="10"){
                        jsonMonth=jsonMonth.replaceAll('0','')
                    }
                    if(jsonMonth.toString() == curmonth && jsonDay == day && appendCount<2){
                        console.log("currentReleaseDates "+jsonObject[i].releaseDate);
						eventDiv=element.querySelector('.eventDiv');
                        var eventLink = document.createElement('a')
                        eventLink.innerHTML ='<i class=\"icon-movies fa-solid fa-film\"></i> '+jsonObject[i].movieName;
                        eventLink.setAttribute("href",jsonObject[i].moviePath+".html")
                        eventLink.setAttribute("class","stacked-link truncate-text mx-1")
                        if(appendCount==0){
							event1span=element.querySelector('.event1span');
                            event1span.appendChild(eventLink);
                        }else if(appendCount==1){
							event2span=element.querySelector('.event2span');
                            event2span.appendChild(eventLink);
                        }
                        //element.appendChild(movieSpan);
                        appendCount++;
                    }

                }
            }
        });

}


 function getSundayOfWeek(date) {
  // Create a copy of the input date to avoid modifying it
  const dateCopy = new Date(date.getTime());

  // Set the day to Sunday (0 for Sunday)
  dateCopy.setDate(dateCopy.getDate() - (dateCopy.getDay() || 7));

  // Return the Sunday date object
  return dateCopy;
}

function getWeekDays(date) {
  // Create a copy of the input date
  const dateCopy = new Date(date.getTime());

  // Set the day to Sunday (0 for Sunday)
  dateCopy.setDate(dateCopy.getDate() - (dateCopy.getDay() || 7));

  // Create an array to store the week's dates
  const week = [];

  // Loop through the week, adding dates to the array
  for (let i = 0; i < 7; i++) {
    const day = new Date(dateCopy.getTime());
    day.setDate(day.getDate() + i);
    week.push(day);
  }

  // Return the array of date objects
  return week;
}


function getDayName(date) {
  // Use the Date object's getDay() method
  const day = date.getDay();

  // Array of day names (adjust for desired locale if needed)
  const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

  // Return the day name using the index from getDay()
  return days[day];
}


