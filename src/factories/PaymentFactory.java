package factories;

import dao.PaymentDaoImpl;

public class PaymentFactory extends AbstractFactory {

	@Override
	public PaymentDaoImpl getPaymentDao (Class<? extends PaymentDaoImpl> typeDao) {
		if ( typeDao == null ) {
			return null;
		}
		
		if (typeDao == PaymentDaoImpl.class) {
			return new PaymentDaoImpl ();
		}
		return null ;
	}
}
