package silviuCRMFormsJava;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import silviuCRMClase.Angajat;
import silviuCRMClase.Lead;
import silviuCRMClase.Operatiune;
import silviuCRMClase.Produs;

@ManagedBean
@SessionScoped
@ViewScoped
public class ProdusForm {

	EntityManager em;
	private Produs produs = new Produs();
	private List<Produs> lstProduse = new ArrayList<Produs>();
	private Angajat angajat = new Angajat();
	private List<Angajat> lstAngajati = new ArrayList<>();
	private Operatiune operatiune= new Operatiune();
	private OperatiuneForm operatiuneForm= new OperatiuneForm();
	private String numeProdus;
	private Angajat angajatLogin= new Angajat();
	
	
	public ProdusForm() {
		super();

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SilviuCRM");
		em = emf.createEntityManager();
		angajatLogin=LoginFormStaticUser.angajatLogin;
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

	public void addProdusNou(ActionEvent event) {
		this.produs = new Produs();
		this.produs.setDataAdaugareProdus(Calendar.getInstance().getTime());
		initLstAngajati();
		this.produs.setProdusOwner(lstAngajati.get(0));


	}

	public void salvareProdus(ActionEvent event) {
		System.out.println("Salvare Produs");

		em.getTransaction().begin();
		if (this.em.contains(this.produs)) {
			this.em.merge(this.produs);
			em.getTransaction().commit();
			opModificareProdus();
		}
		else {
			this.em.persist(this.produs);
			em.getTransaction().commit();
			opAddProdus();
		}
		initLstProduse();
	}

	public void abandonProdus(ActionEvent event) {
		System.out.println("Ati Anulat Actiunea");
		em.clear();

	}
	
	
	public void select(Produs produs) {
		  this.produs=produs;
	   }	
   
   public void stergereProdus(Produs p) {

		this.produs=p;
		numeProdus=p.getNumeProdus();
		if (em.contains(this.produs)) {
			em.getTransaction().begin();
			em.remove(this.produs);
			em.getTransaction().commit();
			initLstProduse();
			opStergereProdus();
			System.out.println("A fost sters din db produsul " + this.produs.getNumeProdus());
			
		}
   }
	
   
  public void opAddProdus() {
	   
	   this.operatiune= new Operatiune();
	   
		 this.operatiune.setCategorieOperatiune("Produse");
	   this.operatiune.setTipOperatiune("Inregistrare Produs");
	   this.operatiune.setAngajatOperatiune(this.angajat);
	   this.operatiune.setDescriereOperatiune("Angajatul '"+angajat.getNumeAngajat()+ "' a inregistrat un  nou produs cu numele '"+this.produs.getNumeProdus()+"'.");
	   this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
	   this.operatiuneForm.initLstOperatiuni();
   }
   
 public void opModificareProdus() {
	   
	   this.operatiune= new Operatiune();
		 this.operatiune.setCategorieOperatiune("Produse");
	   this.operatiune.setTipOperatiune(" Modificare Produs");
	   this.operatiune.setAngajatOperatiune(this.angajat);
	   this.operatiune.setDescriereOperatiune("Angajatul '"+angajat.getNumeAngajat()+ "' a inregistrat modificari asupra produsului '"+this.produs.getNumeProdus()+"'.");
	   this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
	   this.operatiuneForm.initLstOperatiuni();
	   
   }

 public void opStergereProdus() {
	   
	   this.operatiune= new Operatiune();
		 this.operatiune.setCategorieOperatiune("Produse");
	   this.operatiune.setTipOperatiune("Stergere Produs");
	   this.operatiune.setAngajatOperatiune(this.angajat);
	   this.operatiune.setDescriereOperatiune("Angajatul '"+angajat.getNumeAngajat()+ "' a sters produsul '"+numeProdus+"'.");
	   this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
	   this.operatiuneForm.initLstOperatiuni();
 }
   
   public void valueChange(ValueChangeEvent event) {

		
	      if(this.angajatLogin.getRolAngajat().equals("Angajat")) {
	          this.angajat= angajatLogin;
	          this.produs.setProdusOwner(this.angajat);
	          }
	          else {
		Stream<Angajat> streamAngajat = this.lstAngajati.stream()
				.filter(a -> a.getIdAngajat().equals(Integer.parseInt((String) event.getNewValue())));

		List<Angajat> listaAlegeAngajat = new ArrayList<Angajat>();
		streamAngajat.forEach(a -> listaAlegeAngajat.add(a));
		this.angajat = listaAlegeAngajat.get(0);
		this.produs.setProdusOwner(this.angajat);
		System.out.println("A fost selectat angajatul "+this.angajat.getNumeAngajat());
	}
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

	public Angajat getAngajatLogin() {
		return angajatLogin;
	}

	public void setAngajatLogin(Angajat angajatLogin) {
		this.angajatLogin = angajatLogin;
	}

}
