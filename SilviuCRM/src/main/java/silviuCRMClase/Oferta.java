package silviuCRMClase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;
import javax.persistence.Basic;
import javax.persistence.CascadeType;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.ManyToMany;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import static javax.persistence.CascadeType.PERSIST;
@Entity
public class Oferta {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer idOferta;
	@ManyToOne
	private Angajat ownerOferta= new Angajat();
	@ManyToOne
	private Contact contactOferta= new Contact();
	
	@OneToMany(mappedBy = "ofertaLinieOferta", cascade = CascadeType.ALL)
	private List<LinieOferta>lstLiniiProdusOferte= new ArrayList<LinieOferta>();
	private String numeOferta;
	@Temporal(DATE)
	private Date dataInregistrareOferta;
	@Temporal(DATE)
	private Date dataLimitaValabilitateOferta;
	private Double pretOfertaFaraTva;
	private Double pretOfertaCuTva;
	private String descriereOferta;
	
	public Oferta() {
		super();
	}

	public Integer getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(Integer idOferta) {
		this.idOferta = idOferta;
	}

	public Angajat getOwnerOferta() {
		return ownerOferta;
	}

	public void setOwnerOferta(Angajat ownerOferta) {
		this.ownerOferta = ownerOferta;
	}

	public Contact getContactOferta() {
		return contactOferta;
	}

	public List<LinieOferta> getLstLiniiProdusOferte() {
		return lstLiniiProdusOferte;
	}

	public void setLstLiniiProdusOferte(List<LinieOferta> lstLiniiProdusOferte) {
		this.lstLiniiProdusOferte = lstLiniiProdusOferte;
	}

	public void setContactOferta(Contact contactOferta) {
		this.contactOferta = contactOferta;
	}


	public String getNumeOferta() {
		return numeOferta;
	}

	public void setNumeOferta(String numeOferta) {
		this.numeOferta = numeOferta;
	}

	public Date getDataInregistrareOferta() {
		return dataInregistrareOferta;
	}

	public void setDataInregistrareOferta(Date dataInregistrareOferta) {
		this.dataInregistrareOferta = dataInregistrareOferta;
	}

	public Date getDataLimitaValabilitateOferta() {
		return dataLimitaValabilitateOferta;
	}

	public void setDataLimitaValabilitateOferta(Date dataLimitaValabilitateOferta) {
		this.dataLimitaValabilitateOferta = dataLimitaValabilitateOferta;
	}

	public Double getPretOfertaFaraTva() {
		return pretOfertaFaraTva;
	}

	public void setPretOfertaFaraTva(Double pretOfertaFaraTva) {
		this.pretOfertaFaraTva = pretOfertaFaraTva;
	}

	public Double getPretOfertaCuTva() {
		return pretOfertaCuTva;
	}

	public void setPretOfertaCuTva(Double pretOfertaCuTva) {
		this.pretOfertaCuTva = pretOfertaCuTva;
	}

	public String getDescriereOferta() {
		return descriereOferta;
	}

	public void setDescriereOferta(String descriereOferta) {
		this.descriereOferta = descriereOferta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idOferta == null) ? 0 : idOferta.hashCode());
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
		Oferta other = (Oferta) obj;
		if (idOferta == null) {
			if (other.idOferta != null)
				return false;
		} else if (!idOferta.equals(other.idOferta))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Oferta [idOferta=" + idOferta + ", ownerOferta=" + ownerOferta.getNumeAngajat() + ", contactOferta=" + contactOferta.getNumeContact()
				+ ", numeOferta=" + numeOferta + ", dataInregistrareOferta=" + dataInregistrareOferta
				+ ", dataLimitaValabilitateOferta=" + dataLimitaValabilitateOferta + ", pretOfertaFaraTva="
				+ pretOfertaFaraTva + ", pretOfertaCuTva=" + pretOfertaCuTva + ", descriereOferta=" + descriereOferta
				+ "]";
	}
	
	
	
}
