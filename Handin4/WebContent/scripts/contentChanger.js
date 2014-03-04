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
		$('#mainContainer').html('LOGIN');
	});

});

function addItemsToTable(items) {
    //Get the table body we we can add items to it
    var tableBody = document.getElementById("itemtablebody");
    //Remove all contents of the table body (if any exist)
    tableBody.innerHTML = "";

    //Loop through the items from the server
    for (var i = 0; i < items.length; i++) {
        var item = items[i];
        //Create a new line for this item
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