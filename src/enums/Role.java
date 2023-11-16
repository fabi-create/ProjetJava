package enums;

public enum Role {
	SUPERADMINISTRATOR("SuperAdministrator"),
	ADMINISTRATOR("Administrator"),
	RESTAURATEUR("Restaurateur"),
	CHEF("Chef"),
    USER("User");

    private String role;

    private Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
