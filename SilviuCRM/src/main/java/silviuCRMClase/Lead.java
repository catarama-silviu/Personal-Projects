package silviuCRMClase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Enumerated;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.DETACH;

@Entity
public class Lead {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer idLead;
	private String numeLead;
	private String adresaEmailLead;
	private String leadStatus;
	private String nrTelefonLead;
	private String sursaLead;
	private String domeniuDeActivitateLead;
	private String adresaOrasLead;
	private String adresaTaraLead;
	@Temporal(DATE)
	private Date dataInscriereLead= new Date();
	@Temporal(DATE)
	private Date dataNasteriiLead=new Date();
	private String descriereLead;
	@ManyToOne
	private Angajat leadOwner;
	@OneToMany( mappedBy = "leadEmail", cascade = CascadeType.ALL)
	private List<Email>listaEmailLead= new ArrayList<>();

	
	
	
	
	
	

	



	public Lead() {
		super();
	}

	public Integer getIdLead() {
		return idLead;
	}

	public String getNumeLead() {
		return numeLead;
	}

	public void setNumeLead(String numeLead) {
		this.numeLead = numeLead;
	}

	public void setIdLead(Integer idLead) {
		this.idLead = idLead;
	}

	public String getAdresaEmailLead() {
		return adresaEmailLead;
	}

	public void setAdresaEmailLead(String adresaEmailLead) {
		this.adresaEmailLead = adresaEmailLead;
	}



	public String getLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}

	public String getNrTelefonLead() {
		return nrTelefonLead;
	}

	public void setNrTelefonLead(String nrTelefonLead) {
		this.nrTelefonLead = nrTelefonLead;
	}

	public String getSursaLead() {
		return sursaLead;
	}

	public void setSursaLead(String sursaLead) {
		this.sursaLead = sursaLead;
	}

	public String getDomeniuDeActivitateLead() {
		return domeniuDeActivitateLead;
	}

	public void setDomeniuDeActivitateLead(String domeniuDeActivitateLead) {
		this.domeniuDeActivitateLead = domeniuDeActivitateLead;
	}

	public String getAdresaOrasLead() {
		return adresaOrasLead;
	}

	public void setAdresaOrasLead(String adresaOrasLead) {
		this.adresaOrasLead = adresaOrasLead;
	}

	public String getAdresaTaraLead() {
		return adresaTaraLead;
	}

	public void setAdresaTaraLead(String adresaTaraLead) {
		this.adresaTaraLead = adresaTaraLead;
	}

	public Date getDataInscriereLead() {
		return dataInscriereLead;
	}

	public void setDataInscriereLead(Date dataInscriereLead) {
		this.dataInscriereLead = dataInscriereLead;
	}

	public Date getDataNasteriiLead() {
		return dataNasteriiLead;
	}

	public void setDataNasteriiLead(Date dataNasteriiLead) {
		this.dataNasteriiLead = dataNasteriiLead;
	}

	public String getDescriereLead() {
		return descriereLead;
	}

	public void setDescriereLead(String descriereLead) {
		this.descriereLead = descriereLead;
	}

	public Angajat getLeadOwner() {
		return leadOwner;
	}

	public void setLeadOwner(Angajat leadOwner) {
		this.leadOwner = leadOwner;
	}

	

	public List<Email> getListaEmailLead() {
		return listaEmailLead;
	}

	public void setListaEmailLead(List<Email> listaEmailLead) {
		this.listaEmailLead = listaEmailLead;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLead == null) ? 0 : idLead.hashCode());
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
		Lead other = (Lead) obj;
		if (idLead == null) {
			if (other.idLead != null)
				return false;
		} else if (!idLead.equals(other.idLead))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lead [idLead=" + idLead + ", numeLead=" + numeLead + ", adresaEmailLead=" + adresaEmailLead
				+ ", leadStatus=" + leadStatus + ", nrTelefonLead=" + nrTelefonLead + ", sursaLead=" + sursaLead
				+ ", domeniuDeActivitateLead=" + domeniuDeActivitateLead + ", adresaOrasLead=" + adresaOrasLead
				+ ", adresaTaraLead=" + adresaTaraLead + ", dataInscriereLead=" + dataInscriereLead
				+ ", dataNasteriiLead=" + dataNasteriiLead + ", descriereLead=" + descriereLead + ", leadOwner="
				+ leadOwner.getNumeAngajat() + "]";
	}
	
	
	
}
