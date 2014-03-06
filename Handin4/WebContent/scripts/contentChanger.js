$(document).ready(function() {
	$('#homePage').click( function() {
		$('#mainContainer').load('home.html');
	});
	$('#productsPage').click( function() {
		$('#mainContainer').load('products.html');
		$.get('rest/shop/cloudItems', function(itemsText) {
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
	$('#loginPage').click( function() {
		$('#mainContainer').load('login.html');
	});

});

function addItemsToTable(items) {
    //Get the table body we we can add items to it
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
        tr.appendChild(inCartCell);
        
        var addToCartCell = document.createElement("td");
        var btnAddToCart = document.createElement("button");
        btnAddToCart.textContent = "Add to Cart";
        //Tilføjer addItem funktionen
        btnAddToCart.setAttribute("id", "btnAddToCart");
        
        //        
        addToCartCell.appendChild(btnAddToCart);
        tr.appendChild(addToCartCell);
        
        var idCell = document.createElement("td");
        idCell.textContent = item.ID;
        tr.appendChild(idCell);
        
        tableBody.appendChild(tr);
    }
}

/**
 * A function that can add event listeners in any browser
 */
function addEventListener(myNode, eventType, myHandlerFunc) {
    if (myNode.addEventListener)
        myNode.addEventListener(eventType, myHandlerFunc, false);
    else
        myNode.attachEvent("on" + eventType,
            function (event) {
                myHandlerFunc.call(myNode, event);
            });
}

function loadShopURLXMLDoc()
{
var xmlhttp;
var txt,x,xx,i;
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    txt="<table border='1'><tr><th>Title</th><th>Artist</th></tr>";
    x=xmlhttp.responseXML.documentElement.getElementsByTagName("customer");
    for (i=0;i<x.length;i++)
      {
      txt=txt + "<tr>";
      xx=x[i].getElementsByTagName("customerID");
        {
        try
          {
          txt=txt + "<td>" + xx[0].firstChild.nodeValue + "</td>";
          }
        catch (er)
          {
          txt=txt + "<td> </td>";
          }
        }
      xx=x[i].getElementsByTagName("customerName");
        {
        try
          {
          txt=txt + "<td>" + xx[0].firstChild.nodeValue + "</td>";
          }
        catch (er)
          {
          txt=txt + "<td> </td>";
          }
        }
      txt=txt + "</tr>";
      }
    txt=txt + "</table>";
    document.getElementById('txtCDInfo').innerHTML=txt;
    }
  };
xmlhttp.open("GET","http://services.brics.dk/java4/cloud/listCustomers",true);
xmlhttp.send();
}