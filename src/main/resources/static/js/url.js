$(document).ready(function() {
	var URLactual = window.location;
	var URLstring = URLactual.toString()
	var URLid = URLstring.charAt(URLstring.length - 1);
	
	console.log(URLactual);
	console.log(URLstring);
	console.log(URLid);
	
	document.getElementById("cliente_id").value = URLid;
});