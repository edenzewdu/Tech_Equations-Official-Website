function handleSubmit(args, dialog) {
    var jqDialog = jQuery('#' + dialog);
    if (args.validationFailed) {
        jqDialog.effect('shake', {times: 3}, 100);
    } else {
        PF(dialog).hide();
    }
}

function getBooleanValue(result){
    var element = document.getElementById("otFacility");
    var value = element.value;
    
    if(value === true){
        result = true;
    }
    return result;
}

