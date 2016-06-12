
var aniShow = "pop-in";
 //只有ios支持的功能需要在Android平台隐藏；
if (mui.os.android) {
	var list = document.querySelectorAll('.ios-only');
	if (list) {
		for (var i = 0; i < list.length; i++) {
			list[i].style.display = 'none';
		}
	}
	//Android平台暂时使用slide-in-right动画
	if(parseFloat(mui.os.version)<4.4){
		aniShow = "slide-in-right";
	}
}
//跳转页面
			mui('.mui-content').on('tap', 'a', function() {
				var id = this.getAttribute('href');
				var href = this.href;
				var type = this.getAttribute("open-type");
			
				//不使用父子模板方案的页面
				if(type == "Jump"){
					window.DrivingSubjectThree.jumpActivity(this.href);
				}
				else if (type == "common") {
					
					var webview_style = {
						popGesture: "close"
					};
					//侧滑菜单需动态控制一下zindex值；
					if (~id.indexOf('offcanvas-')) {
						webview_style.zindex = 9998;
						webview_style.popGesture = ~id.indexOf('offcanvas-with-right') ? "close" : "none";
					}
					//图标界面需要启动硬件加速
					if(~id.indexOf('icons.html')){
						webview_style.hardwareAccelerated = true;
					}
					mui.openWindow({
						id: id,
						url: this.href,
						styles: webview_style,
						show: {
							aniShow: aniShow
						},
						waiting: {
							autoShow: false
						}
					});
				} else if (id && ~id.indexOf('.html')) {
					if (!mui.os.plus || (!~id.indexOf('popovers.html')&&mui.os.ios)) {
						mui.openWindow({
							id: id,
							url: this.href,
							styles: {
								popGesture: 'close'
							},
							 extras:{
							      //自定义扩展参数，可以用来处理页面间传值
							},

							show: {
								aniShow: aniShow
							},
							waiting: {
								autoShow: false
							}
						});
					} else {
						//TODO  by chb 当初这么设计，是为了一个App中有多个模板，目前仅有一个模板的情况下，实际上无需这么复杂
						//使用父子模板方案打开的页面
						//获得共用模板组
						var template = getTemplate('default');
						//判断是否显示右上角menu图标；
						var showMenu = ~href.indexOf('popovers.html') ? true : false;
						//获得共用父模板
						var headerWebview = template.header;
						//获得共用子webview
						var contentWebview = template.content;
						var title = this.innerText.trim();
						//通知模板修改标题，并显示隐藏右上角图标；
						mui.fire(headerWebview, 'updateHeader', {
							title: title,
							showMenu: showMenu,
							target:href,
							aniShow: aniShow
						});
						
						if(mui.os.ios||(mui.os.android&&parseFloat(mui.os.version)<4.4)){
							var reload = true;
							if (!template.loaded) {
								if (contentWebview.getURL() != this.href) {
									contentWebview.loadURL(this.href);
								} else {
									reload = false;
								}
							} else {
								reload = false;
							}
							(!reload) && contentWebview.show();
							
							headerWebview.show(aniShow, 150);
						}
					}
				}
			});
/**
			 * 下拉刷新具体业务实现
			 */
			function pulldownRefresh(obj) {
				setTimeout(function() {
					var table = document.body.querySelector('.mui-table-view');
					var cells = document.body.querySelectorAll('.mui-table-view-cell');
					//循环添加元素 len后面取
					for (var i = cells.length, len = i + 3; i < len; i++) {
						var li = document.createElement('li');
						li.className = 'mui-table-view-cell';
						li.innerHTML = obj;
						//下拉刷新，新纪录插到最前面；
						table.insertBefore(li, table.firstChild);
					}
					mui('#pullrefresh').pullRefresh().endPulldownToRefresh(); //refresh completed
				}, 1500);
			}			
			var count = 0;
			/**
			 * 上拉加载具体业务实现
			 */
			function pullupRefresh(obj) {
				setTimeout(function() {
					mui('#pullrefresh').pullRefresh().endPullupToRefresh((++count > 2)); //参数为true代表没有更多数据了。
					var table = document.body.querySelector('.mui-table-view');
					var cells = document.body.querySelectorAll('.mui-table-view-cell');
					//循环添加元素 len后面取
					for (var i = cells.length, len = i + 5; i < len; i++) {
						var li = document.createElement('li');
						li.className = 'mui-table-view-cell';
						li.innerHTML = obj;
						table.appendChild(li);
					}
				}, 1500);
			}
	/*
	 * 更多按钮
	 */
		function moreRefresh(obj){
				
				var table=document.getElementById("morefresh");
				var cells=table.getElementsByClassName("mui-table-view-cell");
				for (var i = cells.length, len = i + 5; i < len; i++) {
					var li = document.createElement('li');
						li.className = 'mui-table-view-cell mui-media';
						li.innerHTML = obj;
						table.appendChild(li);
				}
			}
		
//		//将图片转换未二进制流并压缩
//		function uploadPics(url,file){//将图片转化为二进制流
//			var img=new Image();
//			img.src=url;
//			img.onload=function(){
//				var that=img;
//				var w=that.width;
//				var h=that.height;
//				var scale=w/h;
//				w=320||w;
//				h=w/scale;
//				var canvas=document.createElement('canvas');
//				var ctx=canvas.getContext("2d");
//				canvas.width=w;
//				canvas.height=h;
//				
//				ctx.drawImage(that,0,0,w,h);
//				var base64=canvas.toDataURL("image/png",1||0.8);
////				alert(base64)
//				file.value=base64;
//			}
//			
//		}
