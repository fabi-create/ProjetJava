package factories;


public class ConcreteFactory {
	
	public ConcreteFactory() { }
	
	public static AbstractFactory getFactory (Class<? extends AbstractFactory> factory) {
		if (factory == null) {
			return null;
		}
		
		if (factory == UserFactory.class) {
			return new UserFactory ();
		} else if (factory == ChefFactory.class) {
			return new ChefFactory ();
		} else if (factory == RestaurateurFactory.class) {
			return new RestaurateurFactory ();
		} else if (factory == SuperAdminFactory.class) {
			return new SuperAdminFactory ();
		} else if (factory == AdministratorFactory.class) {
			return new AdministratorFactory ();
		} else if (factory == ProductFactory.class) {
			return new ProductFactory ();
		} else if (factory == ProductDetailsFactory.class) {
			return new ProductDetailsFactory ();
		} else if (factory == ProductImageFactory.class) {
			return new ProductImageFactory ();
		} else if (factory == OrderFactory.class) {
			return new OrderFactory ();
		} else if (factory == PaymentFactory.class) {
			return new PaymentFactory ();
		} else if (factory == RecipeFactory.class) {
			return new RecipeFactory ();
		}
		
		return null;
	}
}
