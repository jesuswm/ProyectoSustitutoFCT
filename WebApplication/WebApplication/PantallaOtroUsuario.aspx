<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="PantallaOtroUsuario.aspx.cs" Inherits="WebApplication.PantallaOtroUsuario" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Pantalla de otro usuario</title>
    <link href="CSS/bootstrap.min.css" rel="stylesheet" />
    <link href="CSS/BarraBusqueda.css" rel="stylesheet" />
    <link href="CSS/PantallaOtroUsuario.css" rel="stylesheet" />
    <script src="JS/jquery-3.5.1.min.js"></script>
    <script src="JS/Buscar.js"></script>
    <script src="JS/PantallaOtroUsuario.js"></script>
</head>
<body>
    <div class="topnav">
        <a class="active" href="PaginaPrincipal.aspx">Home</a>
        <div class="search-container">
            <form id="form-busqueda" action="Buscar.aspx">
                <input id="inp-busqueda"type="text" placeholder="Buscar usuarios" name="search"/>
                <button type="submit">buscar</button>
            </form>
        </div>
    </div>
    <div class ="carga"></div>
    <div class="contenedor">
      <div class="row">
            <div class="col-md" id="user-info">
                <div id="info"></div>
                <div id="amigos"></div>
            </div>
            <div class="col col-md-9" id="user-post">
                <div id="posts"></div>
            </div>
        </div>
    </div>
</body>
</html>
