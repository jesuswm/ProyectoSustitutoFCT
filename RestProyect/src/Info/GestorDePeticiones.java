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
@Path("Peticiones")
public class GestorDePeticiones {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response peticionesPendientes(@QueryParam("token") String token) {
		if(GenerarTokens.comprobarCaducidad(token)) {
			try {
				ArrayList<Peticiones> peticiones=new ArrayList<Peticiones>();
				Bd bd=new Bd();
				bd.conecta("proyecto_fct");
				int id=GenerarTokens.obtenerIdUsuario(token);
				ResultSet rs=bd.consulta("select * from peticiones where id_solicitado="+id);
				try {
					while (rs.next()) {
						Peticiones peticion=new Peticiones();
						peticion.setId(rs.getInt(1));
						peticion.setIdSolicitante(rs.getInt(2));
						peticion.setIdSolicitado(rs.getInt(3));
						ResultSet rs2=bd.consulta("select nombre from usuarios where id="+peticion.getIdSolicitante());
						while (rs2.next()) {
							peticion.setNombreSolicitante(rs2.getString(1));
						}
						rs2.close();
						peticiones.add(peticion);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bd.cierraBd();
				return Response.ok(peticiones).build();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).header("error", e.getLocalizedMessage()).build();
			}
		}else {
			return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
		}
	}
	@Path("Crear")
	@POST
	public Response crearPeticion(@QueryParam("token") String token, @QueryParam("idSolicitado") int idSolicitado) {
		if(GenerarTokens.comprobarCaducidad(token)) {
			int id=GenerarTokens.obtenerIdUsuario(token);
			Bd bd=new Bd();
			try {
				bd.conecta("proyecto_fct");
				int resultados=bd.consulta2("INSERT into peticiones(peticiones.id_solicitante,peticiones.id_solicitado) VALUES("+id+","+idSolicitado+")");
				bd.cierraBd();
				return Response.status(Status.ACCEPTED).build();
			} catch (ClassNotFoundException | SQLException e) {
				return Response.status(Status.BAD_REQUEST).header("error", e.getLocalizedMessage()).build();
			}
		}else {
			return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
		}
	}
	@Path("Responder")
	@POST
	public Response responderPeticion(@QueryParam("token") String token, @QueryParam("idSolicitud") int idSolicitud,@QueryParam("aceptar") Boolean aceptar) {
		if(GenerarTokens.comprobarCaducidad(token)) {
			int id=GenerarTokens.obtenerIdUsuario(token);
			Bd bd=new Bd();
			int idSolicitante=-1;
			try {
				bd.conecta("proyecto_fct");
				ResultSet rs= bd.consulta("Select id_solicitante from peticiones where id_solicitado="+id+"&& id="+idSolicitud);
				while (rs.next()) {
					idSolicitante=rs.getInt(1);
				}
				rs.close();
				if(idSolicitante>0) {
					if(aceptar) {
						bd.consulta2("INSERT into amigos(amigos.id_amigo1,amigos.id_amigo2) VALUES("+id+","+idSolicitante+")");
					}
					bd.consulta2("delete from peticiones where id="+idSolicitud);
					return Response.status(Status.ACCEPTED).build();
				}
				bd.cierraBd();
				return Response.status(Status.BAD_REQUEST).header("error", "no existe la solicitud").build();
			} catch (ClassNotFoundException | SQLException e) {
				return Response.status(Status.BAD_REQUEST).header("error", e.getLocalizedMessage()).build();
			}
		}else {
			return Response.status(Status.BAD_REQUEST).header("error","Token caducado o inexistente").build();
		}
	}
}
