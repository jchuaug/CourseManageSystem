//
$.ajaxSetup({
    beforeSend:function(xhr) {
        if(localStorage.jwt){
            xhr.setRequestHeader('Authorization',localStorage.jwt)
        }
    },
    statusCode:{
        401: function () {
            alert("未授权访问");
            window.location.href = "/";
        } ,
        403:function () {
            alert("未授权访问");
            window.location.href = "/";
        },
        404:function () {
            alert("未找到相关资源");
            window.location.href = "/";
        }
    }
});