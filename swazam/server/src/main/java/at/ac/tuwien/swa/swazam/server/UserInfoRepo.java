package at.ac.tuwien.swa.swazam.server;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class UserInfoRepo {
	SessionFactory factory;
	public UserInfoRepo(){
		try{
	         factory = new Configuration().configure().buildSessionFactory();
	      }catch (HibernateException ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
	}
	public void update(UserInfo item){
		Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
                 session.update(item); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	      }finally {
	         session.close(); 
	      }
	}
	public void insert(UserInfo item){
		Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
			 session.persist(item);
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	      }finally {
	         session.close(); 
	      }
	}
	public void delete(UserInfo item){
		Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
			 session.delete(item); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	      }finally {
	         session.close(); 
	      }
	}
	public UserInfo[] read(){
		Session session = factory.openSession();
                try{
                    List<UserInfo> items = (List<UserInfo>)session.createCriteria(UserInfo.class).list();
                    UserInfo[] users = new UserInfo[items.size()];int i = 0;
                    for(UserInfo user : items){
                        users[i++] = user;
                    }
                    return users;
	      }catch (HibernateException e) {
	      }finally {
	         session.close(); 
	      }
	      return null;
	}
        public UserInfo readOne(long id){
		Session session = factory.openSession();
                try{
                    return (UserInfo) session.createCriteria(UserInfo.class).add(Restrictions.eq("userName", id)).uniqueResult();
                    
	      }catch (HibernateException e) {
	      }finally {
	         session.close(); 
	      }
	      return null;
	}
}
