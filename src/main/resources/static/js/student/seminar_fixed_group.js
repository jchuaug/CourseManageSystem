var courseId;
var seminarId;
getId();
console.log(courseId + "  " + seminarId);

function getId() {
	var url = location.href;
	var index1 = url.indexOf("course/");
	var index2 = url.indexOf("toSeminarFixed/");
	courseId = url.substring(index1 + 7, index2 - 1);
	seminarId = url.substring(index2 + 15);
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
	$
			.ajax({
				url : "/seminar/" + seminarId,
				dataType : "json",
				type : "get",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				headers : {
					"Authorization" : token
				},
				success : function(data) {
					document.getElementById("seminar_name").innerHTML = data.name;
					document.getElementById("seminar_description").innerHTML = data.description;
					var groupingMethod = document
							.getElementById("groupingMethod");
					if (data.groupingMethod == "fixed") {
						groupingMethod.innerHTML = "固定分组";
					} else if (data.groupingMethod == "random") {
						groupingMethod.innerHTML = "随机分组";
					} else {
						groupingMethod.innerHTML = "为设置分组方式";
					}
					document.getElementById("startTime").innerHTML = data.startTime;
					document.getElementById("endTime").innerHTML = data.endTime;
				}

			});
	$.ajax({
		url : "/seminar/" + seminarId + "/topic",
		dataType : "json",
		type : "get",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		headers : {
			"Authorization" : token
		},
		success : function(data) {
			var topicList = document.getElementById("topic_list");
			for (var i = 0; i < data.length; i++) {
				var topic = document.createElement("div");
				topic.setAttribute("class", "smallblock");
				topic.setAttribute("onclick", "toTopic(this)");
				topic.innerHTML = "<div hidden='hidden'>" + data[i].id
						+ "</div><div class='BlockFont'>" + data[i].name
						+ "</div>";
				topicList.appendChild(topic)
			}

		}

	});

}

function uploadReport() {
	//window.open ('page.html') ;
}
function toGrade() {
	window.location.href = "/course/" + courseId + "/toGrade";
}
function toTopic(topic) {
	var topicId = topic.firstChild.innerHTML;
	window.location.href = "/course/" + courseId +"/seminar/"+seminarId +"/topicFixed/" + topicId;
}
