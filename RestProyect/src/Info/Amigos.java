package Info;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Amigos {
	private int idAmigo1;
	private int idAmigo2;
	
	public Amigos() {
		
	}
	public Amigos(int idAmigo1, int idAmigo2) {
		this.idAmigo1 = idAmigo1;
		this.idAmigo2 = idAmigo2;
	}
	public int getIdAmigo1() {
		return idAmigo1;
	}
	public void setIdAmigo1(int idAmigo1) {
		this.idAmigo1 = idAmigo1;
	}
	public int getIdAmigo2() {
		return idAmigo2;
	}
	public void setIdAmigo2(int idAmigo2) {
		this.idAmigo2 = idAmigo2;
	}
	
}
