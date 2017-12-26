var courseId;
var classId;
getId();
console.log(courseId+classId);
function getId(){
	var url=location.href;
	var index1= url.indexOf("course/");
	var index2= url.indexOf("toClass/");
	classId= url.substring(index2+8);
	courseId= url.substring(index1+7,index2-1);
}

function getCourse(){
	var storage = window.localStorage;
	if((storage.getItem("name") != null) && (storage.getItem("desciption") != null)) {      
		document.getElementById("course_name").innerHTML = storage.getItem("name");
		document.getElementById("course_description").innerHTML = storage.getItem("description")           
	} else {
		$.ajax({
			url: "/course/"+courseId,
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

function load() {
	getCourse();
	$.ajax({
	 		url: "/class/"+classId,
	 		dataType: "json",
	 		type: "get",
	 		contentType:"application/json;charset=utf-8",
	 		dataType: "json",
	 		success: function(data) {
	 				document.getElementById("class_name").innerHTML=data.name;
	 				document.getElementById("class_location").innerHTML=data.site;
	 				document.getElementById("class_time").innerHTML=data.time;
	 				document.getElementById("report_weight").innerHTML=data.proportions.report;
	 				document.getElementById("present_weight").innerHTML=data.proportions.presentation;
	 				document.getElementById("five").innerHTML=data.proportions.a;
	 				document.getElementById("four").innerHTML=data.proportions.b;
	 				document.getElementById("three").innerHTML=data.proportions.c;
	 			}
	 		

	 	});
	

}

function updateClass(){
	window.location.href="/course/"+courseId+"/updateClass/"+classId;
}

function deleteClass(){
	$.ajax({
 		url: "/class/"+classId,
 		dataType: "json",
 		type: "delete",
 		contentType:"application/json;charset=utf-8",
 		dataType: "json",
 		success: function(data) {
 				alert("删除成功");
 				window.location.href = "/teacherToCourse/" + courseId ;
 			}
 		

 	});
}


