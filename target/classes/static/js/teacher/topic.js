var courseId;
var seminarId;
var topicId;
getId();

function getId() {
	var url = location.href;
	var index1 = url.indexOf("course/");
	var index2 = url.indexOf("seminar/");
	var index3 = url.indexOf("topic/");
	courseId = url.substring(index1 + 7, index2 - 1);
	seminarId = url.substring(index2 + 8, index3 - 1);
	topicId = url.substring(index3 + 6);
}

function load() {
	getCourse();
	$.ajax({
			url: "/topic/" + topicId,
			dataType: "json",
			type: "get",
			contentType: "application/json;charset=utf-8",
			dataType: "json",
			success: function(data) {
				document.getElementById("topic_name").innerHTML = data.name;
				document.getElementById("topic_description").innerHTML = data.description;
				 document.getElementById("group_limit").innerHTML = data.groupLimit;
				document.getElementById("members_limit").innerHTML = data.groupMemberLimit;
				 
			}

		});
}

function getCourse() {
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
			success: function(data) {
				document.getElementById("course_name").innerHTML = data.name;
				document.getElementById("course_description").innerHTML = data.description;
				storage.setItem("name", data.name);  
				storage.setItem("description", data.description); 
			}

		});
	}
}

function updateTopic() {
	window.location.href = "/course/"+courseId+"/updateTopic/"+topicId;
}
function deleteTopic() {
	$.ajax({
			url: "/topic/" + topicId,
			dataType: "json",
			type: "delete",
			contentType: "application/json;charset=utf-8",
			dataType: "json",
			success: function(data) {
				alert("删除成功"); 
				window.location.href = "/course/"+courseId+"/toSeminar/"+seminarId;
			}

		});
}