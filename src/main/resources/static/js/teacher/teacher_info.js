function teainfo() {
	$.ajax({
		type: 'get',
		url: '/me',
		dataType: "json",
		contentType: "application/json;",
		success: function(data, textStatus, xhr) {
			// console.log(xhr.status);
			if(xhr.status == 200) {
				var Gender;
				var Title;
				updateCookie('username', data.id); //store username in cookie
				$("#username").html('用户名：' + '<span>' + data.id + '</span>');
				$("#teaNum").html('教工号：' + '<span>' + data.number + '</span>');
				$("#name").html('姓名：' + '<span>' + data.name + '</span>');
				$("#gender").html('性别：' + '<span>' + data.gender + '</span>');
				$("#school").html('学校：' + '<span>' + data.school.name + '</span>');
				$("#title").html('职称：' + '<span>' + data.title + '</span>');
				$("#email").html('邮箱：' + '<span>' + data.email + '</span>');
				$("#phone").html('联系方式：' + '<span>' + data.phone + '</span>');
			}
		},
		statusCode: {
			401: function() {
				alert("未登录!");
				window.location.href = "/login";
			},
			403: function() {
				alert("未登录!");
				window.location.href = "/login";
			}
		}
	});
}

function logout() {
	if(localStorage.jwt) {
		localStorage.removeItem("jwt");
		window.location.href = '/login';
	} else {
		window.location.href = '/login';
	}
}

function getteainfo() { //get techer information
	$.ajax({
		type: 'get',
		url: '/me',
		dataType: "json",
		contentType: "application/json;",
		success: function(data, textStatus, xhr) {
			if(xhr.status == 200) {
				$("#username").html(data.id);
				$("#teaNum").html(data.number);
				$("input[name='name']").val(data.name);
				$("input[name='idnum']").val(data.number);
				$("input[name='sex']").attr("value", data.gender);
				// $("input[name='school']").attr("value",data.school.name);
				$("#school").html(data.school.name);
				$("input[name='title']").attr("value", data.title);
				$("input[name='e-mail']").attr("value", data.email);
				$("#phone").html(data.phone);
			}
		},
		statusCode: {
			401: function() {
				alert("未登录!");
				window.location.href = "/login";
			},
			403: function() {
				alert("未登录!");
				window.location.href = "/login";
			}
		}
	});
}

function teainfomod() {
	var ata = {
		name: $("#name").val(),
		number: $("#idnum").val(),
		email: $("#eMail").val(),
		gender: $("#gender").val(),
		title: $("#title").val(),
		avatar: "/avatar/3486.png",
		// school:$("#school").val(),
		// phone:$("#phone").val()
	}
	$.ajax({
		type: 'put',
		url: '/me',
		dataType: "json",
		contentType: "application/json;",
		data: JSON.stringify(ata),
		success: function(data, textStatus, xhr) {
			if(xhr.status == 204) {
				alert("修改成功!");
				window.location.href = '/teacher/home';
			}
		},
		statusCode: {
			400: function() {
				alert("信息不合法");
			}
		}
	});
}