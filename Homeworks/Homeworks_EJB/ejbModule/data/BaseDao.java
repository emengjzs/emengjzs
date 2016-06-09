package data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import message.Message;
import dataservice.BaseDaoService;
import dataservice.CommonDaoService;
import debug.Debug;

/**
 * Session Bean implementation class BaseDao
 */
@Stateless
@LocalBean
public class BaseDao implements BaseDaoService, CommonDaoService {
	
	
	@PersistenceContext
	protected EntityManager em;
	
	
	protected Class mainEntityClass;
	
    /**
     * Default constructor. 
     */
    public BaseDao() {
        // TODO Auto-generated constructor stub
    }
    
    /* (non-Javadoc)
	 * @see data.BaseDaoService#setEntityClass(java.lang.Class)
	 */
    @Override
    public void setEntityClass(Class Entityclass) {
    	this.mainEntityClass = Entityclass;
    }
    
    @Override
    public boolean setEntityClass(String Entityclass) {
    	try {
			this.mainEntityClass = Class.forName(Entityclass);			
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    
    @Override
	public Object getById(Class Entityclass, Object id) {
    	try {
    		setEntityClass(Entityclass);
    		return getById(id);
    	} catch(Exception e) {return null;}
    }
    
    /* (non-Javadoc)
	 * @see data.CommonDaoService#getById(java.lang.Object)
	 */
    public Object getById(Object id) {
    	Object result = em.find(mainEntityClass, id);
  	  	em.clear();
  	  	return result;
    }
    
    /* (non-Javadoc)
	 * @see data.CommonDaoService#getByIdWithUpdateSync(java.lang.Object)
	 */
    public Object getByIdWithUpdateSync(Object id) {
    	Object result = em.find(mainEntityClass, id);
  	  	return result;
    }
    
    public void flush() {
    	this.em.flush();
    }
    
    /* (non-Javadoc)
	 * @see data.CommonDaoService#add(java.lang.Object)
	 */
    public Message add(Object entity) {
    	try {
    	em.persist(entity);
    	em.flush();
    	Debug.print("add: ---------------");
    	Debug.outObjPropertyString(entity);
    	Debug.split();
    	return Message.AddMessage.ADD_SUCCESS;
    	} catch(Exception e) {
    		e.printStackTrace();
    		return Message.AddMessage.ADD_FAILED;
    	}
    }
    
    public Message update(Object entity) {
    	try {
        	em.merge(entity);
        	em.flush();
        	Debug.print("update: ---------------");
        	Debug.outObjPropertyString(entity);
        	Debug.split();
        	return Message.UpdateMessage.UPDATE_SUCCESS;
        	} catch(Exception e) {
        		e.printStackTrace();
        		return Message.UpdateMessage.UPDATE_FAILED;
        }
    }
    
    /* (non-Javadoc)
	 * @see data.CommonDaoService#delete(java.lang.Object)
	 */
    public boolean delete(Object id) {
    	return delete(this.mainEntityClass, id);
    }
    
    @Override
    public boolean delete(Class Entityclass, Object id) {
    	try {
    		Object data = em.find(Entityclass, id);
    		if(data != null) {
    			em.remove(data);
    			return true;
    		}
    		return false;
    	} catch(Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    
    /* (non-Javadoc)
	 * @see data.CommonDaoService#findAll()
	 */
    public List findAll() {
    	return this.findAll(this.mainEntityClass);
    }
    
    @Override
    public List findAll(Class entity) {
    	return em.createQuery("from " + entity.getSimpleName()).getResultList(); 
    }
    
    protected List getBy(String parameter, Object value) {
    	List list = null;
		try {
			Query query = em.createQuery("from " + mainEntityClass.getSimpleName()  + " s where s." + parameter + "=:" + parameter);
			query.setParameter(parameter,value);
			list = query.getResultList();
			em.clear();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList(0);
		}
		return list;
    }
}
