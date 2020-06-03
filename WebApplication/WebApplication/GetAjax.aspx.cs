using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Net;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.IO;
using System.Text;
using System.Web.Script.Serialization;

namespace WebApplication
{
    public partial class GetAjax : System.Web.UI.Page
    {
        static string restUrl = "http://localhost:8080/RestProyect/rest";
        protected void Page_Load(object sender, EventArgs e)
        {

        }
        public static void ProcesarPeticion()
        {
            Uri uri;
            string op = HttpContext.Current.Request.Params["op"];
            string result;
            ASCIIEncoding encoding = new ASCIIEncoding();
            System.Net.HttpWebRequest webrequest;
            StreamReader reader;
            byte[] bytePost;
            string nombre;
            string email;
            string idpost;
            string idpeticion;
            string aceptar;
            string busqueda;
            string contenido;
            string viejaContraseña;
            string contraseña;
            string contenidoPost;
            string privado;
            HttpWebRequest request;
            switch (op) {
                case "login":
                    email = HttpContext.Current.Request.Params["email"];
                    contraseña = HttpContext.Current.Request.Params["pass"];
                    uri = new Uri(restUrl + "/Usuarios/Autentificar?pass="+contraseña+"&email="+email);
                    webrequest = (HttpWebRequest)System.Net.WebRequest.Create(uri);
                    try
                    {
                        using (WebResponse response = webrequest.GetResponse())
                        using (reader = new StreamReader(response.GetResponseStream()))
                        {
                            result = reader.ReadToEnd();
                            HttpContext.Current.Session.Add("token", result);
                            //HttpContext.Current.Response.Write(result);
                            HttpContext.Current.Response.Write("1");
                        }
                    }
                    catch (System.Net.WebException)
                    {
                        HttpContext.Current.Response.Write("0");
                    }
                    break;
                case "usuarios":
                    uri = new Uri(restUrl+"/Usuarios");
                    //string u = HttpContext.Current.Request.Params["u"];
                    //string p = HttpContext.Current.Request.Params["p"];
                    //WebClient wclient = new WebClient();
                    //HttpContext.Current.Session.Add("usuario", null);
                    //HttpContext.Current.Response.Write("1");
                    webrequest = (HttpWebRequest)System.Net.WebRequest.Create(uri);
                    using (WebResponse response = webrequest.GetResponse())
                    using (reader = new StreamReader(response.GetResponseStream()))
                    {
                        result = reader.ReadToEnd();
                        HttpContext.Current.Response.Write(result);
                    }
                    break;
                case "registro":
                    nombre = HttpContext.Current.Request.Params["nombre"];
                    email = HttpContext.Current.Request.Params["email"];
                    contraseña = HttpContext.Current.Request.Params["pass"];
                    contenido = "{ \"email\": \""+ email + "\", \"nombre\": \""+nombre+"\",\"pass\": \""+contraseña+"\"}";
                    bytePost = encoding.GetBytes(contenido);
                    uri = new Uri(restUrl + "/Usuarios/Crear");
                    request =(HttpWebRequest)WebRequest.Create(uri);
                    request.Method = "POST";
                    request.ContentType = "application/json";
                    request.ContentLength = bytePost.Length;
                    using (Stream newStream = request.GetRequestStream())
                    {
                        newStream.Write(bytePost, 0, bytePost.Length);
                    }
                    //request.Headers.Add("username", "miUsuario");
                    //request.Headers.Add("password", "MiClave");
                    //request.Headers.Add("grant_type", "Migrant_type");
                    //request.Headers.Add("client_id", "Miclient_id");
                    try
                    {
                        HttpWebResponse respon = request.GetResponse() as HttpWebResponse;
                        HttpContext.Current.Response.Write("1");
                    }
                    catch (System.Net.WebException e)
                    {
                        HttpContext.Current.Response.Write("0");
                    }
                    break;
                case "miUsuario":
                    uri = new Uri(restUrl + "/Usuarios/MiUsuario?token=" + HttpContext.Current.Session["token"]);
                    try
                    {
                        webrequest = (HttpWebRequest)System.Net.WebRequest.Create(uri);
                        using (WebResponse response = webrequest.GetResponse())
                        using (reader = new StreamReader(response.GetResponseStream()))
                        {
                            result = reader.ReadToEnd();
                            HttpContext.Current.Response.Write(result);
                        }
                    }
                    catch (System.Net.WebException)
                    {
                        HttpContext.Current.Response.Write("0");
                    }
                    break;
                case "amigos":
                    uri = new Uri(restUrl + "/Amigos?token=" + HttpContext.Current.Session["token"]);
                    try
                    {
                        webrequest = (HttpWebRequest)System.Net.WebRequest.Create(uri);
                        using (WebResponse response = webrequest.GetResponse())
                        using (reader = new StreamReader(response.GetResponseStream()))
                        {
                            result = reader.ReadToEnd();
                            HttpContext.Current.Response.Write(result);
                        }
                    }
                    catch (System.Net.WebException)
                    {
                        HttpContext.Current.Response.Write("0");
                    }
                    break;
                case "misPost":
                    uri = new Uri(restUrl + "/Posts/Propios?token=" + HttpContext.Current.Session["token"]);
                    try
                    {
                        webrequest = (HttpWebRequest)System.Net.WebRequest.Create(uri);
                        using (WebResponse response = webrequest.GetResponse())
                        using (reader = new StreamReader(response.GetResponseStream()))
                        {
                            result = reader.ReadToEnd();
                            HttpContext.Current.Response.Write(result);
                        }
                    }
                    catch (System.Net.WebException)
                    {
                        HttpContext.Current.Response.Write("0");
                    }
                    break;
                case "comentarios":
                    idpost = HttpContext.Current.Request.Params["idPost"];
                    uri = new Uri(restUrl + "/Comentarios?token=" + HttpContext.Current.Session["token"]+ "&idPost="+idpost);
                    try
                    {
                        webrequest = (HttpWebRequest)System.Net.WebRequest.Create(uri);
                        using (WebResponse response = webrequest.GetResponse())
                        using (reader = new StreamReader(response.GetResponseStream()))
                        {
                            result = reader.ReadToEnd();
                            HttpContext.Current.Response.Write(result);
                        }
                    }
                    catch (System.Net.WebException)
                    {
                        HttpContext.Current.Response.Write("0");
                    }
                    break;
                case "peticiones":
                    uri = new Uri(restUrl + "/Peticiones?token=" + HttpContext.Current.Session["token"]);
                    try
                    {
                        webrequest = (HttpWebRequest)System.Net.WebRequest.Create(uri);
                        using (WebResponse response = webrequest.GetResponse())
                        using (reader = new StreamReader(response.GetResponseStream()))
                        {
                            result = reader.ReadToEnd();
                            HttpContext.Current.Response.Write(result);
                        }
                    }
                    catch (System.Net.WebException)
                    {
                        HttpContext.Current.Response.Write("0");
                    }
                    break;
                case "contestarPeticion":
                    idpeticion= HttpContext.Current.Request.Params["idPeticion"];
                    aceptar= HttpContext.Current.Request.Params["aceptar"];
                    uri = new Uri(restUrl + "/Peticiones/Responder?token=" + HttpContext.Current.Session["token"]+ "&idSolicitud="+ idpeticion+ "&aceptar=" + aceptar);
                    try
                    {
                        webrequest = (HttpWebRequest)System.Net.WebRequest.Create(uri);
                        webrequest.Method = "POST";
                        using (WebResponse response = webrequest.GetResponse())
                        using (reader = new StreamReader(response.GetResponseStream()))
                        {
                            result = reader.ReadToEnd();
                            HttpContext.Current.Response.Write("1");
                        }
                    }
                    catch (System.Net.WebException)
                    {
                        HttpContext.Current.Response.Write("0");
                    }
                    break;
                case "busqueda":
                    busqueda= HttpContext.Current.Request.Params["buscado"];
                    uri = new Uri(restUrl + "/Usuarios/Buscar?token=" + HttpContext.Current.Session["token"] + "&busqueda=" + busqueda);
                    try
                    {
                        webrequest = (HttpWebRequest)System.Net.WebRequest.Create(uri);
                        using (WebResponse response = webrequest.GetResponse())
                        using (reader = new StreamReader(response.GetResponseStream()))
                        {
                            result = reader.ReadToEnd();
                            HttpContext.Current.Response.Write(result);
                        }
                    }
                    catch (System.Net.WebException)
                    {
                        HttpContext.Current.Response.Write("0");
                    }
                    break;
                case "CrearPost":
                    contenidoPost = HttpContext.Current.Request.Params["contenido"];
                    privado = HttpContext.Current.Request.Params["privado"];
                    contenido = "{ \"contenido\": \"" + contenidoPost + "\", \"privado\": \"" + privado + "\"}";
                    bytePost = encoding.GetBytes(contenido);
                    uri = new Uri(restUrl + "/Posts/Crear?token="+ HttpContext.Current.Session["token"]);
                    request = (HttpWebRequest)WebRequest.Create(uri);
                    request.Method = "POST";
                    request.ContentType = "application/json";
                    using (StreamWriter newStream = new StreamWriter(request.GetRequestStream()))
                    {
                        newStream.Write(contenido);
                    }
                    try
                    {
                        HttpWebResponse respon = request.GetResponse() as HttpWebResponse;
                        HttpContext.Current.Response.Write("1");
                    }
                    catch (System.Net.WebException e)
                    {
                        HttpContext.Current.Response.Write("0");
                    }
                    break;
                case "modificarUsuario":
                    nombre = HttpContext.Current.Request.Params["nombre"];
                    email = HttpContext.Current.Request.Params["email"];
                    contraseña = HttpContext.Current.Request.Params["newPass"];
                    viejaContraseña = HttpContext.Current.Request.Params["OldPass"];
                    contenido = "{ ";
                    bool coma = false;
                    if (nombre != null)
                    {
                        contenido=contenido+"\"nombre\": \"" + nombre+"\"";
                        coma = true;
                    }
                    if (email != null)
                    {
                        if (coma)
                        {
                            contenido = contenido + ", \"email\": \"" + email + "\"";
                        }
                        else
                        {
                            contenido = contenido + " \"email\": \"" + email + "\"";
                            coma = true;
                        }
                    }
                    if (contraseña != null)
                    {
                        if (coma)
                        {
                            contenido = contenido+ ", \"pass\": \"" + contraseña + "\"";
                        }
                        else
                        {
                            contenido = contenido+ " \"pass\": \"" + contraseña + "\"";
                            coma = true;
                        }
                    }
                    contenido = contenido + " }";
                    //contenido = "{ \"email\": \"" + email + "\", \"nombre\": \"" + nombre + "\",\"pass\": \"" + contraseña + "\"}";
                    bytePost = encoding.GetBytes(contenido);
                    uri = new Uri(restUrl + "/Usuarios/Modificar?token=" + HttpContext.Current.Session["token"] + "&pass=" + viejaContraseña);
                    request = (HttpWebRequest)WebRequest.Create(uri);
                    request.Method = "POST";
                    request.ContentType = "application/json";
                    request.ContentLength = bytePost.Length;
                    using (Stream newStream = request.GetRequestStream())
                    {
                        newStream.Write(bytePost, 0, bytePost.Length);
                    }
                    //request.Headers.Add("username", "miUsuario");
                    //request.Headers.Add("password", "MiClave");
                    //request.Headers.Add("grant_type", "Migrant_type");
                    //request.Headers.Add("client_id", "Miclient_id");
                    try
                    {
                        HttpWebResponse respon = request.GetResponse() as HttpWebResponse;
                        HttpContext.Current.Response.Write("1");
                    }
                    catch (System.Net.WebException e)
                    {
                        HttpContext.Current.Response.Write("0");
                    }
                    break;
                case "usuarioPorId":
                    busqueda = HttpContext.Current.Request.Params["idBuscado"];
                    uri = new Uri(restUrl + "/Usuarios?token=" + HttpContext.Current.Session["token"] + "&idBuscado=" + busqueda);
                    try
                    {
                        webrequest = (HttpWebRequest)System.Net.WebRequest.Create(uri);
                        using (WebResponse response = webrequest.GetResponse())
                        using (reader = new StreamReader(response.GetResponseStream()))
                        {
                            result = reader.ReadToEnd();
                            HttpContext.Current.Response.Write(result);
                        }
                    }
                    catch (System.Net.WebException)
                    {
                        HttpContext.Current.Response.Write("0");
                    }
                    break;
                case "comprobarAmistad":
                    busqueda = HttpContext.Current.Request.Params["idBuscado"];
                    uri = new Uri(restUrl + "/Amigos/Comprobar?token=" + HttpContext.Current.Session["token"] + "&idBuscado=" + busqueda);
                    try
                    {
                        webrequest = (HttpWebRequest)System.Net.WebRequest.Create(uri);
                        using (WebResponse response = webrequest.GetResponse())
                        using (reader = new StreamReader(response.GetResponseStream()))
                        {
                            result = reader.ReadToEnd();
                            HttpContext.Current.Response.Write(result);
                        }
                    }
                    catch (System.Net.WebException)
                    {
                        HttpContext.Current.Response.Write("0");
                    }
                    break;
                case "solicitarAmistad":
                    idpeticion = HttpContext.Current.Request.Params["idSolicitado"];
                    uri = new Uri(restUrl + "/Peticiones/Crear?token=" + HttpContext.Current.Session["token"] + "&idSolicitado=" + idpeticion);
                    try
                    {
                        webrequest = (HttpWebRequest)System.Net.WebRequest.Create(uri);
                        webrequest.Method = "POST";
                        using (WebResponse response = webrequest.GetResponse())
                        using (reader = new StreamReader(response.GetResponseStream()))
                        {
                            result = reader.ReadToEnd();
                            HttpContext.Current.Response.Write(result);
                        }
                    }
                    catch (System.Net.WebException)
                    {
                        HttpContext.Current.Response.Write("0");
                    }
                    break;
                case "cerrar":
                    HttpContext.Current.Session.Remove("token");
                    HttpContext.Current.Response.Write("1");
                    break;
            }
        }
    }
}