/**
 * 老师主页
 */

$(document).ready(function() {
					var token = localStorage.getItem("jwt");
					$.ajax({
								type : 'GET',
								url : '/course',
								headers : {
									"Authorization" : token
								},
								success : function(datas) {
									for (var i = 0; i < datas.length; i++) {

										var str = "<div onclick=\"getCourseName(this)\" class=\"main_box_right_content\"><h3 class=\'classtitle\'>"
												+ datas[i].name
												+ "<button>删除课程</button><button>修改课程</button></h3><div class=\'divideline\'></div><div class=\'classinfo\'><table class=\'table\'><tr><td class=\'tabletext\'>班级数："
												+ datas[i].numClass
												+ "<span></span></td><td class=\'tabletext\'>学生人数："
												+ datas[i].numStudent
												+ "</td></tr><tr><td class=\'tabletext\'>开始时间："
												+ datas[i].startTime
												+ "</td><td class=\'tabletext\'>结束时间:"
												+ datas[i].endTime
												+ "</td></tr></table></div></div>";
										$("#line_right").after(str);
									}

								}
							});
				});

function getCourseName(div) {
	window.location.href = "/teacherToCourse/5";
};

function createCourse() {
	window.location.href = "/CreateCourse";

};
