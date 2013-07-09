package com.oasis.tmsv5.dao;

import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlMapClientHolder {

    private static SqlSessionFactory sqlMapper;

    private static final Log logger = LogFactory.getLog(SqlMapClientHolder.class);

    private static String environmentId = "test";

    private static SqlMapClientHolder holder;

    private SqlMapClientHolder() {
        /*
         * if (sqlMapper == null) { try { String ibatisResource =
         * "ibatis-config.xml"; Reader ibatisReader =
         * Resources.getResourceAsReader(ibatisResource); sqlMapper = new
         * SqlSessionFactoryBuilder().build(ibatisReader);
         * logger.info("SqlSessionFactory built successfully."); } catch
         * (Exception e) { e.printStackTrace(); throw new RuntimeException(e); }
         * }
         */
    }

    public synchronized static SqlMapClientHolder getInstance() {
        if (sqlMapper == null) {
            try {
                String ibatisResource = "ibatis-config.xml";
                Reader ibatisReader = Resources.getResourceAsReader(ibatisResource);
                sqlMapper = new SqlSessionFactoryBuilder().build(ibatisReader, environmentId);
                logger.info("SqlSessionFactory built successfully.");

                holder = new SqlMapClientHolder();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return holder;
    }

    public SqlSessionFactory getSqlMapper() {
        return sqlMapper;
    }

    public static void setEnvironmentId(String environmentId) {
        SqlMapClientHolder.environmentId = environmentId;
    }

}
