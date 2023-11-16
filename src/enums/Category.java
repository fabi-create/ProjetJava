package enums;

public enum Category {
	ENTREE("Entrees"),
	PlATS_PRINCIPAUX("Plats principaux"),
	ACCOMPAGENEMENTS("Accompagnements"),
	DESSERTS("Desserts"),
	BOISSONS("Boissons"),
	MENU_ENFANT("Menu pour enfants"),
	PLATS_SPECIAUX("Plats spéciaux");
	

    private String category;

    private Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
