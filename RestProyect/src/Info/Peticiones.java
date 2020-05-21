package Info;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Peticiones {
	private int id;
	private int idSolicitante;
	private int idSolicitado;
	
	public Peticiones() {
		
	}
	public Peticiones(int id, int idSolicitante, int idSolicitado) {
		super();
		this.id = id;
		this.idSolicitante = idSolicitante;
		this.idSolicitado = idSolicitado;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdSolicitante() {
		return idSolicitante;
	}
	public void setIdSolicitante(int idSolicitante) {
		this.idSolicitante = idSolicitante;
	}
	public int getIdSolicitado() {
		return idSolicitado;
	}
	public void setIdSolicitado(int idSolicitado) {
		this.idSolicitado = idSolicitado;
	}
	
}
