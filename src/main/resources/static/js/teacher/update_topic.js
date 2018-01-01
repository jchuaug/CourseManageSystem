var courseId;
var topicId;
getId();

console.log(courseId + topicId);

function getId() {
	var url = location.href;
	var index1 = url.indexOf("course/");
	var index2 = url.indexOf("updateTopic/");
	courseId = url.substring(index1 + 7, index2 - 1);
	topicId = url.substring(index2 + 12);
}

function load() {
	getCourse();
	var token = window.localStorage.getItem("jwt");

	$
			.ajax({
				url : "/topic/" + topicId,
				dataType : "json",
				type : "get",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				headers : {
					"Authorization" : token
				},

				success : function(data) {
					document.getElementById("topic_name").setAttribute("value",data.name) ;
					document.getElementById("topic_description").value = data.description;
					document.getElementById("group_limit").setAttribute("value",data.groupLimit);
					document.getElementById("members_limit").setAttribute("value",data.groupMemberLimit);

				}

			});
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
		url : "/topic/" + topicId,
		type : "put",
		contentType : "application/json;charset=utf-8",
		// dataType: "json",
		headers : {
			"Authorization" : token
		},
		data : JSON.stringify({
			"name" : $(" #topic_name ").val(),
			"description" : $(" #topic_description ").val(),
			"groupLimit" : $(" #group_limit ").val(),
			"groupMemberLimit" : $(" #members_limit ").val(),
		}),

		success : function(data) {
			alert("修改成功");
		}
	});
}