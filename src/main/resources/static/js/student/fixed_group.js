var courseId;
var classId;
var groupId;
getId();

function getId() {
	var url = location.href;
	var index1 = url.indexOf("course/");
	var index2 = url.indexOf("toFixedGroup/");
	courseId = url.substring(index1 + 7, index2 - 1);
	classId = url.substring(index2 + 13);
	console.log(courseId+" "+classId+"  "+groupId);
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

function load() {
	var token = window.localStorage.getItem("jwt");
	getCourse();
	$
			.ajax({
				url : "/class/" + classId + "/classgroup",
				dataType : "json",
				type : "get",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				headers : {
					"Authorization" : token
				},

				success : function(data) {
					groupId = data.id;
					var studentTable = document.getElementById("group_table");
					studentTable.innerHTML = "<tr><th>角色</th><th>学号</th><th>姓名</th><th>操作</th></tr>";
					var leader = document.createElement("tr");
					leader.innerHTML = "<tr><td>队长</td><td>"
							+ data.leader.number + "</td><td>"
							+ data.leader.name + "</td><td></td></tr>";
					studentTable.appendChild(leader);
					for (var i = 0; i < data.members.length; i++) {
						var newMember = document.createElement("tr");
						newMember.innerHTML ="<td hidden='hidden'>"
							+ data.members[i].id
							+ "</td><td>队员</td><td>"
								+ data.members[i].number
								+ "</td><td>"
								+ data.members[i].name
								+ "</td><td><img onclick='deleteMember(this)' src='/img/delete.jpg' height='25px' width='25px'/></td>";
						if (i / 2 == 0)
							newMember.setAttribute("class", "alt");
						studentTable.appendChild(newMember);
					}
				},

				statusCode : {
					404 : function() {
						 document.getElementById("group_table").innerHTML="未加入固定分组";
					}
				}
			});

}

function getStudents() {
	var token = window.localStorage.getItem("jwt");

	var name = $("#student_name").val();
	var no = $("#student_number").val();
	$
			.ajax({
				url : "/class/" + classId + "/student",
				dataType : "json",
				type : "get",
				contentType : "application/json;charset=utf-8",
				headers : {
					"Authorization" : token
				},
				data : {
					"nameBeginWith" : name,
					"numBeginWith" : no
				},

				success : function(data) {

					var studentTable = document
							.getElementById("student_list_table");
					if (data.length == 0) {
						studentTable.innerHTML = "无学生信息";
					} else {
						studentTable.innerHTML = "<tr><th>学号</th><th>姓名</th><th>操作</th></tr>";
						for (var i = 0; i < data.length; i++) {
							var newMember = document.createElement("tr");
							newMember.innerHTML = "<td hidden='hidden'>"
									+ data[i].id
									+ "</td><td>"
									+ data[i].number
									+ "</td><td>"
									+ data[i].name
									+ "</td><td><img onclick='addMember(this)' src='/img/home.png'/></td>";
							if (i / 2 == 0)
								newMember.setAttribute("class", "alt");
							studentTable.appendChild(newMember);
						}
					}
				}

			});
}

function addMember(student) {
	var token = window.localStorage.getItem("jwt");
	var studentId = student.parentNode.parentNode.firstChild.innerHTML;
	console.log(studentId);
	$.ajax({
		url : "/class/" + classId + "/classgroup/add",
		type : "put",
		data : {
			"id":studentId
		},
		dataType : "json",
		headers : {
			"Authorization" : token
		},

		success : function(data) {
			alert("添加成功");
			window.location.reload();
		}

	});
}

function deleteMember(student) {
	var token = window.localStorage.getItem("jwt");
	var studentId = student.parentNode.parentNode.firstChild.innerHTML;
	console.log(studentId);
	$.ajax({
		url : "/class/" + classId + "/classgroup/remove",
		type : "put",
		data : {
			"id":studentId
		},
		dataType : "json",
		headers : {
			"Authorization" : token
		},

		success : function(data) {
			alert("删除成功");
			window.location.reload();
		}

	});
}