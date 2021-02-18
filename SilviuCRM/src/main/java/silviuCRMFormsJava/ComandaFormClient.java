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
public class ComandaFormClient {

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
	private List<Comanda> lstComenziGenerala = new ArrayList<>();
	private List<Produs> lstProduse = new ArrayList<>();
	private Operatiune operatiune = new Operatiune();
	private OperatiuneForm operatiuneForm = new OperatiuneForm();
	private EmailForm emailForm = new EmailForm();
	private Contact contactLogin= new Contact();

	

	public ComandaFormClient() {
		super();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SilviuCRM");
		em = emf.createEntityManager();
		contactLogin= LoginFormStaticUser.contactLogin;
		initLstContacte();
		initLstProduse();
		initLstComenzi();
	
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
       lstComenziGenerala.clear();
       lstComenzi.clear();
		lstComenziGenerala = em.createQuery("Select c from Comanda c").getResultList();
        for(Comanda c:lstComenziGenerala) {
        	if(c.getContactComanda().equals(contactLogin)) {
        		lstComenzi.add(c);
        	}
        }
		
		
	}

	public void addAdresaContactComanda(ActionEvent e) {
		this.comanda.setAdresaComanda(
				"Adresa: " + this.contact.getAdresaOrasContact() + ", Tara: " + this.contact.getAdresaTaraContact());
	}

	public void addOComandaNoua(ActionEvent event) {

	
		initLstContacte();
		initLstProduse();
		this.comanda = new Comanda();
		this.comanda.setDataInregistrareComanda(Calendar.getInstance().getTime());
		this.comanda.setContactComanda(this.contactLogin);
		this.lstLiniiComanda = new ArrayList<LinieComanda>();
		this.comanda.setStatusComanda("Creata");

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
		  this.operatiune.setContactOperatiune(contactLogin);
		this.operatiune.setDescriereOperatiune("Clientul '" + contactLogin.getNumeContact()
				+ "' a inregistrat o comanda noua.");
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();
	}

	public void opModificareComanda() {

		this.operatiune = new Operatiune();

		this.operatiune.setTipOperatiune("Modificare Comanda");
		this.operatiune.setCategorieOperatiune("Comenzi");
		  this.operatiune.setContactOperatiune(contactLogin);
		this.operatiune.setDescriereOperatiune("Clientul '" + contactLogin.getNumeContact()
				+ "' a inregistrat modificari asupra comenzii " + "cu ID:'" + this.comanda.getIdComanda());
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();

	}





	public void opAnulareComanda() {

		this.operatiune = new Operatiune();
		this.operatiune.setCategorieOperatiune("Comenzi");
		this.operatiune.setTipOperatiune("Anulare Comanda");
		  this.operatiune.setContactOperatiune(contactLogin);
		this.operatiune.setDescriereOperatiune(
				"Clientul '" + contactLogin.getNumeContact() + "' a ANULAT comanda cu ID:'" + this.comanda.getIdComanda());
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();
	}

	public void selectComanda(Comanda c) {
		this.comanda = c;
	}



	public void anuleazaComanda(Comanda c) {
		this.comanda = c;
		this.comanda.setStatusComanda("Anulata");
		salvareComandaOperatiune();
		opAnulareComanda();
		trimiteEmailAnulareComanda();
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

	
	
	private void trimiteEmailInregistrareComanda() {

		this.emailForm = new EmailForm();
		this.emailForm.addEmail();
		this.emailForm.getEmail().setAdresaEmail(this.contactLogin.getAdresaEmailContact());
		this.emailForm.getEmail().setCategorieDestinatarEmail("Contact");
		this.emailForm.getEmail()
				.setContinutEmail("Stimate dn/dna" + this.contactLogin.getNumeContact() + ",\n"
						+ "Comanda dumneavoastra a fost inregistrata cu succes!\n"
						+ "In cel mai scurt o sa fiti contactat de unul dintre angajatii nostri!!\n" + "Cu drag, \n"
						+ "Echipa SilviuCRM");

		this.emailForm.getEmail().setSubiectEmail("Inregistrare comanda www.SilviuCRM.ro");
		this.emailForm.getEmail().setContactEmail(this.contactLogin);
		this.emailForm.salvareEmail();
		this.contact.getListaEmail_uriContact().add(this.emailForm.getEmail());

		this.emailForm.setSubiectEmail("Inregistrare comanda www.SilviuCRM.ro");
		this.emailForm.setMesajEmail("Stimate dn/dna " + this.contactLogin.getNumeContact() + ",\n"
				+ "Comanda dumneavoastra a fost inregistrata cu succes!! \n"
				+ "In cel mai scurt o sa fiti contactat de unul dintre angajatii nostri!'\n" + "Cu drag, \n"
				+ "Echipa SilviuCRM");
		this.emailForm.setReceptorEmail(this.contactLogin.getAdresaEmailContact());
		this.emailForm.trimiteEmail();
	}



	private void trimiteEmailAnulareComanda() {

		this.emailForm = new EmailForm();
		this.emailForm.addEmail();
		this.emailForm.getEmail().setAdresaEmail(this.contactLogin.getAdresaEmailContact());
		this.emailForm.getEmail().setCategorieDestinatarEmail("Contact");
		this.emailForm.getEmail()
				.setContinutEmail("Stimate dn/dna" + this.contactLogin.getNumeContact() + ",\n"
						+ "Comanda dumneavoastra a fost confirmata cu succes!\n"
						+ "In cel mai scurt o sa fiti contactat de unul dintre angajatii nostri!!\n" + "Cu drag, \n"
						+ "Echipa SilviuCRM");

		this.emailForm.getEmail().setSubiectEmail("Inregistrare comanda www.SilviuCRM.ro");
		this.emailForm.getEmail().setContactEmail(this.contactLogin);
		this.emailForm.salvareEmail();
		this.contact.getListaEmail_uriContact().add(this.emailForm.getEmail());

		this.emailForm.setSubiectEmail("Inregistrare comanda www.SilviuCRM.ro");
		this.emailForm.setMesajEmail("Stimate dn/dna " + this.contactLogin.getNumeContact() + ",\n"
				+ "Comanda dumneavoastra a fost anulata!! \n" + "Cu drag, \n" + "Echipa SilviuCRM");
		this.emailForm.setReceptorEmail(this.contactLogin.getAdresaEmailContact());
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


	public Contact getContactLogin() {
		return contactLogin;
	}


	public void setContactLogin(Contact contactLogin) {
		this.contactLogin = contactLogin;
	}



}
