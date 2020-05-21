package Info;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuarios {
	private int id;
	private String email;
	private String pass;
	private String nombre;
	
	public Usuarios() {
		
	}
	

	public Usuarios(int id, String email, String nombre) {
		super();
		this.id = id;
		this.email = email;
		this.nombre = nombre;
	}


	public Usuarios(int id, String email, String pass, String nombre) {
		super();
		this.id = id;
		this.email = email;
		this.pass = pass;
		this.nombre = nombre;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
