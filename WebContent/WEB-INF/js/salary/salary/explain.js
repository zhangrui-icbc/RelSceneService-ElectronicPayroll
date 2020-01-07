
var ctx = $("#contextPath").val().trim();	
/**
 * 员工信息下载
 * @returns
 */
$(".explain").click(function(){
	window.location.href=ctx+"/mp/salary/explain";
})