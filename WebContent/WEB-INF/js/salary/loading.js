/**
 * html Loading...
 * @return {}
 * @auth  goolser
 * @date  2015-1-5 
 */
	var Loading = {
		handler:null, //setInterval执行函数的句柄
		text:'Loading...', //Loading默认显示的文字
		//获取滚动条距离上边顶部的距离
	    getScrollTop:function(){   
		    var scrollTop=0;   
		    if(document.documentElement&&document.documentElement.scrollTop){   
		        scrollTop=document.documentElement.scrollTop;   
		    }else if(document.body){   
		        scrollTop=document.body.scrollTop;   
		    }   
		    return scrollTop;   
		},
		 //获取内部内容的总高度,
		 getScrollHeight:function(){   
		    return Math.max(document.body.scrollHeight,document.documentElement.scrollHeight);   
		},
		//是获取可见内容的高度
		getHeight: function(){
			if(window.innerHeight!= undefined){
				return window.innerHeight;
			}else{
				var B= document.body, D= document.documentElement;
				return Math.min(D.clientHeight, B.clientHeight) 
				} 
			},
		//显示阴影
		showShadow:function(){
			var maskHeight = this.getScrollHeight()+"px";
			var shadowDiv =  document.createElement("div");
			shadowDiv.innerHTML = "";
			shadowDiv.setAttribute('id','shadowDiv_MASK');
			shadowDiv.setAttribute('style','position:fixed; position: absolute; z-index: 999;left:0;top:0;display:block;width:100%;height:'+maskHeight+'; opacity:0.6;filter: alpha(opacity=60);-moz-opacity: 0.6; background:#000;');
			var body = document.getElementsByTagName("body")[0];
			body.appendChild(shadowDiv);
		},
		//关闭阴影
		hideShadow:function(){
			var body = document.getElementsByTagName("body")[0];
			var shadowDiv_MASK = document.getElementById('shadowDiv_MASK');
			if(body && shadowDiv_MASK){
				body.removeChild(shadowDiv_MASK);
			}
		},
		//显示Loading
		show:function(txt){
			var top = this.getScrollTop()+(this.getHeight()/2)+"px";
			Loading.showShadow();
			var me = this;
			if(txt){
				me.text = txt;
			}
			var loadingDiv =  document.createElement("div");
			loadingDiv.innerHTML = me.text;
			loadingDiv.setAttribute('id','loadingDiv');
			loadingDiv.setAttribute('style','padding:5px;top:'+top+';left:50%;margin:-9px 0 0 -75px;z-index: 9999;text-indent:20px; background:  url("img/loading.gif")  no-repeat #fff 5px 50%;border:1px solid #0099cc;color:#0099cc;font-size:12px;width:150px;line-height:17px; height:18px;position:absolute;');
			var body = document.getElementsByTagName("body")[0];
			body.appendChild(loadingDiv);
			var flag = true;
			handler=setInterval(function(){
				if(flag){
					loadingDiv.innerHTML = "";
					flag = false;
				}else{
					loadingDiv.innerHTML = me.text;
					flag = true;
				}
			},300);
			return handler;
		},
		//关闭Loading
		hide:function(){
			clearInterval(handler);
			var body = document.getElementsByTagName("body")[0];
			var loadingDiv = document.getElementById('loadingDiv');
			if(body && loadingDiv){
				body.removeChild(loadingDiv);
			}
			Loading.hideShadow();
		}
	}