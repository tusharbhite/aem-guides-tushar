window.addEventListener('load', function() {
	const readMore = document.getElementById("readMore");
	const readLess = document.getElementById("readLess");
	const plotbody = document.getElementById("plot-body");
            let isClassAdded = false;

            readMore.addEventListener("click", function() {
                    plotbody.classList.remove("makeSmaller");
                    readMore.classList.add("hideElement");
					readLess.classList.remove("hideElement");
                	plotbody.scrollIntoView();

            });
                readLess.addEventListener("click", function() {                    
                    plotbody.classList.add("makeSmaller");  
                    readMore.classList.remove("hideElement");
					readLess.classList.add("hideElement");
                    plotbody.scrollIntoView();
            });
});