package Info;
import javax.ws.rs.Path;
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
@Path("Comentarios")
public class GestorDeComentarios {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response comentariosPost(@QueryParam("token") String token,@QueryParam("idPost") String idPost) {
		if(GenerarTokens.comprobarCaducidad(token)) {
			try {
				ArrayList<Comentarios> comentarios=new ArrayList<Comentarios>();
				int id=GenerarTokens.obtenerIdUsuario(token);
				int idCreadorPost=-1;
				boolean privado=true;
				boolean amigos=false;
				Bd bd=new Bd();
				bd.conecta("proyecto_fct");
				ResultSet rs=bd.consulta("select post.id_creador,privado from post where id="+idPost);
				try {
					while (rs.next()) {
						idCreadorPost=rs.getInt(1);
						privado=rs.getBoolean(2);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(privado) {
					rs=bd.consulta("select count(*) from amigos where (id_amigo1="+id+" && id_amigo2="+idCreadorPost+") || (id_amigo1="+idCreadorPost+" && id_amigo2="+id+")");
					try {
						while (rs.next()) {
							if(rs.getInt(1)>0) {
								amigos=true;
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(amigos || !privado || idCreadorPost==id) {
					rs=bd.consulta("select comentarios.id,id_post,id_creador,contenido,fecha,usuarios.nombre from comentarios join usuarios on usuarios.id=comentarios.id_creador where id_post="+idPost+" order by comentarios.fecha desc");	
					try {
						while (rs.next()) {
							Comentarios comentario=new Comentarios();
							comentario.setId(rs.getInt(1));
							comentario.setIdPost(rs.getInt(2));
							comentario.setIdCreador(rs.getInt(3));
							comentario.setContenido(rs.getString(4));
							comentario.setFecha(rs.getDate(5));
							comentario.setAutor(rs.getString(6));
							comentarios.add(comentario);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					rs.close();
					bd.cierraBd();
					return Response.ok(comentarios).build();
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).header("error", e.getLocalizedMessage()).build();
			}
		}else {
			return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
		}
		return Response.status(Status.BAD_REQUEST).header("error","Tu usuario no puede ver estos comentarios").build();
	}
	@Path("Crear")
	@Consumes(MediaType.APPLICATION_JSON)
	@POST
	public Response crearComentario(@QueryParam("token") String token,Comentarios comentario) {
		Bd bd=new Bd();
		if(GenerarTokens.comprobarCaducidad(token)) {
			try {
				boolean privado=true;
				boolean amigos=false;
				int idCreadorPost=-1;
				bd.conecta("proyecto_fct");
				int id=GenerarTokens.obtenerIdUsuario(token);
				ResultSet rs=bd.consulta("select post.id_creador,privado from post where id="+comentario.getIdPost());
				try {
					while (rs.next()) {
						idCreadorPost=rs.getInt(1);
						privado=rs.getBoolean(2);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(privado) {
					rs=bd.consulta("select count(*) from amigos where (id_amigo1="+id+" && id_amigo2="+idCreadorPost+") || (id_amigo1="+idCreadorPost+" && id_amigo2="+id+")");
					try {
						while (rs.next()) {
							if(rs.getInt(1)>0) {
								amigos=true;
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(amigos || !privado || idCreadorPost==id) {
					bd.consulta2("INSERT into comentarios(comentarios.id_creador,comentarios.id_post,comentarios.contenido) VALUES("+id+","+comentario.getIdPost()+",'"+comentario.getContenido()+"')");
					bd.cierraBd();
				}else {
					bd.cierraBd();
					return Response.status(Status.BAD_REQUEST).header("error","No puedes comentar en este post").build();
				}
				return Response.status(Status.ACCEPTED).build();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				return Response.status(Status.BAD_REQUEST).header("error", e.getLocalizedMessage()).build();
			}
		}
		return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
	}
}
