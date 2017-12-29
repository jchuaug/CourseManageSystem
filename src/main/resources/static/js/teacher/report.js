var groupId;
var seminarId;
getId();
console.log(groupId + seminarId);




function getId() {
	var url = location.href;
	var index1 = url.indexOf("seminar/");
	var index2 = url.indexOf("group/");
	var index3 = url.indexOf("report");
	seminarId = url.substring(index1 + 8,index2-1);
	groupId = url.substring(index2 + 6, index3 - 1);
}


function load() {
	var token = window.localStorage.getItem("jwt");
	$.ajax({
		url: "/group/" + groupId,
		dataType: "json",
		type: "get",
		contentType: "application/json;charset=utf-8",
		dataType: "json",
		headers : {
			"Authorization" : token
		},
		success: function(data) {
			document.getElementById("group_name").innerHTML=data.name;
			document.getElementById("leader").innerHTML=data.leader.name;
			//data.report
		}

	});
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
			document.getElementById("seminar_name").innerHTML=data.name;
		}

	});


}

function submit() {
	var token = window.localStorage.getItem("jwt");
	var grade=document.getElementById("grade_value").value;
	$.ajax({
		url: "/group/" + groupId+"/grade",
		dataType: "json",
		type: "put",
		data:{"grade":grade},
		headers : {
			"Authorization" : token
		},
		success: function(data) {
			alert("打分成功");
		}

	});
}


