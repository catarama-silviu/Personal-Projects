package silviuCRMClase;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;
import javax.persistence.ManyToOne;
@Entity
public class RaspunsTicket {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer idRaspunsTicket;
	@Temporal(DATE)
	private Date dataRaspunsTicket;
    @ManyToOne
	private Ticket ticket;
    @ManyToOne
	private Angajat angajatRaspunsTicket;
    private String continutRaspunsEmail;
   




	public RaspunsTicket() {
		super();
	}

	public Integer getIdRaspunsTicket() {
		return idRaspunsTicket;
	}

	public void setIdRaspunsTicket(Integer idRaspunsTicket) {
		this.idRaspunsTicket = idRaspunsTicket;
	}

	public Date getDataRaspunsTicket() {
		return dataRaspunsTicket;
	}
	
	


	public Angajat getAngajatRaspunsTicket() {
		return angajatRaspunsTicket;
	}

	public void setAngajatRaspunsTicket(Angajat angajatRaspunsTicket) {
		this.angajatRaspunsTicket = angajatRaspunsTicket;
	}

	public void setDataRaspunsTicket(Date dataRaspunsTicket) {
		this.dataRaspunsTicket = dataRaspunsTicket;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public String getContinutRaspunsEmail() {
		return continutRaspunsEmail;
	}

	public void setContinutRaspunsEmail(String continutRaspunsEmail) {
		this.continutRaspunsEmail = continutRaspunsEmail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idRaspunsTicket == null) ? 0 : idRaspunsTicket.hashCode());
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
		RaspunsTicket other = (RaspunsTicket) obj;
		if (idRaspunsTicket == null) {
			if (other.idRaspunsTicket != null)
				return false;
		} else if (!idRaspunsTicket.equals(other.idRaspunsTicket))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RaspunsTicket [idRaspunsTicket=" + idRaspunsTicket + ", dataRaspunsTicket=" + dataRaspunsTicket
				+ ", ticket=" + ticket.getContinutTicket() + ", continutRaspunsEmail=" + continutRaspunsEmail + "]";
	}
    
    
	
}
