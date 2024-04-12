	//Editing Events
  const calenderDiv = document.getElementById('calenderDiv');

  var calenderJsonString = calenderDiv.getAttribute('calenderJson');
  // Parse the JSON string into a JavaScript object
    calenderJsonString=calenderJsonString.replaceAll("\r","")
  var jsonObject = JSON.parse(calenderJsonString);
//	console.log(jsonObject[0].movieName)
    //jsonObject[0].releaseDate
    //jsonObject[0].releaseYear
    //jsonObject[0].moviePath

//const dateString = "17-12-2021";
var dateString = jsonObject[0].releaseDate
const dateObject = new Date(parseInt(dateString.split("-")[2]), parseInt(dateString.split("-")[1]) - 1, parseInt(dateString.split("-")[0]));
console.log("dateObject"+dateObject); 

/*
// Extract individual components
const year = dateObject.getFullYear();
const month = dateObject.getMonth() + 1; // Adjust for 0-based indexing
const day = dateObject.getDate();

// Print each component on a separate line with digits only
console.log(year);
console.log(month.toString().padStart(2, '0')); // Pad month with leading zero
console.log(day.toString().padStart(2, '0')); // Pad day with leading zero
*/



    var calendar = document.getElementById("calendar-table");
    var gridTable = document.getElementById("table-body");
    var currentDate = new Date();
    var selectedDate = currentDate;
    var selectedDayBlock = null;
    var globalEventObj = {};
    
    var sidebar = document.getElementById("sidebar");
    
    function createCalendar(date, side) {

		    var tableheaderdiv = document.getElementById("table-header");
			tableheaderdiv.setAttribute("year",date.getFullYear())	
            tableheaderdiv.setAttribute("month",date.getMonth() + 1)	
            tableheaderdiv.setAttribute("day",date.getDate())	


       var currentDate = date;
       var startDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);
    
       var monthTitle = document.getElementById("month-name");
       var monthName = currentDate.toLocaleString("en-US", {
          month: "long"
       });
       var yearNum = currentDate.toLocaleString("en-US", {
          year: "numeric"
       });
       monthTitle.innerHTML = `${monthName} ${yearNum}`;
    
       if (side == "left") {
          gridTable.className = "animated fadeOutRight";
       } else {
          gridTable.className = "animated fadeOutLeft";
       }

//       setTimeout(() => {
          gridTable.innerHTML = "";
    
          var newTr = document.createElement("div");
          newTr.className = "row";
          newTr.setAttribute("date", "helloButton");


          var currentTr = gridTable.appendChild(newTr);

          for (let i = 1; i < (startDate.getDay() || 7); i++) {
             let emptyDivCol = document.createElement("div");
             emptyDivCol.className = "col empty-day";
             currentTr.appendChild(emptyDivCol);
          }
    
          var lastDay = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 0);
          lastDay = lastDay.getDate();
    
          for (let i = 1; i <= lastDay; i++) {
             if (currentTr.children.length >= 7) {
                currentTr = gridTable.appendChild(addNewRow());
             }
             let currentDay = document.createElement("div");
             currentDay.className = "col";
             if (selectedDayBlock == null && i == currentDate.getDate() || selectedDate.toDateString() == new Date(currentDate.getFullYear(), currentDate.getMonth(), i).toDateString()) {
                selectedDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), i);
    
                document.getElementById("eventDayName").innerHTML = selectedDate.toLocaleString("en-US", {
                   month: "long",
                   day: "numeric",
                   year: "numeric"
                });
    
                selectedDayBlock = currentDay;
                setTimeout(() => {
                   currentDay.classList.add("blue");
                   currentDay.classList.add("lighten-3");
                }, 900);
             }
             //currentDay.innerHTML = i;
        	 var daySpan = document.createElement('p')
                        daySpan.innerHTML = i;
                        daySpan.setAttribute("class","daySpan")
                        currentDay.appendChild(daySpan);

    
             //show marks
             if (globalEventObj[new Date(currentDate.getFullYear(), currentDate.getMonth(), i).toDateString()]) {
                let eventMark = document.createElement("div");
                eventMark.className = "day-mark";
                currentDay.appendChild(eventMark);
             }
    
             currentTr.appendChild(currentDay);
          }

          for (let i = currentTr.getElementsByTagName("div").length; i < 7; i++) {
             let emptyDivCol = document.createElement("div");
             emptyDivCol.className = "col empty-day";
             currentTr.appendChild(emptyDivCol);
          }

          if (side == "left") {
             gridTable.className = "animated fadeInLeft";
          } else {
             gridTable.className = "animated fadeInRight";
          }
    
          function addNewRow() {
             let node = document.createElement("div");
             node.className = "row";
             return node;
          }

//		}, !side ? 0 : 270);

	 updateEventsForFullMonth();
    }
    
    createCalendar(currentDate);
    
    var todayDayName = document.getElementById("todayDayName");
    todayDayName.innerHTML = "Today is " + currentDate.toLocaleString("en-US", {
       weekday: "long",
       day: "numeric",
       month: "short"
    });
    
    var prevButton = document.getElementById("prev");
    var nextButton = document.getElementById("next");
    
    prevButton.onclick = function changeMonthPrev() {
       currentDate = new Date(currentDate.getFullYear(), currentDate.getMonth() - 1);
       createCalendar(currentDate, "left");
         //updateEventsForFullMonth();
    }
    nextButton.onclick = function changeMonthNext() {
       currentDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1);
       createCalendar(currentDate, "right");
         //updateEventsForFullMonth();
    }
    
    function addEvent(title, desc) {
       if (!globalEventObj[selectedDate.toDateString()]) {
          globalEventObj[selectedDate.toDateString()] = {};
       }
       globalEventObj[selectedDate.toDateString()][title] = desc;
    }
    
    function showEvents() {
       let sidebarEvents = document.getElementById("sidebarEvents");
       let objWithDate = globalEventObj[selectedDate.toDateString()];
    
       sidebarEvents.innerHTML = "";
    
       if (objWithDate) {
          let eventsCount = 0;
          for (key in globalEventObj[selectedDate.toDateString()]) {
             let eventContainer = document.createElement("div");
             eventContainer.className = "eventCard";
    
             let eventHeader = document.createElement("div");
             eventHeader.className = "eventCard-header";
    
             let eventDescription = document.createElement("div");
             eventDescription.className = "eventCard-description";
    
             eventHeader.appendChild(document.createTextNode(key));
             eventContainer.appendChild(eventHeader);
    
             eventDescription.appendChild(document.createTextNode(objWithDate[key]));
             eventContainer.appendChild(eventDescription);
    
             let markWrapper = document.createElement("div");
             markWrapper.className = "eventCard-mark-wrapper";
             let mark = document.createElement("div");
             mark.classList = "eventCard-mark";
             markWrapper.appendChild(mark);
             eventContainer.appendChild(markWrapper);
    
             sidebarEvents.appendChild(eventContainer);
    
             eventsCount++;
          }
          let emptyFormMessage = document.getElementById("emptyFormTitle");
          emptyFormMessage.innerHTML = `${eventsCount} events now`;
       } else {
          let emptyMessage = document.createElement("div");
          emptyMessage.className = "empty-message";
          emptyMessage.innerHTML = "Sorry, no events to selected date";
          sidebarEvents.appendChild(emptyMessage);
          let emptyFormMessage = document.getElementById("emptyFormTitle");
          emptyFormMessage.innerHTML = "No events now";
       }
    }
    
    gridTable.onclick = function (e) {
    
       if (!e.target.classList.contains("col") || e.target.classList.contains("empty-day")) {
          return;
       }
    
       if (selectedDayBlock) {
          if (selectedDayBlock.classList.contains("blue") && selectedDayBlock.classList.contains("lighten-3")) {
             selectedDayBlock.classList.remove("blue");
             selectedDayBlock.classList.remove("lighten-3");
          }
       }
       selectedDayBlock = e.target;
       selectedDayBlock.classList.add("blue");
       selectedDayBlock.classList.add("lighten-3");
    
       selectedDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), parseInt(e.target.innerHTML));
    
       showEvents();
    
       document.getElementById("eventDayName").innerHTML = selectedDate.toLocaleString("en-US", {
          month: "long",
          day: "numeric",
          year: "numeric"
       });
    
    }
    
    var changeFormButton = document.getElementById("changeFormButton");
    var addForm = document.getElementById("addForm");
    changeFormButton.onclick = function (e) {
       addForm.style.top = 0;
    }
    
    var cancelAdd = document.getElementById("cancelAdd");
    cancelAdd.onclick = function (e) {
       addForm.style.top = "100%";
       let inputs = addForm.getElementsByTagName("input");
       for (let i = 0; i < inputs.length; i++) {
          inputs[i].value = "";
       }
       let labels = addForm.getElementsByTagName("label");
       for (let i = 0; i < labels.length; i++) {
          labels[i].className = "";
       }
    }
    
    var addEventButton = document.getElementById("addEventButton");
    addEventButton.onclick = function (e) {
       let title = document.getElementById("eventTitleInput").value.trim();
       let desc = document.getElementById("eventDescInput").value.trim();
    
       if (!title || !desc) {
          document.getElementById("eventTitleInput").value = "";
          document.getElementById("eventDescInput").value = "";
          let labels = addForm.getElementsByTagName("label");
          for (let i = 0; i < labels.length; i++) {
             labels[i].className = "";
          }
          return;
       }
    
       addEvent(title, desc);
       showEvents();
    
       if (!selectedDayBlock.querySelector(".day-mark")) {
          selectedDayBlock.appendChild(document.createElement("div")).className = "day-mark";
       }
    
       let inputs = addForm.getElementsByTagName("input");
       for (let i = 0; i < inputs.length; i++) {
          inputs[i].value = "";
       }
       let labels = addForm.getElementsByTagName("label");
       for (let i = 0; i < labels.length; i++) {
          labels[i].className = "";
       }

    }

	console.log("ended core");



//window.addEventListener('load', function() {
    //updating events
  //  updateEventsForFullMonth();

    let prevbutton = document.querySelector('#prev');

    prevbutton.addEventListener('click', () => {
        console.log('some event content here...')
     //updateEventsForFullMonth();
    })

    let nextbutton = document.querySelector('#next');

    nextButton.addEventListener('click', () => {
        console.log('some event content here...')
     //updateEventsForFullMonth();
    })


    function updateEventsForFullMonth(){


        var tableheader = document.getElementById("table-header");
        var curmonth= tableheader.getAttribute("month");	

        console.log("curmonth"+ typeof curmonth);
        const allElements = document.querySelectorAll('.calenderDiv #table-body .col');

        // Loop through each element and print its text content
        allElements.forEach((element) => {
          var appendCount=0;
            if(appendCount<2){
              const text = element.textContent.trim(); // Extract text content with trimming
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
                    if(jsonMonth.toString() == curmonth && jsonDay == text && appendCount<2){
                        //console.log("currentReleaseDates "+jsonObject[i].releaseDate);
                        var movieSpan = document.createElement('a')
                        movieSpan.innerHTML ='<i class=\"icon-movies fa-solid fa-film\"></i> '+jsonObject[i].movieName;
                        movieSpan.setAttribute("href",jsonObject[i].moviePath+".html")
                        movieSpan.setAttribute("class","stacked-link truncate-text mx-1")
                        element.appendChild(movieSpan);
                        appendCount++;
                    }

                }
            }
        });
    }
//});