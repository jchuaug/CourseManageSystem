var seminarId;
var courseId;
getId();

function getId() {
	var url = location.href;
	var index1 = url.indexOf("course/");
	var index2 = url.indexOf("seminar/");
	var index3 = url.indexOf("addTopic");
	courseId = url.substring(index1 + 7, index2 - 1);
	seminarId = url.substring(index2 + 8, index3 - 1);
}

function load() {
	getCourse();

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

function submit() {
	var token = window.localStorage.getItem("jwt");
	$.ajax({
		url : "/seminar/" + seminarId + "/topic",
		type : "post",
		contentType : "application/json;charset=utf-8",
		// dataType: "json",
		data : JSON.stringify({
			"name" : $(" #topic_name ").val(),
			"description" : $(" #topic_description ").val(),
			"groupLimit" : $(" #group_limit ").val(),
			"groupMemberLimit" : $(" #members_limit ").val(),
		}),
		headers : {
			"Authorization" : token
		},

		success : function(data) {
			alert("添加成功");
			window.location.href = "/course/"+courseId+"/toSeminar/"+seminarId;
		}
	});
}