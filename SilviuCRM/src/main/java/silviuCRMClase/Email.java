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
import javax.persistence.Enumerated;
import static javax.persistence.EnumType.STRING;
import javax.persistence.OneToMany;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Basic;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.TIMESTAMP;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.DETACH;
@Entity



public  class Email {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer idEmail;
	private String adresaEmail;
	private String continutEmail;
	private String subiectEmail;
	@ManyToOne
	private Angajat angajatEmail;
	@Temporal(TIMESTAMP)
	@Basic
	private Date dataTrimitereEmail;
	@ManyToOne
	private Lead leadEmail;
	@ManyToOne
	private Contact contactEmail;
	private String categorieDestinatarEmail;
	
	
	






	public Email() {
		super();
	}


	public Integer getIdEmail() {
		return idEmail;
	}


	public void setIdEmail(Integer idEmail) {
		this.idEmail = idEmail;
	}


	public String getAdresaEmail() {
		return adresaEmail;
	}


	public void setAdresaEmail(String adresaEmail) {
		this.adresaEmail = adresaEmail;
	}


	public String getContinutEmail() {
		return continutEmail;
	}


	public Angajat getAngajatEmail() {
		return angajatEmail;
	}


	public void setAngajatEmail(Angajat angajatEmail) {
		this.angajatEmail = angajatEmail;
	}


	public Lead getLeadEmail() {
		return leadEmail;
	}


	public String getSubiectEmail() {
		return subiectEmail;
	}


	public void setSubiectEmail(String subiectEmail) {
		this.subiectEmail = subiectEmail;
	}


	public void setLeadEmail(Lead leadEmail) {
		this.leadEmail = leadEmail;
	}


	public Contact getContactEmail() {
		return contactEmail;
	}


	public void setContactEmail(Contact contactEmail) {
		this.contactEmail = contactEmail;
	}


	public void setContinutEmail(String continutEmail) {
		this.continutEmail = continutEmail;
	}


	public String getCategorieDestinatarEmail() {
		return categorieDestinatarEmail;
	}


	public void setCategorieDestinatarEmail(String categorieDestinatarEmail) {
		this.categorieDestinatarEmail = categorieDestinatarEmail;
	}


	


	public Date getDataTrimitereEmail() {
		return dataTrimitereEmail;
	}


	public void setDataTrimitereEmail(Date dataTrimitereEmail) {
		this.dataTrimitereEmail = dataTrimitereEmail;
	}





	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEmail == null) ? 0 : idEmail.hashCode());
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
		Email other = (Email) obj;
		if (idEmail == null) {
			if (other.idEmail != null)
				return false;
		} else if (!idEmail.equals(other.idEmail))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Email [idEmail=" + idEmail + ", adresaEmail=" + adresaEmail + ", continutEmail=" + continutEmail
				+ ", emailAngajat=" + angajatEmail.getNumeAngajat() + ", dataTrimitereEmail=" + dataTrimitereEmail + ", categorieEmail="
				+ getCategorieDestinatarEmail() + "]";
	}
			
			
			
			
		
	

}
