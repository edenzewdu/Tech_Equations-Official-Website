/* global PF, app, process */

// $(document).ready(function () {
//     // For each menuButton in the page
//     $('.ui-menubutton').each(function () {
//         var hasVisibleItems = false;
//
//         // Find all the menuitems (li elements) inside this specific menuButton
//         $(this).find('li').each(function () {
//             var displayValue = $(this).css('display');
//             var visibilityValue = $(this).css('visibility');
//
//             // Check if the element is visible by display and visibility
//             if (displayValue !== 'none' && visibilityValue !== 'hidden') {
//                 hasVisibleItems = true; // Mark as visible item found
//                 console.log("Visible item found in a menuButton!");
//             }
//         });
//
//         // If no visible items are found, hide the menuButton
//         if (!hasVisibleItems) {
//             console.log("No visible items found, hiding menuButton");
//             $(this).hide(); // Hides the entire menuButton
//         } else {
//             console.log("Visible items found, keeping menuButton displayed");
//         }
//     });
// });
//
// $(document).ready(function () {
//     // For each submenu in the page
//     $('.ui-panelmenu-content').each(function () {
//         var hasVisibleItems = false;
//
//         // Find all the menuitems (li elements) inside this specific submenu
//         $(this).find('li').each(function () {
//             var displayValue = $(this).css('display');
//             var visibilityValue = $(this).css('visibility');
//
//             // Check if the element is visible by display and visibility
//             if (displayValue !== 'none'){
//                 if(visibilityValue !== 'hidden') {
//                     hasVisibleItems = true; // Mark as visible item found
//                     console.log("Visible item found in a submenu!");
//                 }
//             }
//         });
//
//         // If no visible items are found, hide the submenu
//         if (!hasVisibleItems) {
//             console.log("No visible items found, hiding submenu");
//             $(this).closest('.ui-panelmenu-panel').hide();
//         } else {
//             console.log("Visible items found, keeping submenu displayed");
//         }
//     });
// });

$(document.getElementById('inputTextBenefitGroup')).css("background-color", "pink !important");

document.querySelector("body > div.layout-wrapper.layout-static.layout-sidebar-darkgray > div.layout-content-wrapper > div.layout-topbar > div.topbar-right > ul > li.notifications-item.active-menuitem");

function handleSubmit(args, dialog) {
    var jqDialog = jQuery('#' + dialog);
    if (args.validationFailed) {
        jqDialog.effect('shake', {times: 3}, 100);
    } else {
        PF(dialog).hide();
    }
}

function getBooleanValue(result) {
    var element = document.getElementById("otFacility");
    var value = element.value;

    if (value === true) {
        result = true;
    }
    return result;
}


function selectCurrentRow(tableWidgetVar, index) {
    // Get a reference to the PrimeFaces DataTable component with the provided widgetVar
    var table = PF(tableWidgetVar);

    // Unselect all rows in the DataTable
    table.unselectAllRows();

    // Select the row at the specified index, and do not fire the row select event (false parameter)
    table.selectRow(index, false);
}

function selectCurrentRowAAC(index) {
    var table = PF('approvalAuthorityConstant');
    table.unselectAllRows();
    table.selectRow(index, false);
}

function selectCurrentAllowanceRow(index) {
    var table = PF('allowanceList');
    table.unselectAllRows();
    table.selectRow(index, false);
}

function selectAssigneeCurrentRow(index) {
    var table = PF('assigneeWidget');
    table.unselectAllRows();
    table.selectRow(index, false);
}

function selectApprovalAuthorityCurrentRow(index) {
    var table = PF('approvalAuthorityWidget');
    table.unselectAllRows();
    table.selectRow(index, false);
}

function selectActivityViewActivityMasterRow(index) {
    var table = PF('ActivityViewActivityMasterWidget');
    table.unselectAllRows();
    table.selectRow(index, false);
}

function selectCentralizedCalendarMasterRow(index) {
    console.log("Calendar Master Selection JS");
    var table = PF('centralizedCalendarWidget');
    table.unselectAllRows();
    table.selectRow(index, false);
}

function selectAttachmentFilesRow(index) {
    var table = PF('applicantAttachementItem');
    table.unselectAllRows();
    table.selectRow(index, false);
}

function selectAttachmentFilesRow1(index) {
    var table = PF('widgetAttachement');
    table.unselectAllRows();
    table.selectRow(index, false);
}

function selectTopBarNotificationRow(index) {
    var table = PF('notifWidgetVar');
    table.unselectAllRows();
    table.selectRow(index, false);
}

function selectPayCycleRow(index) {
    var table = PF('payCycleConstantWidget');
    table.unselectAllRows();
    table.selectRow(index, false);
}

function selectProjectsRow(index) {
    var table = PF('projectsWidget');
    table.unselectAllRows();
    table.selectRow(index, false);
}

function openSideBar() {
    document.getElementsByClassName('layout-rightpanel')[0].classList.add('show');
}

function selectByRowKey(rowKey, table) {
    var theTable = PF(table);
    for (var i = 0; i < table.rows.length; i++) {
        var row = table.rows.get(i);
        if (row.getAttribute("data-rk") === rowKey) {
            table.unselectAllRows();
            table.selectRow(i, false);
            break;
        }
    }
}


function dynamicRowSelection(index, table) {
    var theTable = PF('table');
    theTable.unselectAllRows();
    theTable.selectRow(index, false);
}

function openCity(evt, cityName) {
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}

// HTMLElement.prototype.click = function () {
//     var evt = this.ownerDocument.createEvent('MouseEvents');
//     evt.initMouseEvent('click', true, true, this.ownerDocument.defaultView, 1, 0, 0, 0, 0, false, false, false, false, 0, null);
//     this.dispatchEvent(evt);
// }

// function triggerHiddenEvent() {
//
//     document.getElementsByClassName("ui-row-editor-pencil").click();
// }
// document.addEventListener('contextmenu', function (e) {
//     e.preventDefault();
// });
//
//
// document.addEventListener('contextmenu', function (e) {
//     e.preventDefault();
// });


// <![CDATA[
// function disableDevTools(e) {
//     if (e && (e.key === 'F12' || (e.ctrlKey && e.shiftKey && (e.key === 'I' || e.keyCode === 73))
//             || (event.ctrlKey || event.metaKey) && event.key === 'p'
//             || event.ctrlKey && event.key === 'u'
//             || (e.ctrlKey && e.shiftKey && (e.key === 'C' || e.keyCode === 73)))) {
//         if (e.preventDefault) {
//             e.preventDefault();
//         }
//         if (window.console) {
//             console.log('Developer tools are disabled.');
//         }
//         return false;
//     }
// }
//
// window.addEventListener('keydown', disableDevTools);
// // ]]>
//
// // Obfuscated JavaScript code
// var _0x1d68 = ["\x68\x65\x6C\x6C\x6F\x20\x77\x6F\x72\x6C\x64", "\x6C\x6F\x67"];
// function showMessage() {
//     alert(_0x1d68[0]);
// }
// function preventInspect(element) {
//     element.addEventListener(_0x1d68[1], function (event) {
//         event.preventDefault();
//         event.stopPropagation();
//         return false;
//     });
// }
//
// document.addEventListener("DOMContentLoaded", function () {
//     preventInspect(document);
// });
// // Example route handler
// app.get('/data', (req, res) => {
//     // Process sensitive data server-side
//     const sensitiveData = fetchSensitiveData();
//
//     // Render a template with the sensitive data
//     res.render('template', {sensitiveData});
// });
// require('dotenv').config();
//
// const apiUrl = process.env.API_URL;
// const apiKey = process.env.API_KEY;
// // Use apiUrl and apiKey in your application
//
// function detectDevTools() {
//     // Check for the presence of console and firebug
//     if (window.console) {
//         if (window.console.firebug !== undefined) {
//             alert("Developer tools (Firebug) are open!");
//             // You can take further actions here, e.g., redirecting the user
//         }
//     }
// }
// app.get('/lucy', (req, res) => {
//     if (req.user && req.user.isAdmin) {
//         // Render admin dashboard
//         res.render('lucy');
//     } else {
//         // Redirect or show unauthorized page
//         res.status(403).send('Unauthorized');
//     }
// });
// // Example using Fetch API to fetch data from server
// fetch('/data')
//         .then(response => response.json())
//         .then(data => {
//             // Process data securely
//             console.log(data);
//         })
//         .catch(error => console.error('Error fetching data:', error));
// // Example using Fetch API to fetch data from server
// fetch('/data')
//         .then(response => response.json())
//         .then(data => {
//             // Process data securely
//             console.log(data);
//         })
//         .catch(error => console.error('Error fetching data:', error));
// function minifyXHTML($xhtml) {
//     // Implement your XHTML minification logic here
//     // Example: Strip whitespace and comments
//     $xhtml = preg_replace('/\s+/', ' ', $xhtml);
//     return $xhtml;
// }
//
// function obfuscateJS($js) {
//     // Implement JavaScript obfuscation logic here
//     // Example: Basic obfuscation
//     $obfuscatedJS = base64_encode($js);
//     return $obfuscatedJS;
// }
// // Run the detection on page load
// window.onload = detectDevTools;

function showLoading() {
    document.getElementById("loadingContainer").style.display = "flex";
}

function hideLoading() {
    document.getElementById("loadingContainer").style.display = "none";
}

var lastClickTime = 0;

// function handleDateClick() {
//     var currentTime = new Date().getTime();
//     var timeSinceLastClick = currentTime - lastClickTime;
//
//     if (timeSinceLastClick < 300) { // 300 milliseconds threshold for double-click
//         onDoubleClick();
//     }
//
//     lastClickTime = currentTime;
// }
//
// function onDoubleClick() {
//     // Your double-click logic here
//     console.log("Double-click detected!");
// }
function downloadPDF(panelId) {
    const { jsPDF } = window.jspdf;
    let element = document.getElementById(panelId); // Get panel dynamically

    if (!element) {
        console.error("Panel not found: " + panelId);
        return;
    }

    html2canvas(element, { scale: 2 }).then(canvas => {
        let imgData = canvas.toDataURL('image/png');
        let pdf = new jsPDF('p', 'mm', 'a4');
        let imgWidth = 190; // Adjust for A4 width
        let imgHeight = (canvas.height * imgWidth) / canvas.width;
        pdf.addImage(imgData, 'PNG', 10, 10, imgWidth, imgHeight);
        pdf.save('Sales_Order_Report.pdf');
    });
}
function exportTablesToSingleSheet() {
    let workbook = XLSX.utils.book_new();
    let worksheetData = [];
    let tables = document.querySelectorAll(".details-table");

    tables.forEach((table, tableIndex) => {
        let rows = table.rows;
        let mergeCells = [];
        let rowOffset = worksheetData.length; // Track row position

        // Add blank row separator between tables
        if (tableIndex > 0) worksheetData.push([]);

        let spanMap = {}; // Track merged cells

        for (let rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            let row = rows[rowIndex];
            let rowData = [];
            let colIndex = 0;

            for (let cell of row.cells) {
                // Find next available column (skip merged ones)
                while (spanMap[rowIndex] && spanMap[rowIndex][colIndex]) {
                    colIndex++;
                }

                let text = cell.innerText.trim();
                let colSpan = cell.colSpan || 1;
                let rowSpan = cell.rowSpan || 1;

                // Place cell value
                rowData[colIndex] = text;

                // Handle merged cells
                if (colSpan > 1 || rowSpan > 1) {
                    mergeCells.push({
                        s: { r: rowIndex + rowOffset, c: colIndex },
                        e: { r: rowIndex + rowOffset + rowSpan - 1, c: colIndex + colSpan - 1 }
                    });
                }

                // Mark row-spanned columns as occupied
                for (let i = 1; i < rowSpan; i++) {
                    if (!spanMap[rowIndex + i]) spanMap[rowIndex + i] = {};
                    spanMap[rowIndex + i][colIndex] = true;
                }

                // Move to next column
                colIndex += colSpan;
            }

            worksheetData.push(rowData);
        }
    });

    let worksheet = XLSX.utils.aoa_to_sheet(worksheetData);
    worksheet["!merges"] = mergeCells; // Apply merged cell positions

    XLSX.utils.book_append_sheet(workbook, worksheet, "All Tables");
    XLSX.writeFile(workbook, "exported_tables.xlsx");
}
function exportToExcelXLSX(tableID, filename = 'exported_table') {
    const table = document.getElementById(tableID);

    // Create a workbook and a worksheet
    const wb = XLSX.utils.book_new();
    const ws = XLSX.utils.table_to_sheet(table);

    // Append the worksheet to the workbook
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

    // Export the workbook as an .xlsx file
    XLSX.writeFile(wb, `${filename}.xlsx`);
}
function exportToExcelXLSX1(tableID, filename = 'exported_table', sheetname1 = 'nameofSheet') {
    const table = document.getElementById(tableID);
    if (!table) {
        console.error('Table not found!');
        return;
    }

    // Create a workbook and a worksheet
    const wb = XLSX.utils.book_new();
    const ws = XLSX.utils.table_to_sheet(table);

    // Append the worksheet to the workbook
    XLSX.utils.book_append_sheet(wb, ws, sheetname1);

    // Export the workbook as an .xlsx file
    XLSX.writeFile(wb, filename + ".xlsx");
}
function exportToExcel11() {
    let table = document.getElementById('tblSales'); // Get the DataTable
    let workbook = XLSX.utils.book_new(); // Create new workbook
    let sheet = XLSX.utils.table_to_sheet(table); // Convert table to sheet

    // Rename Sheet
    XLSX.utils.book_append_sheet(workbook, sheet, "Trial_Balance");

    // Download as XLSX
    XLSX.writeFile(workbook, "Trial_Balance.xlsx");
}

function exportAllContactTablesToExcel(filename = 'Contact_List_Report') {
    let wb = XLSX.utils.book_new(); // Create a new workbook
    let tables = document.querySelectorAll("table[id^='contactList']");

    if (tables.length === 0) {
        alert("No contact list tables found to export!");
        return;
    }

    tables.forEach((table, index) => {
        let ws = XLSX.utils.aoa_to_sheet([]); // Create an empty sheet
        let sheetData = [];
        let mergeCells = []; // Store merged cells
        let colWidths = [20, 20, 20, 20, 20]; // Default column widths for 5 columns



        // Extracting table content
        let rows = table.querySelectorAll("tr");
        rows.forEach(row => {
            let rowData = [];
            let cells = row.querySelectorAll("th, td");

            cells.forEach((cell, colIndex) => {
                let cellValue = cell.innerText.trim();
                colWidths[colIndex] = Math.max(colWidths[colIndex] || 10, cellValue.length + 5);
                rowData.push(cellValue);
            });

            sheetData.push(rowData);
        });

        // Convert data to worksheet
        XLSX.utils.sheet_add_aoa(ws, sheetData);

        // Apply merged header cells
        if (mergeCells.length > 0) {
            ws['!merges'] = mergeCells;
        }

        // Apply column widths
        ws['!cols'] = colWidths.map(w => ({ wch: w }));

        // Add sheet to workbook
        let sheetName = `Bank_payslip_${index + 1}`.substring(0, 31);
        XLSX.utils.book_append_sheet(wb, ws, sheetName);
    });

    // Save workbook
    XLSX.writeFile(wb, filename + ".xlsx");
}

function formatDate(dateString) {
    const date = new Date(dateString);
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return `Sales Order Report_${date.toLocaleDateString('en-US', options)}`;
}

function exportToExcelXLX(tableID, filename = 'exported_table') {
    const table = document.getElementById(tableID);
    const rows = table.rows;

    // Initialize table content for Excel (HTML format)
    let content = '<table border="1" style="border-collapse:collapse;width:100%;">';

    for (let i = 0; i < rows.length; i++) {
        content += '<tr>';
        const cells = rows[i].cells;

        for (let j = 0; j < cells.length; j++) {
            const cell = cells[j];
            const tag = i === 0 ? 'th' : 'td'; // Use <th> for headers

            // Check for rowspan and colspan
            const rowspan = cell.rowSpan > 1 ? `rowspan="${cell.rowSpan}"` : '';
            const colspan = cell.colSpan > 1 ? `colspan="${cell.colSpan}"` : '';

            // Add cell content with styles
            content += `<${tag} ${rowspan} ${colspan} style="padding:5px;text-align:center;">${cell.innerHTML}</${tag}>`;
        }
        content += '</tr>';
    }

    content += '</table>';

    // Generate the Excel file as a Blob
    const blob = new Blob(
        [
            '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">' +
            '<head><meta charset="utf-8"></head><body>' +
            content +
            '</body></html>',
        ],
        { type: 'application/vnd.ms-excel' }
    );

    // Create a link and trigger the download
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = `${filename}.xls`; // Use .xls extension
    link.click();

    // Revoke the object URL to free memory
    URL.revokeObjectURL(link.href);
}
function exportAllContactTablesToXLS(filename = 'Contact_List_Report') {
    let tables = document.querySelectorAll("table[id^='contactList']");

    if (tables.length === 0) {
        alert("No contact list tables found to export!");
        return;
    }

    // Initialize table content for Excel (HTML format)
    let content = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">';
    content += '<head><meta charset="utf-8"></head><body>';

    tables.forEach((table, index) => {
        content += `<h3>Sheet ${index + 1} - ${table.id}</h3>`; // Table title
        content += '<table border="1" style="border-collapse:collapse;width:100%;">';

        const rows = table.rows;
        for (let i = 0; i < rows.length; i++) {
            content += '<tr>';
            const cells = rows[i].cells;

            for (let j = 0; j < cells.length; j++) {
                const cell = cells[j];
                const tag = i === 0 ? 'th' : 'td'; // Use <th> for headers

                // Check for rowspan and colspan
                const rowspan = cell.rowSpan > 1 ? `rowspan="${cell.rowSpan}"` : '';
                const colspan = cell.colSpan > 1 ? `colspan="${cell.colSpan}"` : '';

                // Add cell content with styles
                content += `<${tag} ${rowspan} ${colspan} style="padding:5px;text-align:center;">${cell.innerHTML}</${tag}>`;
            }
            content += '</tr>';
        }

        content += '</table><br>'; // Separate tables with a line break
    });

    content += '</body></html>';

    // Generate the Excel file as a Blob
    const blob = new Blob([content], { type: 'application/vnd.ms-excel' });

    // Create a link and trigger the download
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = `${filename}.xls`; // Use .xls extension
    link.click();

    // Revoke the object URL to free memory
    URL.revokeObjectURL(link.href);
}

function exportToCSV(tableID, filename = 'exported_table.csv') {
    const table = document.getElementById(tableID);
    let csvContent = '';
    const rows = table.querySelectorAll('tr');

    rows.forEach(row => {
        const cols = row.querySelectorAll('td, th');
        const rowContent = Array.from(cols).map(col => `"${col.textContent}"`).join(',');
        csvContent += rowContent + '\n';
    });

    const blob = new Blob([csvContent], { type: 'text/csv' });
    const downloadLink = document.createElement('a');
    downloadLink.href = URL.createObjectURL(blob);
    downloadLink.download = filename;
    document.body.appendChild(downloadLink);
    downloadLink.click();
    document.body.removeChild(downloadLink);
}

function exportToExcel(tableID, headerData, filename = '') {
    // Get the table element
    const table = document.getElementById(tableID);

    // Dynamically create the header HTML
    let headerHTML = `
        <table>
            <tr>
                <td colspan="5" style="text-align: center; font-weight: bold;">
                    <img src="${headerData.logo}" alt="Logo" style="height: 50px;" />
                </td>
            </tr>
            <tr>
                <td colspan="5" style="text-align: center; font-weight: bold;">${headerData.companyName}</td>
            </tr>
            <tr>
                <td colspan="5" style="text-align: center;">${headerData.title}</td>
            </tr>
            <tr>
                <td colspan="5" style="text-align: center;">${headerData.dateRange}</td>
            </tr>
        </table>
    `;

    // Combine header and table content
    const tableHTML = headerHTML + table.outerHTML.replace(/ /g, '%20');

    // Set the filename
    const filenameWithExtension = filename ? `${filename}.xlsx` : 'exported_table.xlsx';

    // Create a download link
    const downloadLink = document.createElement('a');
    downloadLink.href = 'data:application/vnd.ms-excel,' + tableHTML;
    downloadLink.download = filenameWithExtension;

    // Trigger download
    document.body.appendChild(downloadLink);
    downloadLink.click();
    document.body.removeChild(downloadLink);
}

// Export to Word
 function exportTableToWord(tableID, filename = 'exported_table') {
    const table = document.getElementById(tableID);
    const tableHTML = table.outerHTML.replace(/ /g, '%20');

    const filenameWithExtension = filename ? `${filename}.docx` : 'exported_table.docx';

    const downloadLink = document.createElement('a');
    downloadLink.href = 'data:application/vnd.ms-excel,' + tableHTML;
    downloadLink.download = filenameWithExtension;

    document.body.appendChild(downloadLink);
    downloadLink.click();
    document.body.removeChild(downloadLink);
}
function exportToWord(tableID, filename = 'exported_table') {
    const table = document.getElementById(tableID);
    const rows = table.rows;

    // Initialize table content for Word
    let content = '<table border="1" style="border-collapse:collapse;width:100%;">';

    for (let i = 0; i < rows.length; i++) {
        content += '<tr>';
        const cells = rows[i].cells;

        for (let j = 0; j < cells.length; j++) {
            const cell = cells[j];
            const tag = i === 0 ? 'th' : 'td'; // Use <th> for headers

            // Check for rowspan and colspan
            const rowspan = cell.rowSpan > 1 ? `rowspan="${cell.rowSpan}"` : '';
            const colspan = cell.colSpan > 1 ? `colspan="${cell.colSpan}"` : '';

            // Add cell content with styles
            content += `<${tag} ${rowspan} ${colspan} style="padding:5px;text-align:center;">${cell.innerHTML}</${tag}>`;
        }
        content += '</tr>';
    }

    content += '</table>';

    // Create a Blob with the content
    const blob = new Blob(['<html><body>' + content + '</body></html>'], {
        type: 'application/msword',
    });

    // Create a link and trigger the download
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = `${filename}.doc`;
    link.click();

    // Revoke the object URL to free memory
    URL.revokeObjectURL(link.href);
}


