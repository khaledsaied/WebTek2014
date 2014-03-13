$(document).ready(function() {
	$('#homePage').click(function() {
		$('#mainContainer').load('views/home.html');
	});
	$('#aboutPage').click(function() {
		$('#mainContainer').load('views/about.html');
		$.get('rest/shop/shops', function(shopList) {
			var shops = JSON.parse(shopList);
			showShops(shops);
		});
	});
	$('#productsPage').click(function() {
		$('#mainContainer').load('views/products.html');
		//var shopID = document.getElementById("shopList").value;
		$.get('rest/shop/cloudItems', function(cloudProducts) {
			var items = JSON.parse(cloudProducts);
			showProducts(items);
		});
	});
	$('#contactPage').click(function() {
		$('#mainContainer').load('views/contact.html');
	});
	$('#loginPage').click(function() {
		$('#mainContainer').load('views/login.html');
		$.get('rest/shop/loggedInBool', function(logInResponse) {
			if (logInResponse == "true") {
				$('#mainContainer').load('views/logout.html');
			} else {
				$('#mainContainer').load('views/login.html');
			}
		});
	});
});
function showShops(shops) {
	// Loop through the items from the server
	for (var i = 0; i < shops.length; i++) {
		(function() {

			var shop = shops[i];
			// Create a new line for this item
			
			var shopList = document.getElementById("shopList");
			var option = document.createElement("option");
			option.textContent = shop.shopName;
			
			shopList.appendChild(option);
		}());
	}
}


