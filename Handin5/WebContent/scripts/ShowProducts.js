function showProducts(items) {
	var itemList = document.getElementById("itemList");
	itemList.innerHTML = "";
	

	// Loop through the items from the server
	for (var i = 0; i < items.length; i++) {
		(function() {

			var item = items[i];
			//tem.setAttribute("draggable", "true");
		    //addEvent(item, 'dragstart', onDrag);
			
			var tableBody = document.createElement("table");
			var div = document.createElement("div");
			div.setAttribute("ID", "itemDiv");
			div.setAttribute("data-id", item.ID);
			div.setAttribute("draggable", "true");
			div.setAttribute("ondragstart", "drag(event)")
			//div.textContent = item.name;
			
			itemList.appendChild(div);
			
			var tr = document.createElement("tr");
			
			var urlCell = document.createElement("td");
			//urlCell.textContent = item.URL;
			var productImage = document.createElement("img");
			productImage.setAttribute("id","productImage");
			var imageUrl;
			if(checkURL(item.URL)) {
				imageUrl = item.URL;
			}
			else {
				imageUrl = "http://www.divedesco.com/Content/files/noimage.png";
			}
			
			productImage.setAttribute("src",imageUrl);
			urlCell.appendChild(productImage);
			tr.appendChild(urlCell);
			
			var nameCell = document.createElement("td");
			nameCell.setAttribute("id", "nameCell");
			nameCell.textContent = item.name;
			tr.appendChild(nameCell);

			var priceCell = document.createElement("td");
			priceCell.setAttribute("id", "priceCell");
			priceCell.textContent = "DKK "+item.price;
			tr.appendChild(priceCell);

			var descriptionCell = document.createElement("td");
			descriptionCell.setAttribute("id", "descriptionCell");
			descriptionCell.textContent = item.description;
			tr.appendChild(descriptionCell);

			var stockCell = document.createElement("td");
			stockCell.setAttribute("id", "stockCell");
			stockCell.textContent = "Available on stock: "+item.stock;
			tr.appendChild(stockCell);

			var inCartCell = document.createElement("td");
			inCartCell.setAttribute("id", "inCartCell");
			inCartCell.textContent = "0";
			inCartCell.setAttribute("ID", item.ID);
			tr.appendChild(inCartCell);

			var addToCartCell = document.createElement("td");
			addToCartCell.setAttribute("class", "addToCartBtn");
			addToCartCell.setAttribute("id", "addToCart");
			var btnAddToCart = document.createElement("button");
			btnAddToCart.textContent = "Add to Cart";
			addToCartCell.appendChild(btnAddToCart);

			btnAddToCart
					.addEventListener("click",	
							function() {
								sendRequest("POST","rest/shop/cart", "id=" + item.ID + "&stock=" + item.stock,function(itemsText) {
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

//			var idCell = document.createElement("td");
//			idCell.textContent = item.ID;
//			tr.appendChild(idCell);

			tableBody.appendChild(tr);
			div.appendChild(tableBody);

		}());
	}
}
function checkURL(url) {
    return(url.match(/\.(jpeg|jpg|gif|png)$/) != null);
}