package factories;

import dao.ProductDaoImpl;

public class ProductFactory extends AbstractFactory {
	
	public ProductFactory() {}
	
	@Override
	public ProductDaoImpl getProductDao (Class<? extends ProductDaoImpl> typeDao) {
		
		if ( typeDao == null ) {
			return null;
		}
		
		if (typeDao == ProductDaoImpl.class) {
			return new ProductDaoImpl ();
		}
		
		return null ;
	}
}
