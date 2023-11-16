package enums;

public enum RecipeFrequency {
	JOURNALIERE("Journaliere"),
	HEBDOMADAIRE("Hebdomaire"),
	MENSUELLE("Mensuelle");

    private String frequence;

    private RecipeFrequency(String frequence) {
        this.frequence = frequence;
    }

    public String getFrequency() {
        return frequence;
    }
}
