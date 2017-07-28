<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>照片修改申请记录管理</title>
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

        function egis(id){
			$.ajax({
			    url:"${ctx}/oa/photoAudit/egis",
				type:'POST',
				data:{'id':id},
				success:function(){alert('审核通过！');},
				error:function(){alert('审核出错')}
			});
		}

		function unEgis(id){
            $.ajax({
                url:"${ctx}/oa/photoAudit/unEgis",
                type:'POST',
                data:{'id':id},
                success:function(){alert('审核未通过！');},
                error:function(){alert('审核出错!')}
            });
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/oa/photoAudit/">照片审核</a></li>
		<shiro:hasPermission name="oa:photoAudit:edit"><li><a href="${ctx}/oa/photoAudit/form">照片审核</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="photoAudit" action="${ctx}/oa/photoAudit/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input name="auditstats" type="hidden" value="${page.list.size()==0?0:page.list.get(0).auditstats}"
		<ul class="ul-form">
			<li><label>校园卡号：</label>
				<form:input path="xykid" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="idcard" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>

			<tr>
				<th width="90">校园卡号</th>
				<th width="140">身份证号</th>
				<th width="100">新照片</th>
				<th width="100">原照片</th>
				<shiro:hasPermission name="oa:photoAudit:view"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="photoAudit">
			<tr>
				<td>${photoAudit.xykid}</td>
				<td>${photoAudit.idcard}</td>
				<td>${photoAudit.photoOld}</td>
				<td>${photoAudit.photoNew}</td>

				<shiro:hasPermission name="oa:photoAudit:view"><td>
					<c:if test="${photoAudit.auditstats==0}">
    				<a href="javaScript:void(0);" onclick="egis(${photoAudit.id});">通过</a>
					<a href="${ctx}/oa/photoAudit/delete?id=${photoAudit.id}" onclick="unEgis('${photoAudit.id}');">不通过</a>
					</c:if>
					<c:if test="${photoAudit.auditstats==1}">
						已通过
					</c:if>
					<c:if test="${photoAudit.auditstats==2}">
						未通过
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>