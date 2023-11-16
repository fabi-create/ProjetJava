package factories;

import dao.ProductImageDaoImpl;

public class ProductImageFactory extends AbstractFactory {
	
	public ProductImageFactory() {}
	
	@Override
	public ProductImageDaoImpl getProductImageDao (Class<? extends ProductImageDaoImpl> typeDao) {
		
		if ( typeDao == null ) {
			return null;
		}
		
		if (typeDao == ProductImageDaoImpl.class) {
			return new ProductImageDaoImpl ();
		}
		
		return null ;
	}
}
