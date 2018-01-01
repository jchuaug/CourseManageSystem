/*---------------------------- support functions--------------------------------------*/
function updateCookie(name, value) {
	var exp = new Date();
	exp.setTime(exp.getTime() + 6 * 24 * 60 * 60 * 1000); //6天过期
	document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
	return true;
};

function getCookie(name) {
	var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if(arr != null) return unescape(arr[2]);
	return null;
};


function jumpcourse(cid) { //点击修改课程时，将课程id号存储在cookie中
	updateCookie('courseCurrent', cid);
	window.location.href = '/teacher/course/' + cid + '/update'; //页面跳转
}


function courselist() {
	$.ajax({
		type: 'get',
		url: '/course',
		dataType: "json",
		contentType: "application/json;",
		success: function(data) {
			if(true) {
				var content = document.getElementById("coursecontent"); //获取外围容器
				var str = "";
				str += '<div class=\"title\">课程信息</div><hr class=\"line\"/>'
				for(var i = 0; i < data.length; i++) {
					str += '<div class=\"main_box_right_content\"><h3 class=\"classtitle\" id=\"name\">' +
						' <span id="course">' + data[i].name +
						'</span><button type=\"submit\"  onclick=\"deletecourse(' + data[i].id + ')\">删除课程</button>' +
						'<button id=\"' + data[i].id + '\"onclick=\"jumpcourse(this.id)  \" >修改课程</button></h3>' +
						'<div class=\"divideline\"></div><div  class=\"classinfo\"  id=\"' + data[i].id + '\" onclick=\"jumpcoursedetail(this.id) \" ><table class=\"table\"> <tr>'
						// +'<td class=\"tabletext\">班级数：<span id=\"numClass\">'+data[i].numClass+'</span></td>  <td class=\"tabletext\" id=\"numStudent\">学生人数'+data[i].numStudent
						// +'</td></tr><tr><td class=\"tabletext\" id=\"startTime\">开始时间：'+data[i].startTime+'</td><td class=\"tabletext\" id=\"endTime\">结束时间:'+data[i].endTime
						+
						'<td class=\"tabletext\">班级数：<span id=\"numClass\">' + data[i].numClass + '</span></td> ' +
						'</tr><tr><td class=\"tabletext\" id=\"startTime\">开始时间：' + data[i].startTime + '</td><td class=\"tabletext\" id=\"endTime\">结束时间:' + data[i].endTime +
						'</td></tr></table></div></div>'
				}
				str += '<div class=\"main_box_right_content\"  onclick=\"window.location.href=\'/teacher/courses/create\'\"><img class=\"addcourse\" src=\"/img/add.png\" ></div>'
				content.innerHTML = str;
			}
		},
		statusCode: {
			401: function() { //statuscode unknown
				alert("获取信息失败");
			}
		}
	});
}

function deletecourse(cid) {
	var ata = {
		id: cid
	}
	$.ajax({
		type: 'delete',
		url: '/course/' + cid,
		data: JSON.stringify(ata),
		dataType: "json",
		contentType: "application/json;",
		error: function(data, textStatus, xhr) {
			console.log(cid);
			alert("wrong");
		},
		success: function(data, textStatus, xhr) {
			if(xhr.status == 204) {
				alert("成功");
				document.fe
			}
		},
		statusCode: {
			400: function() {
				alert("错误的ID格式");
			},
			403: function() {
				alert("用户权限不足");
			},
			404: function() {
				alert("未找到课程");
			}
		}
	});
	// courselist();  //删除完再加载
	window.location.reload();
}