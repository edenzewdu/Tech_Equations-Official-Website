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

