function showProducts(items) {
	// Get the table body we we can add items to it
	var tableBody = document.getElementById("itemtablebody");
	//Remove all contents of the table body (if any exist)
    tableBody.innerHTML = "";
    
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

			var idCell = document.createElement("td");
			idCell.textContent = item.ID;
			tr.appendChild(idCell);

			tableBody.appendChild(tr);

		}());
	}
}
