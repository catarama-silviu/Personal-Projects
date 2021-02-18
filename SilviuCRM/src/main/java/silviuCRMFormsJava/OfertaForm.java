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
public class OfertaForm {

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
	private List<LinieOferta> lstLiniiOferta = new ArrayList<LinieOferta>();
	private Operatiune operatiune = new Operatiune();
	private OperatiuneForm operatiuneForm = new OperatiuneForm();
	private Integer idOferta;
	private EmailForm emailForm= new EmailForm();
	private Angajat angajatLogin= new Angajat();

	public OfertaForm() {
		super();

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SilviuCRM");
		em = emf.createEntityManager();
		initLstAngajati();
		initLstContacte();
		initLstProduse();
		initLstOferte();
		angajatLogin=LoginFormStaticUser.angajatLogin;

	}

	public void initLstAngajati() {

		lstAngajati = em.createQuery("Select a from Angajat a").getResultList();
		if (!lstAngajati.isEmpty()) {
			this.setAngajat(lstAngajati.get(0));
		}
	}

	public void initLstContacte() {

		lstContacte = em.createQuery("Select c from Contact c").getResultList();
		if (!lstContacte.isEmpty()) {
			this.setContact(lstContacte.get(0));
		}
	}

	public void initLstProduse() {

		lstProduse = em.createQuery("Select p from Produs p").getResultList();
		if (!lstProduse.isEmpty()) {
			this.setProdus(lstProduse.get(0));
		}
	}

	public void initLstOferte() {

		lstOferte = em.createQuery("Select o from Oferta o").getResultList();

	}

	public void addOfertaNoua(ActionEvent event) {
		this.oferta = new Oferta();
		this.oferta.setDataInregistrareOferta(Calendar.getInstance().getTime());
		initLstAngajati();
		initLstContacte();
		initLstProduse();
		this.oferta.setOwnerOferta(lstAngajati.get(0));
		this.oferta.setContactOferta(lstContacte.get(0));
		this.lstLiniiOferta = new ArrayList<LinieOferta>();

	}

	public void salvareOferte(ActionEvent event) {
		
		
		System.out.println("Salvare Oferta");
		this.oferta.setNumeOferta("Oferta pentru: " + this.oferta.getContactOferta().getNumeContact() + " ID:"
				+ this.oferta.getIdOferta());
		this.oferta.setPretOfertaCuTva(calculTotalOfertaCuTva(this.oferta.getLstLiniiProdusOferte()));
		this.oferta.setPretOfertaFaraTva(calculTotalOfertaFaraTva(this.oferta.getLstLiniiProdusOferte()));

		em.getTransaction().begin();
		if (this.em.contains(this.oferta)) {
			this.em.merge(this.oferta);
			em.getTransaction().commit();
			opModificareOferta();
		} else {
			this.em.persist(this.oferta);
			em.getTransaction().commit();
			opAddOferta();
			trimiteEmailOfertaContact();
		}
		initLstOferte();

	}

	public void abandonOferta(ActionEvent event) {
		System.out.println("Ati Anulat Actiunea");
		em.clear();

	}

	public void opAddOferta() {

		this.operatiune = new Operatiune();
		 this.operatiune.setCategorieOperatiune("Oferte");
		this.operatiune.setTipOperatiune("Inregistrare Oferta");
		   this.operatiune.setAngajatOperatiune(this.angajat);
		this.operatiune.setDescriereOperatiune("Angajatul '" + angajat.getNumeAngajat()
				+ "' a trimis o oferta clientului '" + this.oferta.getContactOferta().getNumeContact() + "'.");
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();
	}

	public void opModificareOferta() {

		this.operatiune = new Operatiune();
		 this.operatiune.setCategorieOperatiune("Oferte");
		this.operatiune.setTipOperatiune("Modificare Oferta");
		   this.operatiune.setAngajatOperatiune(this.angajat);
		this.operatiune.setDescriereOperatiune("Angajatul '" + angajat.getNumeAngajat()
				+ "' a inregistrat modificari asupra ofertei " + "cu ID:'" + this.oferta.getIdOferta()
				+ "' pe care a trimis-o clientului '" + this.oferta.getContactOferta().getNumeContact() + "'.");
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();

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

	public void stergereOferta(Oferta o) {

		this.oferta = o;
		idOferta=o.getIdOferta();
		if (em.contains(this.oferta)) {
			em.getTransaction().begin();
			em.remove(this.oferta);
			em.getTransaction().commit();
			initLstOferte();
			opStergereOferta();
			System.out.println("A fost sters din db oferta (ID:" + this.oferta.getIdOferta() + ")");

		}
	}

	public void valueChange(ValueChangeEvent event) {
	      if(this.angajatLogin.getRolAngajat().equals("Angajat")) {
	          this.angajat= angajatLogin;
	          this.oferta.setOwnerOferta(this.angajat);
	          }
	          else {
		Stream<Angajat> streamAngajat = this.lstAngajati.stream()
				.filter(a -> a.getIdAngajat().equals(Integer.parseInt((String) event.getNewValue())));

		List<Angajat> listaAlegeAngajat = new ArrayList<Angajat>();
		streamAngajat.forEach(a -> listaAlegeAngajat.add(a));
		this.angajat = listaAlegeAngajat.get(0);
		this.oferta.setOwnerOferta(this.angajat);
		System.out.println("A fost selectat angajatul " + this.angajat.getNumeAngajat());
	}
	}

	public void valueChangeContact(ValueChangeEvent event) {

		Stream<Contact> streamContact = this.lstContacte.stream()
				.filter(a -> a.getIdContact().equals(Integer.parseInt((String) event.getNewValue())));

		List<Contact> listaAlegeContact = new ArrayList<Contact>();
		streamContact.forEach(a -> listaAlegeContact.add(a));
		this.contact = listaAlegeContact.get(0);
		this.oferta.setContactOferta(this.contact);
		System.out.println("A fost selectat contactul " + this.contact.getNumeContact());
	}

	public void valueChangeLinieOferta(ValueChangeEvent event) {
		Stream<Produs> streamProdus = this.lstProduse.stream()
				.filter(a -> a.getIdProdus().equals(Integer.parseInt((String) event.getNewValue())));

		List<Produs> listaAlegeProdus = new ArrayList<>();
		streamProdus.forEach(a -> listaAlegeProdus.add(a));
		this.produs = listaAlegeProdus.get(0);

		System.out.println("A fost selectat produsul " + this.produs.getNumeProdus());
	}

	public Double calculLinieOfertaFaraTva(Produs p, Double c) {

		Double x = p.getPretBucataProdus() * c;
		return Math.round(x * 100.0) / 100.0;

	}

	public Double calculLinieOfertaCuTva(Produs p, Double c) {
		Double x = calculLinieOfertaFaraTva(p, c) + p.getCotaTvaProdus() / 100 * calculLinieOfertaFaraTva(p, c);
		return Math.round(x * 100.0) / 100.0;
	}

	public Double calculTotalOfertaFaraTva(List<LinieOferta> lst) {
		Double x = 0.00;
		for (LinieOferta l : lst) {
			x += l.getPretFaraTvaLinieOferta();
		}
		return Math.round(x * 100.0) / 100.0;
	}

	public Double calculTotalOfertaCuTva(List<LinieOferta> lst) {
		Double x = 0.00;
		for (LinieOferta l : lst) {
			x += l.getPretCuTvaLinieOferta();
		}
		return Math.round(x * 100.0) / 100.0;
	}

	public void salvareLinieOferta(ActionEvent e) {
		this.linieOferta.setProdusLinieOferta(this.produs);
		this.linieOferta.setPretFaraTvaLinieOferta(
				calculLinieOfertaFaraTva(produs, this.linieOferta.getCantitateProdusLinieOferta()));
		this.linieOferta.setPretCuTvaLinieOferta(
				calculLinieOfertaCuTva(produs, this.linieOferta.getCantitateProdusLinieOferta()));
		this.oferta.getLstLiniiProdusOferte().add(this.linieOferta);
		this.lstLiniiOferta.add(linieOferta);
		for (LinieOferta l : lstLiniiOferta) {
			System.out.println(l.getProdusLinieOferta().getNumeProdus());
		}

	}

	public void addLinieOferta(ActionEvent e) {
		initLstProduse();
		this.produs = new Produs();
		this.linieOferta = new LinieOferta();
		this.linieOferta.setOfertaLinieOferta(this.oferta);

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

	public Angajat getAngajatLogin() {
		return angajatLogin;
	}

	public void setAngajatLogin(Angajat angajatLogin) {
		this.angajatLogin = angajatLogin;
	}

}
