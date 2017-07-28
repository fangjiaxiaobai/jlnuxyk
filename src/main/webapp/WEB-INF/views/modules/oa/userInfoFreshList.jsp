<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
    <title>垃圾箱</title>
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
    <script type="text/javascript">


        function restore(obj, id) {
            var result = confirm('确认要还原吗');
            if (result) {
                window.location.href="/a/oa/userInfo/restore?id="+id;
            } else {
                alert("还原取消");
            }
        }


        function batchRestore() {
            var result = confirm('确认要还原吗');
            if (result) {
                var ids = "";
                $('input:checkbox[name=ids]:checked').each(function (i) {
                    if (0 == i) {
                        ids = $(this).val();
                    } else {
                        ids += ("," + $(this).val());
                    }
                    $(this).parents("tr").remove();
                });
                window.location.href="/a/oa/userInfo/restore?id="+ids;
            } else {
                alert("还原取消");
            }
        }

        function batchDelete() {
            var result = confirm('确认要删除吗');
            if (result) {
                var ids = "";
                $('input:checkbox[name=ids]:checked').each(function (i) {
                    if (0 == i) {
                        ids = $(this).val();
                    } else {
                        ids += ("," + $(this).val());
                    }
                });
                window.location.href="/a/oa/userInfo/delete?id="+ids;
            } else {
                alert("删除取消");
            }
        }

        function changeStuts(obj) {
            if (!$("#checkbox-id").is(":checked")) {
                $("input[type='checkbox']").attr("checked", false);
            } else {
                $("input[type='checkbox']").attr("checked", 'checked');
            }
        }

    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/oa/userInfo/">垃圾箱信息列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="userInfo" action="${ctx}/oa/userInfo/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="orderBy" name="orderBy" type="hidden" value="xykid"/>
    <input name="flag" type="hidden" value="0"/>
    <ul class="ul-form">
        <li><label>姓名：</label>
            <form:input path="name" htmlEscape="false" maxlength="16" class="input-medium"/>
        </li>
        <li><label>学号：</label>
            <form:input path="uid" htmlEscape="false" maxlength="16" class="input-medium"/>
        </li>
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
<input class="btn btn-primary" type="submit" value="批量还原" onclick="batchRestore();"/>
<input class="btn btn-primary" type="submit" value="批量删除" onclick="batchDelete();"/><br>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th width="25"><input id="checkbox-id" type="checkbox" name="" value="" onclick="changeStuts(this)"></th>
        <th width="100">学号</th>
        <th width="100">学生姓名</th>
        <th width="140">身份证号</th>
        <th width="90">校园卡号</th>
        <th width="40">年级</th>
        <th width="100">手机号</th>
        <th width="80">办卡次数</th>
        <th width="80">办卡时间</th>
        <th width="80">使用状态</th>
        <th width="100">操作</th>
        <shiro:hasPermission name="oa:userInfo:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="user">
        <tr>
            <td><input type="checkbox" value="${user.id}" name="ids"></td>
            <td>${user.uid}</td>
            <td>
                <a href="${ctx}/oa/userInfo/form?id=${user.id}">${user.name}</a>
            </td>
            <td>${user.idcard} </td>
            <td>${user.xykid} </td>
            <td>${user.grade}</td>
            <td>${user.phonenumber} </td>
            <td>${user.timesCard}</td>
            <td><fmt:formatDate value='${user.timeHavecard}' pattern='yyyy-MM-dd'/></td>
            <td>
                <c:if test="${user.flag==1}">使用中</c:if>
                <c:if test="${user.flag==0}">已废弃</c:if>
            </td>
            <shiro:hasPermission name="oa:userInfo:view">
                <td>
                    <c:if test="${user.flag==0}">
                        <a href="javascript:void(0);" onclick="restore(this,'${user.id}')">还原</a>
                        <a href="${ctx}/oa/userInfo/delete?id=${userInfo.id}"
                           onclick="return confirmx('确认要删除该数据吗？', this.href)">删除</a>
                    </c:if>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>

</html>