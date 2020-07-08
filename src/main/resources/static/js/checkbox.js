function checkbox(obj) {
    if(obj.checked){
        document.getElementById('horasvalidadas').readOnly = false;
    }

    else{
        document.getElementById('horasvalidadas').readOnly = true;
    }
}