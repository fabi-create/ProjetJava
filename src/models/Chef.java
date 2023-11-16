package models;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity(name="T_Chefs")
public class Chef extends User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Chef() {
		
	}

	public Chef(int id, String lastname, String firstname, String login, String password, String role) {
		super(id, lastname, firstname, login, password, role);
	}
	
	public Chef(String lastname, String firstname, String login, String password, String role) {
		super(lastname, firstname, login, password, role);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		
		if(!(obj instanceof Chef)) return false;
		
		if(this.id == ((Chef)obj).getId())
			return true;
		
		return false;
	}

}
