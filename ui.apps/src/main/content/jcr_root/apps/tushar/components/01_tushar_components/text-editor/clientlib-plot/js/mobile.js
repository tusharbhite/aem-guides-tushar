



document.getElementById('cancelBtn').addEventListener('click', function() {
  location.reload();
});




window.onload = function() {
  var elements = document.querySelector('[data-name="./savebutton"]');
  if (elements) {
    elements.id = 'saveBtn';
  }

  var elementr = document.querySelector('[data-name="./resetbutton"]');
  if (elementr) {
    elementr.id = 'resetBtn';
  }

  
  var elementp = document.querySelector('[data-name="./processbutton"]');
  if (elementp) {
    elementp.id = 'processBtn';
  }
 
  var elementp = document.querySelector('[name="./txt-file-path"]');
  if (elementp) {
    elementp.id = 'filePath';
  }

  document.getElementById('saveBtn').addEventListener('click', function() {
    const updatedContent = document.getElementById('txtContent').value;
    console.log(updatedContent);
    saveTxtFile(updatedContent);
  });

  
  document.getElementById('resetBtn').addEventListener('click', function() {
    const filePath = document.getElementById('filePath').value;
    fetch(filePath)
        .then(response => response.text())
        .then(data => {
            document.getElementById('txtContent').value = data;
        });
  });

  document.getElementById('processBtn').addEventListener('click', function() {
    const filePath = document.getElementById('filePath').value;
    const fileName=filePath.split('/').pop();
    document.getElementById('filenameelement').innerHTML = "<b>"+fileName+"</b>";
    var successalert=document.getElementById('sucalert')
    if(successalert)
    successalert.remove();
    fetch(filePath)
        .then(response => response.text())
        .then(data => {
            document.getElementById('txtContent').value = data;
        });
  });


  

};



function saveTxtFile(content) {
  const filePath = document.getElementById('filePath').value;

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
const url = 'http://localhost:4502'+filePath+'/jcr:content/renditions/original/jcr:content';
const username = 'admin';
const password = 'admin';
const propertyName = 'jcr:data';
const newValue = content;
var editoralertplaceholder = document.getElementById('editor-alert-placeholder');
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
    body.append(propertyName, newValue);

    const requestOptions = {
      method: 'POST',
      headers: headers,
      body: body,
      redirect: 'follow'
    };

    fetch(url, requestOptions)
      .then(response => response.text())
      // .then(result => console.log("Success Result "+content))
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
}
