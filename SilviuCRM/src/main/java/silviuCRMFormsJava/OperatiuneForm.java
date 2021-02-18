package silviuCRMFormsJava;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import silviuCRMClase.Angajat;
import silviuCRMClase.Lead;
import silviuCRMClase.Operatiune;
import silviuCRMClase.Produs;

@ManagedBean
@SessionScoped
public class OperatiuneForm {

	EntityManager em;
	private Operatiune operatiune = new Operatiune();
	private Lead lead = new Lead();
	private List<Operatiune> lstOperatiuni = new ArrayList<>();
	private Angajat angajat = new Angajat();
	private Angajat angajatModificat = new Angajat();
	private List<String> lstCategorii = new ArrayList<>();
	private Angajat angajatLogin= new Angajat();


	public OperatiuneForm() {
		super();

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SilviuCRM");
		em = emf.createEntityManager();
		initLstOperatiuni();
		initLstCategorii();
		angajatLogin=LoginFormStaticUser.angajatLogin;
	}

	public void initLstOperatiuni() {

		lstOperatiuni = em.createQuery("Select o from Operatiune o").getResultList();

	}

	public void initLstCategorii() {
		lstCategorii= em.createNativeQuery("Select  distinct categorieoperatiune c from Operatiune o").getResultList();
  	}

	public void salvareOperatiuneFormJava(Operatiune o) {
		System.out.println("Salvare Operatiune");
		this.operatiune = o;

		em.getTransaction().begin();
		if (this.em.contains(this.operatiune))
			this.em.merge(this.operatiune);
		else
			this.em.persist(this.operatiune);
		em.getTransaction().commit();

		initLstOperatiuni();

	}

	public void select(Operatiune o) {
		this.operatiune = o;
	}

	public void stergereOperatiune(Operatiune o) {

		this.operatiune = o;
		if (em.contains(this.operatiune)) {
			em.getTransaction().begin();
			em.remove(this.operatiune);
			em.getTransaction().commit();
			initLstOperatiuni();
			System.out.println("A fost sters din db operatiunea " + this.operatiune.getIdOperatiune());

		}
	}

	public Operatiune getOperatiune() {
		return operatiune;
	}

	public void setOperatiune(Operatiune operatiune) {
		this.operatiune = operatiune;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public List<Operatiune> getLstOperatiuni() {
		return lstOperatiuni;
	}

	public List<String> getLstCategorii() {
		return lstCategorii;
	}

	public void setLstCategorii(List<String> lstCategorii) {
		this.lstCategorii = lstCategorii;
	}

	public void setLstOperatiuni(List<Operatiune> lstOperatiuni) {
		this.lstOperatiuni = lstOperatiuni;
	}

	public Angajat getAngajat() {
		return angajat;
	}

	public Angajat getAngajatModificat() {
		return angajatModificat;
	}

	public void setAngajatModificat(Angajat angajatModificat) {
		this.angajatModificat = angajatModificat;
	}

	public void setAngajat(Angajat angajat) {
		this.angajat = angajat;
	}

	public Angajat getAngajatLogin() {
		return angajatLogin;
	}

	public void setAngajatLogin(Angajat angajatLogin) {
		this.angajatLogin = angajatLogin;
	}

}
