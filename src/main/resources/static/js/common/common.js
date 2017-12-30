$(function() {
	// 登录
	$(".Login").click(function(e) {
		postData={
				phone:$(".UserNameField").val(),
				password:$(".PasswordField").val()
		}
		$.ajax({
			type : 'post',
			url : '/signin',
			dataType : "json",
			data : JSON.stringify(postData),
			contentType : "application/json",
			success : function(result, textStatus, xhr) {
				if (xhr.status == 200) {
					window.localStorage.setItem("jwt", result.jwt);
					/* 登陆成功跳转 */
					switch (result.type) {
					case "teacher":
						window.location.href = '/teacher/home';
						break;
					case "student":
						window.location.href = '/student/home';
						break;
					case "unbinded":
						window.location.href = '/student/bind';
						break;
					}
				} else {
					alert(xhr.msg);
					window.location.href = '/';
				}

			}
		});
	});

	// 注册
	// $(".register_submit").onclick(function() {
	// });

});
