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
            string contraseña;
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
                    string contenido = "{ \"email\": \""+ email + "\", \"nombre\": \""+nombre+"\",\"pass\": \""+contraseña+"\"}";
                    bytePost = encoding.GetBytes(contenido);
                    uri = new Uri(restUrl + "/Usuarios/Crear");
                    HttpWebRequest request =(HttpWebRequest)WebRequest.Create(uri);
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
                case "cerrar":
                    HttpContext.Current.Session.Remove("token");
                    HttpContext.Current.Response.Write("1");
                    break;
            }
        }
    }
}