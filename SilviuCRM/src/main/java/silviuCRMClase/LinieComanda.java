package silviuCRMClase;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
@Entity
public class LinieComanda {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer idLinieComanda;
	@ManyToOne
	private Produs produsLinieComanda;
	@ManyToOne
	private Comanda comandaLinieComanda;
	private Double cantitateProdusLinieComanda;
	private Double pretFaraTvaLinieComanda;
	private Double pretCuTvaLinieComanda;
	public LinieComanda() {
		super();
	}
	public Integer getIdLinieComanda() {
		return idLinieComanda;
	}
	public void setIdLinieComanda(Integer idLinieComanda) {
		this.idLinieComanda = idLinieComanda;
	}
	public Produs getProdusLinieComanda() {
		return produsLinieComanda;
	}
	public void setProdusLinieComanda(Produs produsLinieComanda) {
		this.produsLinieComanda = produsLinieComanda;
	}
	public Comanda getComandaLinieComanda() {
		return comandaLinieComanda;
	}
	public void setComandaLinieComanda(Comanda comandaLinieComanda) {
		this.comandaLinieComanda = comandaLinieComanda;
	}
	public Double getCantitateProdusLinieComanda() {
		return cantitateProdusLinieComanda;
	}
	public void setCantitateProdusLinieComanda(Double cantitateProdusLinieComanda) {
		this.cantitateProdusLinieComanda = cantitateProdusLinieComanda;
	}
	public Double getPretFaraTvaLinieComanda() {
		return pretFaraTvaLinieComanda;
	}
	public void setPretFaraTvaLinieComanda(Double pretFaraTvaLinieComanda) {
		this.pretFaraTvaLinieComanda = pretFaraTvaLinieComanda;
	}
	public Double getPretCuTvaLinieComanda() {
		return pretCuTvaLinieComanda;
	}
	public void setPretCuTvaLinieComanda(Double pretCuTvaLinieComanda) {
		this.pretCuTvaLinieComanda = pretCuTvaLinieComanda;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLinieComanda == null) ? 0 : idLinieComanda.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinieComanda other = (LinieComanda) obj;
		if (idLinieComanda == null) {
			if (other.idLinieComanda != null)
				return false;
		} else if (!idLinieComanda.equals(other.idLinieComanda))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LinieComanda [idLinieComanda=" + idLinieComanda + ", produsLinieComanda=" + produsLinieComanda.getNumeProdus()
				+ ", comandaLinieComanda=" + comandaLinieComanda.getNumeComanda()+ ", cantitateProdusLinieComanda="
				+ cantitateProdusLinieComanda + ", pretFaraTvaLinieComanda=" + pretFaraTvaLinieComanda
				+ ", pretCuTvaLinieComanda=" + pretCuTvaLinieComanda + "]";
	}
	
	
	
	
}
