var priceTotal = 0;

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