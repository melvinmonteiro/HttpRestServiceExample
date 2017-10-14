package com.example.restservice.config;

import com.querydsl.sql.H2Templates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import com.querydsl.sql.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.inject.Provider;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Created by melvin on 10/12/17.
 */
@Configuration
public class JdbcConfiguration {

    /**
     * data source knows how to connect to database. in this case h2 embedded
     */
    @Autowired
    DataSource dataSource;

    /**
     * a data source transaction manager
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }


    /**
     * a querysql dsl configuration.
     * add your querydsl conifgurations here.
     * @return
     */
    @Bean
    public com.querydsl.sql.Configuration querydslConfiguration() {
        SQLTemplates templates = H2Templates.builder().build();
        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
        configuration.setExceptionTranslator(new SpringExceptionTranslator());
        configuration.register(new DateTimeType());
        configuration.register(new LocalDateType());
        return configuration;
    }

    /**
     * sql query factory used by other Dao to query the database.
     * @return
     */
    @Bean
    public SQLQueryFactory queryFactory() {
        Provider<Connection> provider = new SpringConnectionProvider(dataSource);
        return new SQLQueryFactory(querydslConfiguration(), provider);
    }

}
