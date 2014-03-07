$(document).ready(function() {
	$('#homePage').click(function() {
		$('#mainContainer').load('home.html');
	});
	$('#productsPage').click(function() {
		$('#mainContainer').load('products.html');
		$.get('rest/shop/cloudItems', function(itemsText) {
			var items = JSON.parse(itemsText);
			addItemsToTable(items);
		});
	});
	$('#aboutPage').click(function() {
		$('#mainContainer').load('about.html');
	});
	$('#contactPage').click(function() {
		$('#mainContainer').load('contact.html');
	});
	$('#loginPage').click(function() {
		$('#mainContainer').load('login.html');
		$.get('rest/shop/loggedInBool', function(logInResponse) {
			if (logInResponse == "true") {
				$('#mainContainer').load('logout.html');
			} else {
				$('#mainContainer').load('login.html');
			}
		});
	});
});

var priceTotal = 0;

function welcomeMessage(name) {
	var welcome = document.getElementById("welcome");
	welcome.textContent = "Velkommen " + name;
}

function addItemsToTable(items) {
	// Get the table body we we can add items to it
	var tableBody = document.getElementById("itemtablebody");
	var thead = document.createElement("thead");
	var tr2 = document.createElement("tr");

	var nameHead = document.createElement("th");
	nameHead.textContent = "NAME";
	tr2.appendChild(nameHead);

	var priceHead = document.createElement("th");
	priceHead.textContent = "PRICE";
	tr2.appendChild(priceHead);

	var descriptionHead = document.createElement("th");
	descriptionHead.textContent = "DESCRIPTION";
	tr2.appendChild(descriptionHead);

	var stockHead = document.createElement("th");
	stockHead.textContent = "STOCK";
	tr2.appendChild(stockHead);

	var inCartHead = document.createElement("th");
	inCartHead.textContent = "IN CART";
	tr2.appendChild(inCartHead);

	var emptyHead = document.createElement("th");
	emptyHead.textContent = " ";
	tr2.appendChild(emptyHead);

	var idHead = document.createElement("th");
	idHead.textContent = "ID";
	tr2.appendChild(idHead);

	thead.appendChild(tr2);
	tableBody.appendChild(thead);

	// Loop through the items from the server
	for (var i = 0; i < items.length; i++) {
		(function() {

			var item = items[i];
			// Create a new line for this item
			var tr = document.createElement("tr");

			var nameCell = document.createElement("td");
			nameCell.textContent = item.name;
			tr.appendChild(nameCell);

			var priceCell = document.createElement("td");
			priceCell.textContent = item.price;
			tr.appendChild(priceCell);

			var descriptionCell = document.createElement("td");
			descriptionCell.textContent = item.description;
			tr.appendChild(descriptionCell);

			var stockCell = document.createElement("td");
			stockCell.textContent = item.stock;
			tr.appendChild(stockCell);

			var inCartCell = document.createElement("td");
			inCartCell.textContent = "0";
			inCartCell.setAttribute("ID", item.ID);
			tr.appendChild(inCartCell);

			var addToCartCell = document.createElement("td");
			var btnAddToCart = document.createElement("button");
			btnAddToCart.textContent = "Add to Cart";
			addToCartCell.appendChild(btnAddToCart);

			btnAddToCart
					.addEventListener(
							"click",
							function() {
								sendRequest(
										"POST",
										"rest/shop/cart",
										"id=" + item.ID + "&stock="
												+ item.stock,
										function(itemsText) {

											// Opdater totalprisen for alle
											// varene
											priceTotal = priceTotal
													+ parseInt(item.price);
											document
													.getElementById("totalPrice").innerHTML = priceTotal;
											// This code is called when the
											// server has sent its data
											updateInCart(item.ID, item.stock);
										});
							});

			tr.appendChild(addToCartCell);

			var idCell = document.createElement("td");
			idCell.textContent = item.ID;
			tr.appendChild(idCell);

			tableBody.appendChild(tr);

		}());
	}

	function updateInCart(buttonID, itemStock) {
		var tdToUpdate = document.getElementById(buttonID);
		var counter = tdToUpdate.textContent;

		if (!itemStock <= 0 && itemStock > counter) {
			counter++;
		}

		tdToUpdate.textContent = counter;
	}

	function userCanBuy(isLoggedIn) {
		var buyBtn = document.getElementById("buy");

		if (isLoggedIn == true) {
			buyBtn.disabled = false;
		} else {
			buyBtn.disabled = true;
		}
	}

}

function buy() {
	// Tell adjustCloud(itemId, itemstock) to delete the items in the hashmap in
	// the session
	document.getElementById("buy").addEventListener(
			"click",
			function() {
				sendRequest("POST", "rest/shop/adjustCloud", "id=" + item.ID
						+ "&stock=" + item.stock, function(itemsText) {
				});
			});
}

function addEventListener(myNode, eventType, myHandlerFunc) {
	if (myNode.addEventListener)
		myNode.addEventListener(eventType, myHandlerFunc, false);
	else
		myNode.attachEvent("on" + eventType, function(event) {
			myHandlerFunc.call(myNode, event);
		});
}

var http;
if (!XMLHttpRequest)
	http = new ActiveXObject("Microsoft.XMLHTTP");
else
	http = new XMLHttpRequest();

function sendRequest(httpMethod, url, body, responseHandler) {
	http.open(httpMethod, url);
	if (httpMethod == "POST") {
		http.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
	}
	http.onreadystatechange = function() {
		if (http.readyState == 4 && http.status == 200) {
			responseHandler(http.responseText);
		}
	};
	http.send(body);
}