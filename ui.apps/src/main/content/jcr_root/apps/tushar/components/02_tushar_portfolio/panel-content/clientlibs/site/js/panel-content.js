console.log("In the JS File");
document.addEventListener('DOMContentLoaded', function() {
    console.log("In the event");


    // document.addEventListener("DOMContentLoaded", function () {
    const navCards = document.querySelectorAll(".foundation-collection-navigator");
    const homeCollection = document.getElementById("globalnav-start-home-collection");
    const summaryElement = document.getElementById('professional-summary');
    const technicalSkillsElement = document.getElementById('technical-skills');
    const experienceElement = document.getElementById('experience');
    const projectsElement = document.getElementById('projects');
    const aboutMeElement = document.getElementById('about-me');
    const majorWorkElement = document.getElementById('major-work');



    console.log("Number of navcards found:", navCards.length);
    navCards.forEach(card => {
        console.log("In the JS loop");
        card.addEventListener("click", function() {
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
                if (titleElement.textContent == "Professional Summary") {
                    summaryElement.style.display = 'block';
                }

                if (titleElement.textContent == "Technical Skills") {
                    technicalSkillsElement.style.display = 'block';
                }

                if (titleElement.textContent == "Experience") {
                    experienceElement.style.display = 'block';
                }

                if (titleElement.textContent == "Projects") {
                    projectsElement.style.display = 'block';
                }

                if (titleElement.textContent == "About Me") {
                    aboutMeElement.style.display = 'block';
                }

                if (titleElement.textContent == "Major Work") {
                    majorWorkElement.style.display = 'block';
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
        homeTitle.addEventListener("click", function() {
            // ✅ Match confirmed, now do something
            console.log("Home was clicked!");
            homeCollection.style.display = "block"; // Optional: hide it if needed
            summaryElement.style.display = 'none';
            technicalSkillsElement.style.display = 'none';
            experienceElement.style.display = 'none';
            projectsElement.style.display = 'none';
            aboutMeElement.style.display = 'none';
            majorWorkElement.style.display = 'none';
        });
    }

    //column view logic
    // Step 1: Select all level 1 elements
    const level1Items = document.querySelectorAll('[data-column-level="1"]');
    const level2ItemsGroups = document.querySelectorAll('.level-two-groups');


    level1Items.forEach(item => {
        // console.log("inside queryselector");
        item.addEventListener("click", function() {
            // Step 2: Find the inner title element

            level2ItemsGroups.forEach(level2itemgroup => {
                level2itemgroup.style.display = "none";
            });

            const titleElement = item.querySelector(".foundation-collection-item-title");

            if (titleElement) {
                // Step 3: Extract and transform the text
                let transformedText = titleElement.textContent
                    .trim()
                    .toLowerCase()
                    .replace(/\s+/g, '-');

                // Step 4: Find the target element by ID and show it
                const targetElement = document.getElementById(transformedText);
                if (targetElement) {
                    targetElement.style.display = "block";
                } else {
                    console.warn(`Element with ID "${transformedText}" not found.`);
                }
            }
        });
    });

});