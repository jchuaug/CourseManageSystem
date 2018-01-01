var courseId = getCourseId();
function getCourseId() {
	var url = location.href;
	var index = url.indexOf("studentToCourse/");
	return url.substring(index + 16);
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
		url : "/course/" + courseId + "/seminar",
		dataType : "json",
		type : "get",
		headers : {
			"Authorization" : token
		},
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		success : function(data) {
			for (var i = 0; i < data.length; i++) {
				var newClass = document.createElement("a");
				newClass.innerHTML = "<label class='blockFont'>" + data[i].name
						+ "</label>";
				if (data[i].groupingMethod == "fixed") {
					newClass.setAttribute("href", "/course/" + courseId
							+ "/toSeminarFixed/" + data[i].id);
				} else {
					newClass.setAttribute("href", "/course/" + courseId
							+ "/toSeminarRandom/" + data[i].id);
				}
				newClass.setAttribute("class", "block");
				document.getElementById("seminar_list").appendChild(newClass);
			}
		}

	});

	var classId = "1";
	document.getElementById("fixedGroup").setAttribute("href",
			"/course/" + courseId + "/toFixedGroup/" + classId);

}
