<%@ include file="/home/header.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-9 col-md-10 col-sm-push-3 col-md-push-2">
            <ol class="breadcrumb">
                <li><a href="/hs/home/home.jsp">Home</a></li>
                <li>Cadastrar Usuário</li>
            </ol>
            <h1>Cadastrar Usuário</h1>
            <form class="form-horizontal">
                <div class="form-group">
                    <label class="col-md-2 control-label" for="textInput">Nome</label>
                    <div class="col-md-6">
                        <input type="text" id="textInput" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label" for="textInput2">Nome de Usuário</label>
                    <div class="col-md-6">
                        <input type="text" id="textInput2" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label" for="textInput3">Pharetra vel</label>
                    <div class="col-md-6">
                        <input type="text" id="textInput3" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label" for="textInput4">Arcu ac</label>
                    <div class="col-md-6">
                        <input type="text" id="textInput4" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">Posuere</label>
                    <div class="col-md-6">
                        <div class="radio">
                            <label>
                                <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
                                Proin lobortis auctor tortor et posuere
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
                                Duis eu ipsum metus
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label" for="boostrapSelect">Vestibulum</label>
                    <div class="col-md-10">
                        <select class="selectpicker" multiple data-selected-text-format="count>3" id="boostrapSelect">
                            <option>Mustard</option>
                            <option>Ketchup</option>
                            <option>Relish</option>
                            <option>Onions</option>
                            <option>Mushrooms</option>
                            <option>Pickles</option>
                            <option>Mayonnaise</option>
                            <option data-divider="true"></option>
                            <option data-subtext="Hot">Tabasco</option>
                            <option data-subtext="Hotter">Sriracha</option>
                            <option data-subtext="Hottest">Wasabi</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-10 col-md-offset-2">
                        <button type="button" class="btn btn-primary">Save</button>
                        <button type="reset" class="btn btn-default">Cancel</button>
                    </div>
                </div>
            </form>
        </div><!-- /col -->
        <%@ include file="/home/right-side-menu.jsp"%>
    </div><!-- /row -->
</div><!-- /container -->

</body>
</html>