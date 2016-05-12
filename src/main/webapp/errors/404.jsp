<%@ include file="/home/header.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<title>Erro 404</title>
<% response.setStatus(404); %>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-9 col-md-10 col-sm-push-3 col-md-push-2">
            <ol class="breadcrumb">
                <li><a href="/hs/home/home.jsp">Home</a></li>
                <li>404</li>
            </ol>
            <h1>Página não Encontrada</h1>

        </div><!-- /col -->
        <%@ include file="/home/right-side-menu.jsp"%>
    </div><!-- /row -->
</div><!-- /container -->
</body>
</html>