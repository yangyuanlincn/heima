<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>

		<!-- 动态包含 -->
	<jsp:include page="/jsp/head.jsp"></jsp:include>

		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>我的订单</strong>
					<table class="table table-bordered">
						<c:forEach items="${bean.list }" var="o">
						<tbody>
							<tr class="success">
								<th colspan="5">订单编号:${o.oid } </th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>
							<c:forEach items="${o.items }" var="i">
							<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="id" value="22">
									<img src="${pageContext.request.contextPath}/${i.product.pimage}" width="70" height="60">
								</td>
								<td width="30%">
									<a target="_blank"> ${i.product.pname }</a>
								</td>
								<td width="20%">
									￥${i.product.shop_price }
								</td>
								<td width="10%">
									${i.count }
								</td>
								<td width="15%">
									<span class="subtotal">￥${i.subtotal }</span>
								</td>
							</tr>
							</c:forEach>
							<tr class="success">
								<th colspan="5">订单总额:${o.total } </th>
							</tr>
						</tbody>
						</c:forEach>
						
					</table>
				</div>
			</div>
			<div style="text-align: center;">
				<ul class="pagination">
					<c:if test="${bean.currPage==1 }">
					<li class="disabled"><a href="${pageContext.request.contextPath }/order?method=findAll&currPage=${bean.currPage-1 }" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
					</c:if>
					<c:if test="${bean.currPage!=1 }">
					<li ><a href="${pageContext.request.contextPath }/order?method=findAll&currPage=${bean.currPage-1 }" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
					</c:if>
					<c:forEach begin="1" end="${bean.totalPage }" var="n">
					<c:if test="${bean.currPage==n }">
						<li class="active"><a href="${pageContext.request.contextPath }/order?method=findAll&currPage=${n}ss">${n }</a></li>
					</c:if>
					<c:if test="${bean.currPage!=n }">
						<li ><a href="${pageContext.request.contextPath }/order?method=findAll&currPage=${n}">${n }</a></li>
					</c:if>
					</c:forEach>
					<c:if test="${bean.currPage==bean.totalPage }">
					<li class="disabled">
						<a href="${pageContext.request.contextPath }/order?method=findAll&currPage=${bean.currPage+1 }" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
					</c:if>
					<c:if test="${bean.currPage!=bean.totalPage }">
					<li>
						<a href="${pageContext.request.contextPath }/order?method=findAll&currPage=${bean.currPage+1 }" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
					</c:if>
				</ul>
			</div>
		</div>

		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2016 传智商城 版权所有
		</div>
	</body>

</html>