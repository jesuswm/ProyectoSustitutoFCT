package Info;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bd {
	Connection con=null;
	public void conecta(String bd) throws ClassNotFoundException, SQLException{
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://localhost:3306/"+bd;
		String username = "root";
		String password = "";
		Class.forName(driver);
		if (this.con==null)this.con = DriverManager.getConnection(url, username, password);
		if (this.con==null) System.out.println("No conectado a la bd");
	}
	
	public void cierraBd() throws SQLException{
		if (this.con!=null) this.con.close();
	}
	
	public ResultSet consulta(String query) throws SQLException{
		ResultSet rs=null;
		if (this.con!=null) {
			Statement st=this.con.createStatement();
			rs=st.executeQuery(query);
		} 
		return rs;
	}
	
	public int consulta2(String query) throws SQLException{
		int cant=-1;
		if (this.con!=null) {
			Statement st=this.con.createStatement();
			cant=st.executeUpdate(query);
		}	
		return cant;
	}
}
