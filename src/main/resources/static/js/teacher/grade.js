var courseId;
var seminarId;
getId();
console.log(courseId + seminarId);




function getId() {
	var url = location.href;
	var index1 = url.indexOf("course/");
	var index2 = url.indexOf("gradeSeminar/");
	seminarId = url.substring(index2 + 13);
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
		url: "/seminar/" + seminarId+"/group",
		dataType: "json",
		type: "get",
		contentType: "application/json;charset=utf-8",
		dataType: "json",
		headers : {
			"Authorization" : token
		},
		success: function(data) {
			/*
			 * <tr> <td>A</td> <td>Bangalore</td> <td>xxx</td> <td>5</td>
			 * <td>已提交</td> <td>5</td> <td>5</td> <td> <img
			 * src="/static/Img/view.png" alt="预览"/> <img
			 * src="/static/Img/download.png" alt="下载"/> </td> </tr>
			 */
			var groupList=document.getElementById("group_list");
			for(var i = 0; i < data.length; i++) {
				$.ajax({
					url: "/group/" + data[i].id,
					dataType: "json",
					type: "get",
					contentType: "application/json;charset=utf-8",
					dataType: "json",
					headers : {
						"Authorization" : token
					},
					success: function(data) {
						
						var group=document.createElement("tr");
						
						group.innerHTML=
							"<td hidden='hidden'>"+data.id+"</td>"+
							"<td>"+data.topics[0].name+"</td>"+
							"<td>"+data.name+"</td>"+
		                    "<td>"+data.leader.name+"</td>"+
		                   /* "<td>"+data.grade.presentationGrade[0].grade+"</td>"+
		                    "<td>已提交</td>"+
		                   	"<td>"+data[i].grade.reportGrade+"</td>"+
		                    "<td>"+data[i].grade.grade+"</td>"+*/
		                    "<td>"+"</td>"+
		                    "<td>已提交</td>"+
		                   	"<td>"+"</td>"+
		                    "<td>"+"</td>"+
		                    "<td>"+
		                        "<img onclick='toReport(this)' src='/Img/view.png' alt='预览'/>"+
		                     	"<img src='/Img/download.png' alt='下载'/>"+
		                    "</td>";
		                   groupList.appendChild(group);
					}

				});
					}
				
			
		}

	});


}

function updateSeminar() {
	window.location.href = "/course/" + courseId + "/updateSeminar/" + seminarId;
}

function toReport(viewNode){
	var groupId =viewNode.parentNode.parentNode.firstChild.innerHTML;
	window.location.href = "/seminar/"+seminarId+"/group/"+groupId+"/report";
}
