package silviuCRMFormsJava;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.primefaces.PrimeFaces;

import silviuCRMClase.Angajat;
import silviuCRMClase.Contact;
import silviuCRMClase.LoginCreateAccount;
import silviuCRMClase.Operatiune;
import silviuCRMFormsJava.*;

@ManagedBean
@SessionScoped
@ViewScoped
public class LoginForm {

	EntityManager em;
	private String email;
	private String password;
	private Contact contact = new Contact();
	private Angajat angajat = new Angajat();
	private LoginCreateAccount loginCreateAccount = new LoginCreateAccount();
	private List<LoginCreateAccount> lstLoginCreateAccount = new ArrayList<>();
	private Operatiune operatiune = new Operatiune();
	private OperatiuneForm operatiuneForm = new OperatiuneForm();
	private EmailForm emailForm = new EmailForm();
	private List<Contact> lstContacts = new ArrayList<>();
	private List<Operatiune> lstOperatiuni = new ArrayList<Operatiune>();
	private List<Operatiune> lstOperatiuniAngajat = new ArrayList<Operatiune>();
	private LoginForm loginForm;

	public LoginForm() {

		super();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SilviuCRM");
		em = emf.createEntityManager();
		initLstLoginCreateAccount();
		initLstContacts();
		System.out.println("logi form");
	}

	public void initLstLoginCreateAccount() {
		lstLoginCreateAccount.clear();
		lstLoginCreateAccount = em.createQuery("Select l From LoginCreateAccount l").getResultList();
	}

	private void initLstContacts() {
		lstContacts = em.createQuery("Select c From Contact c").getResultList();
	}

	public void initLstOperatiuniAngajat() {

		lstOperatiuni = em.createQuery("Select o from Operatiune o").getResultList();
		lstOperatiuniAngajat.clear();
		for (Operatiune o : lstOperatiuni) {
			if (o.getAngajatOperatiune().equals(LoginFormStaticUser.angajatLogin)) {
				lstOperatiuniAngajat.add(o);
			}
		}
	}

	public void validLogin(ActionEvent event) throws InstantiationException, IllegalAccessException {

		initLstLoginCreateAccount();
		for (LoginCreateAccount l : lstLoginCreateAccount) {

			if (l.getUsername().equals(email) && l.getPassword().equals(password)) {
				if (l.getContact() != null) {
					this.contact = l.getContact();
					LoginFormStaticUser.contactLogin = l.getContact();
					ProdusFormClient.class.newInstance();
					OfertaFormClient.class.newInstance();
					ComandaFormClient.class.newInstance();
					System.out.println(LoginFormStaticUser.contactLogin.getNumeContact() + "loginFOrm");
					try {
						
						FacesContext.getCurrentInstance().getExternalContext().redirect("ComandaFormClient.xhtml");
						FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("upProduse");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				} else {
					if (l.getAngajat().getStatusCont().equals("Suspendat")) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Warning!", "Acest cont este suspendat! Nu se poate efectua logarea!"));

						this.angajat = l.getAngajat();
						LoginFormStaticUser.angajatLogin = l.getAngajat();
						break;
					} else {
						this.angajat = l.getAngajat();
						LoginFormStaticUser.angajatLogin = l.getAngajat();
						System.out.println("Login cu succes  angajat " + this.angajat.getNumeAngajat());
						if (LoginFormStaticUser.angajatLogin.getRolAngajat().equals("Administrator")) {
							try {
								FacesContext.getCurrentInstance().getExternalContext().redirect("AngajatForm.xhtml");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						} else {
							try {
								FacesContext.getCurrentInstance().getExternalContext().redirect("ComandaFormAngajat.xhtml");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						}
					}
				}
			}

		}

		if (LoginFormStaticUser.contactLogin.getAdresaEmailContact() == null
				&& LoginFormStaticUser.angajatLogin.getAdresaEmailAngajat() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
					"Contul pe care doriti sa il accesati nu este inregistrat, sau datele de logare sunt gresite."));

		}

	}

	public void addContact(ActionEvent event) {
		this.contact = new Contact();
		this.contact.setDataInscrieriiContact(Calendar.getInstance().getTime());

	}

	public void salvareContact(ActionEvent event) {
		System.out.println("Salvare Contact");

		em.getTransaction().begin();
		if (this.em.contains(this.contact)) {
			em.getTransaction().commit();

		} else
			this.em.persist(this.contact);
		{
			em.getTransaction().commit();
			opAddContact();
			salvareLoginContact();
			trimiteEmailContact();

		}
		initLstContacts();
		initLstLoginCreateAccount();
	}

	private void salvareLoginContact() {
		adaugaLogin();
		this.setEmail(this.contact.getAdresaEmailContact());
		this.setPassword(this.getPassword());
		this.getLoginCreateAccount().setContact(this.contact);
		salvareLogin();
	}

	public void adaugaLogin() {

		this.loginCreateAccount = new LoginCreateAccount();
	}

	public void salvareLogin() {
		System.out.println("Salvare Login");
		this.loginCreateAccount.setUsername(email);
		this.loginCreateAccount.setPassword(password);
		em.getTransaction().begin();
		if (this.em.contains(this.loginCreateAccount)) {
			this.em.merge(this.loginCreateAccount);
			em.getTransaction().commit();
		} else {
			this.em.persist(this.loginCreateAccount);
			em.getTransaction().commit();

		}
		initLstLoginCreateAccount();
	}

	public void stergereLoginContact(Contact c) {
		this.loginCreateAccount = new LoginCreateAccount();
		initLstLoginCreateAccount();
		for (LoginCreateAccount l : lstLoginCreateAccount) {
			if (l.getContact() == c) {
				this.loginCreateAccount = l;
			}

		}
		if (em.contains(this.loginCreateAccount)) {
			em.getTransaction().begin();
			em.remove(this.loginCreateAccount);
			em.getTransaction().commit();

		}
	}

	public void schimbaParolaContAngajat(Angajat a) {
		initLstLoginCreateAccount();

		for (LoginCreateAccount l : lstLoginCreateAccount) {
			if (l.getAngajat().equals(a)) {
				this.loginCreateAccount = l;
				this.loginCreateAccount.setUsername(email);
				this.loginCreateAccount.setPassword(this.password);
				em.getTransaction().begin();
				if (this.em.contains(this.loginCreateAccount)) {
					this.em.merge(this.loginCreateAccount);
					em.getTransaction().commit();
				} else {
					this.em.persist(this.loginCreateAccount);
					em.getTransaction().commit();

				}

			}

		}

	}

	public void opAddContact() {

		this.operatiune = new Operatiune();

		this.operatiune.setTipOperatiune("Inregistrare Contact");
		this.operatiune.setCategorieOperatiune("Contact");
		this.operatiune.setDescriereOperatiune("Contactul '" + this.contact.getNumeContact() + "' s-a inregistrat. ");
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

	public void redirectLogOut(ValueChangeEvent event) throws InstantiationException, IllegalAccessException {
		System.out.println(event.getNewValue().toString());

		LoginForm.class.newInstance();
		if (event.getNewValue().toString().equals("Log Out")) {
			try {
			//	FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove("ProdusFormClient");
			//	FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove("LoginForm");
				FacesContext.getCurrentInstance().getExternalContext().redirect("HomeForm.xhtml");
			//	PrimeFaces.current().ajax().update(":formLogin");
			//	FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove("LoginForm");
			//	FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove("produsFormClient");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (event.getNewValue().toString().equals("Profilul Meu")) {

			PrimeFaces.current().executeScript("PF('dlgProfilAngajat').show();");
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public List<Operatiune> getLstOperatiuniAngajat() {
		return lstOperatiuniAngajat;
	}

	public void setLstOperatiuniAngajat(List<Operatiune> lstOperatiuniAngajat) {
		this.lstOperatiuniAngajat = lstOperatiuniAngajat;
	}

	public Angajat getAngajat() {
		return angajat;
	}

	public void setAngajat(Angajat angajat) {
		this.angajat = angajat;
	}

	public LoginCreateAccount getLoginCreateAccount() {
		return loginCreateAccount;
	}

	public void setLoginCreateAccount(LoginCreateAccount loginCreateAccount) {
		this.loginCreateAccount = loginCreateAccount;
	}

	public List<LoginCreateAccount> getLstLoginCreateAccount() {
		return lstLoginCreateAccount;
	}

	public void setLstLoginCreateAccount(List<LoginCreateAccount> lstLoginCreateAccount) {
		this.lstLoginCreateAccount = lstLoginCreateAccount;
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

	public EmailForm getEmailForm() {
		return emailForm;
	}

	public void setEmailForm(EmailForm emailForm) {
		this.emailForm = emailForm;
	}

	public List<Operatiune> getLstOperatiuni() {
		return lstOperatiuni;
	}

	public void setLstOperatiuni(List<Operatiune> lstOperatiuni) {
		this.lstOperatiuni = lstOperatiuni;
	}

	public List<Contact> getLstContacts() {
		return lstContacts;
	}

	public void setLstContacts(List<Contact> lstContacts) {
		this.lstContacts = lstContacts;
	}

}
