package silviuCRMClase;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Enumerated;
import static javax.persistence.EnumType.STRING;
import javax.persistence.Basic;
import javax.persistence.CascadeType;

import static javax.persistence.FetchType.EAGER;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.TIMESTAMP;
import static javax.persistence.CascadeType.ALL;
@Entity
public  class Operatiune {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer idOperatiune;
	@Temporal(TIMESTAMP)
	private Date dataOperatiune=new Date();
	private String tipOperatiune;
	private String descriereOperatiune;
	private String categorieOperatiune;
	@ManyToOne
	private Angajat angajatOperatiune;
	@ManyToOne
	private Contact contactOperatiune;
	
	



	public Operatiune() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getIdOperatiune() {
		return idOperatiune;
	}


	public void setIdOperatiune(Integer idOperatiune) {
		this.idOperatiune = idOperatiune;
	}


	
	


	public String getDescriereOperatiune() {
		return descriereOperatiune;
	}






	public String getCategorieOperatiune() {
		return categorieOperatiune;
	}


	public void setCategorieOperatiune(String categorieOperatiune) {
		this.categorieOperatiune = categorieOperatiune;
	}


	public void setDescriereOperatiune(String descriereOperatiune) {
		this.descriereOperatiune = descriereOperatiune;
	}


	public Date getDataOperatiune() {
		return dataOperatiune;
	}




	


	public Angajat getAngajatOperatiune() {
		return angajatOperatiune;
	}


	public void setAngajatOperatiune(Angajat angajatOperatiune) {
		this.angajatOperatiune = angajatOperatiune;
	}


	public Contact getContactOperatiune() {
		return contactOperatiune;
	}


	public void setContactOperatiune(Contact contactOperatiune) {
		this.contactOperatiune = contactOperatiune;
	}


	public void setDataOperatiune(Date dataOperatiune) {
		this.dataOperatiune = dataOperatiune;
	}


	public String getTipOperatiune() {
		return tipOperatiune;
	}


	public void setTipOperatiune(String tipOperatiune) {
		this.tipOperatiune = tipOperatiune;
	}






	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idOperatiune == null) ? 0 : idOperatiune.hashCode());
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
		Operatiune other = (Operatiune) obj;
		if (idOperatiune == null) {
			if (other.idOperatiune != null)
				return false;
		} else if (!idOperatiune.equals(other.idOperatiune))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Operatiune [idOperatiune=" + idOperatiune + ", tipOperatiune=" + tipOperatiune
				+ ", descriereOperatiune=" + descriereOperatiune + "]";
	}
	
	
	
}
