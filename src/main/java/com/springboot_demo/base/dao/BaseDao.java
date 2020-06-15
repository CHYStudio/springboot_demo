package com.springboot_demo.base.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName BaseDao
 * @Description: TODO  jpa BaseDao
 * @Author Administrator
 * @Date 2020/6/11
 * @Version V1.0
 **/
public class BaseDao {

    @PersistenceContext //注入的是实体管理器,执行持久化操作
    public EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * 保存单个对象
     * @param t
     * @param <T>
     */
    public <T> void save(T t){
        entityManager.persist(t);
    }


    /**
     * 删除
     * @param entity
     * @param <T>
     * @return
     */
    public <T> boolean delete(T entity) {
        boolean flag=false;
        entityManager.remove(entityManager.merge(entity));
        flag=true;
        return flag;
    }

    /**
     * 修改
     * @param entity
     * @return
     */
    public <T> boolean update(T entity) {
        boolean flag = false;
        entityManager.merge(entity);
        flag = true;
        return flag;
    }


    public <T> T queryById(Integer id,Class c){
        return (T) entityManager.find(c,id);
    }

    public <T> T queryById(String id,Class c){
        return (T) entityManager.find(c, id);
    }

    public <T> List<T> listAll(Class t){
        return entityManager.createQuery("from "+t.getSimpleName()).getResultList();
    }

    public <T> T getSingleResult(Query query, Class t){
        if(query == null){
            return null;
        }else{
            List<T> list = query.getResultList();
            if(list.size() == 0){
                return null;
            }else{
                Object object = list.get(0);
                if(t.getSimpleName().equals("Integer")){
                    Integer i = ((BigDecimal)object).intValue();
                    return (T)i;
                }else{
                    return (T)list.get(0);
                }
            }
        }
    }

    /**
     * 刷新数据到数据库
     */
    public void flush(){
        entityManager.flush();
    }

}
