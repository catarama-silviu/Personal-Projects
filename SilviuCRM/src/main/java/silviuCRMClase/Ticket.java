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
@Entity
public class Ticket {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer idTicket;
	@Temporal(DATE)
	private Date dataDeschidereTicket;
	@Temporal(DATE)
	private Date dataInchidereTicket;
	private String continutTicket;
	@ManyToOne
	private Contact contactTicket;
	private String statusTicket;
	private String categorieTicket;
	
	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
	private List<RaspunsTicket> listaRaspunsuriTicket = new ArrayList<>();
	

	

	public Ticket() {
		super();
	}

	public Integer getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(Integer idTicket) {
		this.idTicket = idTicket;
	}

	public Date getDataDeschidereTicket() {
		return dataDeschidereTicket;
	}

	public void setDataDeschidereTicket(Date dataDeschidereTicket) {
		this.dataDeschidereTicket = dataDeschidereTicket;
	}

	public Date getDataInchidereTicket() {
		return dataInchidereTicket;
	}

	public void setDataInchidereTicket(Date dataInchidereTicket) {
		this.dataInchidereTicket = dataInchidereTicket;
	}

	public String getContinutTicket() {
		return continutTicket;
	}

	public void setContinutTicket(String continutTicket) {
		this.continutTicket = continutTicket;
	}

	public Contact getContactTicket() {
		return contactTicket;
	}

	public void setContactTicket(Contact contactTicket) {
		this.contactTicket = contactTicket;
	}


	public String getStatusTicket() {
		return statusTicket;
	}

	public void setStatusTicket(String statusTicket) {
		this.statusTicket = statusTicket;
	}

	public String getCategorieTicket() {
		return categorieTicket;
	}

	public void setCategorieTicket(String categorieTicket) {
		this.categorieTicket = categorieTicket;
	}

	public List<RaspunsTicket> getListaRaspunsuriTicket() {
		return listaRaspunsuriTicket;
	}

	public void setListaRaspunsuriTicket(List<RaspunsTicket> listaRaspunsuriTicket) {
		this.listaRaspunsuriTicket = listaRaspunsuriTicket;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTicket == null) ? 0 : idTicket.hashCode());
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
		Ticket other = (Ticket) obj;
		if (idTicket == null) {
			if (other.idTicket != null)
				return false;
		} else if (!idTicket.equals(other.idTicket))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ticket [idTicket=" + idTicket + ", dataDeschidereTicket=" + dataDeschidereTicket
				+ ", dataInchidereTicket=" + dataInchidereTicket + ", continutTicket=" + continutTicket
				+ ", contactTicket=" + contactTicket.getNumeContact()  + "]";
	}
	
	
	
	
}
