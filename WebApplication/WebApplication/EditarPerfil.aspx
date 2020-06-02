<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="EditarPerfil.aspx.cs" Inherits="WebApplication.EditarPerfil" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Editar usuario</title>
    <script src="JS/jquery-3.5.1.min.js"></script>
    <script src="JS/bootstrap.bundle.min.js"></script>
    <script src="JS/EditarPerfil.js"></script>
    <link href="CSS/EditarPerfil.css" rel="stylesheet" />
</head>
<body>
    <div class ="carga"></div>
    <div class="contenedor">
      <div class="row">
        <div class="col col-md-12" id="div-res">
            <div id="info">
                <%--<label style="font-weight: 900;">Tu usuario</label><br/>
                usuario<br/>
                correo@algo--%>
            </div>
            <div id="cont-form">
                <form id="formulario">
                    <label style="font-weight: 900;">Solo rellene los campos que quiere modificar y la contraseña actual para modificar su perfil</label><br/>
                    <p>Nuevo nombre de usuario<br /></p>
                    <input type="text"/ id="impnombre" />
                    <p>Nuevo email<br /></p>
                    <input type="text"/ id="impemail" />
                    <p>Nueva contraseña<br /></p>
                    <input type="password" id="impNewPass"/>
                    <p>Confirmar nueva contraseña<br /></p>
                    <input type="password" id="impNewPassConf"/>
                    <p>Contraseña actual<br /></p>
                    <input type="password" id="impPass"/><br/>
                    <label id="error"></label>
                    <div id="div-botPublicar"><button id="botCancelar" type="button">Cancelar</button><button id="botCambiar" type="button">Actualizar usuario</button></div>
                </form>
            </div>
        </div>
      </div>
    </div>
</body>
</html>
