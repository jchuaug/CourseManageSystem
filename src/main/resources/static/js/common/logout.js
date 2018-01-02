/*----logout-----*/
function logout() {
	if(localStorage.jwt) {
		localStorage.removeItem("jwt");
		window.location.href = '/';
	} else {
		window.location.href = '/';
	}
}

function back() {
	history.back(-1);
}


