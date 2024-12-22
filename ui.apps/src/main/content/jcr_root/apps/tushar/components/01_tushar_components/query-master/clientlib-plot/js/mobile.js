

//function saveTxtFile(content) {
 // const filePath = document.getElementById('filePath').value;

//   const url = 'http://localhost:4502'+filePath+'/jcr:content/renditions/original/jcr:content';
// const username = 'admin';
// const password = 'admin';
// const propertyName = 'jcr:data';
// const newValue = content;

// const headers = new Headers();
// headers.append('Content-Type', 'application/x-www-form-urlencoded');

// const body = new URLSearchParams();
// body.append(propertyName, newValue);

// const requestOptions = {
//   method: 'POST',
//   headers: headers,
//   body: body,
//   redirect: 'follow'
// };

// const auth = 'Basic ' + btoa(username + ':' + password);
// headers.append('Authorization', auth);

// fetch(url, requestOptions)
//   .then(response => response.text())
//   .then(result => console.log(result))
//   .catch(error => console.log('error', error));




//2
const url = 'http://localhost:4502/bin/custom/query';
const username = 'admin';
const password = 'admin';
const query = "SELECT * FROM [nt:unstructured] AS s WHERE ISDESCENDANTNODE([/content/tushar/us/en/home/movies/bollywood]) and CONTAINS(s.*, 'ajay')";
const properties = ["jcr:primaryType","cq:lastModified"];

//const newValue = new Blob([content], { type: 'text/plain' });


// Fetch CSRF token
fetch('http://localhost:4502/libs/granite/csrf/token.json', {
  method: 'GET',
  headers: {
    'Authorization': 'Basic ' + btoa(username + ':' + password)
  }
})
  .then(response => response.json())
  .then(data => {
    const csrfToken = data.token;

    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    headers.append('Authorization', 'Basic ' + btoa(username + ':' + password));
    headers.append('CSRF-Token', csrfToken);

     const body = new URLSearchParams();
    body.append(query, properties);

    const requestOptions = {
      method: 'POST',
      headers: headers,
      body: body,
      redirect: 'follow'
    };

    fetch(url, requestOptions)
      .then(response => response.text())
      .then(result => console.log("Success Result "+content))
      .then(result => document.getElementById('editor-alert-placeholder').innerHTML = `
      <coral-alert variant="success" id="sucalert">
        <coral-alert-header></coral-alert-header>
        <coral-alert-content>Text File Has been Updated Successfully.</coral-alert-content>
      </coral-alert>`
      )
      // .catch(error => alert('error', error));
      .catch(error => document.getElementById('editor-alert-placeholder').innerHTML = `
      <coral-alert variant="error">
      <coral-alert-header>ERROR</coral-alert-header>
      <coral-alert-content>Something went wrong while saving Text File.</coral-alert-content>
    </coral-alert>
    `);
  })
  .catch(error => console.log('error fetching CSRF token', error));
//2 end





  // const formData = new FormData();
  // formData.append('file', new Blob([content], { type: 'text/plain' }), 'file.txt');

  // fetch(filePath, {
  //     method: 'POST',
  //     body: formData
  // })
  // .then(response => {
  //     if (response.ok) {
  //         alert('File saved successfully!');
  //     } else {
  //         alert('Failed to save file.');
  //     }
  // });
//}
