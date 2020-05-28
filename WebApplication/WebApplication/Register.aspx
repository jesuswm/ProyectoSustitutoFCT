<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Register.aspx.cs" Inherits="WebApplication.Register" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Registrarse</title>
    <link href="CSS/LoginyRegister.css" rel="stylesheet" />
    <script src="JS/jquery-3.5.1.min.js"></script>
    <script src="JS/Registro.js"></script>
</head>
<body id="body-register">
<div class="register-page">
    <div class="form">
        <form class="register-form" action="Login.aspx">
            <p>Nombre</p>
            <input required="required" type="text" id="nombre" name="nombre" placeholder="nombre" />
            <p>Email</p>
            <input required="required" type="email"  id="email" name="email" placeholder="email" />
            <p>Contraseña</p>
            <input required="required" type="password" id="pass" name="pass" placeholder="contraseña" />
            <p>Confirmar contraseña</p>
            <input required="required" type="password" id="confpass" name="confpass" placeholder="contraseña"/>
            <p id="error"></p>
            <button id="btsub" type="submit">Crear</button>
            <p class="message">¿Ya estas registrado? <a href="Login.aspx">Iniciar sesión</a></p>
        </form>
    </div>
</div>
</body>
</html>
