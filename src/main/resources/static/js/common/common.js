$(function() {
	// 登录
	$(".Login").click(function(e) {
		var token = window.localStorage.getItem("jwt");
		phone = $(".UserNameField").val();
		password = $(".PasswordField").val();
		$.ajax({
			type : 'post',
			url : '/signin',
			data : {
				'phone' : phone,
				'password' : password
			},
			dataType : "json",
			headers : {
				"Authorization" : token
			},
			success : function(result) {
				if (result.statusCode == 200) {
					window.localStorage.setItem("jwt",result.jwt);
					/* 登陆成功跳转 */
					alert(result.type);
					switch (result.type) {
					case "teacher":
						window.location.href = '/teacher/home';
						break;
					case "student":
						window.location.href = '/student/home';
						break;
					case "unbinded":
						window.location.href = '/user/bind';
						break;
					}
				} else {
					alert(result.msg);
					window.location.href = '/';
				}

			}
		});
	});

	// 注册
	// $(".register_submit").onclick(function() {
	// });

});
