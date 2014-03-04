$(document).ready(function() {
	$('#homePage').click( function() {
		$('#mainContainer').load('home.html')
	});
	$('#productsPage').click( function() {
		$('#mainContainer').load('products.html');
		$.get('rest/shop/items', function(itemsText) {
			var items = JSON.parse(itemsText);
            addItemsToTable(items);
		});
	});
	$('#aboutPage').click( function() {
		$('#mainContainer').load('about.html');
	});
	$('#contactPage').click( function() {
		$('#mainContainer').load('contact.html');
	});
	$('#basketPage').click( function() {
		$('#mainContainer').load('basket.html');
	});
	$('#loginPage').click( function() {
		$('#mainContainer').load('login.html');
	});

});

function addItemsToTable(items) {
    // Get the table body we we can add items to it
    var tableBody = document.getElementById("itemtablebody");
    // Remove all contents of the table body (if any exist)
    tableBody.innerHTML = "";

    // Loop through the items from the server
    for (var i = 0; i < items.length; i++) {
        var item = items[i];
        // Create a new line for this item
        var tr = document.createElement("tr");

        var nameCell = document.createElement("td");
        nameCell.textContent = item.name;
        tr.appendChild(nameCell);

        var priceCell = document.createElement("td");
        priceCell.textContent = item.price;
        tr.appendChild(priceCell);

        tableBody.appendChild(tr);
    }
}

//Function from slide AJAX example
function myfunc()
{
	var http;
	
	if (!XMLHttpRequest)
		http = new ActiveXObject("Microsoft.XMLHTTP");
	else
		http = new XMLHttpRequest();
	
	function sendRequest(httpMethod, url, body, responseHandler) 
	{
		http.open(httpMethod, url);
		http.onreadystatechange = function () 
			{
				if (http.readyState == 4 && http.status == 200) 
					{
						responseHandler(http.responseText);
					}
			};
			http.send(body);
	}
}

//Send a GET request to "/query" without an argument
sendRequest("GET", "/query", null, function(value) {
//This code is run when GET data is received
});

//Include a "q" parameter
var foo = encodeURIComponent("Mathias");
sendRequest("GET", "/query?q=" + foo, null, function(value) {
//This code is run when GET data is received
});

//Send a POST request with a parameter in request body
//Use this for updating values on the JAX RS service
sendRequest("POST", "/query", "q=" + foo, function(value) {
//...
});