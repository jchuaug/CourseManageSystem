var courseId;
getId();

function getId() {
	var url = location.href;
	var index1 = url.indexOf("course/");
	var index2 = url.indexOf("toGrade");
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
		url: "/course/" + courseId + "/grade",
		dataType: "json",
		type: "get",
		contentType: "application/json;charset=utf-8",
		dataType: "json",
		success: function(data) {
			var gradeList=document.getElementById("grade_list");
			for(var i = 0; i < data.length; i++) {
				/*
				 * <tr>
                              <th>讨论课</th>
                              <th>组名</th>
                              <th>组长</th>
							  <th>课堂讨论课得分</th>
                              <th>报告分数</th>
                              <th>总分</th>
                            </tr>
				 */

				var tr = document.createElement("tr");
				tr.innerHTML = 
					"<td>"+(i+1)+"</td>" +
					"<td>"+data[i].seminarName+"</td>" +
					"<td>"+data[i].leaderName+"</td>" +
					"<td>"+data[i].presentationGrade+"</td>" +
					"<td>"+data[i].reportGrade+"</td>" +
					"<td>"+data[i].grade+"</td>";
				gradeList.appendChild(tr);
					
			}
		}

	});

}

function getStudents() {

	var name = $("#student_name").val();
	var no = $("#student_number").val();
	$.ajax({
		url: "/class/" + classId + "/student?nameBeginWith=" + name + "&noBeginWith=" + no,
		dataType: "json",
		type: "get",
		contentType: "application/json;charset=utf-8",
		success: function(data) {

			var studentTable = document.getElementById("student_list_table");
			if(data.length == 0) {
				studentTable.innerHTML = "无学生信息";
			} else {
				studentTable.innerHTML = "<tr><th>学号</th><th>姓名</th><th>操作</th></tr>";
				for(var i = 0; i < data.length; i++) {
					var newMember = document.createElement("tr");
					newMember.innerHTML = "<td hidden='hidden'>" + data[i].id + "</td><td>" + data[i].number + "</td><td>" + data[i].name + "</td><td><img onclick='addMember(this)' src='/static/img/home.png'/></td>";
					if(i / 2 == 0)
						newMember.setAttribute("class", "alt");
					studentTable.appendChild(newMember);
				}
			}
		}

	});
}