package silviuCRMFormsJava;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.collections.map.HashedMap;

import silviuCRMClase.Angajat;
import silviuCRMClase.Contact;
import silviuCRMClase.LinieOferta;
import silviuCRMClase.Oferta;
import silviuCRMClase.Operatiune;
import silviuCRMClase.Produs;

@ManagedBean
@SessionScoped
@ViewScoped
public class OfertaFormClient {

	EntityManager em;
	private Oferta oferta = new Oferta();
	private Angajat angajat = new Angajat();
	private Contact contact = new Contact();
	private Produs produs = new Produs();
	private LinieOferta linieOferta = new LinieOferta();
	private List<Angajat> lstAngajati = new ArrayList<>();
	private List<Contact> lstContacte = new ArrayList<>();
	private List<Produs> lstProduse = new ArrayList<Produs>();
	private List<Oferta> lstOferte = new ArrayList<Oferta>();
	private List<Oferta> lstTotalaOferte = new ArrayList<Oferta>();
	private List<LinieOferta> lstLiniiOferta = new ArrayList<LinieOferta>();
	private Operatiune operatiune = new Operatiune();
	private OperatiuneForm operatiuneForm = new OperatiuneForm();
	private Integer idOferta;
	private EmailForm emailForm= new EmailForm();
	private Contact contactLogin= new Contact();

	public OfertaFormClient() {
		super();

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SilviuCRM");
		em = emf.createEntityManager();
	
		contactLogin=LoginFormStaticUser.contactLogin;
		initLstOferte();
	}



	public void initLstOferte() {

		lstTotalaOferte = em.createQuery("Select o from Oferta o").getResultList();
		for(Oferta o:lstTotalaOferte) {
			if(o.getContactOferta().equals(contactLogin)) {
				lstOferte.add(o);
			}
		}

	}


	public void salvareOferte(ActionEvent event) {
		
		
	

		em.getTransaction().begin();
		if (this.em.contains(this.oferta)) {
			this.em.merge(this.oferta);
			em.getTransaction().commit();
			
		}
		initLstOferte();

	}

	public void abandonOferta(ActionEvent event) {
		System.out.println("Ati Anulat Actiunea");
		em.clear();

	}

	
	public void opStergereOferta() {

		this.operatiune = new Operatiune();
		 this.operatiune.setCategorieOperatiune("Oferte");
		this.operatiune.setTipOperatiune("Stergere Oferta");
		   this.operatiune.setAngajatOperatiune(this.angajat);
		this.operatiune.setDescriereOperatiune(
				"Angajatul '" + angajat.getNumeAngajat() + "' a sters oferta cu ID:'" + idOferta + ""
						+ "' pe care a trimis-o clientului '" + this.oferta.getContactOferta().getNumeContact() + "'.");
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();
	}

	public void select(Oferta o) {
		this.oferta = o;
	}


	  private void trimiteEmailOfertaContact() {
			
			this.emailForm= new EmailForm();
			this.emailForm.addEmail();
			this.emailForm.getEmail().setAdresaEmail(this.contact.getAdresaEmailContact());
			this.emailForm.getEmail().setCategorieDestinatarEmail("Contact");
			this.emailForm.getEmail().setContinutEmail("Stimate dn/dna"+ this.contact.getNumeContact()+ ",\n"
					+ "Echipa SilviuCRM ti-a facut o oferta de nerefuzat!\n"
					+ "Acceseaza contul tau de client pe site-ul nostru 'www.SilviuCRM.ro' pentru mai multe detalii!\n"
					+ "Cu drag, \n"
					+ "Echipa SilviuCRM");
			
			this.emailForm.getEmail().setSubiectEmail("Oferta de la Echipa SilviuCRM");
			this.emailForm.getEmail().setContactEmail(this.contact);
	
			this.emailForm.salvareEmail();
			this.contact.getListaEmail_uriContact().add(this.emailForm.getEmail());
			
			this.emailForm.setSubiectEmail("Oferta de la Echipa SilviuCRM");
			this.emailForm.setMesajEmail("Stimate dn/dna "+ this.contact.getNumeContact()+",\n"
					+ "Echipa SilviuCRM ti-a facut o oferta de nerefuzat! \n"
					+ "Acceseaza contul tau de client pe site-ul nostru 'www.SilviuCRM.ro' pentru mai multe detalii!'\n"
					+ "Cu drag, \n"
					+ "Echipa SilviuCRM");
			this.emailForm.setReceptorEmail(this.contact.getAdresaEmailContact());
			//this.emailForm.trimiteEmail();
		}
	
	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	public Angajat getAngajat() {
		return angajat;
	}

	public void setAngajat(Angajat angajat) {
		this.angajat = angajat;
	}

	public Contact getContact() {
		return contact;
	}

	public LinieOferta getLinieOferta() {
		return linieOferta;
	}

	public Contact getContactLogin() {
		return contactLogin;
	}

	public void setContactLogin(Contact contactLogin) {
		this.contactLogin = contactLogin;
	}

	public void setLinieOferta(LinieOferta linieOferta) {
		this.linieOferta = linieOferta;
	}

	public List<LinieOferta> getLstLiniiOferta() {
		return lstLiniiOferta;
	}

	public void setLstLiniiOferta(List<LinieOferta> lstLiniiOferta) {
		this.lstLiniiOferta = lstLiniiOferta;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Produs getProdus() {
		return produs;
	}

	public void setProdus(Produs produs) {
		this.produs = produs;
	}

	public List<Angajat> getLstAngajati() {
		return lstAngajati;
	}

	public List<Oferta> getLstOferte() {
		return lstOferte;
	}

	public void setLstOferte(List<Oferta> lstOferte) {
		this.lstOferte = lstOferte;
	}

	public void setLstAngajati(List<Angajat> lstAngajati) {
		this.lstAngajati = lstAngajati;
	}

	public List<Contact> getLstContacte() {
		return lstContacte;
	}

	public void setLstContacte(List<Contact> lstContacte) {
		this.lstContacte = lstContacte;
	}

	public List<Produs> getLstProduse() {
		return lstProduse;
	}

	public void setLstProduse(List<Produs> lstProduse) {
		this.lstProduse = lstProduse;
	}

	public Operatiune getOperatiune() {
		return operatiune;
	}

	public void setOperatiune(Operatiune operatiune) {
		this.operatiune = operatiune;
	}

	public OperatiuneForm getOperatiuneForm() {
		return operatiuneForm;
	}

	public void setOperatiuneForm(OperatiuneForm operatiuneForm) {
		this.operatiuneForm = operatiuneForm;
	}

	public List<Oferta> getLstTotalaOferte() {
		return lstTotalaOferte;
	}

	public void setLstTotalaOferte(List<Oferta> lstTotalaOferte) {
		this.lstTotalaOferte = lstTotalaOferte;
	}

	public Integer getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(Integer idOferta) {
		this.idOferta = idOferta;
	}

	public EmailForm getEmailForm() {
		return emailForm;
	}

	public void setEmailForm(EmailForm emailForm) {
		this.emailForm = emailForm;
	}



}
