package silviuCRMClase;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Enumerated;
import static javax.persistence.EnumType.STRING;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.TemporalType.TIME;
import static javax.persistence.TemporalType.TIMESTAMP;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.CascadeType.MERGE;
import org.eclipse.persistence.annotations.JoinFetch;
import static org.eclipse.persistence.annotations.JoinFetchType.INNER;
import static org.eclipse.persistence.annotations.JoinFetchType.OUTER;
@Entity
public class Contact {
  @Id
@GeneratedValue(strategy = IDENTITY)
private Integer idContact;
  private String numeContact;
  private String adresaEmailContact;
  private String numarTelefonContact;

private String sursaLeadContact;
@Temporal(DATE)
private Date dataNasteriiContact;
@Temporal(DATE)
private Date dataInscrieriiContact;
  private String adresaOrasContact;
  private String adresaTaraContact;
  private String descriereContact;
  @ManyToOne
private Angajat ContactOwner;


  
  @OneToMany(mappedBy = "contactEmail",  orphanRemoval = true, cascade = CascadeType.ALL)
private List<Email> listaEmail_uriContact = new  ArrayList<>();


  @OneToMany(mappedBy = "contactTicket" , cascade = CascadeType.ALL)
private List<Ticket> listaTicketeContact = new  ArrayList<>();
  @OneToMany(mappedBy = "contactOferta" , cascade = CascadeType.ALL)
private List<Oferta>lstOferteContact= new ArrayList<Oferta>();
  @OneToMany( mappedBy = "contactComanda" , cascade = CascadeType.ALL)
private List<Comanda>lstComenziContact= new ArrayList<Comanda>();
  
  @OneToMany(mappedBy = "contactOperatiune", cascade = CascadeType.ALL)
private List<Operatiune>lstOperatiuniContact= new ArrayList<Operatiune>();
  
  


public Contact() {
	super();
}

public String transformareData(String data) {
	 SimpleDateFormat df= new SimpleDateFormat("dd-MMM-yyyy");
	return df.format(data);
	 
}

public Integer getIdContact() {
	return idContact;
}

public void setIdContact(Integer idContact) {
	this.idContact = idContact;
}

public List<Operatiune> getLstOperatiuniContact() {
	return lstOperatiuniContact;
}

public void setLstOperatiuniContact(List<Operatiune> lstOperatiuniContact) {
	this.lstOperatiuniContact = lstOperatiuniContact;
}

public List<Comanda> getLstComenziContact() {
	return lstComenziContact;
}

public void setLstComenziContact(List<Comanda> lstComenziContact) {
	this.lstComenziContact = lstComenziContact;
}

public String getNumeContact() {
	return numeContact;
}

public void setNumeContact(String numeContact) {
	this.numeContact = numeContact;
}

public List<Oferta> getLstOferteContact() {
	return lstOferteContact;
}

public void setLstOferteContact(List<Oferta> lstOferteContact) {
	this.lstOferteContact = lstOferteContact;
}

public String getAdresaEmailContact() {
	return adresaEmailContact;
}

public void setAdresaEmailContact(String adresaEmailContact) {
	this.adresaEmailContact = adresaEmailContact;
}



public String getNumarTelefonContact() {
	return numarTelefonContact;
}



public void setNumarTelefonContact(String numarTelefonContact) {
	this.numarTelefonContact = numarTelefonContact;
}

public String getSursaLeadContact() {
	return sursaLeadContact;
}

public void setSursaLeadContact(String sursaLeadContact) {
	this.sursaLeadContact = sursaLeadContact;
}

public Date getDataNasteriiContact() {
	return dataNasteriiContact;
}

public void setDataNasteriiContact(Date dataNasteriiContact) {
	this.dataNasteriiContact= dataNasteriiContact;
			}

public Date getDataInscrieriiContact() {
	return dataInscrieriiContact;
}

public void setDataInscrieriiContact(Date dataInscrieriiContact) {
	this.dataInscrieriiContact = dataInscrieriiContact;
}

public String getAdresaOrasContact() {
	return adresaOrasContact;
}

public void setAdresaOrasContact(String adresaOrasContact) {
	this.adresaOrasContact = adresaOrasContact;
}

public String getAdresaTaraContact() {
	return adresaTaraContact;
}

public void setAdresaTaraContact(String adresaTaraContact) {
	this.adresaTaraContact = adresaTaraContact;
}

public String getDescriereContact() {
	return descriereContact;
}

public void setDescriereContact(String descriereContact) {
	this.descriereContact = descriereContact;
}

public Angajat getContactOwner() {
	return ContactOwner;
}

public void setContactOwner(Angajat contactOwner) {
	ContactOwner = contactOwner;
}





public List<Email> getListaEmail_uriContact() {
	return listaEmail_uriContact;
}

public void setListaEmail_uriContact(List<Email> listaEmail_uriContact) {
	this.listaEmail_uriContact = listaEmail_uriContact;
}



public List<Ticket> getListaTicketeContact() {
	return listaTicketeContact;
}

public void setListaTicketeContact(List<Ticket> listaTicketeContact) {
	this.listaTicketeContact = listaTicketeContact;
}



@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((idContact == null) ? 0 : idContact.hashCode());
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
	Contact other = (Contact) obj;
	if (idContact == null) {
		if (other.idContact != null)
			return false;
	} else if (!idContact.equals(other.idContact))
		return false;
	return true;
}

@Override
public String toString() {
	return "Contact [idContact=" + idContact + ", numeContact=" + numeContact + ", adresaEmailContact="
			+ adresaEmailContact + ", numarTelefonContact=" + numarTelefonContact + ", sursaLeadContact="
			+ sursaLeadContact + ", dataNasteriiContact=" + dataNasteriiContact + ", dataInscrieriiContact="
			+ dataInscrieriiContact + ", adresaOrasContact=" + adresaOrasContact + ", adresaTaraContact="
			+ adresaTaraContact + ", descriereContact=" + descriereContact + "]";
}
 

  
  
	
	
}
