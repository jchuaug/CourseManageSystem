var courseId = getCourseId();
console.log(courseId);

function getCourseId() {
	var url = location.href;
	var index = url.indexOf("add_seminar/");
	return url.substring(index + 12);
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

function addClassTime() {

	var classTimeList = document.getElementById("class_time_list");
	var classTime = document.getElementById("class_time");
	var cloneClassTime = classTime.cloneNode(true);
	var addBotton = document.getElementById("add_button");
	var nodebr = document.createElement('br'); // 创建一个li节点
	document.getElementById("class_time_list").appendChild(nodebr);
	document.getElementById("class_time_list").appendChild(cloneClassTime);
}

function resetForm() {
	document.getElementById("myFrom").reset();
}

function submit() {
	var token = window.localStorage.getItem("jwt");
	var name = $(" #seminar_name ").val();
	var description = $(" #seminar_description ").val();
	var groupingMethod = $("#groupingMethod option:selected").val();
	var startTime = $(" #startTime ").val();
	var endTime = $(" #endTime ").val();
	var three = $(" #three ").val();
	var four = $(" #four ").val();
	var five = $(" #five ").val();
	var report = $(" #report_weight ").val();
	var presentation = $(" #present_weight").val();

	var data = {
		"name" : name,
		"description" : description,
		"groupMethod" : groupingMethod,
		"startTime" : startTime,
		"endTime" : endTime,
		"proportions" : {
			"c" : three,
			"b" : four,
			"a" : five,
			"report" : report,
			"presentation" : presentation
		}
	};
	$.ajax({
		url : "/course/" + courseId + "/seminar",
		type : "post",
		contentType : "application/json;charset=utf-8",
		// dataType: "json",
		data : JSON.stringify(data),
		headers : {
			"Authorization" : token
		},
		success : function(data) {
			alert("添加成功");
		}

	});
}