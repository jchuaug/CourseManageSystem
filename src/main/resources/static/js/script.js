/**
 * 
 */
/*
function load() {
	xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			document.getElementById("seminar").innerHTML = xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET", "1/class", true);
	xmlhttp.send();

}
*/
function resetForm() {
	document.getElementById("myFrom").reset();
}

function addClass() {
	var xmlhttp;
	xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			document.getElementById("myDiv").innerHTML = xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET", "teacher/add_class", true);
	xmlhttp.send();
}

function addClassTime() {
	var classTimeList = document.getElementById("class_time_list");
	var classTime = document.getElementById("class_time");
	var cloneClassTime = classTime.cloneNode(true);
	var addBotton = document.getElementById("add_button");
	var nodebr = document.createElement('br'); //创建一个li节点
	document.getElementById("class_time_list").appendChild(nodebr);
	document.getElementById("class_time_list").appendChild(cloneClassTime);
}

function alter_group() {
	var delete_button = document.getElementById("delete_button");
	var add = document.getElementById("add_team_num")

	if(delete_button.style.display == "none") {
		delete_button.style.display = "block";

	}
	if(add.style.display == "none") {
		add.style.display = "block";

	}

}