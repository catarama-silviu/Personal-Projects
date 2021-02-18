package silviuCRMFormsJava;

import java.util.ArrayList;
import java.util.Arrays;
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

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import silviuCRMClase.Angajat;
import silviuCRMClase.Contact;
import silviuCRMClase.Lead;
import silviuCRMClase.Operatiune;


@ManagedBean
@SessionScoped
public class LeadForm {

	 private EntityManager em;
	private Lead lead=new Lead();
	private Contact contact = new Contact();
	private ContactForm contactForm=new ContactForm();
	private  List<Lead>lstLeads= new ArrayList<>();
	private Angajat angajat;
	private Operatiune operatiune= new Operatiune();
	private OperatiuneForm operatiuneForm= new OperatiuneForm();
	private String numeLead;
	private EmailForm emailForm= new EmailForm();
	private List<Angajat> lstAngajati = new ArrayList<>();
	private Angajat angajatLogin= new Angajat();
	
	
	public LeadForm() {
		super();
		 EntityManagerFactory emf= Persistence.createEntityManagerFactory("SilviuCRM");
		em=emf.createEntityManager();
		initLstLeads();
		initLstAngajati();
		angajatLogin=LoginFormStaticUser.angajatLogin;
		System.out.println(angajatLogin.getNumeAngajat());
	}
	
	public  void initLstLeads() {
		lstLeads= this.em.createQuery("Select l From Lead l").getResultList();
	}

	public void onRowSelect(SelectEvent event) {
		this.lead= (Lead) event.getObject();
    }
 
    public void onRowUnselect(UnselectEvent event) {
    	this.lead= lstLeads.get(0);
    }
    
    public void initLstAngajati() {

		lstAngajati = em.createQuery("Select a from Angajat a").getResultList();
		if (!lstAngajati.isEmpty()) {
			this.setAngajat(lstAngajati.get(0));
		}
	}

	public void addLeadNou(ActionEvent event) {
		initLstAngajati();
		this.lead = new Lead();
		this.lead.setLeadOwner(lstAngajati.get(0));
	


	}

	public void salvareLead(ActionEvent event) {
		System.out.println("Salvare Lead");

		em.getTransaction().begin();
		if (this.em.contains(this.lead)) {
			this.em.merge(this.lead);
			em.getTransaction().commit();
		opModificareLead();
		
		}
		else {
		
			this.em.persist(this.lead);
			em.getTransaction().commit();
			opAddLead();
			trimiteEmailLead();
			}
		

		initLstLeads();
		
	}
	
	public void salvareLeadTransferContact() {
		System.out.println("Salvare Lead");

		em.getTransaction().begin();
		if (this.em.contains(this.lead)) {
			this.em.merge(this.lead);
			em.getTransaction().commit();
		
		
		}
		else {
		
			this.em.persist(this.lead);
			em.getTransaction().commit();
			
			}
		

		initLstLeads();
		
	}


	public void abandonLead(ActionEvent event) {
		System.out.println("Ati Anulat Actiunea");
		em.clear();

	}
	
	private void trimiteEmailLead() {
		
		this.emailForm= new EmailForm();
		this.emailForm.addEmail();
		this.emailForm.getEmail().setAdresaEmail(this.lead.getAdresaEmailLead());
		this.emailForm.getEmail().setCategorieDestinatarEmail("Leads");
		this.emailForm.getEmail().setContinutEmail("Stimate dn/dna"+ this.lead.getNumeLead()
				+ "Am auzit ca esti interesat de produsele pe care firma noastra le comercializeaza! \n"
				+ "Pentru mai multe detalii viziteaza site-ul companiei noastre la adresa 'www.SilviuCRM.ro'\n"
				+ "Iti multumim pentru intelegere \n"
				+ "Echipa SilviuCRM");
		
		this.emailForm.getEmail().setSubiectEmail("Multumiri din partea companiei SilviuCRM");
		this.emailForm.getEmail().setLeadEmail(this.lead);
		this.emailForm.salvareEmail();
		this.lead.getListaEmailLead().add(this.emailForm.getEmail());
		this.emailForm.setSubiectEmail("Multumiri din partea companiei SilviuCRM");
		this.emailForm.setMesajEmail("Stimate dn/dna"+ this.lead.getNumeLead()
				+ "Am auzit ca esti interesat de produsele pe care firma noastra le comercializeaza! \n"
				+ "Pentru mai multe detalii viziteaza site-ul companiei noastre la adresa 'www.SilviuCRM.ro'\n"
				+ "Iti multumim pentru intelegere \n"
				+ "Echipa SilviuCRM");
		this.emailForm.setReceptorEmail(this.lead.getAdresaEmailLead());
		this.emailForm.trimiteEmail();
	}
	
	
	
	public void select(Lead lead) {
		  this.lead=lead;
	   }	
   
   public void stergereLead(Lead l) {

		this.lead=l;
		numeLead=this.lead.getNumeLead();
		
		if (em.contains(l)) {
			em.getTransaction().begin();
			em.remove(l);
			em.getTransaction().commit();
			
			initLstLeads();
			opStergereLead();
			System.out.println("A fost sters din db  " + this.lead.getNumeLead());
			
		}
   }
   public void transferLaContacte(Lead l) {
	   this.lead=l;
	   this.lead.setLeadStatus("Contactat");
	   this.contact= new Contact();
	   this.contact.setNumeContact(l.getNumeLead());
	   this.contact.setAdresaEmailContact(l.getAdresaEmailLead());
	   this.contact.setNumarTelefonContact(l.getNrTelefonLead());
	   this.contact.setSursaLeadContact(l.getSursaLead());
	   this.contact.setDataNasteriiContact(l.getDataNasteriiLead());
	   this.contact.setDataInscrieriiContact(Calendar.getInstance().getTime());
	   this.contact.setAdresaOrasContact(l.getAdresaOrasLead());
	   this.contact.setAdresaTaraContact(l.getAdresaTaraLead());
	   this.contact.setDescriereContact(l.getDescriereLead());
	   this.contact.setContactOwner(l.getLeadOwner());
	   this.contactForm.salvareContactTransferLead(this.contact);
	   initLstLeads();
	   opTransferareLead();
	   salvareLeadTransferContact();
   }
   
   public void opAddLead() {
	   
	   this.operatiune= new Operatiune();
	   
	   this.operatiune.setCategorieOperatiune("Leads");
	   this.operatiune.setTipOperatiune("Inregistrare Lead");
	   this.operatiune.setAngajatOperatiune(this.angajat);
	   this.operatiune.setDescriereOperatiune("Angajatul '"+angajat.getNumeAngajat()+ "' a inregistrat un lead nou cu numele '"+this.lead.getNumeLead()+"'.");
	   this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
	   this.operatiuneForm.initLstOperatiuni();
   }
   
 public void opModificareLead() {
	   
	   this.operatiune= new Operatiune();
	   this.operatiune.setCategorieOperatiune("Leads");
	   this.operatiune.setTipOperatiune(" Modificare Lead");
	   this.operatiune.setAngajatOperatiune(this.angajat);
	   this.operatiune.setDescriereOperatiune("Angajatul '"+angajat.getNumeAngajat()+ "' a inregistrat modificari asupra lead-ului '"+this.lead.getNumeLead()+"'.");
	   this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
	   this.operatiuneForm.initLstOperatiuni();
	   
   }
 
 public void opTransferareLead() {
	   
	   this.operatiune= new Operatiune();
	   this.operatiune.setCategorieOperatiune("Leads");
	   this.operatiune.setTipOperatiune("Transfer lead");
	   this.operatiune.setAngajatOperatiune(this.angajat);
	   this.operatiune.setDescriereOperatiune("Angajatul '"+angajat.getNumeAngajat()+ "' a inregistrat  lead-ul '"+this.lead.getNumeLead()+"' ca si contact.");
	   this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
	   this.operatiuneForm.initLstOperatiuni();
 }
 public void opStergereLead() {
	   
	   this.operatiune= new Operatiune();
	   this.operatiune.setCategorieOperatiune("Leads");
	   this.operatiune.setTipOperatiune("Stergere Lead");
	   this.operatiune.setAngajatOperatiune(this.angajat);
	   this.operatiune.setDescriereOperatiune("Angajatul '"+angajat.getNumeAngajat()+ "' a sters lead-ul '"+numeLead+"'.");
	   this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
	   this.operatiuneForm.initLstOperatiuni();
 }
   public void valueChange(ValueChangeEvent event) {

		
	      if(this.angajatLogin.getRolAngajat().equals("Angajat")) {
	          this.angajat= angajatLogin;
	          this.lead.setLeadOwner(this.angajat);
	          }
	          else {
		Stream<Angajat> streamAngajat = this.lstAngajati.stream()
				.filter(a -> a.getIdAngajat().equals(Integer.parseInt((String) event.getNewValue())));

		List<Angajat> listaAlegeAngajat = new ArrayList<Angajat>();
		streamAngajat.forEach(a -> listaAlegeAngajat.add(a));
		this.angajat = listaAlegeAngajat.get(0);
		this.lead.setLeadOwner(this.angajat);
		System.out.println("A fost selectat angajatul "+this.angajat.getNumeAngajat());
	}
   }
	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public List<Lead> getLstLeads() {
		return lstLeads;
	}

	public void setLstLeads(List<Lead> lstLeads) {
		this.lstLeads = lstLeads;
	}

	public Angajat getAngajat() {
		return angajat;
	}

	public ContactForm getContactForm() {
		return contactForm;
	}

	public void setContactForm(ContactForm contactForm) {
		this.contactForm = contactForm;
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

	public void setAngajat(Angajat angajat) {
		this.angajat = angajat;
	}



	public String getNumeLead() {
		return numeLead;
	}

	public void setNumeLead(String numeLead) {
		this.numeLead = numeLead;
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

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}




	public List<Angajat> getLstAngajati() {
		return lstAngajati;
	}

	public void setLstAngajati(List<Angajat> lstAngajati) {
		this.lstAngajati = lstAngajati;
	}

	
	
	
}
