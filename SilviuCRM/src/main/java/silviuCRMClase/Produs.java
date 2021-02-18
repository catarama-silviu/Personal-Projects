package silviuCRMClase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
@Entity
public class Produs {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer idProdus;
	@ManyToOne
	private Angajat produsOwner;
	private Integer codProdus;
	private String numeProdus;
	private float pretBucataProdus;
	private float cotaTvaProdus;
	private String unitateDeMasuraProdus;
	private Integer cantitateProdusInStoc;
	@Temporal(DATE)
	private Date dataAdaugareProdus;
	private String descriereProdus;
	@OneToMany(mappedBy = "produsLinieOferta")
	private List<LinieOferta>lstLiniiOfertaProdus= new ArrayList<LinieOferta>();
	
	public Produs() {
		super();
	}

	public Integer getIdProdus() {
		return idProdus;
	}

	public void setIdProdus(Integer idProdus) {
		this.idProdus = idProdus;
	}

	public Angajat getProdusOwner() {
		return produsOwner;
	}

	public void setProdusOwner(Angajat produsOwner) {
		this.produsOwner = produsOwner;
	}

	public Integer getCodProdus() {
		return codProdus;
	}

	public List<LinieOferta> getLstLiniiOfertaProdus() {
		return lstLiniiOfertaProdus;
	}

	public void setLstLiniiOfertaProdus(List<LinieOferta> lstLiniiOfertaProdus) {
		this.lstLiniiOfertaProdus = lstLiniiOfertaProdus;
	}

	public void setCodProdus(Integer codProdus) {
		this.codProdus = codProdus;
	}

	public String getNumeProdus() {
		return numeProdus;
	}

	public void setNumeProdus(String numeProdus) {
		this.numeProdus = numeProdus;
	}

	public float getPretBucataProdus() {
		return pretBucataProdus;
	}

	public void setPretBucataProdus(float pretBucataProdus) {
		this.pretBucataProdus = pretBucataProdus;
	}

	public float getCotaTvaProdus() {
		return cotaTvaProdus;
	}

	public void setCotaTvaProdus(float cotaTvaProdus) {
		this.cotaTvaProdus = cotaTvaProdus;
	}

	public String getUnitateDeMasuraProdus() {
		return unitateDeMasuraProdus;
	}

	public void setUnitateDeMasuraProdus(String unitateDeMasuraProdus) {
		this.unitateDeMasuraProdus = unitateDeMasuraProdus;
	}

	public Integer getCantitateProdusInStoc() {
		return cantitateProdusInStoc;
	}

	public void setCantitateProdusInStoc(Integer cantitateProdusInStoc) {
		this.cantitateProdusInStoc = cantitateProdusInStoc;
	}

	public Date getDataAdaugareProdus() {
		return dataAdaugareProdus;
	}

	public void setDataAdaugareProdus(Date dataAdaugareProdus) {
		this.dataAdaugareProdus = dataAdaugareProdus;
	}

	public String getDescriereProdus() {
		return descriereProdus;
	}

	public void setDescriereProdus(String descriereProdus) {
		this.descriereProdus = descriereProdus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idProdus == null) ? 0 : idProdus.hashCode());
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
		Produs other = (Produs) obj;
		if (idProdus == null) {
			if (other.idProdus != null)
				return false;
		} else if (!idProdus.equals(other.idProdus))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produs [idProdus=" + idProdus + ", produsOwner=" + produsOwner.getNumeAngajat() + ", codProdus=" + codProdus
				+ ", numeProdus=" + numeProdus + ", pretBucataProdus=" + pretBucataProdus + ", cotaTvaProdus="
				+ cotaTvaProdus + ", unitateDeMasuraProdus=" + unitateDeMasuraProdus + ", cantitateProdusInStoc="
				+ cantitateProdusInStoc + ", dataAdaugareProdus=" + dataAdaugareProdus + ", descriereProdus="
				+ descriereProdus + "]";
	}
	
	
	
	
}
