package io.pivotal.firebase.config;

import io.pivotal.firebase.AbstractLocalDataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("mysql-local")
public class MySqlLocalDataSourceConfig extends AbstractLocalDataSourceConfig {

    @Bean
    public DataSource dataSource() {
        System.out.println("mysql-local");
        return createDataSource("jdbc:mysql://localhost/firebase", "com.mysql.jdbc.Driver", "root", "root");
    }

}