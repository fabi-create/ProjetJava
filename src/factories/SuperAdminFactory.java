package factories;

import dao.SuperAdminDaoImpl;

public class SuperAdminFactory extends AbstractFactory {
	public SuperAdminFactory() {}
	
	@Override
	public SuperAdminDaoImpl getSuperAdministratorDao (Class<? extends SuperAdminDaoImpl> typeDao) {
		if ( typeDao == null ) {
			return null;
		}
		
		if (typeDao == SuperAdminDaoImpl.class) {
			return new SuperAdminDaoImpl ();
		} 
		return null ;
	}
}
