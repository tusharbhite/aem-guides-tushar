 document.addEventListener('DOMContentLoaded', () => {
            const toggleBtn = document.getElementById('toggle-btn');
            const sidebar = document.getElementById('sidebar');
            const fileUpload = document.getElementById('file-upload');
            const getLatestReportBtn = document.getElementById('get-latest-report-btn');
            const generateReportBtn = document.getElementById('generate-report-btn');
            const dataDisplay = document.getElementById('data-display');
            const reportItems = document.querySelectorAll('.report-item');
const username = "admin";
const password = "admin";


            // Handle sidebar toggle
            toggleBtn.addEventListener('click', () => {
                sidebar.classList.toggle('collapsed');
                toggleBtn.classList.toggle('active');
                
                // For mobile view
                if (window.innerWidth <= 768) {
                    sidebar.classList.toggle('active');
                }
            });

            // Handle file upload
            fileUpload.addEventListener('change', (event) => {
                const file = event.target.files[0];
                if (file) {
                    readXlsxFile(file);
                }
            });

            // Handle "Get Latest Report" button click
            getLatestReportBtn.addEventListener('click', () => {
               /* const activeReport = document.querySelector('.report-item.active');
                if (activeReport) {
                    const reportName = activeReport.dataset.reportName;
                    fetchReportData(reportName);
                } else {
                    displayMessage('Please select a report from the sidebar.', 'warning');
                }*/
                displayMessage('Fetching Data from Stored Report file  Please Wait.', 'info');

				const activeReport = document.querySelector('.report-item.active');
                if (activeReport) {
                    xlsxUrl = activeReport.dataset.reportUrl;
                    //alert(xlsxUrl);
                }

				fetchDamFile(xlsxUrl)


            });

            // Handle "Generate Report" button click
            generateReportBtn.addEventListener('click', () => {

				const activeReport = document.querySelector('.report-item.active');
                if (activeReport) {
                    var scriptPath = activeReport.dataset.scriptPath;
                    const reportName = activeReport.dataset.reportName;
                    var xlsxUrl = activeReport.dataset.reportUrl;
                    //const scriptPath = "/var/groovyconsole/scripts/samples/JcrSearch.groovy";

                    executeScript(scriptPath,xlsxUrl)

                    //alert(scriptPath);

                }

				//fetchDamFile(xlsxUrl)

                displayMessage('Executed Report Generation at backend,  Please Wait.', 'info');
				//var xlsxUrl = "/content/dam/reports/pages-reports.xlsx"; // Replace with your actual URL



            });

            // Handle sidebar report selection
            reportItems.forEach(item => {
                item.addEventListener('click', () => {
                    reportItems.forEach(i => i.classList.remove('active'));
                    item.classList.add('active');
                    displayMessage(`Selected report: <strong>${item.textContent}</strong>. Click 'Get Latest Report' to view data.`, 'info');
                });
            });

            // Function to read XLSX file
            function readXlsxFile(file) {
                const reader = new FileReader();
                reader.onload = (e) => {
                    const data = new Uint8Array(e.target.result);
                    const workbook = XLSX.read(data, { type: 'array' });
                    const sheetName = workbook.SheetNames[0];
                    const worksheet = workbook.Sheets[sheetName];
                    const jsonData = XLSX.utils.sheet_to_json(worksheet, { header: 1 });
                    displayData(jsonData);
                };
                reader.onerror = () => {
                    displayMessage('Error reading file. Please try again.', 'error');
                };
                reader.readAsArrayBuffer(file);
            }

             function displayDamXlsx(fileContent){
    				const workbook = XLSX.read(new Uint8Array(fileContent), { type: "array" });
					const sheetName = workbook.SheetNames[0];
					const worksheet = workbook.Sheets[sheetName];
                    const jsonData = XLSX.utils.sheet_to_json(worksheet, { header: 1 });
                    displayData(jsonData);
				}

             function fetchDamFile(xlsxUrl){
                // alert(xlsxUrl);
				fetch(xlsxUrl)
                  .then(response => {
                    if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
                    return response.arrayBuffer(); // Get raw binary data
                  })
                  .then(arrayBuffer => {
                    const fileContent = arrayBuffer; // This holds the XLSX file content
                    console.log("✅ XLSX file fetched and stored in variable");
					//readXlsxFile(fileContent);

                    // Optional: process with SheetJS
					displayDamXlsx(fileContent)

                    // console.log(workbook.SheetNames);
                  })
                  .catch(error => {
                    console.error("❌ Failed to fetch XLSX file:", error);
                  });

                }


            // Function to display data in a table
            function displayData(data) {
                if (!data || data.length === 0) {
                    displayMessage('No data to display.', 'info');
                    return;
                }

                const table = document.createElement('table');
                const headerRow = document.createElement('tr');
                
                // Create table headers from the first row of data
                data[0].forEach(headerText => {
                    const th = document.createElement('th');
                    th.textContent = headerText;
                    headerRow.appendChild(th);
                });
                table.appendChild(headerRow);

                // Create table body from the rest of the data
                for (let i = 1; i < data.length; i++) {
                    const rowData = data[i];
                    const tr = document.createElement('tr');
                    rowData.forEach(cellData => {
                        const td = document.createElement('td');
                        td.textContent = cellData;
                        tr.appendChild(td);
                    });
                    table.appendChild(tr);
                }

                dataDisplay.innerHTML = '';
                dataDisplay.appendChild(table);
            }
            
            // Function to display a message inside the data container
            function displayMessage(message, type) {
                dataDisplay.innerHTML = `<p class="${type}-message">${message}</p>`;
            }

                function executeScript(scriptPath,xlsxUrl){
                    // Encode credentials for Basic Auth
                    const authHeader = "Basic " + btoa(`${username}:${password}`);
                    
                    // Step 1: Get CSRF token
                    fetch("http://localhost:4502/libs/granite/csrf/token.json", {
                      method: "GET",
                      headers: {
                        "Authorization": authHeader
                      }
                    })
                    .then(response => {
                      if (!response.ok) throw new Error("Failed to fetch CSRF token");
                      return response.json();
                    })
                    .then(data => {
                      const csrfToken = data.token;
                      console.log("✅ CSRF Token:", csrfToken);
                    
                      // Step 2: POST to Groovy Console
                      const formData = new URLSearchParams();
                      formData.append("scriptPath", scriptPath);
                    
                      return fetch("http://localhost:4502/bin/groovyconsole/post.json", {
                        method: "POST",
                        headers: {
                          "Authorization": authHeader,
                          "Content-Type": "application/x-www-form-urlencoded",
                          "CSRF-Token": csrfToken
                        },
                        body: formData.toString()
                      });
                    })
                    .then(response => {
                      if (!response.ok) throw new Error("Groovy Console POST failed");
                      return response.json();
                    })
                    .then(result => {
                      console.log("✅ Groovy Script Successful Console Response:");
                      //console.log(result);
                        displayMessage('Fetching Data from Latest Stored Report file  Please Wait.', 'info');	
                        fetchDamFile(xlsxUrl);


                    })
                    .catch(error => {
                      console.error("❌ Error:", error);
                    });
			}

            // Demo function to simulate fetching report data
            function fetchReportData(reportName) {
                displayMessage('Fetching data...', 'info');
                setTimeout(() => {
                    // Simulated data for each report
                    const demoData = {
                        'report-1': [['ID', 'Product', 'Sales', 'Region'], [1, 'Laptop', 12000, 'North'], [2, 'Mouse', 500, 'South'], [3, 'Keyboard', 750, 'East']],
                        'report-2': [['Date', 'Revenue', 'Expenses'], ['2025-01-01', 5000, 1500], ['2025-01-02', 6500, 2000], ['2025-01-03', 7200, 2500]],
                        'report-3': [['User', 'Status', 'Last Login'], ['Alice', 'Active', '2025-05-20'], ['Bob', 'Inactive', '2025-03-10'], ['Charlie', 'Active', '2025-05-22']]
                    };
                    
                    const data = demoData[reportName];
                    if (data) {
                        displayData(data);
                    } else {
                        displayMessage('No data found for this report.', 'info');
                    }
                }, 1500);
            }
        });


