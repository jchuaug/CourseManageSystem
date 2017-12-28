var courseId;
var classId;
getId();
function getId() {
	var url = location.href;
	var index1 = url.indexOf("course/");
	var index2 = url.indexOf("updateClass/");
	classId = url.substring(index2 + 12);
	courseId = url.substring(index1 + 7, index2 - 1);
}

function getCourse() {
	var token = window.localStorage.getItem("jwt");
	var storage = window.localStorage;
	if ((storage.getItem("name") != null)
			&& (storage.getItem("desciption") != null)) {
		document.getElementById("course_name").innerHTML = storage
				.getItem("name");
		document.getElementById("course_description").innerHTML = storage
				.getItem("description")
	} else {
		$
				.ajax({
					url : "/course/" + courseId,
					dataType : "json",
					type : "get",
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					headers : {
						"Authorization" : token
					},
					success : function(data) {
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
		url : "/class/" + classId,
		dataType : "json",
		type : "get",
		contentType : "application/json;charset=utf-8",
		headers : {
			"Authorization" : token
		},
		success : function(data) {
			document.getElementById("class_name").setAttribute("value",
					data.name);
			document.getElementById("class_location").setAttribute("value",
					data.site);
			document.getElementById("report_weight").setAttribute("value",
					data.proportions.report);
			document.getElementById("present_weight").setAttribute("value",
					data.proportions.presentation);
			document.getElementById("five").setAttribute("value",
					data.proportions.a);
			document.getElementById("four").setAttribute("value",
					data.proportions.b);
			document.getElementById("three").setAttribute("value",
					data.proportions.c);
		}
	});
}

function submit() {
	var token = window.localStorage.getItem("jwt");
	$
			.ajax({
				url : "/class/" + classId,
				dataType : "json",
				contentType : "application/json;charset=utf-8",
				type : "put",
				data : JSON
						.stringify({
							"name" : document.getElementById("class_name").value,
							"site" : document.getElementById("class_location").value,
							"time" : "周三 一二节",
							"proportions" : {
								"c" : document.getElementById("three").value,
								"b" : document.getElementById("four").value,
								"a" : document.getElementById("five").value,
								"report" : document
										.getElementById("report_weight").value,
								"presentation" : document
										.getElementById("present_weight").value
							}
						}),
				headers : {
					"Authorization" : token
				},
				success : function(data) {
					alert("修改成功");
				}
			});
}