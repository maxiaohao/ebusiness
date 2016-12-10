var TT = TAOTAO = {
	checkLogin : function(){
		var _ticket = $.cookie("TT_TOKEN");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://sso.taotao.com/user/token/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var username = data.data.username;
					var html = username + "，欢迎您来到淘淘！<a href=\"javascript:checkLogout()\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

function checkLogout(){
	var _ticket = $.cookie("TT_TOKEN");
	if(!_ticket){
		return ;
	}
	$.ajax({
		url : "http://sso.taotao.com/user/logout/" + _ticket,
		dataType : "jsonp",
		type : "GET",
		success : function(data){
			if(data.status == 200){
				var html = "<a href=\"javascript:login()\">[登录]</a>&nbsp;<a href=\"javascript:regist()\">[免费注册]</a>";
				$("#loginbar").html(html);
			}
		}
	});
}

function checkLogoutTest(){
	var _ticket = $.cookie("TT_TOKEN");
	return location.href="http://sso.taotao.com/user/logout/"+_ticket;
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});