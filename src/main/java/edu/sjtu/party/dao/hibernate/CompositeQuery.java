package edu.sjtu.party.dao.hibernate;

import edu.sjtu.dao.QueryConditionStore;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @description:
 * @author: lay
 * @create: 2021/09/01 13:14
 **/
public class CompositeQuery<T> {

    private String order;
    private String select;
    private String group;
    private Vector<QueryConditionStore> conditionVector;
    private Query m_query;
    private Session session;
    private boolean nativeSql;

    public CompositeQuery(String select, String order, Session session) {
        this(select, order, (String)null, false, session);
    }

    public CompositeQuery(String select, String order, String group, Session session) {
        this(select, order, group, false, session);
    }

    public CompositeQuery(String select, String order, boolean nativeSql, Session session) {
        this(select, order, (String)null, nativeSql, session);
    }

    public CompositeQuery(String select, String order, String group, boolean nativeSql, Session session) {
        this.conditionVector = new Vector();
        this.select = select;
        this.order = order;
        this.group = group;
        this.session = session;
        this.nativeSql = nativeSql;
    }

    private void appendCondition(QueryConditionStore qc) {
        this.conditionVector.add(qc);
        this.m_query = null;
    }

    public String getQueryString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.select);
        boolean existWhere = false;
        if (this.select.indexOf(" where ") >= 0) {
            existWhere = true;
        }

        if (this.conditionVector.size() > 0 && !existWhere) {
            stringBuffer.append(" where ");
        }

        for(int i = 0; i < this.conditionVector.size(); ++i) {
            QueryConditionStore qc = (QueryConditionStore)this.conditionVector.elementAt(i);
            if (i != 0 || existWhere) {
                stringBuffer.append(" and ");
            }

            String tmp;
            if (qc.isReplaceParameter()) {
                tmp = this.replaceParameter(qc);
            } else {
                tmp = qc.toString().trim();
            }

            if (tmp.charAt(0) != '(') {
                stringBuffer.append('(');
            }

            stringBuffer.append(tmp);
            if (tmp.charAt(0) != '(') {
                stringBuffer.append(')');
            }
        }

        if (this.group != null && this.group.length() > 0) {
            stringBuffer.append(" group by ").append(this.group);
        }

        if (this.order.length() != 0) {
            stringBuffer.append(" order by ");
            stringBuffer.append(this.order);
        }

        return stringBuffer.toString();
    }

    private String replaceParameter(QueryConditionStore qc) {
        StringBuffer sb = new StringBuffer();
        String sql = qc.toString().trim();
        Iterator<Object> itorPara = qc.iterator();

        for(int i = 0; i < sql.length(); ++i) {
            char c = sql.charAt(i);
            if (c == '?') {
                if (!itorPara.hasNext()) {
                    throw new RuntimeException("parameter is not enough");
                }

                Object para = itorPara.next();
                if (para instanceof String) {
                    sb.append('\'');
                    sb.append(this.processQuot((String)para));
                    sb.append('\'');
                } else {
                    sb.append(para.toString());
                }
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private String processQuot(String s) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '\'') {
                sb.append("''");
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private Query getQuery() throws HibernateException {
        if (this.m_query == null) {
            String tmp = this.getQueryString();
            if (this.nativeSql) {
                this.m_query = this.session.createSQLQuery(tmp);
            } else {
                this.m_query = this.session.createQuery(tmp);
            }
        }

        return this.m_query;
    }

    private void setParameter(Query query) throws HibernateException {
        int paraIndex = 0;
        Iterator var3 = this.conditionVector.iterator();

        while(var3.hasNext()) {
            QueryConditionStore qc = (QueryConditionStore)var3.next();
            Iterator qcItor = qc.iterator();

            while(qcItor.hasNext()) {
                Object para = qcItor.next();
                if (!qc.isReplaceParameter()) {
                    query.setParameter(paraIndex++, para);
                }
            }
        }

    }

    public QueryConditionStore createQueryCondition(String condition) {
        QueryConditionStore qc = new QueryConditionStore(condition);
        this.appendCondition(qc);
        return qc;
    }

    public QueryConditionStore createQueryCondition(String condition, boolean replaceParameter) {
        QueryConditionStore qc = new QueryConditionStore(condition, replaceParameter);
        this.appendCondition(qc);
        return qc;
    }

    public List<T> list() throws HibernateException {
        this.setParameter(this.getQuery());
        return this.getQuery().list();
    }

    public Iterator<T> iterator() throws HibernateException {
        this.setParameter(this.getQuery());
        return this.getQuery().iterate();
    }

    public List<T> list(int first, int max) throws HibernateException {
        this.setParameter(this.getQuery());
        this.getQuery().setFirstResult(first);
        this.getQuery().setMaxResults(max);
        return this.getQuery().list();
    }

    public Iterator<T> iterator(int first, int max) throws HibernateException {
        this.setParameter(this.getQuery());
        this.getQuery().setFirstResult(first);
        this.getQuery().setMaxResults(max);
        return this.getQuery().iterate();
    }

    public void addJoin(String alias, String path) {
        ((SQLQuery)this.getQuery()).addJoin(alias, path);
    }

    public void addEntity(String alias, Class<? extends Object> entityClass) {
        ((SQLQuery)this.getQuery()).addEntity(alias, entityClass);
    }

    public void setResultTransformer(ResultTransformer transformer) {
        ((SQLQuery)this.getQuery()).setResultTransformer(transformer);
    }

    public void addScalar(String scalar) {
        ((SQLQuery)this.getQuery()).addScalar(scalar);
    }

    public void addScalar(String scalar, Type type) {
        ((SQLQuery)this.getQuery()).addScalar(scalar, type);
    }
}
