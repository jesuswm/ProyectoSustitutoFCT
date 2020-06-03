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


@Path("Usuarios")
public class GestorUsuarios {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerUsuario(@QueryParam("token") String token, @QueryParam("idBuscado") String idBuscado) {
		Usuarios usuario=new Usuarios();
		if(GenerarTokens.comprobarCaducidad(token)) {
			try {
				Bd bd=new Bd();
				bd.conecta("proyecto_fct");
				ResultSet rs=bd.consulta("select id,nombre,email from usuarios where id="+idBuscado);
				boolean existe=false;
				try {
					while (rs.next()) {
						usuario.setId(rs.getInt(1));
						usuario.setNombre(rs.getString(2));
						usuario.setEmail(rs.getString(3));
						existe=true;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bd.cierraBd();
				if(!existe) {
					return Response.status(Status.BAD_REQUEST).header("error","No existe usuario con esa id").build();
				}
				return Response.ok(usuario).build();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	@Path("Crear")
	@Consumes(MediaType.APPLICATION_JSON)
	@POST
	public Response crearUsuario(Usuarios user) {
		Bd bd=new Bd();
		//System.out.println(user.getNombre());
		//System.out.println(user.getEmail());
		//System.out.println(user.getPass());
		//System.out.println("INSERT INTO `usuarios` (`nombre`, `email`, `contraseña`) VALUES ('"+user.getNombre()+"','"+user.getEmail()+"',MD5('"+user.getPass()+"'))");
		try {
			bd.conecta("proyecto_fct");
			int resultados=bd.consulta2("INSERT INTO `usuarios` (`nombre`, `email`, `contraseña`) VALUES ('"+user.getNombre()+"','"+user.getEmail()+"',MD5('"+user.getPass()+"'))");
			bd.cierraBd();
			return Response.status(Status.ACCEPTED).build();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			return Response.status(Status.BAD_REQUEST).header("error", e.getLocalizedMessage()).build();
		}
	}
	@Path("Autentificar")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response obtenerToken(@QueryParam("pass") String pass, @QueryParam("email") String email) {
		//SELECT id FROM proyecto_fct.usuarios where email='otro@gmail.com' && contraseña=md5('pass');
		try {
			Bd bd=new Bd();
			bd.conecta("proyecto_fct");
			String token;
			int id=-1;
			ResultSet rs=bd.consulta("SELECT id FROM proyecto_fct.usuarios where email='"+email+"' && contraseña=md5('"+pass+"')");
			try {
				while (rs.next()) {
					id=rs.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rs.close();
			if(id!=-1) {
				ArrayList<String> tokensUsados =new ArrayList<String>();
				rs=bd.consulta("select tokens.token from tokens");
				try {
					while (rs.next()) {
						tokensUsados.add(rs.getString(1));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rs.close();
				do {
					token=GenerarTokens.generar(10);
				}while(tokensUsados.contains(token));
				bd.consulta2("delete from tokens where tokens.id_usuario="+id);
				bd.consulta2("INSERT into tokens(tokens.id_usuario,tokens.token) VALUES("+id+",'"+token+"')");
				String query="INSERT into tokens(tokens.id_usuario,tokens.token) VALUES("+id+",'"+token+"')";
				//System.out.println(query);
			}else {
				bd.cierraBd();
				return Response.status(Status.BAD_REQUEST).header("error","no se ha encontrado el usuario").build();
			}
			bd.cierraBd();
			return Response.ok(token).build();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return Response.status(Status.BAD_REQUEST).header("error","error al acceder a la base de datos").build();
	}
	@Path("Caducidad")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response obtenerToken(@QueryParam("token") String token) {
		if(GenerarTokens.comprobarCaducidad(token)) {
			return Response.ok("todo bien").build();
		}else {
			return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
		}
	}
	@Path("Buscar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarUsuario(@QueryParam("token") String token, @QueryParam("busqueda") String busqueda) {
		if(GenerarTokens.comprobarCaducidad(token)) {
			try {
				ArrayList<Usuarios> usuarios=new ArrayList<Usuarios>();
				Bd bd=new Bd();
				bd.conecta("proyecto_fct");
				ResultSet rs=bd.consulta("select id,nombre,email from usuarios where nombre like '"+busqueda+"%' || nombre LIKE '%"+busqueda+"'");
				try {
					while (rs.next()) {
						Usuarios usuario=new Usuarios();
						usuario.setId(rs.getInt(1));
						usuario.setNombre(rs.getString(2));
						usuario.setEmail(rs.getString(3));
						usuarios.add(usuario);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bd.cierraBd();
				return Response.ok(usuarios).build();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).header("error", e.getLocalizedMessage()).build();
			}
		}else {
			return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
		}
	}
	@Path("MiUsuario")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response miUsuario(@QueryParam("token") String token) {
		if(GenerarTokens.comprobarCaducidad(token)) {
			try {
				Bd bd=new Bd();
				Usuarios usuario=new Usuarios();
				bd.conecta("proyecto_fct");
				ResultSet rs=bd.consulta("select usuarios.id,usuarios.nombre,usuarios.email from usuarios JOIN tokens ON usuarios.id=tokens.id_usuario where tokens.token=\""+token+"\"");
				try {
					while (rs.next()) {
						usuario.setId(rs.getInt(1));
						usuario.setNombre(rs.getString(2));
						usuario.setEmail(rs.getString(3));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bd.cierraBd();
				return Response.ok(usuario).build();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).header("error", e.getLocalizedMessage()).build();
			}
		}else {
			return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
		}
	}
	@Path("Modificar")
	@Consumes(MediaType.APPLICATION_JSON)
	@POST
	public Response modificarUsuario(@QueryParam("token") String token,@QueryParam("pass") String oldpas,Usuarios user) {
		if(GenerarTokens.comprobarCaducidad(token)) {
			Bd bd=new Bd();
			try {
				bd.conecta("proyecto_fct");
				boolean correcto=false;
				ResultSet rs=bd.consulta("select count(*) from usuarios JOIN tokens on usuarios.id=tokens.id_usuario where tokens.token='"+token+"' && usuarios.`contraseña`=MD5('"+oldpas+"')");
				try {
					while (rs.next()) {
						if(rs.getInt(1)>0) {
							correcto=true;
						}else {
							return Response.status(Status.BAD_REQUEST).header("error","parametro de pass incorrecto").build();
						}
					}
					rs.close();
				} catch (SQLException e) {
					return Response.status(Status.BAD_REQUEST).header("error", e.getLocalizedMessage()).build();
				}
				boolean coma=false;
				int id=GenerarTokens.obtenerIdUsuario(token);
				String consulta="UPDATE `proyecto_fct`.`usuarios` SET ";
				if(user.getNombre()!=null) {
					consulta=consulta+"usuarios.nombre = '"+user.getNombre()+"' ";
					coma=true;
				}
				if(user.getEmail()!=null) {
					if(coma==true) {
						consulta=consulta+",usuarios.email = '"+user.getEmail()+"' ";						
					}else {
						consulta=consulta+"usuarios.email = '"+user.getEmail()+"' ";	
						coma=true;
					}
				}
				if(user.getPass()!=null) {
					if(coma==true) {
						consulta=consulta+",usuarios.`contraseña` = MD5('"+user.getPass()+"')";
					}else {
						consulta=consulta+"usuarios.`contraseña` = MD5('"+user.getPass()+"')";
						coma=true;
					}
				}
				consulta=consulta+" WHERE  `id`="+id;
				//System.out.println(consulta);
				int resultados=bd.consulta2(consulta);
				bd.cierraBd();
				return Response.status(Status.ACCEPTED).build();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				return Response.status(Status.BAD_REQUEST).header("error", e.getLocalizedMessage()).build();
			}
		}else {
			return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
		}
	}
		
}
