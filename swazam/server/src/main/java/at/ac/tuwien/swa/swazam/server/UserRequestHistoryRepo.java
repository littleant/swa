/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.swa.swazam.server;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author 19992_000
 */
public class UserRequestHistoryRepo {
    SessionFactory factory;
	public UserRequestHistoryRepo(){
		try{
	         factory = new Configuration().configure().buildSessionFactory();
	      }catch (HibernateException ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
	}
	public void update(UserRequestHistory item){
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
	public void insert(UserRequestHistory item){
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
	public void delete(UserRequestHistory item){
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
	public UserRequestHistory read(long id){
		Session session = factory.openSession();
                try{
                    return (UserRequestHistory)session.createCriteria(UserRequestHistory.class).add(Restrictions.eq("userName", id)).uniqueResult();
	      }catch (HibernateException e) {
	      }finally {
	         session.close(); 
	      }
	      return null;
	}

    public UserRequestHistory[] readAll() {
        Session session = factory.openSession();
                try{
                    List<UserRequestHistory> items = (List<UserRequestHistory>)session.createCriteria(UserRequestHistory.class).list();
                    UserRequestHistory[] users = new UserRequestHistory[items.size()];int i = 0;
                    for(UserRequestHistory user : items){
                        users[i++] = user;
                    }
                    return users;
	      }catch (HibernateException e) {
	      }finally {
	         session.close(); 
	      }
	      return null;
    }
}
