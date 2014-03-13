/*$('#btnLogin').click(
				function() {
					var username = document.getElementById("username").value;
					var password = document.getElementById("password").value;
					$.post('rest/shop/login', "user=" + username + "&password="
							+ password, function(itemsText) {
						alert(itemsText);
						if (itemsText == "success") {
							loginSuccessMessage(username);
						} else if (itemsText == "fail") {
							loginFailMessage();
						}
						loginMessage();
					});
});
function loginSuccessMessage(name) {
	var loginStatus = document.getElementById("loginStatus");
	var welcome = document.getElementById("welcome");
	welcome.textContent = "Welcome " + name;
	loginStatus.textContent = "Logged In";

	setTimeout(function() {
		location.reload();
	}, 1000);

}
function loginFailMessage() {
	var loginStatus = document.getElementById("loginStatus");
	loginStatus.textContent = "Fail!";
}

$('#btnLogOut').click(function() {
	$.post('rest/shop/logout', null, function(itemsText) {
	});
	setTimeout(function() {
		location.reload();
	});
});*/