var courseId = getCourseId();
console.log(courseId);

var token = window.localStorage.getItem("jwt");

function getCourseId() {
	var url = location.href;
	var index = url.indexOf("add_class/");
	return url.substring(index + 10);
}

function load() {
	getCourse();

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

function addClassTime() {
	var classTimeList = document.getElementById("class_time_list");
	var classTime = document.getElementById("class_time");
	var cloneClassTime = classTime.cloneNode(true);
	var addBotton = document.getElementById("add_button");
	var nodebr = document.createElement('br'); //创建一个li节点
	document.getElementById("class_time_list").appendChild(nodebr);
	document.getElementById("class_time_list").appendChild(cloneClassTime);
}

function submit() {
	var file = document.getElementById("file_upload");
	var filename="";
	if(file.files[0] == undefined){
            }else{   
               filename=file.files[0].name;  
            }  
	$.ajax({
		url: "/upload/classroster ",
		type: "post",
		headers : {
			"Authorization" : token
		},
		data:{"url":"/roster/"+filename},
		success: function(data) {
			
		}

	});
	var name = $(" #class_name ").val();
	var site = $(" #class_location ").val();
	var week = $("#week option:selected").val();
	var day = $("#day option:selected").val();
	var time = $("#time option:selected").val();
	var three = $(" #three ").val();
	var four = $(" #four ").val();
	var five = $(" #five ").val();
	var report = $(" #report ").val();
	var presentation = $(" #presentation").val();

	var data = {
		"name": name,
		"site": site,
		"time": week + day + time,
		"roster":"/roster/"+filename,
		"proportions": {
			"c": three,
			"b": four,
			"a": five,
			"report": report,
			"presentation": presentation
		}

	};
	$.ajax({
		url: "/course/" + courseId + "/class",
		type: "post",
		dataType: "json",
		headers : {
			"Authorization" : token
		},
		contentType: "application/json;charset=utf-8",
		data: JSON.stringify(data),
		success: function(data) {
			alert("添加成功");
		}
	});
}