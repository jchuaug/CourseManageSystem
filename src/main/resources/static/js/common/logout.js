/*----logout-----*/
function logout() {
	if(localStorage.jwt) {
		localStorage.removeItem("jwt");
		window.location.href = '/';
	} else {
		window.location.href = '/';
	}
}


