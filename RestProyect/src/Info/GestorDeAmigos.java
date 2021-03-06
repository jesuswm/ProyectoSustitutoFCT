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
@Path("Amigos")
public class GestorDeAmigos {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerAmigos(@QueryParam("token") String token) {
		ArrayList<Integer> idAmigos=new ArrayList<Integer>();
		ArrayList<Usuarios> usuarios=new ArrayList<Usuarios>();
		if(GenerarTokens.comprobarCaducidad(token)) {
			try {
				int id=GenerarTokens.obtenerIdUsuario(token);
				Bd bd=new Bd();
				bd.conecta("proyecto_fct");
				ResultSet rs=bd.consulta("select * from amigos where amigos.id_amigo1="+id+"|| amigos.id_amigo2="+id);
				try {
					while (rs.next()) {
						if(rs.getInt(1)!=id) {
							idAmigos.add(rs.getInt(1));
						}else {
							idAmigos.add(rs.getInt(2));
						}
					}
				} catch (SQLException e) {
				}
				for (int idAmigo : idAmigos) {
					rs=bd.consulta("select id,nombre,email from usuarios where id="+idAmigo);
					try {
						while (rs.next()) {
							Usuarios usuario=new Usuarios();
							usuario.setId(rs.getInt(1));
							usuario.setNombre(rs.getString(2));
							usuario.setEmail(rs.getString(3));
							usuarios.add(usuario);
						}
					} catch (SQLException e) {
					}
				}
				bd.cierraBd();
				return Response.ok(usuarios).build();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Response.ok(usuarios).build();
		}else {
			return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
		}
	}
	@Path("OtroUsuario")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response otroUsuario(@QueryParam("token") String token,@QueryParam("idBuscado") int idBuscado) {
		ArrayList<Integer> idAmigos=new ArrayList<Integer>();
		ArrayList<Usuarios> usuarios=new ArrayList<Usuarios>();
		if(GenerarTokens.comprobarCaducidad(token)) {
			try {
				int id=GenerarTokens.obtenerIdUsuario(token);
				Bd bd=new Bd();
				bd.conecta("proyecto_fct");
				ResultSet rs=bd.consulta("select * from amigos where amigos.id_amigo1="+idBuscado+"|| amigos.id_amigo2="+idBuscado);
				try {
					while (rs.next()) {
						if(rs.getInt(1)!=idBuscado) {
							idAmigos.add(rs.getInt(1));
						}else {
							idAmigos.add(rs.getInt(2));
						}
					}
				} catch (SQLException e) {
				}
				for (int idAmigo : idAmigos) {
					rs=bd.consulta("select id,nombre,email from usuarios where id="+idAmigo);
					try {
						while (rs.next()) {
							Usuarios usuario=new Usuarios();
							usuario.setId(rs.getInt(1));
							usuario.setNombre(rs.getString(2));
							usuario.setEmail(rs.getString(3));
							usuarios.add(usuario);
						}
					} catch (SQLException e) {
					}
				}
				bd.cierraBd();
				return Response.ok(usuarios).build();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Response.ok(usuarios).build();
		}else {
			return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
		}
	}
	@Path("Comprobar")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response comprobarAmistad(@QueryParam("token") String token,@QueryParam("idBuscado") String idBuscado) {
		if(GenerarTokens.comprobarCaducidad(token)) {
			try {
				int id=GenerarTokens.obtenerIdUsuario(token);
				boolean amigos=false;
				Bd bd=new Bd();
				bd.conecta("proyecto_fct");
				ResultSet rs=bd.consulta("select count(*) from usuarios where id ="+idBuscado);
				try {
					while (rs.next()) {
						if(rs.getInt(1)==0) {
							return Response.status(Status.BAD_REQUEST).header("error","No existe el usuario").build();
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rs.close();
				rs=bd.consulta("select count(*) from amigos where (id_amigo1="+id+" && id_amigo2="+idBuscado+") || (id_amigo1="+idBuscado+" && id_amigo2="+id+")");
				try {
					while (rs.next()) {
						if(rs.getInt(1)>0) {
							return Response.status(Status.OK).entity("3").build();
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rs.close();
				rs=bd.consulta("select count(*) from peticiones where (id_solicitante="+id+" && id_solicitado="+idBuscado+") || (id_solicitante="+idBuscado+" && id_solicitado="+id+")");
				try {
					while (rs.next()) {
						if(rs.getInt(1)>0) {
							return Response.status(Status.OK).entity("2").build();
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rs.close();
				return Response.status(Status.OK).entity("1").build();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
		}
		return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
	}
}
