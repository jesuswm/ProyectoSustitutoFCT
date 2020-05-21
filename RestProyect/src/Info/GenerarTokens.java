package Info;

import java.sql.ResultSet;
import java.sql.SQLException;


public class GenerarTokens {
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public static String generar(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	public static Boolean comprobarCaducidad(String token) {
		boolean valido=false;
		try {
			Bd bd=new Bd();
			bd.conecta("proyecto_fct");
			ResultSet rs=bd.consulta("select NOW() < (select tokens.caducidad FROM tokens where token='"+token+"')");
			try {
				while (rs.next()) {
					valido=rs.getBoolean(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rs.close();
			bd.cierraBd();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valido;
	}
	public static int obtenerIdUsuario(String token) {
		int id=-1;
		try {
			Bd bd=new Bd();
			bd.conecta("proyecto_fct");
			ResultSet rs=bd.consulta("select id_usuario from tokens where token='"+token+"'");
			try {
				while (rs.next()) {
					id=rs.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rs.close();
			bd.cierraBd();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
}
