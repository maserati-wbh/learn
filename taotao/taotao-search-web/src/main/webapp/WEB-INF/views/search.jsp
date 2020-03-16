<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Cache-Control" content="max-age=300" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${query}-商品搜索 -淘淘</title>
	<meta name="Keywords" content="java,淘淘java" />
	<link rel="stylesheet" type="text/css" href="/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="/css/psearch20131008.css"/>
	<link rel="stylesheet" type="text/css" href="/css/psearch.onebox.css"/>
	<link rel="stylesheet" type="text/css" href="/css/pop_compare.css"/>
	<link rel="stylesheet" type="text/css" href="/css/taotao.css" media="all" />
</head>
<body>
	<!-- header start -->
	<jsp:include page="../commons/header.jsp" />
	<!-- header end -->
	<div class="w main">
		<div class="crumb">
			全部结果&nbsp;&gt;&nbsp;<strong>"${query}"</strong>
		</div>
		<div class="clr"></div>
		<div class="m clearfix" id="bottom_pager">
			<div id="pagin-btm" class="pagin fr"
				clstag="search|keycount|search|pre-page2"></div>
		</div>
		<div class="m psearch " id="plist">
			<ul class="list-h clearfix" tpl="2">
				<c:forEach items="${itemList}" var="item">
					<li class="item-book" bookid="11078102">
						<div class="p-img">
							<a target="_blank"
								href="http://item.taotao.com/${item.id}.html"> <img
								width="160" height="160" data-img="1"
								data-lazyload="${item.images[0]}" />
							</a>
						</div>
						<div class="p-name">
							<a target="_blank"
								href="http://item.taotao.com/${item.id}.html">
								${item.title} </a>
						</div>
						<div class="p-price">
							<i>淘淘价：</i> <strong>￥<fmt:formatNumber
									groupingUsed="false" maxFractionDigits="2"
									minFractionDigits="2" value="${item.price / 100}" /></strong>
						</div>
						<div class="service">由 淘淘 发货</div>
						<div class="extra">
							<span class="star"><span class="star-white"><span
									class="star-yellow h5">&nbsp;</span></span></span>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<!-- footer start -->
	<jsp:include page="../commons/footer.jsp" />
	<!-- footer end -->
	<script type="text/javascript" src="/js/lib-v1.js" charset="utf-8"></script>
	<script type="text/javascript" src="/js/jquery.hashchange.js"></script>
	<script type="text/javascript" src="/js/search_main.js"></script>
	
	<script type="text/javascript">
		SEARCH.query = "${query}";
		SEARCH.bottom_page_html("${page}","${totalPage}");
		
		(function(){
			var A="<strong>热门搜索：</strong><a href='http://sale.jd.com/act/OfHQzJ2GLoYlmTIu.html' target='_blank' style='color:#ff0000' clstag='homepage|keycount|home2013|03b1'>校园之星</a><a href='http://sale.jd.com/act/aEBHqLFMfVzDZUvu.html' target='_blank'>办公打印</a><a href='http://www.jd.com/pinpai/878-12516.html' target='_blank'>美菱冰箱</a><a href='http://sale.jd.com/act/nuzKb6ZiYL.html' target='_blank'>无肉不欢</a><a href='http://sale.jd.com/act/ESvhtcAJNbaj.html' target='_blank'>万件好货</a><a href='http://sale.jd.com/act/nAqiWgU34frQolt.html' target='_blank'>iPhone6</a><a href='http://sale.jd.com/act/p0CmUlEFPHLX.html' target='_blank'>哈利波特</a><a href='http://sale.jd.com/act/FstSdb2vCOLa8BRi.html' target='_blank'>美模接驾</a>";
			var B=["锤子","apple","小米","天梭","红米","华为"];
			B=pageConfig.FN_GetRandomData(B);
			$("#hotwords").html(A);
			var _searchValue = "${query}";
			if(_searchValue.length == 0){
				_searchValue = B;
			}
			$("#key").val(_searchValue).bind("focus",function(){if (this.value==B){this.value="";this.style.color="#333"}}).bind("blur",function(){if (!this.value){this.value=B;this.style.color="#999"}});
			})();
	</script>
</body>
</html>