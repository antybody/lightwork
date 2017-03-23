/**
 * roleChose javascript
 */

//选中改变样式  和 显示
var changeShow=function(obj,id){
	//现将全部变成正常色
	$("[name=role_type]").each(function(){
		$(this).removeAttr("class");
		$(this).attr("class","level0");
	});
	//将选中变成选中色
	$(obj).removeAttr("class");
	$(obj).attr("class","level1");
	
	//使全部不可见
	$("[name=role_tree]").each(function(){
		$(this).removeAttr("style");
		$(this).attr("style","display:none;");
	});
	//alert(id);
	//使选中的可见
	$("#"+id).removeAttr("style");
	$("#"+id).attr("style","display:block");
}