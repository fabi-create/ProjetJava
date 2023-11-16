package factories;

import dao.OrderDaoImpl;

public class OrderFactory extends AbstractFactory {

	@Override
	public OrderDaoImpl getOrderDao (Class<? extends OrderDaoImpl> typeDao) {
	
		if ( typeDao == null ) {
			return null;
		}
		
		if (typeDao == OrderDaoImpl.class) {
			return new OrderDaoImpl ();
		}
		
		return null ;
	}
}
