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
import silviuCRMClase.Contact;
import silviuCRMClase.Operatiune;
import silviuCRMClase.RaspunsTicket;
import silviuCRMClase.Ticket;

@ManagedBean
@SessionScoped
public class TicketFormClient {
	EntityManager em;
	private Contact contactLogin = new Contact();
	private Angajat angajatLogin= new Angajat();
	private Ticket ticket = new Ticket();
	private RaspunsTicket raspunsTicket = new RaspunsTicket();
	private List<Ticket> lstTickets = new ArrayList<>();
	private List<Ticket> lstTicketsClient = new ArrayList<>();
	private List<RaspunsTicket> lstRaspunsuriTicket = new ArrayList<RaspunsTicket>();
	private Operatiune operatiune = new Operatiune();
	private OperatiuneForm operatiuneForm = new OperatiuneForm();
	private EmailForm emailForm= new EmailForm();
	private Contact c=new Contact();

	public TicketFormClient() {
		super();

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SilviuCRM");
		em = emf.createEntityManager();
		contactLogin = LoginFormStaticUser.contactLogin;
		angajatLogin=LoginFormStaticUser.angajatLogin;
		initLstTickets();
		initLstRaspunsuriTicket();

	}

	public void initLstTickets() {
		lstTicketsClient.clear();
		lstTickets.clear();
		lstTickets = this.em.createQuery("Select t From Ticket t").getResultList();
		for(Ticket t: lstTickets) {
			if(t.getContactTicket().equals(contactLogin)) {
				lstTicketsClient.add(t);
			}
		}
	}


	public void initLstRaspunsuriTicket() {
		lstRaspunsuriTicket = this.em.createQuery("Select r From RaspunsTicket r").getResultList();
	}

	public void adaugaTicket(ActionEvent event) {
		this.ticket = new Ticket();
		this.ticket.setDataDeschidereTicket(Calendar.getInstance().getTime());
		this.ticket.setStatusTicket("Deschis");
		System.out.println("aaaaa");
	}

	public void adaugaRaspunsTicket(ActionEvent event) {

		this.raspunsTicket= new RaspunsTicket();
		this.raspunsTicket.setTicket(ticket);
		this.raspunsTicket.setAngajatRaspunsTicket(angajatLogin);
		this.ticket.getListaRaspunsuriTicket().add(this.raspunsTicket);
		System.out.println("aaaaa");
	}
	public void salvareTicket(ActionEvent event) {
		initLstTickets();
		System.out.println("Salvare Ticket");
		this.ticket.setContactTicket(contactLogin);
		em.getTransaction().begin();
		if (this.em.contains(this.ticket)) {
			this.em.merge(this.ticket);
			em.getTransaction().commit();

		} else {
			this.em.persist(this.ticket);
			em.getTransaction().commit();
			opAddOTicket();

		}
		initLstTickets();

	}

	public void salvareRaspunsTicket(ActionEvent event) {
		initLstTickets();
		initLstTickets();
		System.out.println("Salvare  Raspuns Ticket");
        this.raspunsTicket.setDataRaspunsTicket(Calendar.getInstance().getTime());
		em.getTransaction().begin();
		if (this.em.contains(this.raspunsTicket)) {
			this.em.merge(this.raspunsTicket);
			em.getTransaction().commit();

		} else {
			this.em.persist(this.raspunsTicket);
			em.getTransaction().commit();

		}
		initLstTickets();
        initLstRaspunsuriTicket();
	}

	
	
	public void inchideTicket(Ticket t) {
		initLstTickets();
		System.out.println("Inchidere Ticket");
		this.ticket = t;
		this.ticket.setStatusTicket("Inchis");
		em.getTransaction().begin();
		if (this.em.contains(this.ticket)) {
			this.em.merge(this.ticket);
			em.getTransaction().commit();
         opCloseTicket();
		}
		initLstTickets();

	}
	
	public void inchideTicketAngajat(Ticket t) {
		initLstTickets();
		this.c= t.getContactTicket();
		System.out.println(c.getAdresaEmailContact());
		System.out.println("Inchidere Ticket");
		this.ticket = t;
		this.ticket.setStatusTicket("Inchis");
		em.getTransaction().begin();
		if (this.em.contains(this.ticket)) {
			this.em.merge(this.ticket);
			em.getTransaction().commit();
       opCloseTicketAngajat();
         trimiteEmailTicket();
		}
		initLstTickets();

	}

	public void select(Ticket t) {
		this.ticket = t;
	}



	public void selectRaspuns(RaspunsTicket rt) {
		this.raspunsTicket = rt;
	}

	
	public void opAddOTicket() {

		this.operatiune = new Operatiune();
		 this.operatiune.setCategorieOperatiune("Ticket");
		this.operatiune.setTipOperatiune("Inregistrare Ticket");
		   this.operatiune.setContactOperatiune(contactLogin);
		this.operatiune.setDescriereOperatiune("Clientul '" + this.contactLogin.getNumeContact()
				+ " a inregistrat un ticket nou. ");
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();
	}
	
	public void opCloseTicket() {

		this.operatiune = new Operatiune();
		 this.operatiune.setCategorieOperatiune("Ticket");
		this.operatiune.setTipOperatiune("Inchidere Ticket");
		   this.operatiune.setContactOperatiune(this.ticket.getContactTicket());
		this.operatiune.setDescriereOperatiune("Clientul '" + this.contactLogin.getNumeContact()
				+ " si-a inchis ticketul. ");
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();
	}
	
	public void opCloseTicketAngajat() {

		this.operatiune = new Operatiune();
		 this.operatiune.setCategorieOperatiune("Ticket");
		this.operatiune.setTipOperatiune("Inchidere Ticket");
		   this.operatiune.setAngajatOperatiune(angajatLogin);;
		this.operatiune.setDescriereOperatiune("Angajatul '" + this.angajatLogin.getNumeAngajat()
				+ " a solutionat un ticket. ");
		this.operatiuneForm.salvareOperatiuneFormJava(this.operatiune);
		this.operatiuneForm.initLstOperatiuni();
	}
	
	  private void trimiteEmailTicket() {
			
			this.emailForm= new EmailForm();
			this.emailForm.addEmail();
			this.emailForm.getEmail().setAdresaEmail(this.c.getAdresaEmailContact());
			this.emailForm.getEmail().setCategorieDestinatarEmail("Ticket");
			this.emailForm.getEmail().setContinutEmail("Stimate dn/dna "+ this.c.getNumeContact()+ ",\n"
					+ "Ticket-ul tau a fost solutionat!\n"
					+ "Acceseaza contul tau de client pe site-ul nostru 'www.SilviuCRM.ro' pentru mai multe detalii!\n"
					+ "Cu drag, \n"
					+ "Echipa SilviuCRM");
			
			this.emailForm.getEmail().setSubiectEmail("Solutionare Ticket SilviuCRM");
			this.emailForm.getEmail().setContactEmail(this.c);
	
			this.emailForm.salvareEmail();
			
			
			this.emailForm.setSubiectEmail("Solutionare Ticket SilviuCRM");
			this.emailForm.setMesajEmail("Stimate dn/dna "+ this.c.getNumeContact()+ ",\n"
					+ "Ticket-ul tau a fost solutionat! \n"
					+ "Acceseaza contul tau de client pe site-ul nostru 'www.SilviuCRM.ro' pentru mai multe detalii!'\n"
					+ "Cu drag, \n"
					+ "Echipa SilviuCRM");
			this.emailForm.setReceptorEmail(this.c.getAdresaEmailContact());
			this.emailForm.trimiteEmail();
		}
	public Contact getContactLogin() {
		return contactLogin;
	}

	public void setContactLogin(Contact contactLogin) {
		this.contactLogin = contactLogin;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public RaspunsTicket getRaspunsTicket() {
		return raspunsTicket;
	}

	public void setRaspunsTicket(RaspunsTicket raspunsTicket) {
		this.raspunsTicket = raspunsTicket;
	}

	public List<Ticket> getLstTickets() {
		return lstTickets;
	}

	public void setLstTickets(List<Ticket> lstTickets) {
		this.lstTickets = lstTickets;
	}

	public List<RaspunsTicket> getLstRaspunsuriTicket() {
		return lstRaspunsuriTicket;
	}

	public void setLstRaspunsuriTicket(List<RaspunsTicket> lstRaspunsuriTicket) {
		this.lstRaspunsuriTicket = lstRaspunsuriTicket;
	}

	public Angajat getAngajatLogin() {
		return angajatLogin;
	}

	public void setAngajatLogin(Angajat angajatLogin) {
		this.angajatLogin = angajatLogin;
	}

	public List<Ticket> getLstTicketsClient() {
		return lstTicketsClient;
	}

	public void setLstTicketsClient(List<Ticket> lstTicketsClient) {
		this.lstTicketsClient = lstTicketsClient;
	}

	public Operatiune getOperatiune() {
		return operatiune;
	}

	public void setOperatiune(Operatiune operatiune) {
		this.operatiune = operatiune;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Contact getC() {
		return c;
	}

	public void setC(Contact c) {
		this.c = c;
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

}
