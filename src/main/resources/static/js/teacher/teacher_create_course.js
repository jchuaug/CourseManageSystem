function createcourse(){    //TeacherCreateCourse
        var ata = {
        name:$("#coursename").val(),
        description:$("#description").val(),
        startTime:$("#begintime").val(),
        endTime:$("#endtime").val(),
        proportions:{
            report:$("#reportscore").val(),
            presentation:$("#seminarscore").val(),
            c:$("#five").val(),
            b:$("#four").val(),
            a:$("#three").val()
        }

      }
        $.ajax({
        type:'post',
        url: '/course',
        dataType: "json",
        contentType: "application/json;",
        data: JSON.stringify(ata),
        success: function (data,textStatus,xhr) {
            if(xhr.status== 201){
                alert("创建成功!");
                window.location.href="/teacher/courses";  //返回展示老师课程list的页面
                return "23";  //API上说返回新建课程的ID，不知道该如何分配得到课程的ID
            }
        },
        statusCode: {
            403: function () {  //statuscode unknown
                alert("用户权限不足");
            }
        }
    });
}
