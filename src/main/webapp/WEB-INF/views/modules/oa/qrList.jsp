<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>二维码管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

        function zipAndDownload(id){

            var f = document.createElement("form");
            document.body.appendChild(f);
            var i = document.createElement("input");
            i.type = "hidden";
            f.appendChild(i);
            i.value = id;
            i.name = "id";
            f.action = '${ctx}/oa/qr/zipAndDownload';
            f.submit();

            $("#downloadStatus").html("已下载");

		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/oa/qr/">二维码列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="qr" action="${ctx}/oa/qr/creatQr" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="time"/>
		<ul class="ul-form">
			<li><label>开始编号</label>
				<form:input path="startid" htmlEscape="false" maxlength="8" class="input-medium" /></li>
			<li><label>结束编号</label>
				<form:input path="endid" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="开始生成"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>生成标识</th>
				<th>开始编号</th>
				<th>结束编号</th>
				<th>生成时间</th>
				<th>是否下载</th>
				<shiro:hasPermission name="oa:qr:view"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qr">
			<tr>
				<td><a href="${ctx}/oa/qr/form?id=${qr.id}">
					${qr.name}
				</a></td>
				<td>
					${qr.startid}
				</td>
				<td>
					${qr.endid}
				</td>
				<td>
					<fmt:formatDate value="${qr.time}" pattern="yyyy-MM-dd hh:mm"/>

				</td>
				<td id="downloadStatus">
					<c:if test="${qr.filename == null}">
						<font color="red">未下载</font>
					</c:if>
					<c:if test="${qr.filename != null}">
						已下载
					</c:if>
				</td>
				<shiro:hasPermission name="oa:qr:edit"><td>
					<a href="javaScript:void(0);" onclick="zipAndDownload('${qr.id}')">下载</a>
					<a href="${ctx}/oa/qr/delete?id=${qr.id}" >删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>