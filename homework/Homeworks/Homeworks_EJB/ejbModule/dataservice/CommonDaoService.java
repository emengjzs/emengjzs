package dataservice;

import java.util.List;

import message.Message;

public interface CommonDaoService {
	   public Object getById(Object id);
	   public Object getByIdWithUpdateSync(Object id) ;
	   public Message add(Object entity) ;
	   public boolean delete(Object id);
	   public List findAll();
	   public Message update(Object entity) ;
}
