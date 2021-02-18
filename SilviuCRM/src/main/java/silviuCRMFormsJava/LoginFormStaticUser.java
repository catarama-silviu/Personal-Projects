package silviuCRMFormsJava;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import silviuCRMClase.Angajat;
import silviuCRMClase.Contact;
@ManagedBean
@SessionScoped
public class LoginFormStaticUser {

	
	public static Angajat angajatLogin= new Angajat();
	public static Contact contactLogin= new Contact();
	
	
	
	
	
	public LoginFormStaticUser() {
		super();
		
	}
	
	
	
	
	
}
