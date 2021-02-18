package silviuCRMClase;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.ManyToOne;

@Entity
public class LinieOferta {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer idLinieOferta;
	@ManyToOne
	private Produs produsLinieOferta;
	@ManyToOne
	private Oferta ofertaLinieOferta;
	private Double cantitateProdusLinieOferta;
	private Double pretFaraTvaLinieOferta;
	private Double pretCuTvaLinieOferta;
	
	public LinieOferta() {
		super();
	}

	public Integer getIdLinieOferta() {
		return idLinieOferta;
	}

	public void setIdLinieOferta(Integer idLinieOferta) {
		this.idLinieOferta = idLinieOferta;
	}

	public Produs getProdusLinieOferta() {
		return produsLinieOferta;
	}

	public void setProdusLinieOferta(Produs produsLinieOferta) {
		this.produsLinieOferta = produsLinieOferta;
	}

	public Oferta getOfertaLinieOferta() {
		return ofertaLinieOferta;
	}

	public void setOfertaLinieOferta(Oferta ofertaLinieOferta) {
		this.ofertaLinieOferta = ofertaLinieOferta;
	}

	public Double getCantitateProdusLinieOferta() {
		return cantitateProdusLinieOferta;
	}

	public void setCantitateProdusLinieOferta(Double cantitateProdusLinieOferta) {
		this.cantitateProdusLinieOferta = cantitateProdusLinieOferta;
	}

	public Double getPretFaraTvaLinieOferta() {
		return pretFaraTvaLinieOferta;
	}

	public void setPretFaraTvaLinieOferta(Double pretFaraTvaLinieOferta) {
		this.pretFaraTvaLinieOferta = pretFaraTvaLinieOferta;
	}

	public Double getPretCuTvaLinieOferta() {
		return pretCuTvaLinieOferta;
	}

	public void setPretCuTvaLinieOferta(Double pretCuTvaLinieOferta) {
		this.pretCuTvaLinieOferta = pretCuTvaLinieOferta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLinieOferta == null) ? 0 : idLinieOferta.hashCode());
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
		LinieOferta other = (LinieOferta) obj;
		if (idLinieOferta == null) {
			if (other.idLinieOferta != null)
				return false;
		} else if (!idLinieOferta.equals(other.idLinieOferta))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LinieOferta [idLinieOferta=" + idLinieOferta + ", produsLinieOferta=" + produsLinieOferta.getNumeProdus()
				+ ", ofertaLinieOferta=" + ofertaLinieOferta.getNumeOferta() + ", cantitateProdusLinieOferta="
				+ cantitateProdusLinieOferta + ", pretFaraTvaLinieOferta=" + pretFaraTvaLinieOferta
				+ ", pretCuTvaLinieOferta=" + pretCuTvaLinieOferta + "]";
	}
	
	
	
	
	
}
