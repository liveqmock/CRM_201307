package com.oasis.tmsv5.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class SqlSessionHolder {
    private static final Log logger = LogFactory.getLog(SqlSessionHolder.class);

    private static ThreadLocal<SqlSession> sessionHolder = new ThreadLocal<SqlSession>();

    private static SqlSessionFactory sqlMapper = SqlMapClientHolder.getInstance().getSqlMapper();

    public static void clearSession() {
        if (sessionHolder.get() == null) {
            return;
        }
        sessionHolder.get().close();
        sessionHolder.set(null);
        logger.debug("session close");
    }

    public static SqlSession getSession() {
        if (sessionHolder.get() == null) {
            logger.debug("initial session");
            sessionHolder.set(sqlMapper.openSession(ExecutorType.SIMPLE, false));
        }
        return sessionHolder.get();
    }

    public static SqlSession getSession(ExecutorType executorType) {
        if (sessionHolder.get() == null) {
            sessionHolder.set(sqlMapper.openSession(executorType, false));  
            return sessionHolder.get();
        }

        logger.warn("executorType of an existed sessoin not be changed.");
        return sessionHolder.get();
    }

    public static void setSession(SqlSession sqlSession) {
        sessionHolder.set(sqlSession);
    }

    public static void commitSession() {
        if (sessionHolder.get() == null) {
            return;
        }
        sessionHolder.get().commit(true);
        sessionHolder.get().close();
        sessionHolder.set(null);
        logger.debug("session close");
    }
    public static void rollbackSession() {
        if (sessionHolder.get() == null) {
            return;
        }
        sessionHolder.get().rollback(true);
        sessionHolder.get().close();
        sessionHolder.set(null);
        logger.debug("session close");
    }
}
