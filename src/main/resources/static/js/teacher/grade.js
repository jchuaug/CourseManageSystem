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

function load() {
	getCourse();
	$.ajax({
		url: "/seminar/" + seminarId+"/group",
		dataType: "json",
		type: "get",
		contentType: "application/json;charset=utf-8",
		dataType: "json",
		success: function(data) {
			/*
			 * <tr>
                    <td>A</td>
                    <td>Bangalore</td>
                    <td>xxx</td>
                    <td>5</td>
                    <td>已提交</td>
                    <td>5</td>
                    <td>5</td>
                    <td>
                        <img src="/static/Img/view.png" alt="预览"/>
                        <img src="/static/Img/download.png" alt="下载"/>
                    </td>
                </tr>
			 */
			var groupList=document.getElementById("group_list");
			for(var i = 0; i < data.length; i++) {
				var group=document.createElement("tr");
				
				group.innerHTML=
					"<td hidden='hidden'>"+data[i].id+"</td>"+
					"<td>"+data[i].topics[0].name+"</td>"+
					"<td>"+data[i].name+"</td>"+
                    "<td>"+data[i].leader.name+"</td>"+
                    "<td>"+data[i].grade.presentationGrade[0].grade+"</td>"+
                    "<td>已提交</td>"+
                   	"<td>"+data[i].grade.reportGrade+"</td>"+
                    "<td>"+data[i].grade.grade+"</td>"+
                    "<td>"+
                        "<img onclick='toReport(this)' src='/static/Img/view.png' alt='预览'/>"+
                     	"<img src='/static/Img/download.png' alt='下载'/>"+
                    "</td>";
                   groupList.appendChild(group);
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
