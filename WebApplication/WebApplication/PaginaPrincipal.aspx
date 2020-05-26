<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="PaginaPrincipal.aspx.cs" Inherits="WebApplication.WebForm2" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Pagina principal</title>
    <link href="CSS/BarraBusqueda.css" rel="stylesheet" />
    <link href="CSS/bootstrap.min.css" rel="stylesheet" />
    <style>
        html{
            height: 100%;
            width: 100%;
            overflow: hidden;
        }
        body{
            height: 100%;
            width: 100%;
        }
        .contenedor{
            height: calc(100% - 54px);
            width: 100%;
        }
        .row{
            width: 100%;
            height: 100%;
            margin:0;
        }
        .div-amigo{
            padding-bottom: 10px ;
            border-bottom: solid 1px #adb5bd;
            border-radius: 16px;
            background: #cfdaf9ad;
        }
        #amigos{
            padding-top: 5px ;
            border: 1px solid #ccc!important;
            background: aliceblue;
            border-radius: 16px;
        }
        #info{
            padding-bottom: 10px ;
            border: 1px solid #ccc!important;
            background: antiquewhite;
            border-radius: 16px;
        }
        #user-info{
            background: #f9f5f5;
            text-align: center;
            padding: 0;
            height:100%;
        }
        #user-request{
            background: #f9f5f5;
            padding: 0;
            height:100%;
        }
        #btNewPost {
            font-family: "Roboto", sans-serif;
            text-transform: uppercase;
            outline: 0;
            background: #4CAF50;
            width: 100%;
            border: 0;
            padding: 15px;
            margin-top: 15px;
            color: #FFFFFF;
            font-size: 14px;
         }

        #btNewPost:hover, #btNewPost:active, #btNewPost:focus {
            background: #43A047;
        }

        #btNewPost:disabled {
                background: #195a1c;
        }
        #amigos{
            overflow-y: auto;
        }
        @media screen and (max-width: 768px) {
            html{
                overflow: auto;
                width: 100%;
            }

        }
    </style>
    <script src="JS/jquery-3.5.1.min.js"></script>
    <script src="JS/Pagina%20princial.js"></script>
</head>
<body>
    <div class="topnav">
        <a class="active" href="PaginaPrincipal.aspx">Home</a>
        <div class="search-container">
            <form action="/action_page.php">
                <input type="text" placeholder="Buscar usuarios" name="search"/>
                <button type="submit">buscar</button>
            </form>
        </div>
    </div>
    <div class="contenedor">
      <div class="row">
        <div class="col-md" id="user-info">
            <div id="info"></div>
            <div id="amigos"></div>
        </div>
        <div class="col col-md-6" id="user-post">
          <button id="btNewPost">Crear nuevo post</button>
        </div>
        <div class="col-md" id="user-request">
        </div>
      </div>
    </div>
</body>
</html>
