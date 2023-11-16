package factories;

import dao.UserDaoImpl;

public class UserFactory extends AbstractFactory {
	
	public UserFactory() {}
	
	@Override
	public UserDaoImpl getUserDao (Class<? extends UserDaoImpl> typeDao) {
		if ( typeDao == null ) {
			return null;
		}
		
		if (typeDao == UserDaoImpl.class) {
			return new UserDaoImpl ();
		} 
		
		return null;
	}

}
