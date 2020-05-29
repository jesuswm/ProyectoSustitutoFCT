<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="CrearPost.aspx.cs" Inherits="WebApplication.CrearPost" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>CrearPost</title>
    <link href="CSS/bootstrap.min.css" rel="stylesheet" />
    <link href="CSS/summernote.min.css" rel="stylesheet" />
    <link href="CSS/BarraBusqueda.css" rel="stylesheet" />
    <link href="CSS/CrearPost.css" rel="stylesheet" />
    <script src="JS/jquery-3.5.1.min.js"></script>
    <script src="JS/bootstrap.bundle.min.js"></script>
    <script src="JS/summernote.min.js"></script>
    <script src="JS/BarraBusqueda.js"></script>
    <script src="JS/CrearPost.js"></script>
</head>
<body>
    <div>
        <div class="topnav" id="na">
        <a href="PaginaPrincipal.aspx">Home</a>
        <div class="search-container">
            <form id="form-busqueda" action="Buscar.aspx">
                <input id="inp-busqueda" type="text" placeholder="Buscar usuarios" name="search"/>
                <button type="submit">buscar</button>
            </form>
        </div>
    </div>
    <div class="contenedor">
      <div class="row">
        <div class="col col-md-12" id="div-res">
            <div id="cont-form">
                <form>
                    <div id="div-radios">
                        Tipo de post: <br />
                        <input type="radio" id="publico" name="publico" value="publico" checked="checked"/>
                        <label for="male">Público</label><br />
                        <input type="radio" id="privado" name="publico" value="privado"/>
                        <label for="female">Solo visible para amigos</label><br />
                    </div>
                    <div id="summernote"></div>
                    <div id="div-botPublicar"><button id="botPublicar">Publicar</button></div>
                </form>
            </div>
        </div>
      </div>
    </div>
    </div>
</body>
</html>
