package factories;

import dao.RecipeDaoImpl;

public class RecipeFactory extends AbstractFactory {
	public RecipeFactory() {}
	
	@Override
	public RecipeDaoImpl getRecipeDao (Class<? extends RecipeDaoImpl> typeDao) {
		if ( typeDao == null ) {
			return null;
		}
		
		if (typeDao == RecipeDaoImpl.class) {
			return new RecipeDaoImpl ();
		}
		return null ;
	}
}
