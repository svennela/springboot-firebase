package io.pivotal.firebase;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
/**
 * Created by svennela on 8/4/17.
 */


public class AbstractLocalDataSourceConfig {

    protected DataSource createDataSource(String jdbcUrl, String driverClass, String userName, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setDriverClassName(driverClass);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }
}