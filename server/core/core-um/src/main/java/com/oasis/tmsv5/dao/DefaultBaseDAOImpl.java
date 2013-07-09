/**
 * 
 */
package com.oasis.tmsv5.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.oasis.tmsv5.common.base.Constants;
import com.oasis.tmsv5.common.util.page.BasePageSO;
import com.oasis.tmsv5.model.BaseModel;
import com.oasis.tmsv5.security.SecurityContextHolder;
import com.oasis.tmsv5.util.exception.OptLockException;
import com.oasis.tmsv5.util.exception.QueryException;
import com.oasis.tmsv5.util.helper.DomainHelper;
import com.oasis.tmsv5.util.helper.JPAHelper;
import com.oasis.tmsv5.util.helper.ModelColumn;
import com.oasis.tmsv5.util.helper.PropertyHelper;
import com.oasis.tmsv5.util.properties.GBKDispHelper;

/**
 * <p>
 * 
 * @author liyue
 * @date 2013-5-7 下午05:22:33
 * @version v1.0
 */
public abstract class DefaultBaseDAOImpl<T extends Serializable> implements BaseDAO<T> {

    /**
     * if sqlmapping include chinese character ,have to fix the charset
     */
    static {
        Resources.setCharset(Charset.forName("UTF-8"));
    }

    private static final Log logger = LogFactory.getLog(DefaultBaseDAOImpl.class);

    private static final String ST_FIND = ".find";

    private static final String ST_INSERT = ".insert";

    private static final String ST_UPDATE = ".update";

    private static final String ST_DELETE = ".delete";

    private static final String ST_SELECT = ".select";

    private static final String ST_SELECT_LIST = ".selectList";

    private static final String ST_SELECT_COUNT = ".selectCount";

    private static final String ST_SELECT_PAGELIST = ".selectPageList";

    private static final String ST_DELETE_IDS = ".deleteByIds";

    private static final String ST_SELECT_IDS = ".selectByIds";

    private static final String ST_SEARCHLIST_BY_PARA = ".getModelListByPara";

    private static final String ST_SELECT_BY_FK = ".selectListByFKId";
    
    protected static final String ST_FIND_VO = ".findVO";

    private static final String ID = "id";

    private static final String TABLE = "table";

    private static final String SEQUENCE = "sequence";

    protected Class<T> type = null;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    protected SqlSessionTemplate getSession() {
        return sqlSessionTemplate;
    }

    @SuppressWarnings("unchecked")
    public DefaultBaseDAOImpl() {
        type = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    public T find(Long id) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            String table = JPAHelper.getTableName(type);
            map.put(ID, id);
            map.put(TABLE, table);

            T ret = (T) getSession().selectOne(getStatementNamespace() + ST_FIND, map);
            if (ret == null) {
                logger.warn("Cann't find record by ID=" + String.valueOf(id));
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        }
    }

    public T update(T paraObject) {
        try {
            Map<String, Object> map = getUpdateMap(paraObject);
            int records = getSession().update(getStatementNamespace() + ST_UPDATE, map);
            if (records == 0 || records == -1) {
                throw new OptLockException(GBKDispHelper.getInstance().getValue("OPTLOCKEX_MESSAGE"));
            }

            T ret = find((Long) PropertyHelper.getValue(paraObject, JPAHelper.getPrimaryKey(type)));
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        }
    }

    public Long insert(T paraObject) {
        try {
            Map<String, Object> map = getInsertMap(paraObject);
            getSession().insert(getStatementNamespace() + ST_INSERT, map);
            return new Long(map.get("id").toString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        }
    }

    public List<Long> batchInsert(List<T> list) {
        Long startTime = System.currentTimeMillis();
        List<Long> idList = new ArrayList<Long>();
        try {
            for (T paraObject : list) {
                Map<String, Object> map = getInsertMap(paraObject);
                getSession().getSqlSessionFactory().openSession(ExecutorType.BATCH, false)
                        .insert(getStatementNamespace() + ST_INSERT, map);
                idList.add(new Long(map.get("id").toString()));
            }
            // getSqlSessionTemplate().flushStatements();
            return idList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        } finally {
            logger.debug("Record batch insert took " + (System.currentTimeMillis() - startTime));
        }
    }

    public int deleteByIds(List<Long> ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        String table = JPAHelper.getTableName(type);
        map.put(TABLE, table);
        map.put("ids", ids);
        return getSession().delete(this.getStatementNamespace() + ST_DELETE_IDS, map);
    }

    public int delete(Long id) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            String table = JPAHelper.getTableName(type);
            map.put(ID, id);
            map.put(TABLE, table);

            return getSession().delete(getStatementNamespace() + ST_DELETE, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        }
    }

    public T getModelByPara(Map<String, Object> map) {
        List<T> ret = getModelListByPara(map);
        if (ret.size() > 0) {
            return ret.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<T> getModelListByPara(Map<String, Object> map) {
        try {

            String table = JPAHelper.getTableName(type);
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put(TABLE, table);
            StringBuilder sb = new StringBuilder();
            for (String key : map.keySet()) {
                if (map.get(key) != null) {
                    if (sb.length() > 0) {
                        sb.append(" and ");
                    }
                    sb.append(" ");
                    sb.append(key);
                    sb.append("=#{");
                    sb.append(key);
                    sb.append("}");
                    paraMap.put(key, map.get(key));
                }
            }
            paraMap.put("Clause", sb.toString());
            List<T> ret = (List<T>) getSession().selectList(
                    getStatementNamespace() + ST_SEARCHLIST_BY_PARA, paraMap);
            return ret;

        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        }
    }

    protected int getPaginatedListCount(BasePageSO bpo) {
        Long startTime = System.currentTimeMillis();
        try {
            List<?> retList = getSession().selectList(getStatementNamespace() + ST_SELECT_COUNT,
                    bpo);
            return retList.size() > 0 ? (Integer) retList.get(0) : 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        } finally {
            logger.debug("Total record count loading took " + (System.currentTimeMillis() - startTime));
        }

    }

    @SuppressWarnings({ "unchecked" })
    protected <E> List<E> getPaginatedList(BasePageSO bpo) {
        Long startTime = System.currentTimeMillis();
        try {
            RowBounds rowBounds = new RowBounds((bpo.getPageNumber() - 1) * bpo.getObjectsPerPage(), bpo.getObjectsPerPage());
            List<E> retList = (List<E>) getSession().selectList(
                    getStatementNamespace() + ST_SELECT_PAGELIST, bpo, rowBounds);

            return retList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        } finally {
            logger.debug("One page record loading took " + (System.currentTimeMillis() - startTime));
        }
    }

    @SuppressWarnings({ "unchecked" })
    public <E> List<E> getQueryList(BasePageSO bpo) {
        Long startTime = System.currentTimeMillis();
        try {
            List<E> retList = (List<E>) getSession().selectList(getStatementNamespace() + ST_SELECT, bpo);
            return retList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        } finally {
            logger.debug("Record loading took " + (System.currentTimeMillis() - startTime));
        }
    }

    @SuppressWarnings({ "unchecked" })
    public <E> List<E> getQueryList(@SuppressWarnings("rawtypes") Map inputParameter) {
        Long startTime = System.currentTimeMillis();
        try {
            List<E> retList = (List<E>) getSession().selectList(getStatementNamespace() + ST_SELECT_LIST,
                    inputParameter);
            return retList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        } finally {
            logger.debug("Record loading took " + (System.currentTimeMillis() - startTime));
        }
    }

    @SuppressWarnings({ "unchecked" })
    public <E> List<E> getListByIds(List<Long> ids) {
        Long startTime = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        String table = JPAHelper.getTableName(type);
        map.put(TABLE, table);
        map.put("ids", ids);
        try {
            List<E> retList = (List<E>) getSession().selectList(getStatementNamespace() + ST_SELECT_IDS,
                    map);
            return retList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        } finally {
            logger.debug("Record loading took " + (System.currentTimeMillis() - startTime));
        }
    }

    public List<T> getListByFKId(Long fkId) {
        @SuppressWarnings("unchecked")
        List<T> list = (List<T>) getSession().selectList(
                getStatementNamespace() + ST_SELECT_BY_FK, fkId);
        return list;
    }

    private Map<String, Object> getInsertMap(T paraObject) {
        Map<String, Object> map = new HashMap<String, Object>();
        /**
         * 填入表名，sequence
         */
        String seq = JPAHelper.getSequenceName(type);
        String table = JPAHelper.getTableName(type);
        map.put(SEQUENCE, seq);
        map.put(TABLE, table);
        /**
         * 填入时间戳，创建人
         */
        if (BaseModel.class.isAssignableFrom(type)) {
            if (SecurityContextHolder.getContext().getAccount() != null) {
                PropertyHelper.setValue(paraObject, "domainId", SecurityContextHolder.getContext().getAccount().getDomainId());
                PropertyHelper.setValue(paraObject, "creatorId", SecurityContextHolder.getContext().getAccount().getId());
            } else {
                PropertyHelper.setValue(paraObject, "domainId", 60000L);
                PropertyHelper.setValue(paraObject, "creatorId", 100L);
            }
            PropertyHelper.setValue(paraObject, "createdTime", Calendar.getInstance().getTime());
            PropertyHelper.setValue(paraObject, "lockVersion", 0);
            // PropertyHelper.setValue(paraObject, "status",
            // CommonStatus.ACTIVE);
        }

        /**
         * 组装insertField,insertValues
         */
        List<ModelColumn> colList = JPAHelper.getColumnsByObj(paraObject);
        StringBuilder fieldSb = new StringBuilder();
        StringBuilder valuesSb = new StringBuilder();
        for (ModelColumn col : colList) {
            if (col.getValue() != null || col.getField().equalsIgnoreCase("id")) {
                map.put(col.getField(), col.getValue());
                if (fieldSb.length() > 0) {
                    fieldSb.append(",");
                }
                fieldSb.append(col.getName());

                if (valuesSb.length() > 0) {
                    valuesSb.append(",");
                }
                valuesSb.append("#{" + col.getField() + "}");
            }
        }
        map.put("insertField", fieldSb);
        map.put("insertValues", valuesSb);
        return map;
    }

    private Map<String, Object> getUpdateMap(T paraObject) {
        Map<String, Object> map = new HashMap<String, Object>();
        /**
         * 填入表名
         */
        String table = JPAHelper.getTableName(type);
        map.put(TABLE, table);

        /**
         * 填入时间戳，更新人
         */
        if (BaseModel.class.isAssignableFrom(type)) {
            ((BaseModel) paraObject).setUpdatedTime(Calendar.getInstance().getTime());
            // 用户登录时account为null
            if (SecurityContextHolder.getContext().getAccount() != null) {
                ((BaseModel) paraObject).setUpdatorId(SecurityContextHolder.getContext().getAccount().getId());
            }
        }

        /**
         * 组装updateSql
         */
        List<ModelColumn> colList = JPAHelper.getColumnsByObj(paraObject);
        StringBuilder sb = new StringBuilder();
        String lockVersion = JPAHelper.getVersionName(type);

        for (ModelColumn col : colList) {
            if (col.getValue() != null) {
                map.put(col.getField(), col.getValue());
                if (ID.equalsIgnoreCase(col.getField())) {
                    continue;
                }
                if (lockVersion != null && lockVersion.equalsIgnoreCase(col.getField())) {
                    continue;
                }
                if (sb.length() > 0) {
                    sb.append(",");
                }
                if (list.contains(col.getValue())) {
                    sb.append(" " + col.getName() + "=null");
                } else {
                    sb.append(" " + col.getName() + "=#{" + col.getField() + "}");
                }
                // sb.append(" " + col.getName() + "=#{" + col.getField() +
                // "}");

            }
        }
        map.put("updateSql", sb.toString());

        return map;
    }

    protected String getStatementNamespace() {
        Class<?> clazz = this.getClass().getInterfaces()[0];
        return clazz.getName();
    }

    private static List<Object> list = new ArrayList<Object>();
    static {
        list.add(Constants.DATABASE_KEY_NULL_VALUE);
        list.add(Constants.DATABASE_DATE_NULL_VALUE);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void buildMap (Map map) {
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAccount() != null) {
            map.put("domainId", SecurityContextHolder.getContext().getAccount().getDomainId());
        } else {
            map.put("domainId", 60000);
        }
    }
    
    public Long insertNew(T paraObject) {
        try {
            buildInsertObj(paraObject);
            getSession().insert(getStatementNamespace() + ST_INSERT, paraObject);
            String id = BeanUtils.getProperty(paraObject, ID);
            return Long.valueOf(id);

        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        }
    }
    
    public List<Long> batchInsertNew(List<T> paraObjects) {
        Long startTime = System.currentTimeMillis();
        List<Long> idList = new ArrayList<Long>();
        try {
            for (T paraObject : paraObjects) {
                buildInsertObj(paraObject);
                getSession().getSqlSessionFactory().openSession(ExecutorType.BATCH, false)
                        .insert(getStatementNamespace() + ST_INSERT, paraObject);
                idList.add(Long.valueOf(BeanUtils.getProperty(paraObject, ID)));
            }
            return idList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        } finally {
            logger.debug("Record batch insert took " + (System.currentTimeMillis() - startTime));
        }
    }

    private void buildInsertObj(T paraObject) {
        /**
         * 填入时间戳，创建人
         */
        if (BaseModel.class.isAssignableFrom(type)) {
            if (SecurityContextHolder.getContext().getAccount() != null) {
                ((BaseModel) paraObject).setDomainId(SecurityContextHolder.getContext().getAccount().getDomainId());
                ((BaseModel) paraObject).setCreatorId(SecurityContextHolder.getContext().getAccount().getId());
            } else {
                ((BaseModel) paraObject).setDomainId(DomainHelper.DEFAULT_DOMAIN_ID);
            }
            ((BaseModel) paraObject).setCreatedTime(Calendar.getInstance().getTime());
            ((BaseModel) paraObject).setLockVersion(0);
        }
    }

    public T updateNew(T paraObject) {
        try {
            buildUpdateObj(paraObject);
            int records = getSession().update(getStatementNamespace() + ST_UPDATE, paraObject);
            if (records == 0 || records == -1) {
                T dbOjb = this.find(((BaseModel) paraObject).getId());
                logger.error(paraObject.getClass().getName() + " Bad lockversion is " + ((BaseModel) paraObject).getLockVersion()
                        + " DB lockversion is " + ((BaseModel) dbOjb).getLockVersion());
                throw new OptLockException(GBKDispHelper.getInstance().getValue("OPTLOCKEX_MESSAGE"));
            }

            T ret = find((Long) PropertyHelper.getValue(paraObject, JPAHelper.getPrimaryKey(type)));
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        }
    }

    private void buildUpdateObj(T paraObject) {
        /**
         * 填入时间戳，更新人
         */
        if (BaseModel.class.isAssignableFrom(type)) {
            ((BaseModel) paraObject).setUpdatedTime(Calendar.getInstance().getTime());
            if (SecurityContextHolder.getContext().getAccount() != null) {
                ((BaseModel) paraObject).setUpdatorId(SecurityContextHolder.getContext().getAccount().getId());
            }
        }
    }
    
}
