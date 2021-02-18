package silviuCRMFormsJava;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import silviuCRMClase.Angajat;
import silviuCRMClase.Contact;
import silviuCRMClase.Email;
import silviuCRMClase.Lead;
import silviuCRMClase.LoginCreateAccount;

@ManagedBean
@SessionScoped
public class EmailForm {

	 
     EntityManager em;
  
    private Email email= new Email();
    private List<Email> lstEmail= new ArrayList<>();
    private Contact contact= new Contact();
     
     
     
     public EmailForm() {
		super();
		EntityManagerFactory emf= Persistence.createEntityManagerFactory("SilviuCRM");
		em=emf.createEntityManager();
		initLstEmail();
	}


 	private void initLstEmail() {
 		lstEmail= em.createQuery("Select e From Email e").getResultList();
 	}

  	public void addEmail() {
 		System.out.println("Creare Email");
 		this.email = new Email();
 		this.contact= new Contact();
  	}
 	public void salvareEmail() {
		System.out.println("Salvare Email");
		this.email.setDataTrimitereEmail(Calendar.getInstance().getTime());
		em.getTransaction().begin();
		if (this.em.contains(this.email))
			{
			this.em.merge(this.email);
			em.getTransaction().commit();
		
			}
		else
			this.em.persist(this.email);{
		em.getTransaction().commit();
		
			}
		
	}
	public void stergereEmailContact(Contact c) {
		initLstEmail();
		System.out.println(lstEmail.size());
	       this.email= new Email();
	       
			for(Email e:lstEmail) {
				System.out.println(c.getListaEmail_uriContact().size()+"email cintact");
				
				if(e.getContactEmail()==c) {
					this.email=e;
					System.out.println(e.getContactEmail().getNumeContact());
				}
				if (em.contains(this.email)) {
					em.getTransaction().begin();
					em.remove(this.email);
					em.getTransaction().commit();
				
				}
				
			}
			
		}

     String mesajEmail=" ";
	 String subiectEmail=" ";
	//String receptorEmail="cataramasilviu99@gmail.com";
	 String receptorEmail;
	 String expeditorEmail="silviusivlad2020@gmail.com";
	     String username = "silviusivlad2020@gmail.com";
       String password = "VladSiSilviu2020?";
	public void trimiteEmail() {
    	
       
     Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
     prop.put("mail.smtp.port", "587");
     prop.put("mail.smtp.auth", "true");
     prop.put("mail.smtp.starttls.enable", "true"); //TLS

     Session session = Session.getInstance(prop,
             new javax.mail.Authenticator() {
                 protected PasswordAuthentication getPasswordAuthentication() {
                     return new PasswordAuthentication(username, password);
                 }
             });

     try {

         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(expeditorEmail));
         message.setRecipients(
                 Message.RecipientType.TO,
                 InternetAddress.parse(receptorEmail)
         );
         message.setSubject(subiectEmail);
         message.setText(mesajEmail);

         Transport.send(message);

         System.out.println("Done");

     } catch (MessagingException e) {
         e.printStackTrace();
     }
     }

	
     
     
     public String getMesajEmail() {
		return mesajEmail;
	}

	public void setMesajEmail(String mesajEmail) {
		this.mesajEmail = mesajEmail;
	}

	public String getSubiectEmail() {
		return subiectEmail;
	}

	public Email getEmail() {
		return email;
	}



	public void setEmail(Email email) {
		this.email = email;
	}



	public void setSubiectEmail(String subiectEmail) {
		this.subiectEmail = subiectEmail;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}




	public String getReceptorEmail() {
		return receptorEmail;
	}




	public void setReceptorEmail(String receptorEmail) {
		this.receptorEmail = receptorEmail;
	}




	public String getExpeditorEmail() {
		return expeditorEmail;
	}




	public void setExpeditorEmail(String expeditorEmail) {
		this.expeditorEmail = expeditorEmail;
	}


	public List<Email> getLstEmail() {
		return lstEmail;
	}


	public void setLstEmail(List<Email> lstEmail) {
		this.lstEmail = lstEmail;
	}


	public Contact getContact() {
		return contact;
	}


	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
     
     
}
