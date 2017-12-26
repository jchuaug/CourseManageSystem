/*$(function() {
    // setArea("top", "0");//页面加载时就现实数据库第一个数据，一定要加上
	setArea("provinceSelect", "1");
    $("#provinceSelect").change(function() {
        // 当省级改变的时候，让市级和县级文本清空
        $("#citySelect option").remove();
         //获得省级的id
        var region_id=$("#provinceSelect option:selected").attr("region_id");
         //加载该省级的市级数据
        setArea("citySelect", region_id);
    });
});
//selectid:select标签的id，pid数据库省级县级的pid
function setArea(selectid, region_id) {
	    $.ajax({
		url : "/resgiter/getArea/" + region_id,
		type : "get",
		cache : false,
		success : function(res) {
			//注意!!!这里必须使用eval(res)函数，否则获取到的json数据无法遍历,无话获取到数据
			var arr = eval(res);
			//遍历返回的json数据加载到select标签;
			$.each(arr, function(key, val) {
				$("#" + selectid).append(
						" <option id='" + val.region_id + "'>" + val.region_name
								+ "</option>");
			});
		}
	});
};
*/