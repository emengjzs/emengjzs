package cn.edu.nju.software.jzs.dao;

import cn.edu.nju.software.jzs.entity.Job;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by emengjzs on 2016/4/4.
 */
@Transactional
public class BaseDao {

    @Autowired
    protected SessionFactory session;


    public <T> T findById(Class<T> entityClass, Serializable id) {
        return (T) session.getCurrentSession().get(entityClass, id);
    }

    public void save(Object... entities) {
        for (Object entity : entities) {
            session.getCurrentSession().saveOrUpdate(entity);
        }
    }



    public <T> List<T> getAll(Class<T> entityClass, Order... orderBys) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Job.class);

        if (orderBys.length > 0) {
            for (Order order : orderBys) {
                criteria.addOrder(order);
            }
        }
        return criteria.getExecutableCriteria(this.session.getCurrentSession()).list();
    }
}
