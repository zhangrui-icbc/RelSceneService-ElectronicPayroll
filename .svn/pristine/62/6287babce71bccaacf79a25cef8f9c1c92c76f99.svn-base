/**
 * * <h1>日期格式话</h1>
 * @author kfzx-lidd03
 */
Date.prototype.toText = function(splitstr){
		var m,d;
		if(this.getMonth() < 9){
			m = "0" + (this.getMonth() + 1);
		}else{
			m = this.getMonth() + 1;
		}
		if(this.getDate() < 10){
			d = "0" + this.getDate();
		}else{
			d = this.getDate();
		}
		var rnStr = "";
		if(typeof(splitstr)!="undefined"){
			return this.getFullYear() + splitstr + m + splitstr + d;
		}else{
			return this.getFullYear() + "" + m + "" + d;
		}
	};
/**
 * <h1>轮播图</h1>
 * @author kfzx-lidd03
 * @version 1.1
 * @param t
 * @param options
 * @returns {scrollAd}
 */
function scrollAd(t, options) {
	var timer = null;
	if(!$(t))return;
	
	options=options || {};
	options.time1=options.time1 || 2000;  
	options.time2=options.time2 || 800;  
	
	var oU=$(t).children("ul");
	var oOl=$(t).children("ol");
	var aLi=oU.children();
	var length=aLi.length;
	if(length < 1)return;
	$(oOl).empty();
	for(var i=0;i<length;i++)
	{
		$(oOl).append('<a href="javascript:;"></a>');	
	}
	oOl.children()[0].className='active';
	
	var iNow=0;
	function tab()
	{
		var h=iNow%length;  
		aLi.stop(true,true).css("zIndex","1");   
		aLi.eq(h).css("zIndex","18").stop(true,true).animate({left:0},options.time2,function()
		{
			$(this).siblings("li").css("zIndex","1"); 
			if($(this).position().left==0)
			{
				$(this).siblings("li").css({left:$(this).width()});   
			}
		});
		oOl.find("a").removeClass().eq(h).addClass("active");
	}
	
	function next()
	{	
		iNow++;
		tab();	
	}
	
	timer=setInterval(next,options.time1);
	$(t).mouseenter(function()
	{
		clearInterval(timer);
	});
	
	oOl.find("a").mouseenter(function()
	{
		iNow=oOl.find("a").index($(this));	
		setTimeout(tab,300);
	});
	
	$(t).mouseleave(function()
	{
		clearInterval(timer);
		timer=setInterval(next,options.time1);
	});
	this.stop = function(){
		clearInterval(timer);
	};
}

/**
 * <h1>BootStrapDropdown</h1>
 * @author kfzx-lidd03
 * @version 1.1
 * @param object 父容器ID，例如<div id="bootStrap_dropdown"></div>
 * @param options 参数
 * <h2>example:</h2>
 * <div id="bootStrap_dropdown"></div>
 * <script type="text/javascript">
 * $(function(){
		var option = {
			data:[{name:"日报",
				  value:"day",
				  active:true
				},{
					name:"周报",
					value:"week"
				},{
					name:"月报",
					value:"month"
				}
			],
			onselect:function(name,value){
				alert(name + "---" + value);
			}
	};
	var dropdown = new BootStrapDropdown("bootStrap_dropdown",option);
	//获取当前选项的value
	alert(dropdown.getVal());
});
 * </script>
 * });
 */
function BootStrapDropdown(divId, options){
	var $_container = $("<div class=\"btn-group\"></div>");
	var $_button = $("<button class=\"btn btn-default\" type=\"button\" " +
			"data-toggle=\"dropdown\" style=\"border: 1px solid #95B8E7\"></button>");
	$_button.attr("id",divId + "_button");
	var $_ul = $("<ul class=\"dropdown-menu\" role=\"menu\"></ul>");
	$_ul.attr("aria-labelledby",divId + "_button");
	
	this.divId = divId;
	this.options = options;
	this.init = function(options){
		$("#" + this.divId).empty();
		if(typeof(options) != "undefined"){
			this.options = options;
		}
		var isBtnInit = false;
		for(var i = 0; i < this.options.data.length; i++){
			var dataEach = this.options.data[i];
			var $_li = $("<li role = \"presentation\"></li>");
			var $_a = $("<a role=\"menuitem\" tabindex=\"-1\" href=\"#\"></a>");
			if(typeof(dataEach.value) != "undefined"){
				$_a.attr("dataVal",dataEach.value);
			}else{
				$_a.attr("dataVal","");
			}
			$_a.html(dataEach.name);
			if(typeof(dataEach.active) != "undefined"){
				if(dataEach.active == true && isBtnInit == false){
					$_a.attr("class","btn-primary  btn-lg");
					$_button.append(dataEach.name + "<span class=\"caret\"></span>");
					$("#" + this.divId).attr("curVal",dataEach.value);
				}
			}
			$_li.append($_a);
			$_ul.append($_li);
		}
		if(typeof(this.options.height) != "undefined"){
			$_ul.css("height",this.options.height);
			$_ul.css("overflow-y","auto");
		}
		$_container.append($_button).append($_ul).appendTo("#" + this.divId);
		var obj = this;
		$("#" + divId + " a").bind("click",function(){
			$("#" + divId + " a[class]").removeAttr("class");
			$(this).attr("class","btn-primary  btn-lg");
			$("#" + divId + " button").html($(this).text() + "<span class=\"caret\"></span>");
			$("#" + divId).attr("curVal",$(this).attr("dataVal"));
			if(typeof(obj.options.onselect) != "undefined"){
				obj.options.onselect($(this).text(),$(this).attr("dataVal"));
			}
		});
	};
	this.getVal = function(){
		return $("#" + this.divId).attr("curVal");
	};
	this.init();
}

/**
 * <h1>Pagination 分页</h1>
 * @author kfzx-lidd03, kfzx-caiyue
 * @version 1.1
 * @param object 父容器ID，例如&lt;div id="pagination"&gt;&lt;/div&gt;
 * @param options 参数
 * <table border="1">
 *   <tr><td>pageNumber</td><td>页数</td></tr>
 *   <tr><td>pageSize</td><td>每页记录数</td></tr>
 *   <tr><td>total</td><td>记录总数</td></tr>
 *   <tr><td>onSelected</td><td>回调方法；当选中指定业务或者翻页，触发此方法</td></tr>
 * </table>
 * <h2>example:</h2>
 * func_pagination("pagination", {<br/>
 *   pageNumber: 3,<br/>
 *   pageSize: 10,<br/>
 *   total: 51,<br/>
 *   onSelected: function(pageNumber, pageSize) {<br/>
 *   }<br/>
 * });<br/>
 */
function func_pagination(object, options) {
	var maxShowNum = 9;//最大显示页码数
	var startPageNum = 1;//显示的起始页码（初始化1）
	var n = parseInt(options.pageNumber);
	if (n <= 0) {
		n = 1;
	}
	if(n >= maxShowNum){
		startPageNum = n - parseInt(maxShowNum/2);
	}
	
	var t = parseInt(options.total);
	var s = parseInt(options.pageSize);
	$("#" + object).html('');
	if(t <= 0){
		return;
	}
	var maxPage = (parseInt(t / s)) + 1 - ((t % s == 0) ? 1 : 0);
	if(maxPage <= maxShowNum){
		maxShowNum = maxPage;
	}
	
	$("<ul></ul>").addClass("pagination").attr("id","temp_ul_" + object).appendTo("#" + object);
	$("<li></li>").attr("id", "temp_li_" + object).appendTo("#temp_ul_" + object);
	$("<a></a>").html("首页").appendTo("#temp_li_" + object).bind("click", function() {
		n = 1;
		startPageNum = 1;
		editShow();
		options.onSelected(n, s);
	});
	$("#temp_li_" + object).removeAttr("id");
	$("<li></li>").attr("id", "temp_li_" + object).appendTo("#temp_ul_" + object);
	$("<a></a>").html("上一页").appendTo("#temp_li_" + object).bind("click", function() {
		n = n-1 <= 0 ? 1 : n - 1;
		if(n <= startPageNum){
			startPageNum = startPageNum - parseInt(maxShowNum/2);
		}
		editShow();
		options.onSelected(n, s);
	});
	$("<li num='pre'><a>...</a></li>").appendTo("#temp_ul_" + object);
	$("#temp_li_" + object).removeAttr("id");
	for (var i = 0; i < maxShowNum; i++) {
		$("<li></li>").attr("id", "temp_li_" + object).attr("num",i + 1).appendTo("#temp_ul_" + object);
		$("<a></a>").html(i + 1).appendTo("#temp_li_" + object).bind("click", function() {
			n = parseInt($(this).html());
			if(n >= startPageNum + maxShowNum - 1){
				startPageNum = startPageNum + parseInt(maxShowNum/2);
			}
			if(n <= startPageNum){
				startPageNum = startPageNum - parseInt(maxShowNum/2);
			}
			editShow();
			options.onSelected(n, s);
		});;
		if ((i + 1) == n) {
			$("#temp_li_" + object).addClass("active");
		}
		$("#temp_li_" + object).removeAttr("id");
	}
	$("<li num='af'><a>...</a></li>").appendTo("#temp_ul_" + object);
	$("<li></li>").attr("id", "temp_li_" + object).appendTo("#temp_ul_" + object);
	$("<a></a>").html("下一页").appendTo("#temp_li_" + object).bind("click", function() {
		n = n == maxPage ? n : n + 1;
		if(n >= startPageNum + maxShowNum){
			startPageNum = startPageNum + parseInt(maxShowNum/2);
		}
		editShow();
		options.onSelected(n, s);
	});;
	$("#temp_li_" + object).removeAttr("id");
	$("<li></li>").attr("id", "temp_li_" + object).appendTo("#temp_ul_" + object);
	$("<a></a>").html("尾页").appendTo("#temp_li_" + object).bind("click", function() {
		n = maxPage;
		startPageNum = maxPage - maxShowNum + 1;
		editShow();
		options.onSelected(n , s);
	});;
	$("#temp_li_" + object).removeAttr("id");
	$("#temp_ul_" + object).removeAttr("id");
	//计算要显示的页码数
	function editShow(){
		$("#" + object + " ul li[num='pre']").hide();
		$("#" + object + " ul li[num='af']").hide();
		if(startPageNum < 1){
			startPageNum = 1;
		}
		if(startPageNum + maxShowNum > maxPage +1){
			startPageNum = maxPage + 1 - maxShowNum;
		}
		if (startPageNum > 1){
			$("#" + object + " ul li[num='pre']").show();
		}
		if (startPageNum + maxShowNum -1 < maxPage){
			
			$("#" + object + " ul li[num='af']").show();
		}
		for(var i = 1; i <= maxShowNum; i++){
			$("#" + object + " ul li[num=" + i + "] a").html(i + startPageNum-1);
		}
		$("#" + object + " ul li").removeAttr("class");
		$("#" + object + " ul li[num=" + (n - startPageNum + 1) + "]").addClass("active");
	}
	editShow();
}
function func_IsIE() {
	var isIE = document.all ? true : false;
	if (isIE) {
		return true;
	}
	isIE = navigator.userAgent.indexOf("MSID") > 0 ? true : false;
	if (isIE) {
		return true;
	}
	navigator.appName.indexOf("Microsoft") != -1 ? true : false;
	if (isIE) {
		return true;
	}
	return false;
}

/**
 * 调整父容器高度
 * @version 1.1
 * 解决IE和Chrome效果不同的问题，并解决多个纵向滚动条的问题
 */
function func_resizeParent() {
	if (!func_IsIE() && arguments.length == 0) {
		return;
	}
	var height = parseInt(document.body.scrollHeight);
	if (arguments.length > 0) {
		height = arguments[0];
	}
	//跨域问题解决,新增加一个判断
	if(parent.document.all["subWindow"]){
		parent.document.all["subWindow"].style.height = height + "px";
	}
	
	// parent.document.getElementById("rightframe-parent").style.height = height + 20 + "px";
}



/**
 * 打开单独聊天窗口
 */
 function openSingleChat(clientId, clientName, portrait,replymsgid) {
	if(replymsgid){
		window.location.href = "../Manage/single_client.jsp?c=" + clientId + "&n=" + encodeURIComponent(clientName, "utf-8") + "&p=" + portrait +"&rmID=" + replymsgid;
	}else{
		window.location.href = "../Manage/single_client.jsp?c=" + clientId + "&n=" + encodeURIComponent(clientName, "utf-8") + "&p=" + portrait;
	}
	
}

(function($) {
	/**
	 * <h1>弹出对话框</h1>
	 * @author kfzx-caiyue
	 * @version 1.0
	 * @param option 参数
	 * style: message-等同于window.alert
	 * ……（后续补充confirm和custom）
	 * labelId: 对话框标题ID
	 * 
	 * labelName: 对话框标题
	 * 
	 * modalBody: 对话框正文
	 */
	$.fn.mp_modal = function(option) {
		$(this.selector).addClass("modal fade").attr("role", "dialog").attr("aria-hidden", "true").attr("aria-labelledby", option.labelId).html("<div class='modal-dialog modal-sm modal-dialog-sm'><div class='modal-content'><div class='modal-header'><button type='button' class='close' data-dismiss='modal'><span aria-hidden='true'>&times;</span><span class='sr-only'>Close</span></button><h4 class='modal-title' id='" + option.labelId + "'>" + option.labelName + "</h4></div><div id='modal-body' class='modal-body'></div><div id='modal-footer' class='modal-footer'></div></div></div>");
		if ("message" == option.style) {
			$(this.selector + " #modal-body").html(option.modalBody);
			$("<button></button>").addClass("btn btn-primary").attr("data-dismiss", "modal").html("确定").appendTo(this.selector + " #modal-footer");
		}
		return this;
	};
 
	/**
	 * <h1>Tab栏</h1>
	 * @author kfzx-caiyue
	 * @version 1.0
	 * @param option 参数
	 * name: 标签名称
	 * selected: 默认被选中的标签（从0开始）
	 * callback: 回调方法
	 */

	$.fn.mp_title_tab = function(option) {

		$(this.selector).addClass("title_tab").append("<ul id='tab_navs' class='tab_navs title_tab'></ul>");
		for (var i = 0; i < option.tabs.length; i++) {
			var tab = option.tabs[i];
			$("#" + tab.targetId).css("display", "none");
			$("<li id='tab_nav'></li>").addClass("tab_nav").html("<a ths='" + tab.tabId + "'>" + tab.tabName + "</a>").appendTo("#tab_navs");
			if (i == 0) {
				$("#tab_nav").addClass("first");
			}
			if (option.selected == i) {
				$("#tab_nav").addClass("selected");
			}
			$("#tab_nav a").bind("click", function() {
				$(".tab_nav.selected").removeClass("selected");
				$(this).parent().addClass("selected");
				option.callback.apply(this, new Array($(this).attr("ths")));
			});
			$("#tab_nav").removeAttr("id");
		}
		$("#tab_navs").removeAttr("id");
	};
})(jQuery);

/*----------------------------- 表情控件相关函数 --------------------------------*/
/**
 * 将任意字符串作为输入参数，返回一段带有表情图片的html代码
 */
function replaceByEmoji(str){
	var codeArray = [
	                '哈哈','大笑','愉快','喜泣','傻笑','憨笑','微笑','可爱',
					'脸红','调皮','淘气','鬼脸','吐舌','嘻嘻','超开心','思考',
					'学霸','墨镜男','天使','打盹','真棒','好的','剪刀手',
					'强壮','握拳','鼓掌','祈祷','爱心','相爱','穿心','香吻',
					'玫瑰','钻戒','钻石','抱抱','花心','亲亲','亲嘴','笑吻',
					'亲吻','惊喜','惊吓','惊讶','惊愕','气愤','坏笑',
					'冷','沉默','害羞','斜眼','糟糕','烦躁','生气','愤怒',
					'不关心','纠结','绝望','悲惨','难过','沮丧','欢心','伤心',
					'尴尬','糗死了','瞌睡','郁闷','大哭','生病','禁言',
					'负伤','升温','嗜钱','白眼','囧','平和','切','呦',
					'喔','盯','倒立','拳头','挥手','停','食指','不咋地',
					'不开心','不高兴','心碎','上','下','右','左',
					'猪头','恶魔','外星',
	                '左吐舌', '抿嘴', '开怀', '畅怀', '呲牙', '瞅', '害怕', '闭嘴', 
	                '右吐舌', '酷', '挤眼', '淡然', '黑脸', '怀疑', '晕菜', '感动', 
	                '斗眼', '呆', '无语', '酷笑', '酷牙', '不爽', '装酷', '挤眼笑', 
	                '眨眼', '花痴', '桃花', '哭泣', '白痴', '汗', '喊', '笑', 
	                '翻白眼', '歪嘴', '鄙视', '痴呆', '小丑', '喜悦', '忍耐', '心酸', 
	                '窘迫', '大吼', '怒了', '疑惑', '惊叹', '流泪', '痛心',
	                '丝带','礼物','生日蛋糕','南瓜灯','圣诞树','七夕树','松柏','中秋','烟花',
	                '绚烂','彩纸带','彩纸','气球','流星','火花','碰撞','毕业帽','王冠','娃娃',
	                '鱼旗',
	                '#','0','1','2','3','4','5','6','7','8','9','10',
	                '数字','字母','小写字母'
	                ];
	
	var fileArray = [
	                '1_1@2x.png', '1_2@2x.png', '1_3@2x.png', '1_4@2x.png', 
		                '1_5@2x.png', '1_6@2x.png', '1_7@2x.png', '1_8@2x.png', 
		                '1_9@2x.png', '1_10@2x.png', '1_11@2x.png', '1_12@2x.png', 
		                '1_13@2x.png', '1_14@2x.png', '1_15@2x.png', '1_16@2x.png', 
		                '1_17@2x.png', '1_18@2x.png', '1_19@2x.png', '1_20@2x.png', 
		                '1_21@2x.png', '1_22@2x.png', '1_23@2x.png', 
		                '1_25@2x.png', '1_26@2x.png', '1_27@2x.png', '1_28@2x.png', 
		                '1_29@2x.png', '1_30@2x.png', '1_31@2x.png', '1_32@2x.png', 
		                '1_33@2x.png', '1_34@2x.png', '1_35@2x.png', '1_36@2x.png', 
		                '1_37@2x.png', '1_38@2x.png', '1_39@2x.png', '1_40@2x.png', 
		                '1_41@2x.png', '1_42@2x.png', '1_43@2x.png', '1_44@2x.png', 
		                '1_45@2x.png', '1_46@2x.png', '1_47@2x.png',  
		                '1_49@2x.png', '1_50@2x.png', '1_51@2x.png', '1_52@2x.png', 
		                '1_53@2x.png', '1_54@2x.png', '1_55@2x.png', '1_56@2x.png', 
		                '1_57@2x.png', '1_58@2x.png', '1_59@2x.png', '1_60@2x.png', 
		                '1_61@2x.png', '1_62@2x.png', '1_63@2x.png', '1_64@2x.png', 
		                '1_65@2x.png', '1_66@2x.png', '1_67@2x.png', '1_68@2x.png', 
		                '1_69@2x.png', '1_70@2x.png', '1_71@2x.png', 
		                '1_73@2x.png', '1_74@2x.png', '1_75@2x.png', '1_76@2x.png', 
		                '1_77@2x.png', '1_78@2x.png', '1_79@2x.png', '1_80@2x.png',
		                '1_81@2x.png', '1_82@2x.png', '1_83@2x.png', '1_84@2x.png',
		                '1_85@2x.png', '1_86@2x.png', '1_87@2x.png', '1_88@2x.png',
		                '1_89@2x.png', '1_90@2x.png', '1_91@2x.png', '1_92@2x.png',
		                '1_93@2x.png', '1_94@2x.png', '1_95@2x.png', 
		                '1_97@2x.png', '1_98@2x.png', '1_99@2x.png',
		                '2_1@2x.png', '2_2@2x.png', '2_3@2x.png', '2_4@2x.png', 
		                '2_5@2x.png', '2_6@2x.png', '2_7@2x.png', '2_8@2x.png', 
		                '2_9@2x.png', '2_10@2x.png', '2_11@2x.png', '2_12@2x.png', 
		                '2_13@2x.png', '2_14@2x.png', '2_15@2x.png', '2_16@2x.png', 
		                '2_17@2x.png', '2_18@2x.png', '2_19@2x.png', '2_20@2x.png', 
		                '2_22@2x.png', '2_23@2x.png', '2_24@2x.png', '2_25@2x.png', 
		                '2_26@2x.png', '2_27@2x.png', '2_28@2x.png', '2_29@2x.png', 
		                '2_30@2x.png', '2_31@2x.png', '2_32@2x.png', '2_33@2x.png', 
		                '2_34@2x.png', '2_35@2x.png', '2_36@2x.png', '2_37@2x.png', 
		                '2_38@2x.png', '2_39@2x.png', '2_40@2x.png', '2_41@2x.png', 
		                '2_43@2x.png', '2_44@2x.png', '2_45@2x.png', '2_46@2x.png', 
		                '2_47@2x.png', '2_48@2x.png', '2_49@2x.png',
		                '3_1@2x.png', '3_2@2x.png', '3_3@2x.png', '3_4@2x.png',
		                '3_5@2x.png', '3_6@2x.png', '3_7@2x.png', '3_8@2x.png',
		                '3_9@2x.png', '3_10@2x.png', '3_11@2x.png', '3_12@2x.png',
		                '3_13@2x.png', '3_14@2x.png', '3_15@2x.png', '3_16@2x.png',
		                '3_17@2x.png', '3_18@2x.png', '3_19@2x.png', '3_20@2x.png',
		                '4_1@2x.png',  '4_2@2x.png',  '4_3@2x.png',  '4_4@2x.png',
		                '4_5@2x.png',  '4_6@2x.png',  '4_7@2x.png',  '4_8@2x.png',
		                '4_9@2x.png',  '4_10@2x.png', '4_11@2x.png', '4_12@2x.png',
		                '4_13@2x.png', '4_14@2x.png', '4_15@2x.png'
	                ];
	
	for(var i = 0; i < codeArray.length; i++) {
		str = str.replace(new RegExp('\\[' + codeArray[i] + '\\]', 'g'), '<img src="../image/emoji/' + fileArray[i] + '" border="0" style="width: 20px; height: 20px;"/>');
	}
	
	return str;
}
/*----------------------------- END OF 表情控件相关函数 --------------------------------*/

/*
 * 根据类型创建下拉列表
 * id：selct标签id
 * func：select的change事件
 * 
 * */
function CreateSelectByType(id,func)
{
	var type = $("#"+id).attr("type");
	$.ajax({
		type : "POST",
		url : "../ajax/servlet/ICBCBaseReqServlet",
		data : "dse_operationName=QueryMappingDetailOp"
				+ "&in_typecode=" + type
				+ "&in_typeidcode=",
		dataType : "json",
		async:false,
		success : function(data) {
			if('0' == data.TranErrorCode){
				var groupData = data.opdata.jsonData.select;
					$("#"+id).append("<option value='0'>空</option>");
				for ( var i = 0; i < groupData.length; i++) {
					$("#"+id).append("<option value="+groupData[i].typeidcode+">"+groupData[i].typeiddescription+"</option>");
				}
				
				if(func!=null) {
					$("#"+id).bind("change",function (event){
						var value = this.value;
						var text = this.options[this.selectedIndex].innerText;
						func(value,text);
					});
				}
			}
		},
		error : function(data) {
			
		}
	});
}
/*
 * 根据类型创建下拉列表
 * id：selct标签id
 * func：select的change事件
 * 取消了默认为空的select
 * */
function CreateSelectByTypeForAll(id,func)
{
	var type = $("#"+id).attr("type");
	$.ajax({
		type : "POST",
		url : "../ajax/servlet/ICBCBaseReqServlet",
		data : "dse_operationName=QueryMappingDetailOp"
				+ "&in_typecode=" + type
				+ "&in_typeidcode=",
		dataType : "json",
		async:false,
		success : function(data) {
			if('0' == data.TranErrorCode){
				var groupData = data.opdata.jsonData.select;
				for ( var i = 0; i < groupData.length; i++) {
					$("#"+id).append("<option value="+groupData[i].typeidcode+">"+groupData[i].typeiddescription+"</option>");
				}
				
				if(func!=null) {
					$("#"+id).bind("change",function (event){
						var value = this.value;
						var text = this.options[this.selectedIndex].innerText;
						func(value,text);
					});
				}
			}
		},
		error : function(data) {
			
		}
	});
}
/*
 * 根据类型创建下拉列表
 * id：selct标签id
 * func：select的change事件
 * 为手机号的国家地区号创建下拉列表
 * */
function CreateSelectByTypeForPhoneArea(id,func)
{
	var type = $("#"+id).attr("type");
	$.ajax({
		type : "POST",
		url : "../ajax/servlet/ICBCBaseReqServlet",
		data : "dse_operationName=QueryMappingDetailOp"
				+ "&in_typecode=" + type
				+ "&in_typeidcode=",
		dataType : "json",
		async:false,
		success : function(data) {
			if('0' == data.TranErrorCode){
				var groupData = data.opdata.jsonData.select;
				for ( var i = 0; i < groupData.length; i++) {
					$("#"+id).append("<option value="+groupData[i].typeidcode+" code="+groupData[i].typeiddescription+">"+groupData[i].typeiddescription+" "+groupData[i].reserve1+"</option>");
				}
				
				if(func!=null) {
					$("#"+id).bind("change",function (event){
						var value = this.value;
						var text = this.options[this.selectedIndex].innerText;
						func(value,text);
					});
				}
			}
		},
		error : function(data) {
			
		}
	});
}

function getDataFromSMappingDetail(idcode,type){
	var resultdata = "";
	$.ajax({
		type : "POST",
		url : "../ajax/servlet/ICBCBaseReqServlet",
		data : "dse_operationName=QueryMappingDetailOp"
				+ "&in_typecode=" + type
				+ "&in_typeidcode="+idcode,
		dataType : "json",
		async: false,
		success : function(data) {
			if('0' == data.TranErrorCode){
				resultdata = data.opdata.jsonData.select;
				}
		},
		error : function(data) {
			
		}
	});
	return resultdata;
}

function getAreaFlagByStruId(stuid){
	var flag = "";
	
	$.ajax({
		type : "POST",
		url : "../ajax/servlet/ICBCBaseReqServlet",
		data : "dse_operationName=QueryAreaFlagByStruIdOp"
				+ "&in_struid=" + stuid,
		dataType : "json",
		async: false,
		success : function(data) {
			if('0' == data.TranErrorCode){
				flag = data.opdata.jsonData.out_strupartflag;
				
				}
		},
		error : function(data) {
			
		}
	});
	return flag;
}
/*
 * 根据ID和TYPE获取描述（mapping_detail）
 * 
 * */
function getDescriptionByIDandType(idcode,type){
	var retdata = "";
	$.ajax({
		type : "POST",
		url : "../ajax/servlet/ICBCBaseReqServlet",
		data : "dse_operationName=QueryMappingDetailOp"
				+ "&in_typecode=" + type
				+ "&in_typeidcode="+idcode,
		dataType : "json",
		async: false,
		success : function(data) {
			if('0' == data.TranErrorCode){
				var groupData = data.opdata.jsonData.select;
				if(groupData!=""){
					retdata = groupData[0].typeiddescription;
				}
			}
		},
		error : function(data) {
			
		}
	});
	return retdata;
}

/*
 * 根据层级获取可以配置的菜单
 *
 * */
function getMeunListToVerify(grade) {
	var retdata = {};
	$.ajax({
		type : "POST",
		url : "../ajax/servlet/ICBCBaseReqServlet",
		data : "dse_operationName=QueryStruRightOp"
				+ "&in_grade=" + grade,
		dataType : "json",
		async: false,
		success : function(data) {
			if('0' == data.TranErrorCode){
				retdata  = data.opdata.jsonData.rows;	
				}
		},
		error : function(data) {
			
		}
	});
	return retdata;
}

/*
 * 根据机构号获取机构名
 * */
function getStruNameById(struid) {
	var retdata = {};
	$.ajax({
		type: "POST",
		url: "../ajax/servlet/ICBCBaseReqServlet",
		data: "dse_operationName=QueryStruNameOp"+
			"&in_struid="+struid,
		dataType: "json",
		async: false,
		success: function(d, s) {
			if (d.TranErrorCode == "0") {
				retdata = d.opdata.jsonData.rows[0];
			}
		},
		error:function(XMLHttpRequest,
				textStatus, errorThrown) {
		}
	});
	return retdata;
}

/*
 * 根据机构号获取机构名
 * */
function getStruIdByStruType(strutype) {
	var retdata = {};
	$.ajax({
		type: "POST",
		url: "../ajax/servlet/ICBCBaseReqServlet",
		data: "dse_operationName=QueryStruIdByStruTypeOp"+
			"&in_strutype="+strutype,
		dataType: "json",
		async: false,
		success: function(d, s) {
			if (d.TranErrorCode == "0") {
				retdata = d.opdata.jsonData.rows;
			}
		},
		error:function(XMLHttpRequest,
				textStatus, errorThrown) {
		}
	});
	return retdata;
}
function refreshAccountDetail(json,operation){
	
	$("."+operation).css("display","");
	$("#taskid").text(json.taskid);
	$("#orioperid").text(json.orioperid);
	if(json.mpsubflag){
	$("#mpsubflag").val(json.mpsubflag);//五月版新增公众号标志	
	}else{
	$("#mpsubflag").val("2");	
	}

	$("#username").html(json.username);
	$("#loginname").val(decodeForOPFormat(json.loginid));
	$("#area").text(getStruNameById(json.stru_id).stru_fname);
	$("#srvlvl").text(json.srvleveldesc);
	$("#icbc_flag").text(json.icbcflag=='1'?'是':'否');

	$("#email").val(decodeForOPFormat(json.email));
	$("#name").val(decodeForOPFormat(json.linkman));
	//联系人电话地区号
	var phone_temp = json.phoneno;	
	$("#phone-area").empty();
	CreateSelectByTypeForPhoneArea("phone-area",null);
	if(json.phonearea&&json.phonearea!="undefined"){
		$("#phone-area").val(json.phonearea);	
	}
	var areaflag = json.areaflag;
	if(areaflag!="1" && phone_temp.indexOf("-")>0)
	{
		var phone_temps = phone_temp.split("-");
		$.each($("#phone-area option"), function() {
			if($(this).attr("code")==phone_temps[0]){
				$(this).attr("selected","true");
			}
		});
		$("#mobile").val(decodeForOPFormat(phone_temps[1]));
	}else{
		$("#mobile").val(decodeForOPFormat(json.phoneno));
	}
	
	$("#certtype").empty();
	CreateSelectByTypeForAll("certtype",null);
	$("#certtype").val(json.certtype);
	$("#indentcode").val(decodeForOPFormat(json.identcode));
	$("#address").val(decodeForOPFormat(json.commadress));
	$("#carelist-detail").val(json.carelistflag);
	$("#carelist-detail").html(json.carelistflagdesc);
	
	if(areaflag!=""){
		if(areaflag=="1"){
			$("#carelist-label").show();
			$("#carelist-detail").show();
			$("#srvarea-div").hide();
		}else{
			$("#carelist-label").hide();
			$("#carelist-detail").hide();
			$("#srvarea-div").show();
			$("#srvarealist").empty();
			var areaobj = getDataFromSMappingDetail("","ABZON");
			for (var i=0;i<areaobj.length;i++) {
				$("<div class='col-xs-3'><label class='checkbox-inline'><input name='areacheckbox' type='checkbox' id="+areaobj[i].typeidcode+" value="+areaobj[i].typeidcode+">"+areaobj[i].typeiddescription+"</label></div>").appendTo("#srvarealist");
			}
			var srvarea_str = json.srvarea;			
			var srvarea = srvarea_str.split('|');
			for(var i=0;i<srvarea.length;i++){
				$('#'+srvarea[i]).attr("checked",true);
			}
		}
	}
	
	CreateSelectByTypeForAll("confirmman-certtype-detail",null);
	if(json.icbcflag=='1'){
		$("div[type='forICBC']").show();
		$("div[type='forNoICBC']").hide();
		$("#confirmman-loginID-detail").val(json.confirmmanloginid);
		$("#confirmman-name-detail").val(json.confirmmanname);
		$("#confirmman-phoneno-detail").val(json.confirmmanphoneno);
		$("#confirmman-email-detail").val(json.confirmmanemail);
		$("#confirmman-certtype-detail").val(json.confirmmancerttype);
		$("#confirmman-indentcode-detail").val(json.confirmmanindentcode);
		$("#confirmman-commaddress-detail").val(json.confirmmanaddress);
	}else{
		$("div[type='forICBC']").hide();
		$("div[type='forNoICBC']").show();
	}
	$("#icbcclient"+json.icbcclientflag).css("display","");
	$("#icbcclientflag").text(json.icbcclientflag=='1'?'是':'否');
	if(json.icbcclientflag=='1') {
		$("#icbcclient0").show();
		$("#accno").text(json.accno);
		$("#cisno").text(json.cisno);
	} else {
		$("#icbcclient0").hide();
	}
	
	$("#acctype").text('账号类型：'+json.usertypedesc);
	if(json.usertypedesc == '政府') {
		$("#orgname").text('政府全称');
		$("#orgnamecontent").html(json.orgname);
 
		$("#orgno").text('');
		$("#orgnocontent").text('');

	}
	if(json.usertypedesc == '媒体') {
		$("#orgname").text('组织名称');
		$("#orgnamecontent").html(json.orgname);
		$("#orgno").text('组织机构代码');
		$("#orgnocontent").text(json.orgno);
	}
	if(json.usertypedesc == '企业') {
		$("#orgname").text('企业名称');
		$("#orgnamecontent").html(json.orgname);
		$("#orgno").text('营业执照号');
		$("#orgnocontent").text(json.orgno);
	}
	if(json.usertypedesc == '其他组织') {
		$("#orgname").text('组织名称');
		$("#orgnamecontent").html(json.orgname);
		$("#orgno").text('组织机构代码');
		$("#orgnocontent").text(json.orgno);
	}
	if(json.usertypedesc == '个人'||json.usertypedesc == '融e购'||json.usertypedesc == 'e生活') {
		$("#orgname").text('');
		$("#orgnamecontent").html('');
		$("#orgno").text('');
		$("#orgnocontent").text('');

	}
	if(json.usertypedesc == '行内') {
		$("#orgname").text('备注');
		$("#orgnamecontent").html(json.orgname);
		$("#orgno").text('');
		$("#orgnocontent").text('');

	}
	$("#srvlvl_select").empty();
	CreateSelectByType('srvlvl_select',null);
	
	$("#srvtypedesc").text(json.srvtypedesc);
	$("#mptype").text(json.usertype);
	$("#menulist").empty();
	
	menuobj = getMeunListToVerify("A");
	for (var i=0;i<menuobj.length;i++) {
		$("<div class='col-xs-3'><label class='checkbox-inline'><input name='checkbox' type='checkbox' id="+menuobj[i].rightId+" value="+menuobj[i].rightId+">"+menuobj[i].rightName+"</label></div>").appendTo("#menulist");
	}
	
}
//接口权限申请审批
function AuthrefreshAccountDetail(json,operation){
	
	$("."+operation).css("display","");
	$("#taskid").text(json.taskid);
	$("#orioperid").text(json.orioperid);

	$("#username").html(json.username);
	$("#loginname").val(decodeForOPFormat(json.loginid));
	$("#srvlvl").text(json.srvleveldesc);
	$("#icbc_flag").text(json.icbcflag=='1'?'是':'否');
	
	/*$("#userinfocisno").text(json.userinfocisno=='cisno:1'?'是':'否');
	$("#userinfounino").text(json.userinfounino=='unino:1'?'是':'否');
	$("#userinfoicbcuserid").text(json.userinfoicbcuserid=='icbcuserid:1'?'是':'否');
	$("#userinfomobileno").text(json.userinfomobileno=='mobileno:1'?'是':'否');*/
	$("#fanslistinter").text(json.fanslistinter=='user/get:1'?'是':'否');
	$("#userinfointer").text(json.userinfointer=='user/info:1'?'是':'否');
	$("#webauthinter").text(json.webauthinter=='oauth2:1'?'是':'否');
	$("#webauthgettokeninter").text(json.webauthgettokeninter=='oauth2/access_token:1'?'是':'否');
	$("#webauthchecktokeninter").text(json.webauthchecktokeninter=='oauth2/auth:1'?'是':'否');
	$("#webauthgetuserinfointer").text(json.webauthgetuserinfointer=='oauth2/userinfo:1'?'是':'否');
	$("#updateremarkinter").text(json.updateremarkinter=='user/updateremark:1'?'是':'否');
	$("#groupcreateinter").text(json.groupcreateinter=='groups/create:1'?'是':'否');
	$("#groupnameinter").text(json.groupnameinter=='groups/update:1'?'是':'否');
	$("#moveusergroupinter").text(json.moveusergroupinter=='groups/members/update:1'?'是':'否');
	$("#delgroupinter").text(json.delgroupinter=='groups/delete:1'?'是':'否');
	$("#queryusergroupinter").text(json.queryusergroupinter=='groups/getid:1'?'是':'否');
	$("#querygroupinter").text(json.querygroupinter=='groups/get:1'?'是':'否');
	$("#querygroupusernointer").text(json.querygroupusernointer=='groups/getcount:1'?'是':'否');
	$("#messgroupsendinter").text(json.messgroupsendinter=='message/sendall:1'?'是':'否');
	$("#messsendbygroupidinter").text(json.messsendbygroupidinter=='message/sendallbygroupid:1'?'是':'否');
	$("#messsendbyopenidinter").text(json.messsendbyopenidinter=='message/sendallbyopenid:1'?'是':'否');
	$("#messsendinter").text(json.messsendinter=='message/send:1'?'是':'否');
	$("#defmenucreateinter").text(json.defmenucreateinter=='menu/create:1'?'是':'否');
	$("#defmenudeleteinter").text(json.defmenudeleteinter=='menu/delete:1'?'是':'否');
	$("#defmenugetinter").text(json.defmenugetinter=='menu/get:1'?'是':'否');
	$("#refreshtokenbuffinter").text(json.refreshtokenbuffinter=='notify/accesstoken:1'?'是':'否');
	$("#refreshauthbuffinter").text(json.refreshauthbuffinter=='notify/authlist:1'?'是':'否');
	$("#getjsapitickinter").text(json.getjsapitickinter=='ticket/getticket:1'?'是':'否');
	$("#getaccesstokeninter").text(json.getaccesstokeninter=='token:1'?'是':'否');
	
	$("#email").val(decodeForOPFormat(json.email));
	$("#name").val(decodeForOPFormat(json.linkman));
	//联系人电话地区号
	var phone_temp = json.phoneno;	
	$("#phone-area").empty();
	CreateSelectByTypeForPhoneArea("phone-area",null);
	if(json.phonearea&&json.phonearea!="undefined"){
		$("#phone-area").val(json.phonearea);	
	}
	var areaflag = json.areaflag;
	if(areaflag!="1" && phone_temp.indexOf("-")>0)
	{
		var phone_temps = phone_temp.split("-");
		$.each($("#phone-area option"), function() {
			if($(this).attr("code")==phone_temps[0]){
				$(this).attr("selected","true");
			}
		});
		$("#mobile").val(decodeForOPFormat(phone_temps[1]));
	}else{
		$("#mobile").val(decodeForOPFormat(json.phoneno));
	}
	
	$("#certtype").empty();
	CreateSelectByTypeForAll("certtype",null);
	$("#certtype").val(json.certtype);
	$("#indentcode").val(decodeForOPFormat(json.identcode));
	$("#address").val(decodeForOPFormat(json.commadress));
	$("#carelist-detail").val(json.carelistflag);
	$("#carelist-detail").html(json.carelistflagdesc);
	
	if(areaflag!=""){
		if(areaflag=="1"){
			$("#carelist-label").show();
			$("#carelist-detail").show();
			$("#srvarea-div").hide();
		}else{
			$("#carelist-label").hide();
			$("#carelist-detail").hide();
			$("#srvarea-div").show();
			$("#srvarealist").empty();
			var areaobj = getDataFromSMappingDetail("","ABZON");
			for (var i=0;i<areaobj.length;i++) {
				$("<div class='col-xs-3'><label class='checkbox-inline'><input name='areacheckbox' type='checkbox' id="+areaobj[i].typeidcode+" value="+areaobj[i].typeidcode+">"+areaobj[i].typeiddescription+"</label></div>").appendTo("#srvarealist");
			}
			var srvarea_str = json.srvarea;			
			var srvarea = srvarea_str.split('|');
			for(var i=0;i<srvarea.length;i++){
				$('#'+srvarea[i]).attr("checked",true);
			}
		}
	}
	$("#icbcclient"+json.icbcclientflag).css("display","");
	$("#icbcclientflag").text(json.icbcclientflag=='1'?'是':'否');
	if(json.icbcclientflag=='1') {
		$("#icbcclient0").show();
		$("#accno").text(json.accno);
		$("#cisno").text(json.cisno);
	} else {
		$("#icbcclient0").hide();
	}
	$("#srvlvl_select").empty();
	CreateSelectByType('srvlvl_select',null);
	
	$("#srvtypedesc").text(json.srvtypedesc);
	$("#mptype").text(json.usertype);
	$("#menulist").empty();
	
	menuobj = getMeunListToVerify("A");
	for (var i=0;i<menuobj.length;i++) {
		$("<div class='col-xs-3'><label class='checkbox-inline'><input name='checkbox' type='checkbox' id="+menuobj[i].rightId+" value="+menuobj[i].rightId+">"+menuobj[i].rightName+"</label></div>").appendTo("#menulist");
	}
	
}


//商户支付安全域名配置项详情ShopPayResultAccountDetail
function ShopPayRefreshAccountDetail(json,operation){
	
	$("."+operation).css("display","");
	$("#taskid").text(json.taskid);
	$("#orioperid").text(json.orioperid);
	
	var status = json.status;
	var shopsupporturl00 = json.shopsupporturls.split(',')[0];
	var shopsupporturl10 = json.shopsupporturls.split(',')[1];
	var shopsupporturl20 = json.shopsupporturls.split(',')[2];
	var shopsupporturl30 = json.shopsupporturls.split(',')[3];
	var shopsupporturl40 = json.shopsupporturls.split(',')[4];
	
	$("#shopname0").val(decodeForOPFormat(json.shopname));
	$("#shopid0").val(decodeForOPFormat(json.shopid));
	$("#shoptype0").val(decodeForOPFormat(json.shoptype));
	$("#shopSupportUrl00").val(decodeForOPFormat(json.shopsupporturls.split(',')[0]));
	$("#shopSupportUrl10").val(decodeForOPFormat(json.shopsupporturls.split(',')[1]));
	$("#shopSupportUrl20").val(decodeForOPFormat(json.shopsupporturls.split(',')[2]));
	$("#shopSupportUrl30").val(decodeForOPFormat(json.shopsupporturls.split(',')[3]));
	$("#shopSupportUrl40").val(decodeForOPFormat(json.shopsupporturls.split(',')[4]));
	$("#notifyurl0").val(decodeForOPFormat(json.notifyurl));
	$("#details_apply_reason").val(decodeForOPFormat(json.applyreason));
	$("#details_apply_reason").attr('disabled','disabled');
	if(status == "4"){

		$("#refuseresult").show();
		$("#refusemsgid").show();
		$("#refusemsg").val(decodeForOPFormat(json.refusemsg));
	}else{
		$("#refuseresult").hide();
		$("#refusemsgid").hide();
	}
}
//商户支付安全域名配置项回显
function ShopPayResultAccountDetail(json,operation){
	
	$("."+operation).css("display","");

	$("input[id='shopname']").attr('disabled','disabled');
	$("select[id='shoptype']").attr('disabled','disabled');
	$("input[id='shopid']").attr('disabled','disabled');
	$("input[id='shopSupportUrl0']").attr('disabled','disabled');
	$("input[id='shopSupportUrl1']").attr('disabled','disabled');
	$("input[id='shopSupportUrl2']").attr('disabled','disabled');
	$("input[id='shopSupportUrl3']").attr('disabled','disabled');
	$("input[id='shopSupportUrl4']").attr('disabled','disabled');
	$("input[id='notifyurl']").attr('disabled','disabled');
/*	$("button[id='btn_shoppay_save']").hide();
	$("button[id='btn_shoppay_update']").show();*/

	var shopsupporturl0 = json.shopsupporturls.split(',')[0];
	var shopsupporturl1 = json.shopsupporturls.split(',')[1];
	var shopsupporturl2 = json.shopsupporturls.split(',')[2];
	var shopsupporturl3 = json.shopsupporturls.split(',')[3];
	var shopsupporturl4 = json.shopsupporturls.split(',')[4];
	
	$("#shopname").val(decodeForOPFormat(json.shopname));
	$("#shopid").val(decodeForOPFormat(json.shopid));
	$("#shoptype").val(decodeForOPFormat(json.shoptype));
	$("#shopSupportUrl0").val(decodeForOPFormat(json.shopsupporturls.split(',')[0]));
	$("#shopSupportUrl1").val(decodeForOPFormat(json.shopsupporturls.split(',')[1]));
	$("#shopSupportUrl2").val(decodeForOPFormat(json.shopsupporturls.split(',')[2]));
	$("#shopSupportUrl3").val(decodeForOPFormat(json.shopsupporturls.split(',')[3]));
	$("#shopSupportUrl4").val(decodeForOPFormat(json.shopsupporturls.split(',')[4]));
	$("#notifyurl").val(decodeForOPFormat(json.notifyurl));

}

/*
 * 根据选择的机构类型展现机构列表
 */
function onStruTypeChange(value,text){
	var strulist = getStruIdByStruType(value);
	$("#stuname_select").empty();
	if(strulist.length==0){
		$("<option></option>").attr("value","").html("空").appendTo("#stuname_select");
	}else{
		for (var i=0;i<strulist.length;i++) {
			$("<option></option>").attr("value",strulist[i].struid).html(strulist[i].struname).appendTo("#stuname_select");
		}
	}
}
/*
 * 根据帖子id展现帖子详情，详情页bar_article_info.jsp
 * 此为公用方法，财富吧帖子详情都调用此方法
 */
function showBarArticleInfo(articleid){
	var retdata = {};
	$.ajax({
		type: "POST",
		url: "../ajax/servlet/ICBCBaseReqServlet",
		data: "dse_operationName=BarQueryArticleInfoOp"+
			"&in_articleid="+articleid+"&pageNumber=1&pageSize=10",
		dataType: "json",
		async: false,
		success: function(d, s) {
			if(d.TranErrorCode == "9998") {
				parent.location = "../icbc/syserror1.jsp";
			}else if (d.TranErrorCode == "0") {
				var returnCode = d.opdata.jsonData.PUBLIC.retcode;
				if (returnCode == "0") {
					retdata = d.opdata.jsonData.PRIVATE.Record;
					refreshArticleInfoDetail(retdata);
				}
			}else{
				alert("查询信息失败，错误编码:"+d.TranErrorCode+",错误信息:"+d.TranErrorDisplayMsg);
			}
		},
		error:function(XMLHttpRequest,
				textStatus, errorThrown) {
			parent.location = "../icbc/syserror1.jsp";
		}
	});
}
/*
 * 根据帖子id展现帖子详情，对bar_article_info.jsp填充数据
 */
function refreshArticleInfoDetail(data){
	$("#article_id").html(data[0].article_id);
	$("#createuser").html(data[0].nickname);
	$("#createuserid").html(data[0].createuserid);
	$("#article_title").html(data[0].article_title);
	$("#article_type").html(translateArticleType(data[0].article_type));
	$("#article-text").text(data[0].article_content);
	$("#istop").html(translateStatus(data[0].istop));
	$("#eliteflag").html(translateStatus(data[0].eliteflag));
	$("#iscomment").html(translateReverseStatus(data[0].iscomment));
	//展现图片
	$("#article-imagelist").empty();
	if(data[0].image!=""){
		var imagelist = data[0].image;
		var imagesplit = imagelist.split('|');
		var path =$("#BLOGFILEURL").html()+"IMARTICLE/";  
		for(var i=0;i<imagesplit.length;i++){
			$("#article-imagelist").append("<img width='128px' height='128px' src='"+path+imagesplit[i]+"'/>");
		}
	}
	$("#create_time").html(data[0].create_time);
	$("#update_time").html(data[0].update_time);
	$("#status").html(translateReverseStatus(data[0].status));
	//展现回复
	//todo:是否加flag区分展现
	refreshReplayInfo(data[0].article_id);
}
/*
 * 根据帖子id展现帖子-回复详情
 */
function refreshReplayInfo(articleid){
	var pageNumber = 1;
	var pageSize = 10;
	$.ajax({
		type: "POST",
		url: "../ajax/servlet/ICBCBaseReqServlet",
		data: "dse_operationName=BarQueryCommentOp&in_articleid=" + articleid
		+ "&pageNumber=" + pageNumber
		+ "&pageSize=" + pageSize,
		dataType: "json",
		success: function(d, s) {
			if (d.TranErrorCode == "9998") {
				parent.location = "../icbc/syserror1.jsp";
			} else if (d.TranErrorCode == "0") {
				Data = d.opdata.jsonData.PRIVATE.Record;
				pageCount = d.opdata.jsonData.PRIVATE.recordnum;
				$("#reply-table").empty();
				if(pageCount == 0) {
					$("#reply-table").append("<div style='text-align: center'>暂无回复内容</div>");
					$("#panel-all-pagination").empty();
				} else {					
					// 根据查询的消息结果集，刷新页面UI中的消息列表
					updateReplayInfo(articleid, Data);
					
					// 初始化翻页菜单，页码被选中时候，继续执行查询下行消息列表的OP - QueryMOMsgOp
					func_pagination("panel-all-pagination", {
						pageNumber: pageNumber,
						pageSize: pageSize,
						total: pageCount,
						onSelected: function(pageNumber, pageSize) {
							$.ajax({
								type: "POST",
								url: "../ajax/servlet/ICBCBaseReqServlet",
								data: "dse_operationName=BarQueryCommentOp&in_articleid=" + articleid
								+ "&pageNumber=" + pageNumber
								+ "&pageSize=" + pageSize,
								dataType: "json",
								success: function(d, s) {
									if (d.TranErrorCode == "9998") {
										parent.location = "../icbc/syserror1.jsp";
									} else if (d.TranErrorCode == "0") {
										// 根据查询的消息结果集，刷新页面UI中的消息列表
										updateReplayInfo(articleid, d.opdata.jsonData.PRIVATE.Record);
									} else {
										alert("查询回复信息失败，错误编码:"+d.TranErrorCode+",错误信息:"+d.TranErrorDisplayMsg);
									}
								},
								error: function(req, s, e) {
									parent.location = "../icbc/syserror1.jsp";
								}
							});
						}
					});
				}
			}
		},
		error: function(req, s, e) {
			parent.location = "../icbc/syserror1.jsp";
		}
	});
}
/*
 * 根据帖子id展现帖子-回复详情，对bar_article_info.jsp填充数据
 */
function updateReplayInfo(articleid,data){
	$("#reply-table").empty();
	var barid=$("#barid").val();
	// 循环结果集
	for(var i = 0; i < data.length; i ++) {
		if(barid&&barid!="null"){
			$("#reply-table").append("<tr>"+"<td width='10%'><div id='"+data[i].commentuser+"' style='margin-left:10px'>"+data[i].nickname+"("+data[i].commentuser+")"+"</div></td>"
					+"<td width='90%' style='word-break;break-all;'>"
					+"<div style='margin-right:20px;'>"+decodeForOPFormat(data[i].content)+"</div>"
					+"<div class='row'>"
					+"<div class='col-sm-4' ></div>"
					+"<div class='col-sm-8'>"
					+	"<div align='right' style='font-size:12px;margin-right:20px;'>"+"赞("+data[i].thumbcount+")"
						+"<a onclick='showSubCommentInfo(\""+data[i].commentid+"\",\""+articleid+"\")'>"+" 回复("+data[i].subcommentcount+")</a> <a onclick='delSubCommentInfo(\""+articleid+"\",\""+data[i].commentid+"\",\""+articleid+"\",1)'>"+" 删除</a>&nbsp;"+data[i].commenttime+"</div>"	
					+"</div>"
					+"<div class='row'>"
					+"<div class='col-sm-2' ></div>"
					+"<div class='col-sm-10'>"
					+   "<table id='"+data[i].commentid+"_table' class='table' style='display:none;font-size:12px;margin-bottom:0px;'></table>"
					+ 	"<div id='"+data[i].commentid+"_pagination' class='row' style='display:none;' >"
					+		"<div id='pagination-subcomment-"+data[i].commentid+"' class='col-md-12' style='font-size:10px;'>"
					+		"<ul class='pagination'></ul>"
				    +		"</div>"
				    +	"</div>"
					+"</div>"
					+"</div>"
					+"</tr>");
		}else{
			$("#reply-table").append("<tr>"+"<td width='10%'><div id='"+data[i].commentuser+"' style='margin-left:10px'>"+data[i].nickname+"("+data[i].commentuser+")"+"</div></td>"
					+"<td width='90%' style='word-break;break-all;'>"
					+"<div style='margin-right:20px;'>"+decodeForOPFormat(data[i].content)+"</div>"
					+"<div class='row'>"
					+"<div class='col-sm-4' ></div>"
					+"<div class='col-sm-8'>"
					+	"<div align='right' style='font-size:12px;margin-right:20px;'>"+"赞("+data[i].thumbcount+")"
						+"<a onclick='showSubCommentInfo(\""+data[i].commentid+"\",\""+articleid+"\")'>"+" 回复("+data[i].subcommentcount+")</a>"+data[i].commenttime+"</div>"	
					+"</div>"
					+"<div class='row'>"
					+"<div class='col-sm-2' ></div>"
					+"<div class='col-sm-10'>"
					+   "<table id='"+data[i].commentid+"_table' class='table' style='display:none;font-size:12px;margin-bottom:0px;'></table>"
					+ 	"<div id='"+data[i].commentid+"_pagination' class='row' style='display:none;' >"
					+		"<div id='pagination-subcomment-"+data[i].commentid+"' class='col-md-12' style='font-size:10px;'>"
					+		"<ul class='pagination'></ul>"
				    +		"</div>"
				    +	"</div>"
					+"</div>"
					+"</div>"
					+"</tr>");
		}
		/*todo:使用style布局
		$("#reply-table").append("<tr>"+"<td width='10%'><div id='"+data[i].commentuser+"' style='margin-left:10px'>"+data[i].nickname+"</div></td>"
				+"<td width='90%' style='word-break;break-all;'>"
				+"<div style='margin-right:20px;'>"+decodeForOPFormat(data[i].content)+"</div>"
				+"<div style='float:right;margin-top:15px;height:30px;font-size:12px;'>"
				
				+	"赞("+data[i].thumbcount+")"
				+	"<a onclick='showSubCommentInfo(\""+data[i].commentid+"\")'>"+" 回复("+data[i].subcommentcount+")</a> "+data[i].commenttime
				+   "<table id='"+data[i].commentid+"_table' class='table' style='display:none;'></table>"
				+ 	"<div id='"+data[i].commentid+"_pagination' class='row' style='display:none;' >"
				+		"<div id='pagination-subcomment-"+data[i].commentid+"' class='col-md-12'>"
				+		"<ul class='pagination'></ul>"
			    +		"</div>"
			    +	"</div>"
				+"</div>"
				+"</tr>");
				*/
	}
}

/*
 * 根据帖子id展现帖子-回复详情，对bar_article_info.jsp填充数据
 */
function showSubCommentInfo(commentid,articleid){

	var tableid = "#"+commentid+"_table";
	var paginationdiv = "#"+commentid+"_pagination";
	var pagination = "pagination-subcomment-"+commentid;
	if($(tableid).is(":visible")){
		$(tableid).hide();
		$(paginationdiv).hide();
	}else{//展现
		$(tableid).show();
		$(paginationdiv).show();
		var pageNumber = 1;
		var pageSize = 10;
		$.ajax({
			type: "POST",
			url: "../ajax/servlet/ICBCBaseReqServlet",
			data: "dse_operationName=BarQuerySubCommentOp&in_commentid=" + commentid
			+ "&pageNumber=" + pageNumber
			+ "&pageSize=" + pageSize,
			dataType: "json",
			async: false,
			success: function(d, s) {
				if (d.TranErrorCode == "9998") {
					parent.location = "../icbc/syserror1.jsp";
				} else if (d.TranErrorCode == "0") {
					Data = d.opdata.jsonData.PRIVATE.Record;
					pageCount = d.opdata.jsonData.PRIVATE.recordnum;
					$(tableid).empty();
					if(pageCount == 0) {//无回复隐藏tab，不响应点击事件
						$(tableid).hide();
						$(paginationdiv).hide();
					} else {					
						// 根据查询的消息结果集，刷新页面UI中的消息列表
						updateSubCommmentInfo(tableid,commentid, Data,articleid);
						
						// 初始化翻页菜单，页码被选中时候，继续执行查询下行消息列表的OP - QueryMOMsgOp
						func_pagination(pagination, {
							pageNumber: pageNumber,
							pageSize: pageSize,
							total: pageCount,
							onSelected: function(pageNumber, pageSize) {
								$.ajax({
									type: "POST",
									url: "../ajax/servlet/ICBCBaseReqServlet",
									data: "dse_operationName=BarQuerySubCommentOp&in_commentid=" + commentid
									+ "&pageNumber=" + pageNumber
									+ "&pageSize=" + pageSize,
									dataType: "json",
									async: false,
									success: function(d, s) {
										if (d.TranErrorCode == "9998") {
											parent.location = "../icbc/syserror1.jsp";
										} else if (d.TranErrorCode == "0") {
											// 根据查询的消息结果集，刷新页面UI中的消息列表
											updateSubCommmentInfo(tableid,commentid, d.opdata.jsonData.PRIVATE.Record,articleid);
										} else {
											alert("查询回复信息失败，错误编码:"+d.TranErrorCode+",错误信息:"+d.TranErrorDisplayMsg);
										}
									},
									error: function(req, s, e) {
										parent.location = "../icbc/syserror1.jsp";
									}
								});
							}
						});
					}
				}
			},
			error: function(req, s, e) {
				parent.location = "../icbc/syserror1.jsp";
			}
		});

	}
}

/*
 * 单条删除帖子回复
 */
function delSubCommentInfo(parent_commentid,commentid,articleid,flag){

	if(confirm("是否确定删除该条评论？")){
		$.ajax({
			type: "POST",
			url: "../ajax/servlet/ICBCBaseReqServlet",
			data: "dse_operationName=BarModifySubCommentOp"
				  +"&in_articleid=" + parent_commentid
				  +"&in_commentid=" + commentid
				  + "&in_flag="+flag,
			dataType: "json",
			async: false,
			success: function(d, s) {
				if (d.TranErrorCode == "9998") {
					parent.location = "../icbc/syserror1.jsp";
				} else if (d.TranErrorCode == "0") {
					var retcode = d.opdata.jsonData.PUBLIC.retcode;
					if(retcode=="0"){
						alert("删除成功！");
						showBarArticleInfo(articleid);
					}else{
						alert("系统错误，请联系工行！");
					}
				}
			},
			error: function(req, s, e) {
				parent.location = "../icbc/syserror1.jsp";
			}
		});
	}
}
/*
 * 根据帖子id展现帖子-二级回复详情
 */
function updateSubCommmentInfo(tableid,commentid,data,articleid){
	$(tableid).empty();
	var barid=$("#barid").val();
	// 循环结果集
	for(var i = 0; i < data.length; i ++) {
		if(barid&&barid!="null"){
			$(tableid).append("<tr>"+"<td width='20%' style='min-width:100px;'><div id='"+data[i].commentuser+"' style='margin-left:10px'>"+data[i].nickname+"("+data[i].commentuser+")"+"</div></td>"
					+"<td width='80%' style='word-break;break-all;'>"
					+"<div style='margin-right:20px;'>"+decodeForOPFormat(data[i].content)+"</div>"
					+"<div class='row'>"
					+"<div class='col-sm-2' ></div>"
					+"<div class='col-sm-10'>"
					+	"<div align='right' style='font-size:11px;margin-right:20px;'><a onclick='delSubCommentInfo(\""+data[i].commentid+"\",\""+data[i].subcommentid+"\",\""+articleid+"\",2)'>"+" 删除</a>&nbsp;"+data[i].commenttime+"</div>"
					+"</div>"
					+"</div>"
					+"</tr>")
		}else{
			$(tableid).append("<tr>"+"<td width='20%' style='min-width:100px;'><div id='"+data[i].commentuser+"' style='margin-left:10px'>"+data[i].nickname+"("+data[i].commentuser+")"+"</div></td>"
					+"<td width='80%' style='word-break;break-all;'>"
					+"<div style='margin-right:20px;'>"+decodeForOPFormat(data[i].content)+"</div>"
					+"<div class='row'>"
					+"<div class='col-sm-2' ></div>"
					+"<div class='col-sm-10'>"
					+	"<div align='right' style='font-size:11px;margin-right:20px;'>"+data[i].commenttime+"</div>"
					+"</div>"
					+"</div>"
					+"</tr>")
		}
	}
}
/**
 * 弹出窗
 */
function PopoverContent() {
	// 弹出窗触发文字
	this.text = "";
	// 弹出窗标题
	this.title = "";
	// 弹出窗正文
	this.body = "";
	// 位置，接受left, right, top, bottom四个参数
	this.placement = "left";
	
	this.makePopover = function() {
		var htmlCode = "<u style='font' data-toggle='popover' title = '" + this.title + "' data-content='" + this.body 
			+ "' data-placement='" + this.placement + "'data-container='body' data-trigger='hover' data-html='true'>" + this.text + "</u>";
		return htmlCode;
	};
}

//PopoverContent.prototype = {
//		constructor: PopoverContent,
//		
//	};

/**
 * 根据两个数组，形成一个键值对形式的的htmlcode, 例：<br/>
 * <b>输入</b>：generateKeyValueList(["code", "content", "date"], ["001", "what we got here is...", "2014-10-29"]) <br/>
 * <b>输出</b>：(类似于) <br/>
 * code: 001 <br/>
 * content: what we got here is... <br/>
 * date: 2014-10-29
 * 
 */
function generateKeyValueList(keyArray, valueArray) {
	var content = "";
 	for(var i = 0; i < keyArray.length; i ++) {
 		content += (keyArray[i] + ":&nbsp" + valueArray[i] + "&lt;br&gt;");
 	}
 	return content;
}

/**
 * 为一个id为rowId的tr添加td元素，元素内容为array中各项元素
 * @param rowId tr的元素id
 * @param array 元素内容
 */
function TableRow(rowId, array) {
	this.array = array;
	this.rowId = rowId;

	this.makeRow = function() {
		for(var i = 0; i < this.array.length; i++) {
			$("#" + this.rowId).append("<td>" + this.array[i] + "</td>");
		}
	};
	
// 	function addTd(value) {
// 	};
}

/**
 *	实现了一个简单的Hash表
 *	<b>使用方式：</b><br/>
 *	var hashmap = new HashMap();
 *	hashmap.put(key, value);
 *	...
 *	@author 作者未知。由kfzx-xunx引进及维护
 */
function HashMap() {
}

HashMap.prototype = {
		length: 0,
		obj: {},
		
		isEmpty: function() {
			return (this.length == 0);
		},
		
		containsKey: function(key) {
			return (key in this.obj);
		},
		
		containsValue: function(value) {
			for(var key in this.obj) {
				if(this.obj[key] == value) {
					return true;
				}
			}
			return false;
		},
		
		put: function(key, value) {
			if(!this.containsKey(key)) {
				this.length ++;
			}
			this.obj[key] = value;
		},
		
		get: function(key) {
			return this.containsKey(key) ? this.obj[key] : null;
		},
		
		remove: function(key) {
			if(!this.containsKey(key) && (delete this.obj[key])) {
				this.length --;
			}
		},
		
		values: function() {
			var _values = [];
			for(var key in this.obj) {
				_values.push(this.obj[key]);
			}
			return _values;
		},
		
		keys: function() {
			var _keys = [];
			for(var key in this.obj) {
				_keys.push(key);
			}
			return _keys;
		},
		
		size: function() {
			return this.length;
		},
		
		clear: function() {
			this.length = 0;
			this.obj = {};
		}
};

/** 
 * 根据url的search域获取两个Array，一个存储key，一个存储value
 */
function getSearchMap(search) {
	var array = search.substring(1).split("&");
	
	var hashmap = new HashMap();
	for(var i = 0; i < array.length; i ++) {
		var index = array[i].indexOf("=");
		
		hashmap.put(array[i].substring(0, index), array[i].substring(index + 1, array[i].length));
	}
	
	return hashmap;
}

/** 
 * TODO 这段代码的问题出在：违反了尊重对象所有权的原则
 * 对Date的扩展，将 Date 转化为指定格式的String 
 * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
 * 例子： 
 * (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
 * (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
 * @author GitHub - meizz.  
 */
Date.prototype.Format = function(fmt) { //
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

/**
 * 取下一处理人列表 right赋值： 审批：B0008
 */
function getNextOperators(nextdealerTag,right) {
	// 取下一处理人列表
	$.ajax({
		type: "POST",
		url: "../ajax/servlet/ICBCBaseReqServlet",
		data: "dse_operationName=QueryNextOperatorOp&"+
			  "rightid="+right,
		dataType: "json",
		success: function(d, s) {
			if (d.TranErrorCode == "9998") {
				parent.location = "../icbc/syserror1.jsp";
			}
			if (d.TranErrorCode == "0") {
				var dataList = d.opdata.jsonData.rows;
				if (dataList.length == 0) {
					$("#"+nextdealerTag).append("<option value='0'>无</option>");
				} else {
					for ( var i = 0; i < dataList.length; i++) {
						var operatorName = dataList[i].nextoperator;
						var operatorID = dataList[i].nextoperatorID;
						var operatorloginID = dataList[i].nextoperatorLoginID;
						
						$("#"+nextdealerTag).append("<option value="+operatorID+">"+operatorName+"("+operatorloginID+")</option>");
					}
				}
			} else {
				if (d.TranErrorCode == "9998") {
					parent.location = "../icbc/syserror1.jsp";
				}
				alert(d.TranErrorDisplayMsg);
			}
			
		},
		error: function(req, s, e) {
			parent.location = "../icbc/syserror1.jsp";
		}
	});	
}

/* 将空数据翻译为'---------' */
function translateEmptyString(originName) {
	var displayedName = originName;
	if (originName == "") {
		displayedName = "------";
	}
	return displayedName;
}

/* 将空数据翻译为'---------' */
function translateMgrMobile(number) {
	var reg = /^9\d{11}$/;
	if(reg.test(number)) {
		return number.slice(1);
	} else {
		return number;
	}
}

/* 对数组进行排序 */
function sortArrayByAsc(a, b) {
	return a - b;
}

/* 对html代码进行反转义 */
function noEscapeHtml(html) {  
	return html.replace(/(\&|\&)gt;/g, ">")
			.replace(/(\&|\&)lt;/g, "<")
			.replace(/(\&|\&)quot;/g, "\"");
}

/* 对html代码进行转义 */
function escapeHtml(html) {  
	return html.replace("<", "&lt;")
		.replace(">", "&gt;");
}

/**
 * 如果你在子窗体或者iframe中调用，则会把dialog放入父容易，因此要注意css的外部引用
 * @param option 
 * id: dom的ID,会重复利用ID相同的DIV
 * header: 自制header的内容
 * body：自制body的内容
 * confirmCallback: 点确定调用的方法，如果你的闭包函数返回false，则按钮的click事件不会执行
 * 					默认关闭dialog
 * cancelCallback:  点取消调用的方法，如果你的闭包函数返回false，则按钮的click事件不会执行
 * 					默认关闭dialog
 * @return
 * added by caozq
 */
function showHeavyDialog(option)
{
	$div = $("<div>").attr("id", option.id).attr("class","modal fade").
					append( $("<div>").attr("class","modal-content modal-dialog")
							.append( $("<div>").attr("class","modal-header").html(option.header)
									.prepend($("<button>").attr("type","button").attr("class","close").attr("data-dismiss","modal").html("&times;"))
									)
							.append( $("<div>").attr("class","modal-body").html(option.body))
							.append( $("<div>").attr("class","modal-footer").css({"height":"75px","text-align":"center"})
									.append( $("<button>").attr("class","btn btn-primary buttonwidth cOnfirm").attr("data-dismiss","modal").html("确定"))
									.append( $("<button>").attr("class","btn btn-default buttonwidth cAncel").attr("data-dismiss","modal").html("取消"))
							)
					);
	if(document.getElementById(option.id)==undefined)
	{
		$div.appendTo(document.body);
	}
	obj = null;
	loopDoc = window.parent;
	layer = 0;//控制最多嵌套两层iframe
	while(loopDoc!=window && loopDoc.mainPageFlag!="main" && layer<3 )
	{
		loopDoc = loopDoc.parent;
		layer++;
	}
	if(loopDoc && loopDoc.mainPageFlag=="main")
	{
		loopDoc.$("#"+ option.id).remove();
		if(!loopDoc.document.getElementById(option.id))
			$div.appendTo(loopDoc.document.body);
		obj = loopDoc.$("#"+ option.id);
	}
	else
	{
		obj = $("#"+ option.id);
	}
	obj.html($div.html());
	if(option.style == "alert")
	{
		obj.find('.cAncel').remove();
	}
	obj.modal("show");
	obj.find('.cOnfirm').unbind('click').click(function(e){
		if(option.confirmCallback!=undefined && !option.confirmCallback(obj,e))
		{
			this.blur();
			return false;
		}
		loopDoc.$('.modal-backdrop').fadeOut();
	});
	if(option!=undefined && option.style !=" alert")
		obj.find('.cAncel').unbind('click').click(function(e){
			if(option.cancelCallback!=undefined && !option.cancelCallback(obj,e))
			{
				this.blur();
				return false;
			}
			loopDoc.$('.modal-backdrop').fadeOut();
		});
	
	return obj;
}

/**
 * 文本输入模式下，监控还可以输入多少个字符
 */
function changeEnterNum(e) {
	var pastedText = "";
	var tipId = e.data.tip;
	btnId = e.data.btnId;
	var textareaId = this.id;
	var clipboardDataObj;
	var maxl = e.data.maxl;//总长
	var selectText = "";
	if("paste" == e.type) {	
		if (window.clipboardData && window.clipboardData.getData) { // IE
			//pastedText = window.clipboardData.getData('Text');
			clipboardDataObj = window.clipboardData;
		} else {
			//pastedText = e.originalEvent.clipboardData.getData('Text');//e.clipboardData.getData('text/plain');
			clipboardDataObj = e.originalEvent.clipboardData;
		}
		pastedText = clipboardDataObj.getData("Text");
	}
	
	if(document.selection) {
		selectText = document.selection.createRange().text;	
	} else if (this.selectionStart||this.selectionStart == "0"){
		var start = this.selectionStart;
		var end = this.selectionEnd;
		
		if(start!=end) {
			selectText = this.value.substr(start,end);	
		}
	}
	
	var s = $("#" + textareaId).val().length + pastedText.length;
	
	var num = maxl - s + selectText.length;
	if(num < 0) {
		$("#" + tipId).css("color","red");
		$("#" + btnId).attr('disabled',true);
	} else {
		$("#" + tipId).css("color","black");
		$("#" + btnId).removeAttr("disabled");
	}
	$("#" + tipId).text("还可以输入"+ num + "字");
}
/**
 * op里调用TransformDataForEncode后，前台获取原本字符串的解码函数
 */
function decodeForOPFormat(str) {
	var result = $('<div>').html(str).html();
	result= result.replace(/&lt;/g,"<");
	result= result.replace(/&gt;/g,">")
	return result;
}
/**
 * IE支持HTML5的placeholder属性
 */
function SupportPlaceHolder(){
	if(!BrowerPlaceholderSupport()){//浏览器是否支持placeholder
		$('[placeholder]').focus(
									function(){
										var input=$(this);
										if(input.val()==input.attr('placeholder')){
											input.val('');
											input.removeClass('placeholder');
										}
									}	
								).blur(
									function(){
											var input=$(this);
											if(input.val()==''||input.val()==input.attr('placeholder')){
												input.addClass('placeholder');
												input.val(input.attr('placeholder'));
											}
									}
								).blur();
		
	};
};
function BrowerPlaceholderSupport(){//浏览器是否支持placeholder
	return 'placeholder' in document.createElement('input');
}

/**
 * 求两个时间的天数差 日期格式为 YYYY-MM-dd,格式不对返回null
 * added by kfzx-wangsh   
 */ 
function daysBetween(DateOne,DateTwo)  
{
	if(DateOne.length!=10||DateTwo.length!=10||DateOne.split('-').length!=3 || DateOne.split('-').length!=3 ){
		return null;
	}
	var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));  
	var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);  
	var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));  

	var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));  
	var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);  
	var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));  
	var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);   
	return Math.abs(cha);  
}
/**
 * 校验两个时间是否跨年 日期格式为 YYYY-MM-dd,格式不对返回null
 * added by kfzx-wangsh   
 */ 
function isTwoDateInOneYear(DateOne,DateTwo){
	if(DateOne.length!=10||DateTwo.length!=10||DateOne.split('-').length!=3 || DateOne.split('-').length!=3 ){
		return null;
	}
	var OneYear = DateOne.substring(0,DateOne.indexOf ('-')); 
	var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));  
	return OneYear==TwoYear
}


/**
 * string转化为日期，ie不支持new Date(string) 
 * 输入参数："2015-10-13" 或者"2015-10-13 15:30:30"
 * added by kfzx-wangsh   
 */ 
function NewDate(str){
	var str0 = str.split(' ');
	var str_ymd = str0[0].split('-');
	var date =new Date();
	if(str_ymd.length!=3){
		return "";
	}
	date.setFullYear(str_ymd[0],str_ymd[1]-1,str_ymd[2]);
	if(str0.length ==2){
		 var str_hms = str0[1].split(':');
		 //最后一个参数是毫米，暂时没用
		 date.setHours(str_hms[0],str_hms[1],str_hms[2],0);
	}
	
	return date;	
}


/**
 * 判断浏览器类型
 * by kfzx-caiyue
 */
var Sys = {};
(function() {
	var ua = navigator.userAgent.toLowerCase();
	var s;
	(window.ActiveXObject || "ActiveXObject" in window) ? Sys.ie = true:
	 (s = ua.match(/edge\/([\d.]+)/)) ? Sys.ie = s[1] :
		(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
			(s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
				(s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
					(s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
						(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : (Sys.ie = true);
})();


/**
 *  根据客户类型的数据词典翻译信息 
 */
function translateCustomerType(typeCodeString) {
	var displayedType = "";
	switch (typeCodeString) {
	case "1":
		displayedType = "实名客户";
		break;
	case "2":
		displayedType = "非实名客户";
		break;
	case "3":
		displayedType = "客户经理";
		break;
		
	default:
		break;
	}
	return displayedType;
}

/**
 *  根据星级的数据词典翻译信息 
 */
function translateStarLevel(starLevel) {
	var resultString = "全部";
	if(starLevel != undefined) {
		if(starLevel != "") {
			if(starLevel == "0") {
				resultString = "准星级";
			}else if(starLevel == "3"){
				resultString = "三星级";
			} else if(starLevel == "4"){
				resultString = "四星级";
			} else if(starLevel == "5"){
				resultString = "五星级";
			} else if(starLevel == "6"){
				resultString = "六星级";
			} else if(starLevel == "7"){
				resultString = "七星级";
			}else if(starLevel == "99"){
				resultString = "零星级";
			}else{
				
			}
		}
	}
	return resultString;
}

/**
 *  根据审查状态的数据词典翻译信息 
 */
function translateReviewStatus(status) {
	var resultString = "未知";
	if(status != undefined) {
		if(status != "") {
			if(status == "0") {
				resultString = "未审查";
			} else if(status == "1"){
				resultString = "审查通过";
			} else if(status == "2"){
				resultString = "审查未通过";
			} 
		}
	}
	return resultString;
}
/**
 *  翻译状态字段
 */
function translateStatus(status) {
	var resultString = "未知";
	if(status != undefined) {
		if(status != "") {
			if(status == "1") {
				resultString = "是";
			} else if(status == "0"){
				resultString = "否";
			} 
		}
	}
	return resultString;
}
/**
 *  翻译状态字段
 */
function translateReverseStatus(status) {
	var resultString = "未知";
	if(status != undefined) {
		if(status != "") {
			if(status == "1") {
				resultString = "否";
			} else if(status == "0"){
				resultString = "是";
			} 
		}
	}
	return resultString;
}
/**
 *  翻译帖子类型/来源
 */
function translateArticleType(type){
	var resultString = "未知";
	if(type != undefined) {
		if(type != "") {
			if(type == "1") {
				resultString = "手机";
			} else if(type == "2"){
				resultString = "电脑";
			} 
		}
	}
	return resultString;
}
/**
 *  字符串过滤特殊字符，针对不可见特殊字符
 */
function filterSpecialStr(str){
	var temp = "";
	var temp1 = "";
	var r = "";
	for(i=0;i<str.length;i++){
		temp=str.charAt(i);
		temp1=str.charCodeAt(i).toString(16);
		if(temp1.length==1){
			continue;
		}
		r+=temp;
	}
	return r;
}
/**
 *  获取字符串的字节长度
 */
function strByteLen(str){
	var byteLen=0,len=str.length;
	if(str){
		for(var i=0;i<len;i++){
			if(str.charCodeAt(i)>255){
				byteLen+=2;
			}else{
				byteLen++;
			}
		}
		return byteLen;
	}
}