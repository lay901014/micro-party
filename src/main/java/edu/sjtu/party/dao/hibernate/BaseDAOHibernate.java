package edu.sjtu.party.dao.hibernate;

import edu.sjtu.dao.QueryCondition;
import edu.sjtu.dao.QueryConditionStore;
import edu.sjtu.dao.QueryOrder;
import edu.sjtu.dao.QueryOrderStore;
import edu.sjtu.dao.convert.QueryParameterConvert;
import edu.sjtu.dao.convert.QuerySQLConvert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.IntegerType;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * DAO的Hibernate实现
 * Kanged by marstone since 2015/10/16 to support Hibernate4
 * @author gwang, marstone
 * @version 1.0 (2005-11-11)
 */
public class BaseDAOHibernate<T> extends HibernateDaoSupport {

    private static Log log = LogFactory.getLog(BaseDAOHibernate.class);

    protected int getListCount(final Map<? extends QueryCondition, ?> conditions, final String sql, final Object[][] queryConditions) {
        return this.getListCount(conditions, sql, null, queryConditions);
    }

    protected int getListCount(final Map<? extends QueryCondition, ?> conditions, final String sql, final String group, final Object[][] queryConditions) {
        Integer result = getHibernateTemplate().execute( new HibernateCallback<Integer>() {

            public Integer doInHibernate(Session session) throws HibernateException {
                CompositeQuery<Number> query = new CompositeQuery<>(sql, "", group, session);
                if (conditions != null) appendConditions(query, conditions, queryConditions);
                List<Number> result = query.list();
                if (!result.isEmpty()) {
                    Number count = result.get(0);
                    if (count != null) return count.intValue();
                }
                return null;
            }

        });
        if (result == null) return 0;
        return result;
    }

    protected int deleteList(final Map<? extends QueryCondition, ?> conditions, final String sql, final Object[][] queryConditions) {
        return this.deleteList(conditions, sql, null, queryConditions);
    }

    protected int deleteList(final Map<? extends QueryCondition, ?> conditions, final String sql, final String group, final Object[][] queryConditions) {
        return 0;
        //todo:implement later
        /*
        Integer result = getHibernateTemplate().execute( new HibernateCallback<Integer>() {

            public Integer doInHibernate(Session session) throws HibernateException {
                CompositeQuery<Number> query = new CompositeQuery<>(sql, "", group, session);
                if (conditions != null) appendConditions(query, conditions, queryConditions);
                List<Number> result = query.list();
                if (!result.isEmpty()) {
                    Number count = result.get(0);
                    if (count != null) return count.intValue();
                }
                return null;
            }

        });
        if (result == null) return 0;
        return result;
         */
    }


    protected List<T> getList(final Map<? extends QueryCondition, ?> conditions, final int pageNo, final int pageSize, final Object order, final boolean descend, final String sql, final Object[][] queryConditions, final Object[][] queryOrders) {
        return this.getList(conditions, pageNo, pageSize, order, descend, sql, null, queryConditions, queryOrders);
    }

    protected List<T> getList(final Map<? extends QueryCondition, ?> conditions, final int pageNo, final int pageSize, final List<QueryOrderStore> orderList, final String sql, final String group, final Object[][] queryConditions, final Object[][] queryOrders) {
        return getHibernateTemplate().execute( new HibernateCallback<List<T>>() {

            public List<T> doInHibernate(Session session) throws HibernateException {
                CompositeQuery<T> query = new CompositeQuery<>(sql, getOrder(orderList, queryOrders), group, session);
                if (conditions != null) appendConditions(query, conditions, queryConditions);
                if (pageSize == -1) return query.list();
                return query.list(pageNo * pageSize, pageSize);
            }

        });
    }

    protected List<T> getList(final Map<? extends QueryCondition, ?> conditions, final int pageNo, final int pageSize, final Object order, final boolean descend, final String sql, final String group, final Object[][] queryConditions, final Object[][] queryOrders) {
        if(order==null) {
            return getList(conditions, pageNo, pageSize, null, sql, group, queryConditions, queryOrders);
        }
        List<QueryOrderStore> orders = new ArrayList<>();
        orders.add(new QueryOrderStore(order, descend));
        return getList(conditions, pageNo, pageSize, orders, sql, group, queryConditions, queryOrders);
    }

    protected int getListCount(final Map<? extends QueryCondition, ?> conditions, final String sql, final Object[][] queryConditions, final String scalar) {
        Integer result = getHibernateTemplate().execute( new HibernateCallback<Integer>() {

            public Integer doInHibernate(Session session) throws HibernateException {
                CompositeQuery<Integer> query = new CompositeQuery<>(sql, "", true, session);
                if (conditions != null) appendConditions(query, conditions, queryConditions);
                if (scalar != null) {
                    query.addScalar(scalar, IntegerType.INSTANCE);
                }
                List<Integer> result = query.list();
                if (!result.isEmpty()) {
                    return result.get(0);
                }
                return null;
            }

        });
        if (result == null) return 0;
        return result;
    }

    @SuppressWarnings("unchecked")
    protected List<?> getList(final Map<? extends QueryCondition, ?> conditions, final int pageNo, final int pageSize, final Object order, final boolean descend, final String sql, final Object[][] queryConditions, final Object[][] queryOrders, final Object[] entities, final String[] joins, final ResultTransformer resultTransformer) {
        List<?> list = getHibernateTemplate().execute( new HibernateCallback<List<Object>>() {

            public List<Object> doInHibernate(Session session) throws HibernateException {
                List<QueryOrderStore> orders=null;
                if(order != null) {
                    orders = new ArrayList<>();
                    orders.add(new QueryOrderStore(order ,descend));
                }
                CompositeQuery<Object> query = new CompositeQuery<>(sql, getOrder(orders, queryOrders), true, session);
                if (conditions != null) appendConditions(query, conditions, queryConditions);
                if (entities != null) {
                    for (int i = 0; i < entities.length; i += 2) {
                        query.addEntity((String)entities[i], (Class<Object>)entities[i + 1]);
                    }
                }
                if (joins != null) {
                    for (int i = 0; i < joins.length; i += 2) {
                        query.addJoin(joins[i], joins[i + 1]);
                    }
                }
                if(resultTransformer != null) {
                    query.setResultTransformer(resultTransformer);
                }
                if (pageSize == -1) return query.list();
                return query.list(pageNo * pageSize, pageSize);
            }

        });

        if (entities == null || entities.length != 1) {
            return list;
        }
        List<Object> result = new ArrayList<>();
        result.addAll(list);
        return result;
    }



    protected static String getOrder(List<QueryOrderStore> orders,Object[][] queryOrders) {
        if (orders != null) {
            StringBuilder sb = new StringBuilder(256);
            Map<QueryOrder, Object[]> orderMap = new HashMap<>();
            for (Object[] queryOrder : queryOrders) {
                orderMap.put((QueryOrder) queryOrder[0], queryOrder);
            }
            for (QueryOrderStore qo : orders) {
                Object[] orderArray = orderMap.get(qo.getKey());
                if(orderArray==null) throw new RuntimeException("order list error");

                if(orderArray.length==3) {
                    if(qo.isDescend()) {
                        sb.append( orderArray[2].toString());
                    } else {
                        sb.append( orderArray[1].toString());
                    }
                } else if(orderArray.length==2) {
                    if (qo.isDescend()) {
                        sb.append(orderArray[1].toString()).append(" desc");
                    } else {
                        sb.append( orderArray[1].toString());
                    }
                } else {
                    throw new RuntimeException("order array error");
                }
            }
            return sb.toString();
        }
        return "";
    }


    protected static void appendConditions(CompositeQuery<?> query, Map<? extends QueryCondition, ?> condition, Object[][] conditions) {
        if(condition==null) return;
        Map<QueryCondition, Object> theConditions=new HashMap<>();
        theConditions.putAll(condition);
        for (Object[] c : conditions) {
            if (theConditions.containsKey(c[0])) {
                Object value = theConditions.get(c[0]);
                if(c.length==3)
                    makeSimpleCondition(query, c[1].toString(), value, c[2]);
                else if(c.length==2)
                    makeSimpleCondition(query, c[1].toString(), value, null);
                else throw new RuntimeException("condition array error");
                theConditions.remove(c[0]);
            }
        }
        if(!theConditions.isEmpty()) {
            throw new RuntimeException("condition map error");
        }
    }

    protected static void makeSimpleCondition(final CompositeQuery<?> query, final String sql, final Object parameter) {
        makeSimpleCondition(query, sql, parameter, null);
    }

    protected static void makeSimpleCondition(final CompositeQuery<?> query, final String sql, final Object parameter, final Object converter) {
        QueryConditionStore qc = null;
        String finalSql = sql;
        Object finalParameter = parameter;
        if (converter != null) {
            if (converter.getClass().isArray()) {
                int length = Array.getLength(converter);
                for (int i = 0; i < length; i++) {
                    Object currentConverter = Array.get(converter, i);
                    if (currentConverter != null) {
                        if (currentConverter instanceof QueryParameterConvert) {
                            finalParameter = ((QueryParameterConvert) currentConverter).convert(finalParameter);
                            continue;
                        }
                        if (currentConverter instanceof QuerySQLConvert) {
                            finalSql = ((QuerySQLConvert) currentConverter).convert(finalSql, finalParameter);
                            continue;
                        }
                    }
                }
            } else {
                if (converter instanceof QueryParameterConvert) {
                    finalParameter = ((QueryParameterConvert) converter).convert(finalParameter);
                } else if (converter instanceof QuerySQLConvert) {
                    finalSql = ((QuerySQLConvert) converter).convert(finalSql, finalParameter);
                }
            }
        }

        if (finalSql != null) {
            qc=query.createQueryCondition(finalSql);
        }

        if (finalParameter != null) {
            if (finalParameter instanceof Object[]) {
                Object[] parameters = (Object[]) finalParameter;
                for (Object para : parameters) qc.appendParameter(para);
            } else {
                qc.appendParameter(finalParameter);
            }
        }
    }

    protected static InputStream preRead(InputStream is) throws IOException {
        if (is instanceof ByteArrayInputStream) {
            return is;
        }
        byte[] buffer = new byte[4096];
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int len;
        while ((len = is.read(buffer)) > 0) {
            os.write(buffer, 0, len);
        }
        is.close();
        return new ByteArrayInputStream(os.toByteArray());
    }

    protected static void copyStream(InputStream in, OutputStream out) throws IOException {
        copyStream(in, out, 4096);
    }

    protected static void copyStream(InputStream in, OutputStream out, int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
    }

    protected static void copyStream(Reader in, Writer out) throws IOException {
        copyStream(in, out, 4096);
    }

    protected static void copyStream(Reader in, Writer out, int bufferSize) throws IOException {
        char[] buffer = new char[bufferSize];
        int len;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
    }

    protected T getObject(Class<T> clazz, Serializable id) {
        return this.getHibernateTemplate().get(clazz, id);
    }

    protected T saveObject(T obj) {
        return this.saveObject(obj, false);
    }

    protected T saveObject(T obj, boolean flush) {
        if (obj == null) {
            log.warn("try to save null object");
            return null;
        }
        HibernateTemplate template = this.getHibernateTemplate();
        template.save(obj);
        if (flush) {
            template.flush();
        }
        return obj;
    }

    protected T updateObject(T obj) {
        return this.updateObject(obj, false);
    }

    protected T updateObject(T obj, boolean flush) {
        if (obj == null) {
            log.warn("try to update null object");
            return null;
        }
        HibernateTemplate template = this.getHibernateTemplate();
        if (template.contains(obj)) {
            template.update(obj);
        } else {
            obj = template.merge(obj);
        }
        if (flush) {
            template.flush();
        }
        return obj;
    }

    protected void deleteObject(T obj) {
        this.deleteObject(obj, true);
    }

    protected void deleteObject(T obj, boolean flush) {
        if (obj == null) {
            log.warn("try to delete null object");
            return;
        }
        HibernateTemplate template = this.getHibernateTemplate();
        template.delete(obj);
        if (flush) {
            template.flush();
        }
    }

    protected T lockObject(T obj) {
        if (obj == null) {
            log.warn("try to lock null object");
            return null;
        }
        HibernateTemplate template = this.getHibernateTemplate();
        if (!template.contains(obj)) {
            obj = template.merge(obj);
        }
        template.lock(obj, LockMode.PESSIMISTIC_WRITE);
        return obj;
    }

    protected void  batchSaveOrUpdate(List<T> list) {
        HibernateTemplate template = this.getHibernateTemplate();
        for(int i=0; i<list.size(); i++) {
            template.merge(list.get(i));
            if(i%20 == 0) {
                template.flush();
                template.clear();
            }
        }
    }

    public void disconnect(T obj) {
        if (obj == null) {
            return;
        }
        HibernateTemplate template = this.getHibernateTemplate();
        try {
            template.evict(obj);
        } catch (Exception ignored) {
        }
    }

    public void disconnect(Collection<T> objs) {
        if (objs == null) {
            return;
        }
        HibernateTemplate template = this.getHibernateTemplate();
        for (T obj : objs) {
            try {
                template.evict(obj);
            } catch (Exception ignored) {
            }
        }
    }


}
