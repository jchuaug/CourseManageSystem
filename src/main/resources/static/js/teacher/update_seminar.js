var courseId;
var seminarId;
getId();

var token = window.localStorage.getItem("jwt");



function getId() {
	var url = location.href;
	var index1 = url.indexOf("course/");
	var index2 = url.indexOf("updateSeminar/");
	seminarId = url.substring(index2 + 14);
	courseId = url.substring(index1 + 7, index2 - 1);
}

function getCourse() {
	var token = window.localStorage.getItem("jwt");
	var storage = window.localStorage;
	if((storage.getItem("name") != null) && (storage.getItem("desciption") != null)) {      
		document.getElementById("course_name").innerHTML = storage.getItem("name");
		document.getElementById("course_description").innerHTML = storage.getItem("description")           
	} else {
		$.ajax({
			url: "/course/" + courseId,
			dataType: "json",
			type: "get",
			contentType: "application/json;charset=utf-8",
			dataType: "json",
			headers : {
				"Authorization" : token
			},
			success: function(data) {
				document.getElementById("course_name").innerHTML = data.name;
				document.getElementById("course_description").innerHTML = data.description;
				storage.setItem("name", data.name);  
				storage.setItem("description", data.description); 
			}

		});
	}
}

function load() {
	var token = window.localStorage.getItem("jwt");
	getCourse();
	$.ajax({
		url: "/seminar/" + seminarId,
		dataType: "json",
		type: "get",
		contentType: "application/json;charset=utf-8",
		headers : {
			"Authorization" : token
		},
		success: function(data) {
			document.getElementById("seminar_name").setAttribute("value", data.name);
			document.getElementById("seminar_description").setAttribute("value", data.description);
			if (data.groupMethod=="fixed") {
				document.getElementById("groupingMethod").innerHTML="<option>固定分组</option><option>随机分组</option>";
			} else{
					document.getElementById("groupingMethod").innerHTML="<option>随机分组</option><option>固定分组</option>";
			}
			document.getElementById("present_weight").setAttribute("value", data.proportions.presentation);
			document.getElementById("report_weight").setAttribute("value", data.proportions.report);
			document.getElementById("five").setAttribute("value", data.proportions.a);
			document.getElementById("four").setAttribute("value", data.proportions.b);
			document.getElementById("three").setAttribute("value", data.proportions.c);
		}
	});
}

function submit() {
	var token = window.localStorage.getItem("jwt");
	var groupingMethod=$("#groupingMethod option:selected").val();
	if (groupingMethod=="固定分组") {
		groupingMethod="fixed";
	} else{
		groupingMethod="random";
	}
	console.log(groupingMethod);
	$.ajax({
		url: "/seminar/" + seminarId,
		contentType: "application/json;charset=utf-8",
		dataType: "json",
		type: "put",
		headers : {
			"Authorization" : token
		},
		data: JSON.stringify({
				"name": document.getElementById("seminar_name").value,
				"description": document.getElementById("seminar_description").value,
				"groupMethod": groupingMethod,
				"startTime": document.getElementById("startTime").value,
				"endTime": document.getElementById("endTime").value,
				"proportions": {
					"c": document.getElementById("three").value,
					"b": document.getElementById("four").value,
					"a": document.getElementById("five").value,
					"report": document.getElementById("report_weight").value,
					"presentation": document.getElementById("present_weight").value
				}
			})
		,
		success: function(data) {
			alert("成功修改");
		}
	});
}