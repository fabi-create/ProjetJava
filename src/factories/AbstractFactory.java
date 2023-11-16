package factories;

import dao.AdministratorDaoImpl;
import dao.ChefDaoImpl;
import dao.OrderDaoImpl;
import dao.PaymentDaoImpl;
import dao.ProductDaoImpl;
import dao.ProductDetailsDaoImpl;
import dao.ProductImageDaoImpl;
import dao.RecipeDaoImpl;
import dao.RestaurateurDaoImpl;
import dao.SuperAdminDaoImpl;
import dao.UserDaoImpl;

public class AbstractFactory {
	public AbstractFactory() { }
	
	public UserDaoImpl getUserDao (Class<? extends UserDaoImpl> typeDao) {
		return null ;
	}
	
	public ChefDaoImpl getChefDao (Class<? extends ChefDaoImpl> typeDao) {
		return null ;
	}
	
	public RestaurateurDaoImpl getRestaurateurDao (Class<? extends RestaurateurDaoImpl> typeDao) {
		return null ;
	}
	
	public AdministratorDaoImpl getAdministratorDao (Class<? extends AdministratorDaoImpl> typeDao) {
		return null ;
	}
	
	public SuperAdminDaoImpl getSuperAdministratorDao (Class<? extends SuperAdminDaoImpl> typeDao) {
		return null ;
	}
	
	public ProductDaoImpl getProductDao (Class<? extends ProductDaoImpl> typeDao) {
		return null ;
	}
	
	public ProductDetailsDaoImpl getProductDetailsDao (Class<? extends ProductDetailsDaoImpl> typeDao) {
		return null ;
	}
	
	public ProductImageDaoImpl getProductImageDao (Class<? extends ProductImageDaoImpl> typeDao) {
		return null ;
	}
	
	public OrderDaoImpl getOrderDao (Class<? extends OrderDaoImpl> typeDao) {
		return null ;
	}
	
	public PaymentDaoImpl getPaymentDao (Class<? extends PaymentDaoImpl> typeDao) {
		return null ;
	}
	
	public RecipeDaoImpl getRecipeDao (Class<? extends RecipeDaoImpl> typeDao) {
		return null ;
	}
}
