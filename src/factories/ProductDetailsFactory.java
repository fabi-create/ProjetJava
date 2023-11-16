package factories;

import dao.ProductDetailsDaoImpl;

public class ProductDetailsFactory extends AbstractFactory {
	
	public ProductDetailsFactory() {}
	
	@Override
	public ProductDetailsDaoImpl getProductDetailsDao (Class<? extends ProductDetailsDaoImpl> typeDao) {
		
		if ( typeDao == null ) {
			return null;
		}
		
		if (typeDao == ProductDetailsDaoImpl.class) {
			return new ProductDetailsDaoImpl ();
		}
		
		return null ;
	}
}
