package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="T_Users")
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	protected int id;
	
	protected String lastname;
	protected String firstname;

	protected String login;
	protected String password;
	
	protected String role;
	
	public User(int id, String lastname, String firstname, String login, String password, String role) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.login = login;
		this.password = password;
		this.role = role;
	}
	
	public User(String lastname, String firstname, String login, String password, String role) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.login = login;
		this.password = password;
		this.role = role;
	}
	
	public User() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
    public String toString() {
        return "User { " +
                "id=" + id +
                ", nom=" + lastname +
                ", prenom=" + firstname + 
                ", login=" + login + 
                ", password=" + password +
                ", role=" + role + 
                " }";
    }
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		
		if(!(obj instanceof User)) return false;
		
		if((this.id == ((User)obj).getId()) && 
			this.login == ((User)obj).getLogin() && 
			this.password == ((User)obj).getPassword())
			return true;
		
		return false;
	}
	
}
