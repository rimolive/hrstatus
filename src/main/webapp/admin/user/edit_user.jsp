<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/home/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<script type="text/javascript">
    window.onload = function () {
        document.getElementById("password").onchange = validatePassword;
        document.getElementById("verifyPassword").onchange = validatePassword;
    }
    function validatePassword() {
        var pass2 = document.getElementById("verifyPassword").value;
        var pass1 = document.getElementById("password").value;
        if (pass1 != pass2)
            document.getElementById("verifyPassword").setCustomValidity("As senhas digitadas não são iguais");
        else
            document.getElementById("verifyPassword").setCustomValidity('');
    }
</script>
<c:if test="${error == 'true'}">
    <div class="toast-pf toast-pf-max-width toast-pf-top-right alert alert-danger alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
            <span class="pficon pficon-close"></span>
        </button>
        <span class="pficon pficon-error-circle-o"></span>
        Falha ao criar usuário, verificar logs, Mensagem de erro: ${message}
    </div>
</c:if>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-9 col-md-10 col-sm-push-3 col-md-push-2">
            <ol class="breadcrumb">
                <li><a href="/hs/home/home.jsp">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/rest/user/admin/list/form">
                    Gerenciar Usuários</a></li>
                <li>Editar Usuário</li>
            </ol>
            <h1>Editar Usuário</h1>
            <form method="POST" class="form-horizontal"
                  action="${pageContext.request.contextPath}/rest/user/admin/update">
                <div class="form-group">
                    <label class="col-md-2 control-label" for="name">Nome</label>
                    <div class="col-md-6">
                        <input name="nome" type="text" id="name"
                               value="${user.nome}"
                               class="form-control" required readonly
                               data-errormessage-value-missing="Campo Obrigatório">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label" for="username">Nome de Usuário</label>
                    <div class="col-md-6">
                        <input name="username" type="text" id="username"
                               value="${user.username}"
                               class="form-control" required readonly
                               data-errormessage-value-missing="Campo Obrigatório">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label" for="password">Senha</label>
                    <div class="col-md-6">
                        <input name="password" type="password" id="password"
                               value="${user.password}"
                               class="form-control" required
                               data-errormessage-value-missing="Campo Obrigatório"
                               data-errormessage="Senha não atinge os requisitos necessários: mínimo 8 caracteres sendo no mínimo 1 minúsculo, 1 maiúsculo e um caracter especial."
                               pattern="(?=^.{6,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label" for="verifyPassword">Repita Senha</label>
                    <div class="col-md-6">
                        <input name="verifyPassword" type="password" id="verifyPassword"
                               value="${user.password}"
                               class="form-control" required
                               data-errormessage-value-missing="Campo Obrigatório">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label" for="email">E-mail</label>
                    <div class="col-md-6">
                        <input name="email" type="email" id="email"
                               value="${user.mail}"
                               class="form-control" required
                               data-errormessage-type-mismatch="Email inválido."
                               data-errormessage-value-missing="Campo Obrigatório">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">Ativo</label>
                    <div class="col-md-6">
                        <div class="radio">
                            <label>
                                <input name="enabled" type="radio" name="optionsRadios" id="optionsRadios1" value="true"
                                <c:if test="${user.enabled}"> checked</c:if>>
                                Sim
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input name="enabled" type="radio" name="optionsRadios" id="optionsRadios2"
                                       value="false" <c:if test="${not user.enabled}"> checked</c:if>>
                                Não
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label" for="boostrapSelect">Roles</label>
                    <div class="col-md-10">
                        <select name="roles" class="selectpicker" multiple data-selected-text-format="count>3"
                                id="boostrapSelect"
                                required>
                            <c:forEach items="${user.roles}" var="role" varStatus="stat">
                                <c:set var="myVar" value="${stat.first ? '' : myVar} ${role}" />
                            </c:forEach>
                            <option <c:if test="${myVar.contains('ADMIN')}" > selected </c:if> value="ADMIN">Administrador</option>
                            <option <c:if test="${myVar.contains('USER')}" > selected </c:if>value="USER">Usuário</option>
                            <option <c:if test="${myVar.contains('REST')}" > selected </c:if>value="REST">Permissão para Requisições Rest</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-10 col-md-offset-2">
                        <button type="submit" class="btn btn-primary">Save</button>
                        <button type="reset" class="btn btn-default">Cancel</button>
                    </div>
                </div>
            </form>
        </div><!-- /col -->
        <div class="col-sm-3 col-md-2 col-sm-pull-9 col-md-pull-10 sidebar-pf sidebar-pf-left">
            <div class="panel-group" id="accordion">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" class="collapsed">
                                Usuários
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse in">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li class="active"><a
                                        href="${pageContext.request.contextPath}/rest/user/admin/list/form">
                                    Gerenciar Usuários</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" class="collapsed">
                                Servidores
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="${pageContext.request.contextPath}/admin/server/edit-server.jsp">Cadastrar
                                    Servidores</a></li>
                                <li><a href="#">Editar ou Remover Servidores</a></li>
                                <li><a href="#">Listar Servidores</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseFive" class="collapsed">
                                Banco de Dados
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="${pageContext.request.contextPath}/admin/server/edit-database.jsp">Cadastrar
                                    Banco de Dados</a></li>
                                <li><a href="#">Editar ou Remover Banco de Dados</a></li>
                                <li><a href="#">Listar Banco de Dados</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree" class="collapsed">
                                Configuração
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="${pageContext.request.contextPath}/rest/setup/load">Editar Configuração</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour" class="collapsed">
                                Logs
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="#">Extrair Logs</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseSix" class="collapsed">
                                Relatórios
                            </a>
                        </h4>
                    </div>
                    <div id="collapseSix" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="#">Sed est</a></li>
                                <li><a href="#">Curabitur</a></li>
                                <li><a href="#">Eu dignissim</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseSeven" class="collapsed">
                                Gráficos
                            </a>
                        </h4>
                    </div>
                    <div id="collapseSeven" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="#">Sed est</a></li>
                                <li><a href="#">Curabitur</a></li>
                                <li><a href="#">Eu dignissim</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseEigth" class="collapsed">
                                Agendamentos
                            </a>
                        </h4>
                    </div>
                    <div id="collapseEigth" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="#">Sed est</a></li>
                                <li><a href="#">Curabitur</a></li>
                                <li><a href="#">Eu dignissim</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseNine" class="collapsed">
                                Sobre o Hrstatus
                            </a>
                        </h4>
                    </div>
                    <div id="collapseNine" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="${pageContext.request.contextPath}/home/about">Sobre</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div><!-- /row -->
</div>
<!-- /container -->

</body>
</html>