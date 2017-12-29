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
				"Authorization" : "Bearer" + token
			},
			success : function(result) {
				var msg = result.msg;
				window.localStorage.setItem("jwt", result.jwt);
				console.log(result.jwt);
				if (result.code == 200) {
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
						window.location.href = '/student/bind';
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
