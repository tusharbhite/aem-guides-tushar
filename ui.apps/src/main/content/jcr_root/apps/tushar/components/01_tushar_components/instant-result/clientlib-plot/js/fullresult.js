
async function makePostRequest(url, data) {
  try {
    // Set options for the request (optional)
    const options = {
      method: 'POST', 

      body: "SELECT * FROM [cq:Page] AS s WHERE ISDESCENDANTNODE([/content/tushar/us/en/home])" 

    };

    const response = await fetch("/bin/customquery", options);

    if (!response.ok) {
      console.log("ERRRROR")
    }

    const responseData = await response.json(); // Parse JSON response
    console.log("Response data:", responseData);

	document.getElementById("InstantResultDiv").setAttribute("InstantResultJson",JSON.stringify(responseData))

  } catch (error) {
    console.error("Error:", error);
  }
}

// Example usage
const url = "/bin/customquery"; // Replace with the actual URL of your servlet
const data = { message: "This is a test message" }; // Optional data to send (as JSON)

makePostRequest(url, data);
