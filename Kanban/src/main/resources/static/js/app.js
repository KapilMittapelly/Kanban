let clearFlag = true;
function forceClick(elementId) {
	window.addEventListener("DOMContentLoaded", function() {
		var button = document.getElementById(elementId);
		button.click();
	});
}

function showPanel() {
	$('.button-collapse').sideNav('show');
}

function hidePanel() {
	$('.button-collapse').sideNav('hide');
}

function validate() {
	try {
		if (document.login.login_username.value == "") {
	        document.login.login_username.focus();
	        $("#login_username_error").removeClass("hide");
	        return false;
	    } else {
	    	$("#login_username_error").addClass("hide");
	    }
	    if (document.login.login_password.value == "") {
	        document.login.login_password.focus();
	        $("#login_password_error").removeClass("hide");
	        return false;
	    } else {
	    	$("#login_password_error").addClass("hide");
	    }
	} catch(e) {
		console.log(e);
	}
}
