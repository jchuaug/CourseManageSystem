var courseId;
var seminarId;
getId();
console.log(courseId + seminarId);




function getId() {
	var url = location.href;
	var index1 = url.indexOf("course/");
	var index2 = url.indexOf("toSeminar/");
	seminarId = url.substring(index2 + 10);
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
		dataType: "json",
		headers : {
			"Authorization" : token
		},
		success: function(data) {
			document.getElementById("seminar_name").innerHTML = data.name;
			document.getElementById("seminar_description").innerHTML = data.description;
			if(data.groupingMethod == "fixed") {
				document.getElementById("groupingMethod").innerHTML = "固定分组";
			} else if(data.groupingMethod == "random") {
				document.getElementById("groupingMethod").innerHTML = "随机分组";
			} else {
				document.getElementById("groupingMethod").innerHTML = "未设置分组方式";
			}
			document.getElementById("startTime").innerHTML = data.startTime;
			document.getElementById("endTime").innerHTML = data.endTime;
		}

	});
	$.ajax({
		url: "/seminar/" + seminarId + "/topic",
		dataType: "json",
		type: "get",
		contentType: "application/json;charset=utf-8",
		dataType: "json",
		headers : {
			"Authorization" : token
		},
		success: function(data) {
			var topicList = document.getElementById("topic_list");
			for(var i = 0; i < data.length; i++) {
				var topic = document.createElement("div");
				topic.setAttribute("class", "topicBlock");
				topic.setAttribute("onclick", "toTopic(this)");
				topic.innerHTML = "<div hidden='hidden'>" + data[i].id + "</div><div class='topicBlockFont'>" + data[i].name + "</div>";
				topicList.appendChild(topic)
			}
			var topic = document.createElement("div");
			topic.setAttribute("class", "topicBlock");
			topic.innerHTML = "<img class='addImg' src='/Img/smalladd.png' alt='添加' onclick='addTopic()' />";
			topicList.appendChild(topic);
		}

	});

}

function updateSeminar() {
	window.location.href = "/course/" + courseId + "/updateSeminar/" + seminarId;
}

function deleteSeminar() {
	var token = window.localStorage.getItem("jwt");
	$.ajax({
		url: "/seminar/" + seminarId,
		dataType: "json",
		type: "delete",
		contentType: "application/json;charset=utf-8",
		dataType: "json",
		headers : {
			"Authorization" : token
		},
		success: function(data) {
			alert("删除成功");
			window.location.href = "/teacherToCourse/" + courseId ;
		}

	});
}
function grade(){
	window.location.href = "/course/"+courseId+"/gradeSeminar/" + seminarId;
}
function addTopic(){
	window.location.href = "/course/"+courseId+"/seminar/"+seminarId+"/addTopic";
}
function toTopic(topic){
	var topicId=topic.firstChild.innerHTML;
	window.location.href = "/course/"+courseId+"/seminar/"+seminarId+"/topic/"+topicId;
}
