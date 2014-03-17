$(document).ready(function() {
	
//	if (this === "http://localhost:8080/Handin5/#PRODUCTS")
//		$('#mainContainer').load('views/products.html');
	$('#homePage').click(function() {
		$('#mainContainer').load('views/home.html');
	});
	$('#aboutPage').click(function() {
		$('#mainContainer').load('views/about.html');	
	});
	$('#productsPage').click(function() {
		$('#mainContainer').load('views/products.html');
		$.get('rest/shop/shops', function(shopList) {
			var shops = jQuery.parseJSON(shopList);
			showShops(shops);
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
	var index=0;
	$.ajax({
        type: "GET",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        url: 'rest/shop/cloudItems/'+shops[index].shopID,  
        beforeSend: function (XMLHttpRequest) {
            //Specifying this header ensures that the results will be returned as JSON.
            XMLHttpRequest.setRequestHeader("Accept", "application/json");
        },
        success: function (cloudProducts) {
            //alert(JSON.stringify(msg));
    	   //var items =JSON.parse(cloudProducts);
   			showProducts(cloudProducts);
        },
        error: function () {
            alert("Network error");
        }
    });
	// Get Items for shop on first load
//	
//	$.get('rest/shop/cloudItems/'+shops[index].shopID, function(cloudProducts) {
//		var items = jQuery.parseJSON(cloudProducts);
//		showProducts(items);
//	});
//	sendRequest("POST", 'rest/shop/cloudItems/'+shops[index].shopID, function(cloudProducts) {
//		var items = jQuery.parseJSON(cloudProducts);
//		showProducts(items);
//	});

	shopList.addEventListener("change",	
			function() {
				index = $("#shopList").prop("selectedIndex");
				$.ajax({
			        type: "GET",
			        dataType: "json",
			        contentType: "application/json;charset=utf-8",
			        url: 'rest/shop/cloudItems/'+shops[index].shopID,
			        beforeSend: function(xhrObj){
		                xhrObj.setRequestHeader("Content-Type","application/json");
		                xhrObj.setRequestHeader("Accept","application/json");
		        },
			       success: function (cloudProducts) {
			            //alert(JSON.stringify(cloudProducts));
			            //var items = jQuery.parseJSON(cloudProducts);
						showProducts(cloudProducts);
			        },
			        error: function () {
			            alert("Network error");
			        }
			    });
				//alert(this.selected);
//				$.get('rest/shop/cloudItems/'+shops[index].shopID, function(cloudProducts) {
//					var items = jQuery.parseJSON(cloudProducts);
//					showProducts(items);
//				});
//				sendRequest("POST", 'rest/shop/cloudItems/'+shops[index].shopID, function(cloudProducts) {
//					var items = jQuery.parseJSON(cloudProducts);
//					showProducts(items);
//				});
	});
}


