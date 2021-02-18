package silviuCRMFormsJava;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import silviuCRMClase.Angajat;
import silviuCRMClase.Contact;
import silviuCRMClase.Lead;
import silviuCRMClase.Operatiune;
import silviuCRMClase.Produs;

@ManagedBean
@SessionScoped
@ViewScoped
public class ProdusFormClient {

	EntityManager em;
	private Produs produs = new Produs();
	private List<Produs> lstProduse = new ArrayList<Produs>();
	private Angajat angajat = new Angajat();
	private List<Angajat> lstAngajati = new ArrayList<>();
	private Operatiune operatiune= new Operatiune();
	private OperatiuneForm operatiuneForm= new OperatiuneForm();
	private String numeProdus;
	private Contact contactLogin= new Contact();
	
	
	public ProdusFormClient() {
		super();

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SilviuCRM");
		em = emf.createEntityManager();
		this.contactLogin=LoginFormStaticUser.contactLogin;
		System.out.println(contactLogin.getNumeContact()+"p");
		initLstAngajati();
		initLstProduse();
	
	}

	public void initLstAngajati() {
	
		lstAngajati = em.createQuery("Select a from Angajat a").getResultList();
		if (!lstAngajati.isEmpty()) {
			this.setAngajat(lstAngajati.get(0));
		}
	}

	public void initLstProduse() {

		lstProduse = em.createQuery("Select p from Produs p").getResultList();

	}



	public void select(Produs produs) {
		  this.produs=produs;
	   }	
   

	
	
	public Produs getProdus() {
		return produs;
	}

	public void setProdus(Produs produs) {
		this.produs = produs;
	}

	public List<Produs> getLstProduse() {
		return lstProduse;
	}

	public void setLstProduse(List<Produs> lstProduse) {
		this.lstProduse = lstProduse;
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

	public String getNumeProdus() {
		return numeProdus;
	}

	public void setNumeProdus(String numeProdus) {
		this.numeProdus = numeProdus;
	}

	public Contact getContactLogin() {
		return contactLogin;
	}

	public void setContactLogin(Contact contactLogin) {
		this.contactLogin = contactLogin;
	}



}
