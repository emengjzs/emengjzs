package dataservice;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import message.Message;
@Local
public interface BaseDaoService {

	public void setEntityClass(Class Entityclass);
	
	public boolean setEntityClass(String Entityclass);
	
	public Object getById(Class Entityclass, Object id);
	
	public boolean delete(Class Entityclass, Object id);
	
	public List findAll(Class entity);
	
	public Message add(Object entity);
}