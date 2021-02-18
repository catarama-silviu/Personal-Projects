package silviuCRMFormsJava;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import silviuCRMClase.Angajat;
import silviuCRMClase.Contact;
import silviuCRMClase.Operatiune;

@ManagedBean
@SessionScoped
public class AngajatForm {

	private EntityManager em;
	private Angajat angajat= new Angajat();
	private Angajat angajatOperatiune= new Angajat();
	private List<Angajat> lstAngajati = new ArrayList<>();
	private Operatiune operatiune= new Operatiune();
	private OperatiuneForm operatiuneForm= new OperatiuneForm();
	private String numeAngajatSters;
   private EmailForm emailForm= new EmailForm();
   private String password;
   private LoginForm loginForm = new LoginForm();
   private Angajat angajatLogin= new Angajat();

   
	public AngajatForm() {
		super();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SilviuCRM");
		em = emf.createEntityManager();
	initLstAngajati();
	angajatLogin=LoginFormStaticUser.angajatLogin;
	
	}

	private void initLstAngajati() {
		lstAngajati = em.createQuery("Select a From Angajat a").getResultList();
	}

	public void adaugaAngajat(ActionEvent event) {
		this.angajat = new Angajat();
		this.angajat.setInregistrareAngajat(Calendar.getInstance().getTime());
		this.angajat.setStatusCont("Activ");
System.out.println("aaaaa");
	}

	public void salvareAngajat(ActionEvent event) {
		initLstAngajati();
		System.out.println("Salvare Angajat");

		em.getTransaction().begin();
		if (this.em.contains(this.angajat)) {
			this.em.merge(this.angajat);
		em.getTransaction().commit();
		opModificareAngajat();
		}
		else {
			this.em.persist(this.angajat);
		em.getTransaction().commit();
		opAddAngajat();
		trimiteEmailInregistrareAngajat();
		salvareLoginAngajat();
			}
		initLstAngajati();

	}

	public void abandonAngajat(ActionEvent event) {
		System.out.println("Ati Anulat Actiunea");
		em.clear();

	}

	public void suspendaAngajat(Angajat a) {

		this.angajat = a;
		numeAngajatSters= this.angajat.getNumeAngajat();
	    this.angajat.setStatusCont("Suspendat");
		if (em.contains(this.angajat)) {
			em.getTransaction().begin();
			
			em.merge(this.angajat);
			em.getTransaction().commit();
			opSuspendareAngajat();
			initLstAngajati();
			
			System.out.println("A fost sters din db  " + this.angajat.getNumeAngajat());
		}
		
	}
	
	

	public void select(Angajat a) {
		this.angajat = a;
	}

	 public void opAddAngajat() {
		   
		   this.operatiune= new Operatiune();
		   this.operatiune.setDataOperatiune(Calendar.getInstance().getTime());
		   this.operatiune.setAngajatOperatiune(this.angajat);
		   this.operatiune.setTipOperatiune("Inregistrare Angajat");
		   this.operatiune.setCategorieOperatiune("Angajati");
		   this.operatiune.setDescriereOperatiune("Angajatul '"+angajat.getNumeAngajat()+ "' a fost inregistrat.");
		   this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		   this.operatiuneForm.initLstOperatiuni();
	   }
	   
	  public void opModificareAngajat() {
		   
		   this.operatiune= new Operatiune();
		   this.operatiune.setDataOperatiune(Calendar.getInstance().getTime());
		   this.operatiune.setCategorieOperatiune("Angajati");
		   this.operatiune.setAngajatOperatiune(this.angajat);
		   this.operatiune.setTipOperatiune("Modificare Angajat");
		   this.operatiune.setDescriereOperatiune("Asupra profilului angajatului '"+angajat.getNumeAngajat()+ "' s-au realizat modificari .");
		   this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		   this.operatiuneForm.initLstOperatiuni();
	   }
	  
	  public void opSuspendareAngajat() {
		   
		   this.operatiune= new Operatiune();
		   this.operatiune.setCategorieOperatiune("Angajati");
		   this.operatiune.setTipOperatiune("Suspendare Cont Angajat");
		   this.operatiune.setAngajatOperatiune(this.angajat);
		   this.operatiune.setDataOperatiune(Calendar.getInstance().getTime());
		   this.operatiune.setDescriereOperatiune("Contul angajatului '"+numeAngajatSters+ "' a fost suspendat");
		   this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		   this.operatiuneForm.initLstOperatiuni();
	   }
	   
	  
	  
	  private void trimiteEmailInregistrareAngajat() {

			this.emailForm = new EmailForm();
			this.emailForm.addEmail();
			this.emailForm.getEmail().setAdresaEmail(this.angajat.getAdresaEmailAngajat());
			this.emailForm.getEmail().setCategorieDestinatarEmail("Angajat");
			this.emailForm.getEmail()
					.setContinutEmail("Stimate dn/dna" + this.angajat.getNumeAngajat() + ",\n"
							+ "Contul dumneavoastra a fost inregistrata cu succes!\n"
							+ "Bine ai venit in echipa noastra!!\n" + "Cu drag, \n"
							+ "Echipa SilviuCRM");

			this.emailForm.getEmail().setSubiectEmail("Inregistrare cont angajat SilviuCRM");
			this.emailForm.getEmail().setAngajatEmail(this.angajat);
			this.emailForm.salvareEmail();
			

			this.emailForm.setSubiectEmail("Inregistrare cont angajat SilviuCRM\"");
			this.emailForm.setMesajEmail("Stimate dn/dna " + this.angajat.getNumeAngajat() + ",\n"
					+ "Contul dumneavoastra a fost inregistrata cu succes!! \n"
					+ "Bine ai venit in echipa noastra!'\n" + "Cu drag, \n"
					+ "Echipa SilviuCRM");
			this.emailForm.setReceptorEmail(this.angajat.getAdresaEmailAngajat());
			this.emailForm.trimiteEmail();
		}
	  
		private void salvareLoginAngajat() {
			this.loginForm.adaugaLogin();
			this.loginForm.setEmail(this.angajat.getAdresaEmailAngajat());
			this.loginForm.setPassword(this.password);
			this.loginForm.getLoginCreateAccount().setAngajat(this.angajat);;
			this.loginForm.salvareLogin();
		}
	
	public void onRowSelect(SelectEvent event) {
		this.angajat = (Angajat) event.getObject();
	}

	public void onRowUnselect(UnselectEvent event) {
		this.angajat = lstAngajati.get(0);
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

	public Angajat getAngajatOperatiune() {
		return angajatOperatiune;
	}

	public void setAngajatOperatiune(Angajat angajatOperatiune) {
		this.angajatOperatiune = angajatOperatiune;
	}

	public void setLstAngajati(List<Angajat> lstAngajati) {
		this.lstAngajati = lstAngajati;
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

	public String getNumeAngajatSters() {
		return numeAngajatSters;
	}

	public void setNumeAngajatSters(String numeAngajatSters) {
		this.numeAngajatSters = numeAngajatSters;
	}

	public EmailForm getEmailForm() {
		return emailForm;
	}

	public void setEmailForm(EmailForm emailForm) {
		this.emailForm = emailForm;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginForm getLoginForm() {
		return loginForm;
	}

	public void setLoginForm(LoginForm loginForm) {
		this.loginForm = loginForm;
	}

	public Angajat getAngajatLogin() {
		return angajatLogin;
	}

	public void setAngajatLogin(Angajat angajatLogin) {
		this.angajatLogin = angajatLogin;
	}
	
	

}
