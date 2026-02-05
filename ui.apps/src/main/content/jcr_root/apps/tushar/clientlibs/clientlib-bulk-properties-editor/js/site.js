(function(document, $, Granite) {
    "use strict";

    $(document).on("click", "#btn-export", function() {
        var path = $("#export-root-path").val();
        if (!path) {
            UI.alert("Error", "Please select a root path first.");
            return;
        }
        window.location.href = "/bin/mytools/bulk-page-updater?operation=export&path=" + path;
    });

    $(document).on("click", "#btn-dryrun, #btn-process", function(e) {
        var operation = (e.currentTarget.id === "btn-dryrun") ? "dryrun" : "process";
        var fileUpload = $("#csv-upload")[0];
        var file = fileUpload.querySelector("input[type='file']").files[0];

        if (!file) {
            alert("Please select a CSV file to upload.");
            return;
        }

        var formData = new FormData();
        formData.append("file", file);
        formData.append("operation", operation);

        $.ajax({
            url: "/bin/mytools/bulk-page-updater",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                // Assuming response is a list of changes
                var html = "<h3>" + (operation === "dryrun" ? "Dry Run Results" : "Final Process Results") + "</h3>";
                html += "<table border='1'><tr><th>Path</th><th>Status</th></tr>";
                response.results.forEach(function(item) {
                    html += "<tr><td>" + item.path + "</td><td>" + item.status + "</td></tr>";
                });
                html += "</table>";
                $("#result-area").html(html);
            },
            error: function() {
                alert("An error occurred during processing.");
            }
        });
    });

})(document, Granite.$, Granite);