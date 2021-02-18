package silviuCRMClase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.TIMESTAMP;
import javax.persistence.OneToOne;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.OneToMany;
import static javax.persistence.CascadeType.ALL;
@Entity
public class Comanda {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer idComanda;
	private String numeComanda;
	@ManyToOne
	private Angajat ownerComanda;
	@ManyToOne
	private Contact contactComanda;
	@OneToOne
	@JoinColumn(name = "idOferta")
	private Oferta ofertaComanda;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "comandaLinieComanda")
	private List<LinieComanda>lstLiniiComanda= new ArrayList<>();
	private Double pretComandaFaraTva;
	private Double pretComandaCuTva;
	@Temporal(TIMESTAMP)
	private Date dataInregistrareComanda= new Date();
	private String statusComanda;
	private String adresaComanda;
	private String descriereComanda;
	public Comanda() {
		super();
	}
	public Integer getIdComanda() {
		return idComanda;
	}
	public void setIdComanda(Integer idComanda) {
		this.idComanda = idComanda;
	}
	public String getNumeComanda() {
		return numeComanda;
	}
	public void setNumeComanda(String numeComanda) {
		this.numeComanda = numeComanda;
	}
	public Angajat getOwnerComanda() {
		return ownerComanda;
	}
	public void setOwnerComanda(Angajat ownerComanda) {
		this.ownerComanda = ownerComanda;
	}
	public Contact getContactComanda() {
		return contactComanda;
	}
	public void setContactComanda(Contact contactComanda) {
		this.contactComanda = contactComanda;
	}
	public Oferta getOfertaComanda() {
		return ofertaComanda;
	}
	public void setOfertaComanda(Oferta ofertaComanda) {
		this.ofertaComanda = ofertaComanda;
	}
	public List<LinieComanda> getLstLiniiComanda() {
		return lstLiniiComanda;
	}
	public void setLstLiniiComanda(List<LinieComanda> lstLiniiComanda) {
		this.lstLiniiComanda = lstLiniiComanda;
	}
	public Date getDataInregistrareComanda() {
		return dataInregistrareComanda;
	}
	public void setDataInregistrareComanda(Date dataInregistrareComanda) {
		this.dataInregistrareComanda = dataInregistrareComanda;
	}
	
	public Double getPretComandaFaraTva() {
		return pretComandaFaraTva;
	}
	public void setPretComandaFaraTva(Double pretComandaFaraTva) {
		this.pretComandaFaraTva = pretComandaFaraTva;
	}
	public Double getPretComandaCuTva() {
		return pretComandaCuTva;
	}
	public void setPretComandaCuTva(Double pretComandaCuTva) {
		this.pretComandaCuTva = pretComandaCuTva;
	}
	public String getStatusComanda() {
		return statusComanda;
	}
	public void setStatusComanda(String statusComanda) {
		this.statusComanda = statusComanda;
	}
	public String getAdresaComanda() {
		return adresaComanda;
	}
	public void setAdresaComanda(String adresaComanda) {
		this.adresaComanda = adresaComanda;
	}
	public String getDescriereComanda() {
		return descriereComanda;
	}
	public void setDescriereComanda(String descriereComanda) {
		this.descriereComanda = descriereComanda;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idComanda == null) ? 0 : idComanda.hashCode());
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
		Comanda other = (Comanda) obj;
		if (idComanda == null) {
			if (other.idComanda != null)
				return false;
		} else if (!idComanda.equals(other.idComanda))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Comanda [idComanda=" + idComanda + ", numeComanda=" + numeComanda + ", ownerComanda=" + ownerComanda.getNumeAngajat()
				+ ", contactComanda=" + contactComanda.getNumeContact() + ", ofertaComanda=" + ofertaComanda.getNumeOferta() + ", lstLiniiComanda="
				+ lstLiniiComanda + ", dataInregistrareComanda=" + dataInregistrareComanda + ", statusComanda="
				+ statusComanda + ", adresaComanda=" + adresaComanda + ", descriereComanda=" + descriereComanda + "]";
	}
	
	
	
	
}
