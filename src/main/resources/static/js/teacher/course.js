
var courseId=getCourseId();




function getCourseId(){
	var url=location.href;
var index= url.indexOf("teacherToCourse/");
return url.substring(index+16);
}

function getCourse(){
	var storage = window.localStorage;
	var token = window.localStorage.getItem("jwt");
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

function ajxRequest() {
	var token = window.localStorage.getItem("jwt");
	getCourse();
	$.ajax({
		url: "/course/"+courseId+"/class",
		dataType: "json",
		type: "get",
		contentType: "application/json;charset=utf-8",
		dataType: "json",
		headers : {
			"Authorization" : token
		},
		success: function(data) {
			for(var i = 0; i < data.length; i++) {
				var newClass = document.createElement("a");
				newClass.innerHTML = "<label class='blockFont'>" + data[i].name + "</label>";
				newClass.setAttribute("href", "/course/"+courseId+"/toClass/" + data[i].id);
				newClass.setAttribute("class", "block");
				document.getElementById("class_list").appendChild(newClass);
			}
		}

	});
	$.ajax({
		url: "/course/"+courseId+"/seminar",
		dataType: "json",
		type: "get",
		contentType: "application/json;charset=utf-8",
		dataType: "json",
		headers : {
			"Authorization" : token
		},
		success: function(data) {
			for(var i = 0; i < data.length; i++) {
				var newSenimar = document.createElement("a");
				newSenimar.innerHTML = "<label class='blockFont'>" + data[i].name + "</label>";
				newSenimar.setAttribute("href", "/course/"+courseId+"/toSeminar/"+data[i].id);
				newSenimar.setAttribute("class", "block");
				document.getElementById("seminar_list").appendChild(newSenimar);
			}
		}

	});
	
	document.getElementById("add_class").setAttribute("href","/add_class/"+courseId);
	document.getElementById("add_seminar").setAttribute("href","/add_seminar/"+courseId);
	
}

