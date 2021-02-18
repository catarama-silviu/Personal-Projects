package silviuCRMFormsJava;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import silviuCRMClase.Angajat;
import silviuCRMClase.Contact;
import silviuCRMClase.Lead;
import silviuCRMClase.LoginCreateAccount;
import silviuCRMClase.Operatiune;

@ManagedBean
@SessionScoped
public class ContactForm {

	EntityManager em;
	private Contact contact = new Contact();
	private Angajat angajat;
	private List<Contact> lstContacts = new ArrayList<>();
	private List<Angajat> lstAngajati = new ArrayList<>();
	private Operatiune operatiune = new Operatiune();
	private OperatiuneForm operatiuneForm = new OperatiuneForm();
	private String numeContact;
	private EmailForm emailForm = new EmailForm();
	private LoginForm loginForm = new LoginForm();
	private Angajat angajatLogin= new Angajat();
    private String password;
	

	public ContactForm() {
		super();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SilviuCRM");
		em = emf.createEntityManager();
		initLstContacts();
		initLstAngajati();
		angajatLogin=LoginFormStaticUser.angajatLogin;
	}

	public void initLstAngajati() {

		lstAngajati = em.createQuery("Select a from Angajat a").getResultList();
		if (!lstAngajati.isEmpty()) {
			this.setAngajat(lstAngajati.get(0));
		}
	}

	private void initLstContacts() {
		lstContacts = em.createQuery("Select c From Contact c").getResultList();
	}

	public void addContact(ActionEvent event) {
		initLstAngajati();
		this.contact = new Contact();
		this.contact.setDataInscrieriiContact(Calendar.getInstance().getTime());
		if (!lstAngajati.isEmpty())
			this.contact.setContactOwner(lstAngajati.get(0));

	}

	public void salvareContact(ActionEvent event) {
		System.out.println("Salvare Contact");
		
		em.getTransaction().begin();
		if (this.em.contains(this.contact)) {
			this.em.merge(this.contact);
			em.getTransaction().commit();
			opModificareContact();
			
			
		} else
			this.em.persist(this.contact);
		{
			em.getTransaction().commit();
			opAddContact();
		
			trimiteEmailContact();
			 salvareLoginContact();
		}
		
		initLstContacts();
	}

	private void salvareLoginContact() {
		this.loginForm.adaugaLogin();
		this.loginForm.setEmail(this.contact.getAdresaEmailContact());
		this.loginForm.setPassword(this.password);
		this.loginForm.getLoginCreateAccount().setContact(this.contact);
		this.loginForm.salvareLogin();
		trimiteEmailParolaContact();
	}
	private void salvareLoginContactTransfer() {
		this.loginForm.adaugaLogin();
		this.loginForm.setEmail(this.contact.getAdresaEmailContact());
		this.loginForm.setPassword("password");
		this.loginForm.getLoginCreateAccount().setContact(this.contact);
		this.loginForm.salvareLogin();
		trimiteEmailParolaContact();
	}

	public void salvareContactTransferLead(Contact c) {
		System.out.println("Salvare Contact");
		this.contact = c;
		em.getTransaction().begin();
		if (this.em.contains(this.contact))
			this.em.merge(this.contact);
		else {
			this.em.persist(this.contact);
			
			em.getTransaction().commit();
			salvareLoginContactTransfer();
			
		}
		initLstContacts();
	}

	public void stergereContact(Contact c) {
		this.contact = c;
		initLstContacts();
		numeContact = this.contact.getNumeContact();
		//this.loginForm.stergereLoginContact(this.contact);
		//this.emailForm.stergereEmailContact(this.contact);
		if (em.contains(this.contact)) {
			em.getTransaction().begin();
			em.remove(this.contact);
			em.getTransaction().commit();
			initLstContacts();
			opStergereContact();
			System.out.println("A fost sters din db  " + this.contact.getNumeContact());

		} else
			System.out.println("nu e in em");
	}

	public void abandonContact(ActionEvent event) {
		System.out.println("Ati Anulat Actiunea");
		em.clear();

	}

	public void select(Contact c) {
		this.contact = c;
	}

	public void opAddContact() {

		this.operatiune = new Operatiune();

		this.operatiune.setTipOperatiune("Inregistrare Contact");
		this.operatiune.setCategorieOperatiune("Contact");
		   this.operatiune.setAngajatOperatiune(this.angajat);
		this.operatiune.setDescriereOperatiune("Angajatul '" + angajat.getNumeAngajat()
				+ "' a inregistrat un contact nou cu numele '" + this.contact.getNumeContact() + "'.");
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();
	}

	public void opModificareContact() {

		this.operatiune = new Operatiune();

		this.operatiune.setTipOperatiune("Modificare Contact");
		this.operatiune.setCategorieOperatiune("Contact");
		   this.operatiune.setAngajatOperatiune(this.angajat);
		this.operatiune.setDescriereOperatiune("Angajatul '" + angajat.getNumeAngajat()
				+ "' a modificat profilul contactului cu numele '" + this.contact.getNumeContact() + "'.");
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();
	}

	public void opStergereContact() {

		this.operatiune = new Operatiune();
		this.operatiune.setTipOperatiune("Stergere Contact");
		this.operatiune.setCategorieOperatiune("Contact");
		   this.operatiune.setAngajatOperatiune(this.angajat);
		this.operatiune.setDescriereOperatiune(
				"Angajatul '" + angajat.getNumeAngajat() + "' a sters contactul cu numele '" + numeContact + "'.");
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();
	}

	private void trimiteEmailContact() {

		this.emailForm = new EmailForm();
		this.emailForm.addEmail();
		this.emailForm.getEmail().setAdresaEmail(this.contact.getAdresaEmailContact());
		this.emailForm.getEmail().setCategorieDestinatarEmail("Contact");
		this.emailForm.getEmail()
				.setContinutEmail("Stimate dn/dna" + this.contact.getNumeContact() + ",\n"
						+ "Contul dumnevoastra a fost creat cu succes!\n"
						+ "Iti uram sport la cumparaturi pe site-ul nostru 'www.SilviuCRM.ro'\n" + "Cu drag, \n"
						+ "Echipa SilviuCRM");

		this.emailForm.getEmail().setSubiectEmail("Creare cont client www.SilviuCRM.ro");
		this.emailForm.getEmail().setContactEmail(this.contact);
		this.emailForm.salvareEmail();
		this.contact.getListaEmail_uriContact().add(this.emailForm.getEmail());

		this.emailForm.setSubiectEmail("Creare cont client www.SilviuCRM.ro");
		this.emailForm.setMesajEmail("Stimate dn/dna " + this.contact.getNumeContact() + ",\n"
				+ "Contul dumnevoastra a fost creat cu succes! \n"
				+ "Iti uram sport la cumparaturi pe site-ul nostru 'www.SilviuCRM.ro'\n" + "Cu drag, \n"
				+ "Echipa SilviuCRM");
		this.emailForm.setReceptorEmail(this.contact.getAdresaEmailContact());
		 this.emailForm.trimiteEmail();
	}

	private void trimiteEmailParolaContact() {

		this.emailForm = new EmailForm();
		this.emailForm.addEmail();
		this.emailForm.getEmail().setAdresaEmail(this.contact.getAdresaEmailContact());
		this.emailForm.getEmail().setCategorieDestinatarEmail("Contact");
		this.emailForm.getEmail()
				.setContinutEmail("Stimate dn/dna " + this.contact.getNumeContact() + ",\n"
						+ "Contul dumnevoastra a fost creat cu succes!\n"
						+ "Parola contului dvs este "+this.loginForm.getPassword()+".\n" + "Cu drag, \n"
						+ "Echipa SilviuCRM");

		this.emailForm.getEmail().setSubiectEmail("Creare cont client www.SilviuCRM.ro");
		this.emailForm.getEmail().setContactEmail(this.contact);
		this.emailForm.salvareEmail();
		this.contact.getListaEmail_uriContact().add(this.emailForm.getEmail());

		this.emailForm.setSubiectEmail("Creare cont client www.SilviuCRM.ro");
		this.emailForm.setMesajEmail("Stimate dn/dna " + this.contact.getNumeContact() + ",\n"
				+ "Contul dumnevoastra a fost creat cu succes! \n"
				+ "Parola contului dvs este "+this.loginForm.getPassword()+"\n" + "Cu drag, \n"
				+ "Echipa SilviuCRM");
		this.emailForm.setReceptorEmail(this.contact.getAdresaEmailContact());
		 this.emailForm.trimiteEmail();
	}
	
	public void valueChange(ValueChangeEvent event) {
		 if(this.angajatLogin.getRolAngajat().equals("Angajat")) {
		        this.angajat= angajatLogin;
		        this.contact.setContactOwner(this.angajat);
		        }
		        else {
		Stream<Angajat> streamAngajat = this.lstAngajati.stream()
				.filter(a -> a.getIdAngajat().equals(Integer.parseInt((String) event.getNewValue())));

		List<Angajat> listaAlegeAngajat = new ArrayList<Angajat>();
		streamAngajat.forEach(a -> listaAlegeAngajat.add(a));
		this.angajat = listaAlegeAngajat.get(0);
		this.contact.setContactOwner(this.angajat);
		System.out.println("A fost selectat angajatul " + this.angajat.getNumeAngajat());
	}
	}
	
	public void onRowSelect(SelectEvent event) {
		this.contact = (Contact) event.getObject();
	}

	public void onRowUnselect(UnselectEvent event) {
		this.contact = lstContacts.get(0);
	}

	public Contact getContact() {
		return contact;
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

	public String getNumeContact() {
		return numeContact;
	}



	public void setNumeContact(String numeContact) {
		this.numeContact = numeContact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public EmailForm getEmailForm() {
		return emailForm;
	}

	public void setEmailForm(EmailForm emailForm) {
		this.emailForm = emailForm;
	}

	public LoginForm getLoginForm() {
		return loginForm;
	}

	public void setLoginForm(LoginForm loginForm) {
		this.loginForm = loginForm;
	}

	public Angajat getAngajat() {
		return angajat;
	}

	public void setAngajat(Angajat angajat) {
		this.angajat = angajat;
	}

	public List<Angajat> getLstAngajati() {
		return lstAngajati;
	}

	public void setLstAngajati(List<Angajat> lstAngajati) {
		this.lstAngajati = lstAngajati;
	}

	public List<Contact> getLstContacts() {
		return lstContacts;
	}

	public void setLstContacts(List<Contact> lstContacts) {
		this.lstContacts = lstContacts;
	}

	

	public Angajat getAngajatLogin() {
		return angajatLogin;
	}

	public void setAngajatLogin(Angajat angajatLogin) {
		this.angajatLogin = angajatLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
