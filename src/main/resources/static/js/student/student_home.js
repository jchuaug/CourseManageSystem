//---------------------------- support functions--------------------------------------
function updateCookie(name, value) {
	document.cookie = name + '=' + value;
}

function getCookie(name) {
	var arr = document.cookie
			.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (arr != null)
		return unescape(arr[2]);
	return null;
};

// ----------------------------StudentHomePage-------------------------------

function stuinfo() { // StudentHomePage showstuinfo
	$
			.ajax({
				type : 'get',
				url : '/me',
				dataType : "json",
				contentType : "application/json;",
				success : function(data, status, xhr) {
					if (xhr.status == 200) {
						document.cookie = 'username=' + data.id; // store
						// username
						// in cookie
						$("#username").html(
								'用户名：' + '<span>' + data.id + '</span>');
						$("#stuffNum").html(
								'学号：' + '<span>' + data.number + '</span>');
						$("#name").html(
								'姓名：' + '<span>' + data.name + '</span>');
						$("#gender").html(
								'性别：' + '<span>' + data.gender + '</span>');
						$("#school").html(
								'学校：' + '<span>' + data.school + '</span>');
						$("#title").html(
								'学历：' + '<span>' + data.title + '</span>');
						$("#email").html(
								'邮箱：' + '<span>' + data.email + '</span>');
						$("#phone").html(
								'联系方式：' + '<span>' + data.phone + '</span>');
					}
				},
				statusCode : {
					401 : function() { // statuscode unknown
						alert("获取信息失败");
					}
				}
			});
}

// ----------------------------StudentInfoModifyPage-------------------------------

function stumodinfo() { // StudentInfoModifyPage getstuinfo
	$
			.ajax({
				type : 'get',
				url : '/me',
				dataType : "json",
				contentType : "application/json;",
				success : function(data, status, xhr) {
					if (xhr.status == 200) {
						$("#username").html(
								'用户名：' + '<span>' + data.id + '</span>');
						$("#stuffNum").html(
								'学号：' + '<span>' + data.number + '</span>');
						$("#name").val(data.name);
						$("#gender").val(data.gender);
						$("#school").val(data.school.name);
						$("#title").val(data.title);
						$("#email").val(data.email);
						$("#phone").html(
								'联系方式：' + '<span>' + data.phone + '</span>');
					}
				},
				statusCode : {
					401 : function() { // statuscode unknown
						alert("获取信息失败");
					}
				}
			});
}

function stuinfomod() { // StudentInfoModifyPage updatestuinfo
	var ata = {
		name : $("#name").val(),
		gender : $("#gender").val(),
		school : $("#school").val(),
		title : $("#title").val(),
		email : $("#email").val(),
		avatar : "/avatar/3486.png"
	}
	$.ajax({
		type : 'put',
		url : '/me',
		dataType : "json",
		contentType : "application/json;",
		data : JSON.stringify(ata),
		success : function(data, status, xhr) {
			console.log(xhr.status);
			if (xhr.status == 204) {
				alert("修改成功!");
				window.location.href = "/student/profile";
			}
		},
		statusCode : {
			400 : function() {
				alert("信息不合法");
			}
		}
	});
}

var courseid = '';

function jumpCourse(id) {
	var info = id;
	var arr = info.split(";");
	var classid = arr[0];
	var courseid = arr[1];
	updateCookie('classcurrent', classid);
	updateCookie('coursecurrent', courseid);
	window.location.href = "/student/course/" + courseid;
}
/*
 * function classinfo() { // StudentCourse_List showclassinfo $ .ajax({ type :
 * 'get', url : '/course/student', dataType : "json", contentType :
 * "application/json;", success : function(data, status, xhr) { if (xhr.status ==
 * 200) { var content = document.getElementById("classcontent"); var str = "";
 * str += '<div class="title">课程信息</div><hr class="line"/>' for (var i = 0;
 * i < data.length; i++) { str += '<div class="main_box_right_content" ><h3 class="classtitle"><span
 * id="course">' + data[i].courseName + '</span><button id="' +
 * data[i].classId + '" onclick="dropclass(this.id)">退选课程</button></h3><div
 * class="divideline"></div><div class="classinfo"
 * onclick="jumpCourse(this.id)" id="' + data[i].classId + ';' +
 * data[i].courseId + '"><table class="table"><tr><td class="tabletext">班级：<span
 * id="name">' + data[i].className + '</span></td><td class="tabletext" id="site">课程地点：' +
 * data[i].site + '</td></tr><tr><td class="tabletext" id="teacher">教师：' +
 * data[i].courseTeacher + '</td><td class="tabletext"></td></tr></table></div></div>'; }
 * content.innerHTML = str; } }, statusCode : { 401 : function() {
 * alert("classinfo查询失败！"); } }, statusCode : { 404 : function() {
 * alert("查找不到班级！"); } } }); }
 */

function dropclass(id) { // StudentCourseHome dropclass();
	$.ajax({
		type : 'delete',
		url : '/class/' + id + '/student/' + getCookie("username"),
		error : function() {
			alert("请登录学生账户");
		},
		success : function(data, status, xhr) {
			if (xhr.status == 204) {
				alert("成功");
				window.location.href = "/student/courses";
			}
		},
		statusCode : {
			400 : function() {
				alert("错误的ID格式");
				window.location.href = "/student/courses";
			}
		},
		statusCode : {
			403 : function() {
				alert("学生无法为他人退课");
				window.location.href = "/student/courses";
			}
		},
		statusCode : {
			404 : function() {
				alert("不存在这个选课或不存在这个学生、班级");
				window.location.href = "/student/courses";
			}
		}
	});
	// classinfo();
}

// ---------------------------- support
// functions--------------------------------------
function updateCookie(name, value) {
	document.cookie = name + '=' + value;
}

function getCookie(name) {
	var arr = document.cookie
			.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (arr != null)
		return unescape(arr[2]);
	return null;
};

function logout() {
	if (localStorage.jwt) {
		localStorage.removeItem("jwt");
		window.location.href = '/';
	} else {
		window.location.href = '/';
	}
}

// ----------------------------StudentbindPage-------------------------------

function stubind() { // StudentbindPage bindstu-updatestuinfo
	var Gender = $("input[type='radio']:checked").val();
	var ata = {
		name : $("#name").val(),
		gender : Gender,
		title : $("#title").val(),
		email : $("#email").val(),
		phone : $("#phone").val(),
		avatar : "/avatar/3486.png"
	}
	$.ajax({
		type : 'put',
		url : '/me',
		dataType : "json",
		contentType : "application/json;",
		data : JSON.stringify(ata),
		success : function(data, status, xhr) {
			if (xhr.status == 204) {
				alert("成功!");
				window.location.href = "/student/profile";
			}
		},
		statusCode : {
			400 : function() {
				alert("信息不合法");
			}
		}
	});
}

// ----------------------------StudentHomePage-------------------------------

function stuinfo() { // StudentHomePage showstuinfo
	$
			.ajax({
				type : 'get',
				url : '/me',
				dataType : "json",
				contentType : "application/json;",
				success : function(data, status, xhr) {
					if (xhr.status == 200) {
						document.cookie = 'username=' + data.id; // store
						// username
						// in cookie
						$("#username").html(
								'用户名：' + '<span>' + data.id + '</span>');
						$("#stuffNum").html(
								'学号：' + '<span>' + data.number + '</span>');
						$("#name").html(
								'姓名：' + '<span>' + data.name + '</span>');
						$("#gender").html(
								'性别：' + '<span>' + data.gender + '</span>');
						$("#school")
								.html(
										'学校：' + '<span>' + data.school.name
												+ '</span>');
						$("#title").html(
								'学历：' + '<span>' + data.title + '</span>');
						$("#email").html(
								'邮箱：' + '<span>' + data.email + '</span>');
						$("#phone").html(
								'联系方式：' + '<span>' + data.phone + '</span>');
					}
				},
				statusCode : {
					401 : function() { // statuscode unknown
						alert("获取信息失败");
					}
				}
			});
}

// ----------------------------StudentInfoModifyPage-------------------------------

function stumodinfo() { // StudentInfoModifyPage getstuinfo
	$
			.ajax({
				type : 'get',
				url : '/me',
				dataType : "json",
				contentType : "application/json;",
				success : function(data, status, xhr) {
					if (xhr.status == 200) {
						$("#username").html(
								'用户名：' + '<span>' + data.id + '</span>');
						$("#stuffNum").html(
								'学号：' + '<span>' + data.number + '</span>');
						$("#name").val(data.name);
						$("#gender").val(data.gender);
						$("#school").val(data.school.name);
						$("#title").val(data.title);
						$("#email").val(data.email);
						$("#phone").html(
								'联系方式：' + '<span>' + data.phone + '</span>');
					}
				},
				statusCode : {
					401 : function() { // statuscode unknown
						alert("获取信息失败");
					}
				}
			});
}

function stuinfomod() { // StudentInfoModifyPage updatestuinfo
	var ata = {
		name : $("#name").val(),
		gender : $("#gender").val(),
		school : $("#school").val(),
		title : $("#title").val(),
		email : $("#email").val(),
		avatar : "/avatar/3486.png"
	}
	$.ajax({
		type : 'put',
		url : '/me',
		dataType : "json",
		contentType : "application/json;",
		data : JSON.stringify(ata),
		success : function(data, status, xhr) {
			console.log(xhr.status);
			if (xhr.status == 204) {
				alert("修改成功!");
				window.location.href = "/student/profile";
			}
		},
		statusCode : {
			400 : function() {
				alert("信息不合法");
			}
		}
	});
}

// --------------------------------StudentCourse_List-----------------------------------
var courseid = '';

function jumpCourse(id) {
	var info = id;
	var arr = info.split(";");
	var classid = arr[0];
	var courseid = arr[0];
	updateCookie('classcurrent', classid);
	updateCookie('coursecurrent', courseid);
	window.location.href = "/studentToCourse/" + courseid;
}

function classinfo() { // StudentCourse_List showclassinfo
	$
			.ajax({
				type : 'get',
				url : '/class',
				dataType : "json",
				contentType : "application/json;",
				success : function(data, status, xhr) {
					if (xhr.status == 200) {
						var content = document.getElementById("classcontent");
						var str = "";
						str += '<div class="title">课程信息</div><hr class="line"/>'
						for (var i = 0; i < data.length; i++) {
							str += '<div class="main_box_right_content" ><h3 class="classtitle"><span id="course">'
									+ data[i].courseName
									+ '</span><button id="'
									+ data[i].id
									+ '" onclick="dropclass(this.id)">退选课程</button></h3><div class="divideline"></div><div  class="classinfo" onclick="jumpCourse(this.id)" id="'
									+ data[i].id
									+ ';'
									+ data[i].courseId
									+ '"><table class="table"><tr><td class="tabletext">班级：<span id="name">'
									+ data[i].name
									+ '</span></td><td class="tabletext" id="site">课程地点：'
									+ data[i].site
									+ '</td></tr><tr><td class="tabletext" id="teacher">教师：'
									+ data[i].courseTeacher
									+ '</td><td class="tabletext"></td></tr></table></div></div>';
						}
						content.innerHTML = str;
					}
				},
				statusCode : {
					401 : function() {
						alert("classinfo查询失败！");
					}
				},
				statusCode : {
					404 : function() {
						alert("查找不到班级！");
					}
				}
			});
}

function classlist() { // StudentCourse_List showclassinfo
	$
			.ajax({
				type : 'get',
				url : "/class/list",
				dataType : "json",
				contentType : "application/json;",
				success : function(data, status, xhr) {
					if (xhr.status == 200) {
						var content = document.getElementById("classcontent");
						var str = "";
						str += '<div class="title">课程信息</div><hr class="line"/>'
						for (var i = 0; i < data.length; i++) {
							str += '<div class="main_box_right_content" ><h3 class="classtitle"><span id="course">'
									+ data[i].courseName
									+ '</span><button id="'
									+ data[i].id
									+ '" onclick="selectclass(this.id)">选择课程</button></h3><div class="divideline"></div><div  class="classinfo" onclick="jumpCourse(this.id)" id="'
									+ data[i].id
									+ ';'
									+ data[i].courseId
									+ '"><table class="table"><tr><td class="tabletext">班级：<span id="name">'
									+ data[i].className
									+ '</span></td><td class="tabletext" id="site">课程地点：'
									+ data[i].site
									+ '</td></tr><tr><td class="tabletext" id="teacher">教师：'
									+ data[i].courseTeacher
									+ '</td><td class="tabletext"></td></tr></table></div></div>';
						}
						content.innerHTML = str;
					}
				},
				statusCode : {
					401 : function() {
						alert("classinfo查询失败！");
					}
				},
				statusCode : {
					404 : function() {
						alert("查找不到班级！");
					}
				}
			});
}

function dropclass(id) { // StudentCourseHome dropclass();
	
	$.ajax({
		type : 'delete',
		url : '/class/' + id + '/student/' + getCookie("username"),
		error : function() {
			alert("请登录学生账户");
		},
		success : function(data, status, xhr) {
			if (xhr.status == 204) {
				alert("成功");
				window.location.href = "/student/courses";
			}
		},
		statusCode : {
			400 : function() {
				alert("错误的ID格式");
				window.location.href = "/student/courses";
			}
		},
		statusCode : {
			403 : function() {
				alert("学生无法为他人退课");
				window.location.href = "/student/courses";
			}
		},
		statusCode : {
			404 : function() {
				alert("不存在这个选课或不存在这个学生、班级");
				window.location.href = "/student/courses";
			}
		}
	});
}
function selectclass(id) { // StudentCourseHome dropclass();
	var token = window.localStorage.getItem("jwt");
	$.ajax({
		type : 'post',
		url : '/class/' + id + '/student',
		contentType : "application/json;charset=utf-8",
		headers : {
			"Authorization" : token
		},
		error : function() {
			alert("请登录学生账户");
		},
		success : function(data, status, xhr) {
			if (xhr.status == 204) {
				alert("成功");
				window.location.href = "/student/courses";
			}
		},
		statusCode : {
			400 : function() {
				alert("错误的ID格式");
				window.location.href = "/student/courses";
			}
		},
		statusCode : {
			403 : function() {
				alert("学生无法为他人退课");
				window.location.href = "/student/courses";
			}
		},
		statusCode : {
			404 : function() {
				alert("不存在这个选课或不存在这个学生、班级");
				window.location.href = "/student/courses";
			}
		}
	});
	// classinfo();
}