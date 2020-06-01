package Info;

import java.io.Console;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
@Path("Posts")
public class GestorDePosts {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerPost(@QueryParam("token") String token, @QueryParam("idCreador") String idCreador) {
		ArrayList<Posts> posts=new ArrayList<Posts>();
		if(GenerarTokens.comprobarCaducidad(token)) {
			try {
				int id=GenerarTokens.obtenerIdUsuario(token);
				//System.out.println(id);
				//System.out.println("select count(*) from amigos where (id_amigo1="+id+" && id_amigo2="+idCreador+") || (id_amigo1="+idCreador+" && id_amigo2="+id+")");
				boolean amigos=false;
				Bd bd=new Bd();
				bd.conecta("proyecto_fct");
				ResultSet rs=bd.consulta("select count(*) from amigos where (id_amigo1="+id+" && id_amigo2="+idCreador+") || (id_amigo1="+idCreador+" && id_amigo2="+id+")");
				try {
					while (rs.next()) {
						if(rs.getInt(1)>0) {
							amigos=true;
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				//System.out.println("Amigos: "+amigos);
				rs.close();
				rs=bd.consulta("select * from post where id_creador="+idCreador+" order by fecha desc");
				try {
					while (rs.next()) {
						Posts post=new Posts(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getBoolean(4),rs.getDate(5));
						if(!post.isPrivado()) {
							posts.add(post);
						}else {
							if(amigos) {
								posts.add(post);
							}
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rs.close();
				bd.cierraBd();
				return Response.ok(posts).build();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Response.ok(posts).build();
		}else {
			return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
		}
	}
	@Path("Propios")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerMisPost(@QueryParam("token") String token) {
		ArrayList<Posts> posts=new ArrayList<Posts>();
		if(GenerarTokens.comprobarCaducidad(token)) {
			try {
				int id=GenerarTokens.obtenerIdUsuario(token);
				//System.out.println(id);
				Bd bd=new Bd();
				bd.conecta("proyecto_fct");
				ResultSet rs=bd.consulta("select * from post where id_creador="+id+" order by fecha desc");
				try {
					while (rs.next()) {
						Posts post=new Posts(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getBoolean(4),rs.getDate(5));
						posts.add(post);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rs.close();
				bd.cierraBd();
				return Response.ok(posts).build();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Response.ok(posts).build();
		}else {
			return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
		}
	}
	@Path("Crear")
	@Consumes(MediaType.APPLICATION_JSON)
	@POST
	public Response crearPost(@QueryParam("token") String token,Posts post) {
		Bd bd=new Bd();
		if(GenerarTokens.comprobarCaducidad(token)) {
			try {
				bd.conecta("proyecto_fct");
				int id=GenerarTokens.obtenerIdUsuario(token);
				int resultados=bd.consulta2("INSERT INTO post(post.contenido,post.id_creador,post.privado) VALUES ('"+post.getContenido()+"',"+id+","+post.isPrivado()+")");
				//System.out.println("INSERT INTO post(post.contenido,post.id_creador,post.privado) VALUES ('"+post.getContenido()+"',"+id+","+post.isPrivado()+")");
				bd.cierraBd();
				return Response.status(Status.ACCEPTED).build();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				return Response.status(Status.BAD_REQUEST).header("error", e.getLocalizedMessage()).build();
			}
		}
		return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
	}
}
