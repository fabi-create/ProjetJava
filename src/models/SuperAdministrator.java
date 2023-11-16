package models;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity(name="T_SuperAdministrators")
public class SuperAdministrator extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SuperAdministrator() {
		
	}

	public SuperAdministrator(int id, String lastname, String firstname, String login, String password, String role) {
		super(id, lastname, firstname, login, password, role);
	}
	
	public SuperAdministrator(String lastname, String firstname, String login, String password, String role) {
		super(lastname, firstname, login, password, role);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		
		if(!(obj instanceof Administrator)) return false;
		
		if(this.id == ((Administrator)obj).getId())
			return true;
		
		return false;
	}

}
