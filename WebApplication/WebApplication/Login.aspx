<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="WebApplication.WebForm1" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Entrar</title>
    <link href="CSS/LoginyRegister.css" rel="stylesheet" />
    <script src="JS/jquery-3.5.1.min.js"></script>
    <script src="JS/Entrar.js"></script>
</head>
<body>
    <div class="login-page">
        <div class="form">
            <form class="login-form">
                <h3>Iniciar sesión</h3>
                <p>Email</p>
                <input required="required" id="email" type="email" name="email" placeholder="email"/>
                <p>Contraseña</p>
                <input type="password" id="pass" required="required" name="pass" placeholder="contraseña"/>
                <p class="error">Los datos introducidos no corresponden a ningún usuario</p>
                <button id="btnEntrar" type="button">Entrar</button>
                <p class="message">Aun no estas registrado <a href="Register.aspx">Registrarse</a></p>
            </form>
        </div>
    </div>
</body>
</html>
