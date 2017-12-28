var courseId;
var topicId;
getId();
var token = window.localStorage.getItem("jwt");

function getId() {
	var url = location.href;
	var index1 = url.indexOf("course/");
	var index2 = url.indexOf("topicFixed/");
	courseId = url.substring(index1 + 7, index2 - 1);
	topicId = url.substring(index2 + 11);
}

function getCourse() {
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
	getCourse();
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
					document.getElementById("topic_id").innerHTML = data.id;
					document.getElementById("topic_name").innerHTML = data.name;
					document.getElementById("topic_description").innerHTML = data.description;
					document.getElementById("group_limit").innerHTML = data.groupLimit;
					document.getElementById("members_limit").innerHTML = data.groupMemberLimit;
				}

			});

}

function chooseTopic() {
	var groupId = 1;
	$.ajax({
		url : "/group/" + groupId + "/topic",
		dataType : "json",
		type : "post",
		data : {
			"id" : document.getElementById("topic_id").innerHTML
		},
		headers : {
			"Authorization" : token
		},
		success : function(data) {
			alert("选择话题成功");
		}

	});
}
