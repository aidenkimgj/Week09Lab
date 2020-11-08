package database;

import models.Users;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class UserDB {

    private EntityManager em;
    private EntityTransaction trans;
         
    public int insert(Users user) throws NotesDBException {
        em = DBUtil.getEmFactory().createEntityManager();
        trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(user);
            trans.commit();
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            throw new NotesDBException("Error inserting user");
        
        } finally {
            em.close();
            
        }
    }

    public int update(Users user) throws NotesDBException {
        em = DBUtil.getEmFactory().createEntityManager();
        trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            throw new NotesDBException("Error updating user");
        
        } finally {
            em.close();
            
        }
    }

    public List<Users> getAll() throws NotesDBException {
        em = DBUtil.getEmFactory().createEntityManager();
        trans = em.getTransaction();
        try {
            List<Users> list = em.createNamedQuery("Users.findAll", Users.class).getResultList();
            
            return list;
            
        } finally {
            em.close();
        }
    }
        
    public Users getUser(String username) throws NotesDBException {
        em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Users user = em.find(Users.class, username);
            return user;
        } catch (Exception ex) {
            throw new NotesDBException("Error getting Users");
        } finally {
            em.close();
        }
    }

    public int delete(Users user) throws NotesDBException {
        em = DBUtil.getEmFactory().createEntityManager();
        trans = em.getTransaction();

        try {
           trans.begin();
           em.remove(em.merge(user));
           trans.commit();
           return 1;
        } catch (Exception ex) {
            trans.rollback();
            throw new NotesDBException("Error deleting User");
        } finally {
            em.close();
            
        }
    }
}
