<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>年级管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/oa/userInfo/">年级管理</a></li>
    <shiro:hasPermission name="oa:userInfo:view">
        <li><a href="${ctx}/oa/userInfo/batchAdd">信息添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="userInfo" action="${ctx}/oa/userInfo/manageList" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>年级：</label>
            <form:input path="name" htmlEscape="false" maxlength="16" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th width="25"><input type="checkbox" name="ids" value=""></th>
        <th width="80">年级</th>
        <th width="100">人总数</th>
        <th width="140">卡总数</th>
        <th width="100">操作</th>
        <shiro:hasPermission name="oa:userInfo:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${grade}" var="grade">
        <tr>
            <td><input type="checkbox" value='${grade.id}'/></td>
            <td>${grade.grade}</td>
            <td>${grade.peopleCount}</td>
            <td>${grade.cardCount}</td>
            <shiro:hasPermission name="oa:userInfo:view">
                <td>
                    <a href="${ctx}/oa/userInfo/delete?grade=${grade.grade}"
                       onclick="return confirmx('确认要删除该单表吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>