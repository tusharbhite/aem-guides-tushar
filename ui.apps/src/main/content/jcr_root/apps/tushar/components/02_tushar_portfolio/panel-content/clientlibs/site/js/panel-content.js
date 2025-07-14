console.log("In the JS File");
document.addEventListener('DOMContentLoaded', function() {
    console.log("In the event");

    
    // document.addEventListener("DOMContentLoaded", function () {
        const navCards = document.querySelectorAll(".foundation-collection-navigator");
        const homeCollection = document.getElementById("globalnav-start-home-collection");
        const summaryElement = document.getElementById('professional-summary');
        const technicalSkillsElement = document.getElementById('technical-skills');


        console.log("Number of navcards found:", navCards.length);
        navCards.forEach(card => {
            console.log("In the JS loop");
        card.addEventListener("click", function () {
            // Get the inner text from the title element
            const titleElement = card.querySelector(".globalnav-homecard-title");
            if (titleElement) {
            console.log(titleElement.textContent); // Prints "Projects"
            }
    
            // Hide the content of the container with the given ID
            if (homeCollection) {
            // homeCollection.innerHTML = ""; // Remove all existing content
            homeCollection.style.display = "none"; // Optional: hide it if needed
    
            // Then show it again and add a button
            // homeCollection.style.display = "block";
            if(titleElement.textContent=="Professional Summary"){
                summaryElement.style.display = 'block';
            }

            if(titleElement.textContent=="Technical Skills"){
                technicalSkillsElement.style.display = 'block';
            }

            // const button = document.createElement("button");
            // button.textContent = "click me";
            // homeCollection.appendChild(button);

            }
        });
        });
    // });


    //Home button back 
    const homeTitle = document.querySelector(".granite-title");
    if (homeTitle && homeTitle.textContent.trim() === "Home") {
        homeTitle.addEventListener("click", function () {
        // ✅ Match confirmed, now do something
        console.log("Home was clicked!");
        homeCollection.style.display = "block"; // Optional: hide it if needed
        summaryElement.style.display = 'none';
        technicalSkillsElement.style.display = 'none';
        });
    }
});    