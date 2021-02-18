package silviuCRMClase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Enumerated;
import static javax.persistence.EnumType.STRING;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

import static javax.persistence.TemporalType.DATE;
import javax.persistence.OneToMany;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Basic;
import javax.persistence.CascadeType;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.CascadeType.MERGE;
import org.eclipse.persistence.annotations.JoinFetch;
import static org.eclipse.persistence.annotations.JoinFetchType.INNER;
import static org.eclipse.persistence.annotations.JoinFetchType.OUTER;
@Entity
public class Angajat {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer idAngajat;
	private String nrTelefonAngajat;
	private String adresaAngajat;
	@Temporal(DATE)
	private Date dataNastereAngajat;
	private String numeAngajat;	
	private String rolAngajat;	
	@Temporal(DATE)
	private Date inregistrareAngajat;
	private String adresaEmailAngajat;	
	private String statusCont;
	
	@OneToMany( mappedBy = "leadOwner", cascade = CascadeType.ALL)
	private List<Lead> listaLeadsAngajat= new  ArrayList<>();
	
	@OneToMany(mappedBy = "ContactOwner", cascade = CascadeType.ALL)
	private List<Contact>listaContacteAngajat = new  ArrayList<>();	
	


	@OneToMany(cascade = CascadeType.ALL, mappedBy = "angajatEmail")
	private List<Email>listaEmailuriAngajat= new ArrayList<Email>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "angajatRaspunsTicket")
	private List<RaspunsTicket>listaRaspunsuriTicketAngajat= new ArrayList<RaspunsTicket>();
	
	@OneToMany(mappedBy = "produsOwner", cascade = CascadeType.ALL)
	private List<Produs>listaProduseAdaugateAngajat=  new ArrayList<Produs>();
	
	@OneToMany(mappedBy = "ownerOferta", cascade = CascadeType.ALL)
	private List<Oferta>lstOferteTrimiseAngajat= new ArrayList<Oferta>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ownerComanda")
	private List<Comanda>lstComenziAngajat= new ArrayList<Comanda>();
	
	@OneToMany(mappedBy = "angajatOperatiune",cascade = CascadeType.ALL)
	private List<Operatiune> lstOperatiuniAngajat= new ArrayList<Operatiune>();
	




	public Angajat() {
		super();
	}


	public Integer getIdAngajat() {
		return idAngajat;
	}


	public void setIdAngajat(Integer idAngajat) {
		this.idAngajat = idAngajat;
	}





	public String getNumeAngajat() {
		return numeAngajat;
	}


	public void setNumeAngajat(String numeAngajat) {
		this.numeAngajat = numeAngajat;
	}


	public String getNrTelefonAngajat() {
		return nrTelefonAngajat;
	}


	public void setNrTelefonAngajat(String nrTelefonAngajat) {
		this.nrTelefonAngajat = nrTelefonAngajat;
	}


	public String getStatusCont() {
		return statusCont;
	}


	public void setStatusCont(String statusCont) {
		this.statusCont = statusCont;
	}


	public List<Operatiune> getLstOperatiuniAngajat() {
		return lstOperatiuniAngajat;
	}


	public void setLstOperatiuniAngajat(List<Operatiune> lstOperatiuniAngajat) {
		this.lstOperatiuniAngajat = lstOperatiuniAngajat;
	}


	public List<Oferta> getLstOferteTrimiseAngajat() {
		return lstOferteTrimiseAngajat;
	}


	public List<Comanda> getLstComenziAngajat() {
		return lstComenziAngajat;
	}




	public void setLstComenziAngajat(List<Comanda> lstComenziAngajat) {
		this.lstComenziAngajat = lstComenziAngajat;
	}


	public void setLstOferteTrimiseAngajat(List<Oferta> lstOferteTrimiseAngajat) {
		this.lstOferteTrimiseAngajat = lstOferteTrimiseAngajat;
	}


	public String getAdresaAngajat() {
		return adresaAngajat;
	}


	public void setAdresaAngajat(String adresaAngajat) {
		this.adresaAngajat = adresaAngajat;
	}


	public Date getDataNastereAngajat() {
		return dataNastereAngajat;
	}


	public List<Produs> getListaProduseAdaugateAngajat() {
		return listaProduseAdaugateAngajat;
	}


	public void setListaProduseAdaugateAngajat(List<Produs> listaProduseAdaugateAngajat) {
		this.listaProduseAdaugateAngajat = listaProduseAdaugateAngajat;
	}


	public void setDataNastereAngajat(Date dataNastereAngajat) {
		this.dataNastereAngajat = dataNastereAngajat;
	}


	public String getRolAngajat() {
		return rolAngajat;
	}


	public void setRolAngajat(String rolAngajat) {
		this.rolAngajat = rolAngajat;
	}


	public Date getInregistrareAngajat() {
		return inregistrareAngajat;
	}


	public void setInregistrareAngajat(Date inregistrareAngajat) {
		this.inregistrareAngajat = inregistrareAngajat;
	}


	public String getAdresaEmailAngajat() {
		return adresaEmailAngajat;
	}


	public void setAdresaEmailAngajat(String adresaEmailAngajat) {
		this.adresaEmailAngajat = adresaEmailAngajat;
	}


	public List<Lead> getListaLeadsAngajat() {
		return listaLeadsAngajat;
	}


	public void setListaLeadsAngajat(List<Lead> listaLeadsAngajat) {
		this.listaLeadsAngajat = listaLeadsAngajat;
	}


	public List<Contact> getListaContacteAngajat() {
		return listaContacteAngajat;
	}


	public void setListaContacteAngajat(List<Contact> listaContacteAngajat) {
		this.listaContacteAngajat = listaContacteAngajat;
	}





	public List<Email> getListaEmailuriAngajat() {
		return listaEmailuriAngajat;
	}


	public void setListaEmailuriAngajat(List<Email> listaEmailuriAngajat) {
		this.listaEmailuriAngajat = listaEmailuriAngajat;
	}


	public List<RaspunsTicket> getListaRaspunsuriTicketAngajat() {
		return listaRaspunsuriTicketAngajat;
	}


	public void setListaRaspunsuriTicketAngajat(List<RaspunsTicket> listaRaspunsuriTicketAngajat) {
		this.listaRaspunsuriTicketAngajat = listaRaspunsuriTicketAngajat;
	}


	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAngajat == null) ? 0 : idAngajat.hashCode());
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
		Angajat other = (Angajat) obj;
		if (idAngajat == null) {
			if (other.idAngajat != null)
				return false;
		} else if (!idAngajat.equals(other.idAngajat))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Angajat [idAngajat=" + idAngajat + ", numeAngajat=" + numeAngajat
				+ ", rolAngajat=" + rolAngajat + ", inregistrareAngajat=" + inregistrareAngajat
				+ ", adresaEmailAngajat=" + adresaEmailAngajat + "]";
	}



	
	
	
	
	
}