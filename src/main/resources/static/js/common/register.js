function registerload() {
	$.ajax({
		type : 'get',
		url : '/school/province',
		dataType : "json",
		contentType : "application/json;",
		success : function(data, textStatus, xhr) {
			if (xhr.status == 200) {
				var content = document.getElementById("pro");
				var str = "";
				for (i = 0; i < data.length; i++) {
					str = str + "<option>" + data[i] + "</option>"
				}
				content.innerHTML = str;
			}
		},
		statusCode : {
			400 : function() {
				alert("错误的ID格式");
			},
			404 : function() {
				alert("加载初始信息失败");
			}
		}
	});

}

function find_city() {
	// var province=$("#pro").find("option:selected").text();
	// alert(province);
	$.ajax({
		type : "get",
		url : "/school/city?province="
				+ $("#pro").find("option:selected").text(),
		success : function(data) {
			var content = document.getElementById("city");
			var str = "";
			for (i = 0; i < data.length; i++) {
				str = str + "<option>" + data[i] + "</option>"
			}
			content.innerHTML = str;
		}
	});
}

function find_school() {
	$.ajax({
		type : "get",
		url : "/school?city=" + $("#city").find("option:selected").text(),
		success : function(data) {
			var content = document.getElementById("school");
			var str = "";
			for (i = 0; i < data.length; i++) {
				str = str + "<option>" + data[i].name + "</option>"
			}
			content.innerHTML = str;
		}
	});
}

function submitregister() {
	var ata = {
		phone : $("#phone").val(),
		password : $("#password").val(),
		name : $("#name").val(),
		gender : ($('.male > input:radio:checked').val() == '男') ? 1 : 0,
		type : ($('.student > input:radio:checked').val() == "学生") ? 0 : 1,
		number : ($('.student > input:radio:checked').val() == "学生") ? $("#stuNum").val() : $("#teaNum").val(),
		email : $("#eMail").val()
	}
	$.ajax({
		type : 'post',
		url : '/register',
		dataType : "json",
		data : JSON.stringify(ata),
		contentType : "application/json",
		success : function(data, textStatus, xhr) {
			if (xhr.status == 200) {
				alert("注册成功,立即登录");
				window.location.href = "/";
			}
		},
		statusCode : {
			401 : function() {
				alert("无法注册！");
			}
		}
	});
}