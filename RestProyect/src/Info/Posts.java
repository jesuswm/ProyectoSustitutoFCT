package Info;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Posts {
	private int id;
	private int idCreador;
	private String contenido;
	private boolean privado;
	private Date fecha;
	
	public Posts() {
		
	}
	public Posts(int id, int idCreador, String contenido, boolean privado, Date fecha) {
		super();
		this.id = id;
		this.idCreador = idCreador;
		this.contenido = contenido;
		this.privado = privado;
		this.fecha = fecha;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdCreador() {
		return idCreador;
	}
	public void setIdCreador(int idCreador) {
		this.idCreador = idCreador;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public boolean isPrivado() {
		return privado;
	}
	public void setPrivado(boolean privado) {
		this.privado = privado;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
