<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>登录淘淘</title>
    <link type="text/css" rel="stylesheet" href="/css/login.css"/>
    <script type="text/javascript" src="/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="/js/login/g.base.js"></script>
   	<script type="text/javascript" src="/js/login/jdEdit.js"></script>
    <script type="text/javascript">
        $(function(){
            if(pgeditor.checkInstall()){
                $("#chkOpenCtrl").attr("checked",true);
                $("#nloginpwd").hide();
                $("#sloginpwd").show();
                if(pgeditor.checkUpdate()==1){
                    $("#updata").show();
                }
            }
        });
    </script>
</head>
<body>
<div class="w">
    <div id="logo">
    	<a href="http://www.taotao.com/" clstag="passport|keycount|login|01">
    		<img src="/images/taotao-logo.gif" alt="淘淘" width="170" height="60"/>
    	</a><b></b>
   	</div>
</div>
<form id="formlogin" method="post" onsubmit="return false;">
    <input type="hidden" id="uuid" name="uuid" value="1359c13d-7daa-4a2a-972d-f09e09e6605a"/>
    <div class=" w1" id="entry">
        <div class="mc " id="bgDiv">
            <div id="entry-bg" clstag="passport|keycount|login|02" style="width: 511px; height: 455px; position: absolute; left: -44px; top: -44px; background: url(/images/544a11d3Na5a3d566.png) 0px 0px no-repeat;">
			</div>
            <div class="form ">
                <div class="item fore1">
                    <span>用户名/已验证手机</span>
                    <div class="item-ifo">
                        <input type="text" id="loginname" name="username" class="text"  tabindex="1" autocomplete="off"/>
                        <div class="i-name ico"></div>
                        <label id="loginname_succeed" class="blank invisible"></label>
                        <label id="loginname_error" class="hide"><b></b></label>
                    </div>
                </div>
                <script type="text/javascript">
                    setTimeout(function () {
                        if (!$("#loginname").val()) {
                            $("#loginname").get(0).focus();
                        }
                    }, 0);
                </script>
                <div id="capslock"><i></i><s></s>键盘大写锁定已打开，请注意大小写</div>
                <div class="item fore2">
                    <span>密码</span>
                    <div class="item-ifo">
                        <label id="sloginpwd" style="display: none;">
                            <script type="text/javascript">pgeditor.generate()</script>
                        </label>
                        <input type="password" id="nloginpwd" name="password" class="text" tabindex="2" autocomplete="off"/>
                        <input type="hidden" name="loginpwd" id="loginpwd" value="" class="hide" />

                        <div class="i-pass ico"></div>
                        <label id="loginpwd_succeed" class="blank invisible"></label>
                        <label id="loginpwd_error" class="hide"></label>
                        <script type="text/javascript">
							$('#nloginpwd')[0].onkeypress = function(event){
								var e = event||window.event,
								$tip = $('#capslock'),
								kc  =  e.keyCode||e.which, // 按键的keyCode
								isShift  =  e.shiftKey ||(kc  ==   16 ) || false ; // shift键是否按住
								if (((kc >=65&&kc<=90)&&!isShift)|| ((kc >=97&&kc<=122)&&isShift)){
									$tip.show();
								}else{
									$tip.hide();
								}
							};
                        </script>
                    </div>
                </div>
                <input type="hidden" name="machineNet" id="machineNet" value="" class="hide"/>
                
                <div class="item fore3  hide " id="o-authcode">
                    <span>验证码</span>

                    <div class="item-ifo">
                        <input type="text" id="authcode" class="text text-1" name="authcode" tabindex="6" style="ime-mode:disabled"/>
                        <label class="img">
                            <img style="cursor:pointer;width:100px;height:33px;display:block;"
                                 src2="https://authcode.jd.com/verify/image?a=1&amp;acid=1359c13d-7daa-4a2a-972d-f09e09e6605a&amp;uid=1359c13d-7daa-4a2a-972d-f09e09e6605a"
                                                                 onclick="this.src= document.location.protocol +'//authcode.jd.com/verify/image?a=1&amp;acid=1359c13d-7daa-4a2a-972d-f09e09e6605a&amp;uid=1359c13d-7daa-4a2a-972d-f09e09e6605a&amp;yys='+new Date().getTime();$('#authcode').val('');"
                                 ver_colorofnoisepoint="#888888" id="JD_Verification1"/>
                        </label>
                        <label class="ftx23 hline">看不清？<br/>
                        	<a href="javascript:void(0)" class="flk13"
                                     onclick="jQuery('#JD_Verification1').click();">换一张</a></label>
                        <label id="authcode_succeed" class="blank invisible"></label>
                        <label id="authcode_error" class="hide"></label>
                    </div>
                </div>
                <div class="item fore4 hide" id="autoentry">
                    <div class="item-ifo">
                        <input type="checkbox" class="checkbox" name="chkRememberMe" clstag="passport|keycount|login|04"/>
                        <label class="mar">自动登录</label>
                                                <div style="float:left;" id="ctrlDiv">
                            <input type="checkbox" class="checkbox" id="chkOpenCtrl" name="chkOpenCtrl" onclick="javascript:inputSelect();"/>
                        </div>
                        <label><a href="http://safe.jd.com/findPwd/index.action" class="" clstag="passport|keycount|login|05">忘记密码?</a></label>
                        <div class="clr"></div>
                    </div>

                </div>
                <div class="item login-btn2013">
                    <input type="button" class="btn-img btn-entry" id="loginsubmit" value="登录" tabindex="8" clstag="passport|keycount|login|06"/>
                </div>
                
             	<div class="free-regist">
            		<span><a href="http://sso.taotao.com/register" clstag="passport|keycount|login|08">免费注册&gt;&gt;</a></span>
        		</div> 
            </div>
        </div>
    </div>
</form>
<div class="w">
	<!-- links start -->
    <jsp:include page="../commons/footer-links.jsp"></jsp:include>
    <!-- links end -->
</div>
<script type="text/javascript" src="/js/login/login.js"></script>
<script type="text/javascript" src="/js/login/jdThickBox.js"></script>
<script type="text/javascript" src="/js/login/checkClient.js"></script>
<script>
   $("#loginsubmit").click(function () {
	    var flag = validateFunction.FORM_validate();
	    if (flag) {
	        var uuid = $("#uuid").val();
	        $(this).attr({ "disabled": "disabled" });
	        var _username = $("#formlogin [name=username]").val();
	        var _password = $("#formlogin [name=password]").val();
	        $.ajax({
	            type: "post",
	            url: "/user/login",
	            data: {username:_username,password:_password},
	            dataType : "json",
	            error: function () {
	                $("#nloginpwd").attr({ "class": "text highlight2" });
	                $("#loginpwd_error").html("网络超时，请稍后再试").show().attr({ "class": "error" });
	                $("#loginsubmit").removeAttr("disabled");
	                $this.removeAttr("disabled");
	            },
	            success: function (obj) {
	                if (obj) {
	                    if (obj.status == 200) {
                    		//登录成功，跳转到首页
                    		obj.success = "http://www.taotao.com/";
                    		//当跳转的地址不为空时，跳转到该地址
                    		var redirectUrl = "${redirectURL}";
                    		if(redirectUrl){
                    			obj.success = redirectUrl;
                    		}
	                        var isIE = !-[1,];
	                        if (isIE) {
	                            var link = document.createElement("a");
	                            link.href = obj.success;
	                            link.style.display = 'none';
	                            document.body.appendChild(link);
	                            link.click();
	                        } else {
	                            window.location = obj.success;
	                        }
	                    }else{
	                    	$("#loginsubmit").removeAttr("disabled");
	                    	verc();
	                      	$("#nloginpwd").attr({ "class": "text highlight2" });
	                     	 $("#loginpwd_error").html("账号或密码错误!").show().attr({ "class": "error" });
	                    }
	                }
	            }
	        });
	    }
	});
</script>
</body>
</html>