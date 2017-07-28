<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
    <title>单表管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#batchImport").click(function () {
                $.jBox($("#importBox").html(), {
                    title: "导入数据",
                    buttons: {
                        "关闭": true
                    },
                    bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"
                });
            });

            $("#batchExport").click(function () {
                window.location.href="${ctx}/oa/userInfo/exportTemplate?flag=1&type=1";
                window.history(-1);
            });


        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
    <script type="text/javascript">

        //将一条信息移入垃圾箱
        function hypocrisyDelete(obj, id) {
            var result = confirm('确认要删除吗');
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: '/a/oa/userInfo/hypocrisyDelete',
                    dataType: 'text',
                    data: {
                        'id': id
                    },
                    success: function (data) {
                        $(obj).parents("tr").remove();
                    },
                    error: function (data) {
                        alert("删除过程出现错误，请稍后再试");
                    },
                });
            } else {
                alert("删除取消");
            }
        }

        //将信息批量移入垃圾箱
        function batchHypocrisyDelete() {
            var result = confirm('确认要删除吗');
            if (result) {
                var ids = "";
                $('input:checkbox[name=ids]:checked').each(function (i) {
                    if (0 == i) {
                        ids = $(this).val();
                    } else {
                        ids += ("," + $(this).val());
                    }
                    // $(this).parents("tr").remove();
                });

                window.location.href = "/a/oa/userInfo/hypocrisyDelete?id=" + ids;
//                $.ajax({
//                    type: 'POST',
//                    url: '/a/oa/userInfo/hypocrisyDelete',
//                    dataType: 'text',
//                    async: false,
//                    cache:false,
//                    data: {
//                        'id': ids
//                    }
//                });
            } else {
                alert("删除取消");
            }
        }

        //反选和全选
        function changeStuts(obj) {
            if (!$("#checkbox-id").is(":checked")) {
                $("input[type='checkbox']").attr("checked", false);
            } else {
                $("input[type='checkbox']").attr("checked", 'checked');
            }
        }

        //批量导出
        function batchExport() {

        }

    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/oa/userInfo/">学生列表</a></li>
    <shiro:hasPermission name="oa:userInfo:view">
        <li><a href="${ctx}/oa/userInfo/form">信息添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="userInfo" action="${ctx}/oa/userInfo/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input name="flag" type="hidden" value="1"/>
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
<input class="btn btn-primary" type="submit" value="批量删除" onclick="batchHypocrisyDelete();"/>
<input class="btn btn-primary" type="submit" value="批量导入" id="batchImport"/>
<input class="btn btn-primary" type="submit" value="全部导出" id="batchExport"/>
<!-- 导入信息-->
<div id="importBox" class="hide">
    <form id="importForm" action="${ctx}/oa/userInfo/importEXCEL" method="post" enctype="multipart/form-data"
          style="padding-left:20px;text-align:center;" class="form-search"
          onsubmit="loading('正在导入，请稍等...');">
        <br/>
        <input id="uploadFile" name="file" type="file" style="width:330px"/><br/> <br/>
        <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="  导    入   "/>
        <a href="${ctx}/oa/userInfo/exportTemplate?flag=1&type=2">下载模板</a>
    </form>
</div>
<br/><br/>
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
                    <a href="javascript:void(0);" onclick="hypocrisyDelete(this,'${user.id}')">删除</a>
                    <a href="${ctx}/oa/userInfo/form?id=${userInfo.id}">修改</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>

</html>