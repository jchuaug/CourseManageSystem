/**
 * 老师主页
 */

function courselist(){
        $.ajax({
        type:'get',
        url: '/course',
        dataType: "json",
        contentType: "application/json;",
        success: function (data,textStatus,xhr) {
            if(xhr.status == 200){
                var content=document.getElementById("coursecontent");   //获取外围容器
                var str="";
                str+='<div class=\"title\">课程信息</div><hr class=\"line\"/>'
                for(var i=0;i<data.length;i++){
                        str+='<div class=\"main_box_right_content\"><h3 class=\"classtitle\" id=\"name\">'
                              +' <span id="course">'+data[i].name
                              +'</span><button type=\"submit\"  onclick=\"deletecourse('+data[i].id+')\">删除课程</button>'
                              +'<button id=\"'+data[i].id+'\"onclick=\"jumpcourse(this.id)  \" >修改课程</button></h3>'
                              +'<div class=\"divideline\"></div><div  class=\"classinfo\"  id=\"'+data[i].id+'\" onclick=\"jumpcoursedetail(this.id) \" ><table class=\"table\"> <tr>'
                              // +'<td class=\"tabletext\">班级数：<span id=\"numClass\">'+data[i].numClass+'</span></td>  <td class=\"tabletext\" id=\"numStudent\">学生人数'+data[i].numStudent
                              // +'</td></tr><tr><td class=\"tabletext\" id=\"startTime\">开始时间：'+data[i].startTime+'</td><td class=\"tabletext\" id=\"endTime\">结束时间:'+data[i].endTime
                              +'<td class=\"tabletext\">班级数：<span id=\"numClass\">'+data[i].numClass+'</span></td> '
                              +'</tr><tr><td class=\"tabletext\" id=\"startTime\">开始时间：'+data[i].startTime+'</td><td class=\"tabletext\" id=\"endTime\">结束时间:'+data[i].endTime
                              +'</td></tr></table></div></div>'
                }
                str+='<div class=\"main_box_right_content\"  onclick=\"window.location.href=\'/teacher/courses/create\'\"><img class=\"addcourse\" src=\"/img/add.png\" ></div>'
                content.innerHTML=str;
            }
        },
        statusCode:{
            401: function (){//statuscode unknown
                alert("获取信息失败");
            }
        }
    });
}

function getCourseName(div) {
	window.location.href = "/teacherToCourse/5";
};

function createCourse() {
	window.location.href = "/CreateCourse";

};
