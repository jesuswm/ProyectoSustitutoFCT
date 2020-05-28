<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Buscar.aspx.cs" Inherits="WebApplication.Buscar" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Buscar</title>
    <link href="CSS/bootstrap.min.css" rel="stylesheet" />
    <link href="CSS/BarraBusqueda.css" rel="stylesheet" />
    <link href="CSS/Buscar.css" rel="stylesheet" />
    <script src="JS/jquery-3.5.1.min.js"></script>
    <script src="JS/Buscar.js"></script>
    <script src="JS/BarraBusqueda.js"></script>
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
    <div class ="carga"></div>
    <div class="contenedor">
      <div class="row">
        <div class="col col-md-12" id="div-res">
            <div id="resultados"></div>
        </div>
      </div>
    </div>
    </div>
</body>
</html>
