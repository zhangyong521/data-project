<%@page pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>查询</title>
    <link href="<%=path%>/bootstrap/css/bootstrap.css" rel="stylesheet">
</head>
<body>
<form>
    <div class="form-group">
        <label for="tel">电话号码</label>
        <input type="text" class="form-control" id="tel" placeholder="请输入电话号码">
    </div>
    <div class="form-group">
        <label for="calltime">查询时间</label>
        <input type="text" class="form-control" id="calltime" placeholder="请输入查询时间">
    </div>
    <button type="button" class="btn btn-default" onclick="queryData()">查询</button>
</form>

<script src="<%=path%>/jquery/jquery-2.1.1.min.js"></script>
<script src="<%=path%>/bootstrap/js/bootstrap.js"></script>
<script>
    // 声明方法
    function queryData() {
        window.location.href = "/view?tel=" + $("#tel").val() + "&calltime=" + $("#calltime").val();
    }
</script>
</body>
</html>