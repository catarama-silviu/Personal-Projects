package silviuCRMFormsJava;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import silviuCRMClase.Angajat;
import silviuCRMClase.Comanda;
import silviuCRMClase.Contact;
import silviuCRMClase.LinieComanda;
import silviuCRMClase.LinieOferta;
import silviuCRMClase.Oferta;
import silviuCRMClase.Operatiune;
import silviuCRMClase.Produs;
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ComandaForm {

	EntityManager em;
	private Comanda comanda = new Comanda();
	private Angajat angajat = new Angajat();
	private Contact contact = new Contact();
	private Oferta oferta = new Oferta();
	private Produs produs = new Produs();
	private LinieComanda linieComanda = new LinieComanda();
	private List<Angajat> lstAngajati = new ArrayList<>();
	private List<Contact> lstContacte = new ArrayList<>();
	private List<LinieComanda> lstLiniiComanda = new ArrayList<>();
	private List<Comanda> lstComenzi = new ArrayList<>();
	private List<Produs> lstProduse = new ArrayList<>();
	private Operatiune operatiune = new Operatiune();
	private OperatiuneForm operatiuneForm = new OperatiuneForm();
	private EmailForm emailForm = new EmailForm();
	private Angajat angajatLogin= new Angajat();

	

	public ComandaForm() {
		super();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SilviuCRM");
		em = emf.createEntityManager();
		angajatLogin=LoginFormStaticUser.angajatLogin;
		initLstAngajati();
		initLstContacte();
		initLstProduse();
		initLstComenzi();
		
		
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

	public void initLstComenzi() {

		lstComenzi = em.createQuery("Select c from Comanda c").getResultList();

	}

	public void addAdresaContactComanda(ActionEvent e) {
		this.comanda.setAdresaComanda(
				"Adresa: " + this.contact.getAdresaOrasContact() + ", Tara: " + this.contact.getAdresaTaraContact());
	}

	public void addOComandaNoua(ActionEvent event) {

		initLstAngajati();
		initLstContacte();
		initLstProduse();
		this.comanda = new Comanda();
		this.comanda.setDataInregistrareComanda(Calendar.getInstance().getTime());

		this.comanda.setOwnerComanda(lstAngajati.get(0));
		this.comanda.setContactComanda(lstContacte.get(0));
		this.lstLiniiComanda = new ArrayList<LinieComanda>();

	}

	public void salvareComanda(ActionEvent event) {
		System.out.println("Salvare Comanda");
		this.comanda.setNumeComanda("Comanda pentru: " + this.comanda.getContactComanda().getNumeContact());
		this.comanda.setPretComandaCuTva(calculTotalComandaCuTva(this.comanda.getLstLiniiComanda()));
		this.comanda.setPretComandaFaraTva(calculTotalComandaFaraTva(this.comanda.getLstLiniiComanda()));

		em.getTransaction().begin();
		if (this.em.contains(this.comanda)) {
			this.em.merge(this.comanda);
			em.getTransaction().commit();
			opModificareComanda();
			
            

		} else {
			this.em.persist(this.comanda);
			em.getTransaction().commit();
			opAddComanda();
	trimiteEmailInregistrareComanda();
		}

		initLstComenzi();
		trimiteEmailConfirmareAnulareComanda();
	}

	public void salvareComandaOperatiune() {

		em.getTransaction().begin();
		if (this.em.contains(this.comanda)) {
			this.em.merge(this.comanda);
			em.getTransaction().commit();
		} else {
			this.em.persist(this.comanda);
			em.getTransaction().commit();

		}

		initLstComenzi();

	}

	public void abandonComanda(ActionEvent event) {
		System.out.println("Ati Anulat Actiunea");
		em.clear();

	}

	public void opAddComanda() {

		this.operatiune = new Operatiune();
		this.operatiune.setCategorieOperatiune("Comenzi");
		this.operatiune.setTipOperatiune("Inregistrare Comanda");
		   this.operatiune.setAngajatOperatiune(this.angajat);
		this.operatiune.setDescriereOperatiune("Angajatul '" + angajat.getNumeAngajat()
				+ "' a inregistrat comanda clientului '" + this.comanda.getContactComanda().getNumeContact() + "'.");
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();
	}

	public void opModificareComanda() {

		this.operatiune = new Operatiune();

		this.operatiune.setTipOperatiune("Modificare Comanda");
		this.operatiune.setCategorieOperatiune("Comenzi");
		   this.operatiune.setAngajatOperatiune(this.angajat);
		this.operatiune.setDescriereOperatiune("Angajatul '" + angajat.getNumeAngajat()
				+ "' a inregistrat modificari asupra comenzii " + "cu ID:'" + this.comanda.getIdComanda()
				+ "' atribuita clientului '" + this.comanda.getContactComanda().getNumeContact() + "'.");
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();

	}

	public void opStergereComanda() {

		this.operatiune = new Operatiune();
		this.operatiune.setCategorieOperatiune("Comenzi");
		this.operatiune.setTipOperatiune("Stergere Comanda");
		   this.operatiune.setAngajatOperatiune(this.angajat);
		this.operatiune.setDescriereOperatiune(
				"Angajatul '" + angajat.getNumeAngajat() + "' a sters comanda cu ID:'" + this.comanda.getIdComanda()
						+ "" + "' atribuita clientului '" + this.comanda.getContactComanda().getNumeContact() + "'.");
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();
	}

	public void opAprobaComanda() {

		this.operatiune = new Operatiune();
		this.operatiune.setCategorieOperatiune("Comenzi");
		this.operatiune.setTipOperatiune("Aprobare Comanda");
		   this.operatiune.setAngajatOperatiune(this.angajat);
		this.operatiune.setDescriereOperatiune(
				"Angajatul '" + angajat.getNumeAngajat() + "' a CONFIRMAT comanda cu ID:'" + this.comanda.getIdComanda()
						+ "" + "' atribuita clientului '" + this.comanda.getContactComanda().getNumeContact() + "'.");
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();
	}

	public void opAnulareComanda() {

		this.operatiune = new Operatiune();
		this.operatiune.setCategorieOperatiune("Comenzi");
		this.operatiune.setTipOperatiune("Anulare Comanda");
		   this.operatiune.setAngajatOperatiune(this.angajat);
		this.operatiune.setDescriereOperatiune(
				"Angajatul '" + angajat.getNumeAngajat() + "' a ANULAT comanda cu ID:'" + this.comanda.getIdComanda()
						+ "" + "' atribuita clientului '" + this.comanda.getContactComanda().getNumeContact() + "'.");
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();
	}

	public void selectComanda(Comanda c) {
		this.comanda = c;
	}

	public void aprobaComanda(Comanda c) {
		this.comanda = c;
		this.comanda.setStatusComanda("Aprobata");
		salvareComandaOperatiune();
		opAprobaComanda();

		trimiteEmailConfirmareComanda();
	}

	public void anuleazaComanda(Comanda c) {
		this.comanda = c;
		this.comanda.setStatusComanda("Anulata");
		salvareComandaOperatiune();
		opAnulareComanda();
		trimiteEmailAnulareComanda();
	}
	
	


	public void stergereComanda(Comanda c) {

		this.comanda = c;
		if (em.contains(this.comanda)) {
			em.getTransaction().begin();
			em.remove(this.comanda);
			em.getTransaction().commit();
			initLstComenzi();
			opStergereComanda();
			System.out.println("A fost sters din db comanda (ID:" + this.comanda.getIdComanda() + ")");

		}
	}

	public void valueChange(ValueChangeEvent event) {
        if(this.angajatLogin.getRolAngajat().equals("Angajat")) {
        this.angajat= angajatLogin;
        this.comanda.setOwnerComanda(this.angajat);
        }
        else {
		Stream<Angajat> streamAngajat = this.lstAngajati.stream()
				.filter(a -> a.getIdAngajat().equals(Integer.parseInt((String) event.getNewValue())));

		List<Angajat> listaAlegeAngajat = new ArrayList<Angajat>();
		streamAngajat.forEach(a -> listaAlegeAngajat.add(a));
		this.angajat = listaAlegeAngajat.get(0);
		this.comanda.setOwnerComanda(this.angajat);
		System.out.println("A fost selectat angajatul " + this.angajat.getNumeAngajat());
	}
	}

	public void valueChangeContact(ValueChangeEvent event) {

		Stream<Contact> streamContact = this.lstContacte.stream()
				.filter(a -> a.getIdContact().equals(Integer.parseInt((String) event.getNewValue())));

		List<Contact> listaAlegeContact = new ArrayList<Contact>();
		streamContact.forEach(a -> listaAlegeContact.add(a));
		this.contact = listaAlegeContact.get(0);
		this.comanda.setContactComanda(this.contact);
		System.out.println("A fost selectat contactul " + this.contact.getNumeContact());
	}

	public void valueChangeLinieComanda(ValueChangeEvent event) {
		Stream<Produs> streamProdus = this.lstProduse.stream()
				.filter(a -> a.getIdProdus().equals(Integer.parseInt((String) event.getNewValue())));

		List<Produs> listaAlegeProdus = new ArrayList<>();
		streamProdus.forEach(a -> listaAlegeProdus.add(a));
		this.produs = listaAlegeProdus.get(0);

		System.out.println("A fost selectat produsul " + this.produs.getNumeProdus());
	}

	public Double calculLinieComandaFaraTva(Produs p, Double c) {

		Double x = p.getPretBucataProdus() * c;
		return Math.round(x * 100.0) / 100.0;

	}

	public Double calculLinieComandaCuTva(Produs p, Double c) {
		Double x = calculLinieComandaFaraTva(p, c) + p.getCotaTvaProdus() / 100 * calculLinieComandaFaraTva(p, c);
		return Math.round(x * 100.0) / 100.0;
	}

	public Double calculTotalComandaFaraTva(List<LinieComanda> lst) {
		Double x = 0.00;
		for (LinieComanda l : lst) {
			x += l.getPretFaraTvaLinieComanda();
		}
		return Math.round(x * 100.0) / 100.0;
	}

	public Double calculTotalComandaCuTva(List<LinieComanda> lst) {
		Double x = 0.00;
		for (LinieComanda l : lst) {
			x += l.getPretCuTvaLinieComanda();
		}
		return Math.round(x * 100.0) / 100.0;
	}

	public void salvareLinieComanda(ActionEvent e) {
		this.linieComanda.setProdusLinieComanda(this.produs);
		this.linieComanda.setPretFaraTvaLinieComanda(
				calculLinieComandaFaraTva(produs, this.linieComanda.getCantitateProdusLinieComanda()));
		this.linieComanda.setPretCuTvaLinieComanda(
				calculLinieComandaCuTva(produs, this.linieComanda.getCantitateProdusLinieComanda()));
		this.comanda.getLstLiniiComanda().add(this.linieComanda);
		this.lstLiniiComanda.add(linieComanda);
		for (LinieComanda l : lstLiniiComanda) {
			System.out.println(l.getProdusLinieComanda().getNumeProdus());
		}

	}

	public void addLinieComanda(ActionEvent e) {
		this.produs = new Produs();
		this.linieComanda = new LinieComanda();
		this.linieComanda.setComandaLinieComanda(this.comanda);

	}

	private void trimiteEmailConfirmareAnulareComanda() {
		if(this.comanda.getStatusComanda()=="Aprobata")
		{
			System.out.println("merge");
			trimiteEmailConfirmareComanda();
		}
		if(this.comanda.getStatusComanda()=="Anulata") {
			System.out.println("merge 2");
			trimiteEmailConfirmareComanda();
		}
		
	}
	
	private void trimiteEmailInregistrareComanda() {

		this.emailForm = new EmailForm();
		this.emailForm.addEmail();
		this.emailForm.getEmail().setAdresaEmail(this.contact.getAdresaEmailContact());
		this.emailForm.getEmail().setCategorieDestinatarEmail("Contact");
		this.emailForm.getEmail()
				.setContinutEmail("Stimate dn/dna" + this.contact.getNumeContact() + ",\n"
						+ "Comanda dumneavoastra a fost inregistrata cu succes!\n"
						+ "In cel mai scurt o sa fiti contactat de unul dintre angajatii nostri!!\n" + "Cu drag, \n"
						+ "Echipa SilviuCRM");

		this.emailForm.getEmail().setSubiectEmail("Inregistrare comanda www.SilviuCRM.ro");
		this.emailForm.getEmail().setContactEmail(this.contact);
		this.emailForm.salvareEmail();
		this.contact.getListaEmail_uriContact().add(this.emailForm.getEmail());

		this.emailForm.setSubiectEmail("Inregistrare comanda www.SilviuCRM.ro");
		this.emailForm.setMesajEmail("Stimate dn/dna " + this.contact.getNumeContact() + ",\n"
				+ "Comanda dumneavoastra a fost inregistrata cu succes!! \n"
				+ "In cel mai scurt o sa fiti contactat de unul dintre angajatii nostri!'\n" + "Cu drag, \n"
				+ "Echipa SilviuCRM");
		this.emailForm.setReceptorEmail(this.contact.getAdresaEmailContact());
		this.emailForm.trimiteEmail();
	}

	private void trimiteEmailConfirmareComanda() {

		this.emailForm = new EmailForm();
		this.emailForm.addEmail();
		this.emailForm.getEmail().setAdresaEmail(this.contact.getAdresaEmailContact());
		this.emailForm.getEmail().setCategorieDestinatarEmail("Contact");
		this.emailForm.getEmail()
				.setContinutEmail("Stimate dn/dna" + this.contact.getNumeContact() + ",\n"
						+ "Comanda dumneavoastra a fost inregistrata cu succes!\n"
						+ "In cel mai scurt o sa fiti contactat de unul dintre angajatii nostri!!\n" + "Cu drag, \n"
						+ "Echipa SilviuCRM");

		this.emailForm.getEmail().setSubiectEmail("Inregistrare comanda www.SilviuCRM.ro");
		this.emailForm.getEmail().setContactEmail(this.contact);
		this.emailForm.salvareEmail();
		this.contact.getListaEmail_uriContact().add(this.emailForm.getEmail());

		this.emailForm.setSubiectEmail("Inregistrare comanda www.SilviuCRM.ro");
		this.emailForm.setMesajEmail("Stimate dn/dna " + this.contact.getNumeContact() + ",\n"
				+ "Comanda dumneavoastra a fost inregistrata cu succes!! \n"
				+ "In cel mai scurt o sa fiti contactat de unul dintre angajatii nostri!'\n" + "Cu drag, \n"
				+ "Echipa SilviuCRM");
		this.emailForm.setReceptorEmail(this.contact.getAdresaEmailContact());
		this.emailForm.trimiteEmail();
	}

	private void trimiteEmailAnulareComanda() {

		this.emailForm = new EmailForm();
		this.emailForm.addEmail();
		this.emailForm.getEmail().setAdresaEmail(this.contact.getAdresaEmailContact());
		this.emailForm.getEmail().setCategorieDestinatarEmail("Contact");
		this.emailForm.getEmail()
				.setContinutEmail("Stimate dn/dna" + this.contact.getNumeContact() + ",\n"
						+ "Comanda dumneavoastra a fost confirmata cu succes!\n"
						+ "In cel mai scurt o sa fiti contactat de unul dintre angajatii nostri!!\n" + "Cu drag, \n"
						+ "Echipa SilviuCRM");

		this.emailForm.getEmail().setSubiectEmail("Inregistrare comanda www.SilviuCRM.ro");
		this.emailForm.getEmail().setContactEmail(this.contact);
		this.emailForm.salvareEmail();
		this.contact.getListaEmail_uriContact().add(this.emailForm.getEmail());

		this.emailForm.setSubiectEmail("Inregistrare comanda www.SilviuCRM.ro");
		this.emailForm.setMesajEmail("Stimate dn/dna " + this.contact.getNumeContact() + ",\n"
				+ "Comanda dumneavoastra a fost anulata!! \n" + "Cu drag, \n" + "Echipa SilviuCRM");
		this.emailForm.setReceptorEmail(this.contact.getAdresaEmailContact());
		this.emailForm.trimiteEmail();
	}

	
	
	
	
	public Comanda getComanda() {
		return comanda;
	}

	public void setComanda(Comanda comanda) {
		this.comanda = comanda;
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

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	public Produs getProdus() {
		return produs;
	}

	public void setProdus(Produs produs) {
		this.produs = produs;
	}

	public List<Comanda> getLstComenzi() {
		return lstComenzi;
	}

	public void setLstComenzi(List<Comanda> lstComenzi) {
		this.lstComenzi = lstComenzi;
	}

	public LinieComanda getLinieComanda() {
		return linieComanda;
	}

	public void setLinieComanda(LinieComanda linieComanda) {
		this.linieComanda = linieComanda;
	}

	public List<Angajat> getLstAngajati() {
		return lstAngajati;
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

	public List<LinieComanda> getLstLiniiComanda() {
		return lstLiniiComanda;
	}

	public void setLstLiniiComanda(List<LinieComanda> lstLiniiComanda) {
		this.lstLiniiComanda = lstLiniiComanda;
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
