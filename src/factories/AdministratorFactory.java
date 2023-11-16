package factories;

import dao.AdministratorDaoImpl;

public class AdministratorFactory extends AbstractFactory {
	
	public AdministratorFactory() {}
	
	@Override
	public AdministratorDaoImpl getAdministratorDao (Class<? extends AdministratorDaoImpl> typeDao) {
		if ( typeDao == null ) {
			return null;
		}
		
		if (typeDao == AdministratorDaoImpl.class) {
			return new AdministratorDaoImpl ();
		} 
		return null ;
	}

}
