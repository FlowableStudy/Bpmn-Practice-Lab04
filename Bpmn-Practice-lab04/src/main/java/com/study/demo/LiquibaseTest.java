package com.study.demo;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class LiquibaseTest {


    public static  void main(String []args) throws  SQLException,DatabaseException,LiquibaseException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("dataBase.xml") ;

        AtomicReference<DruidDataSource> dataSource = new AtomicReference<DruidDataSource>((DruidDataSource) applicationContext.getBean("dataSource"));

        DruidPooledConnection connection = dataSource.get().getConnection();

        DatabaseConnection databaseConnection = new JdbcConnection(connection);

        final Database db = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseConnection);


        Liquibase liquibase = new Liquibase("changelog.xml",new ClassLoaderResourceAccessor(),db);

        liquibase.update("testliquibase");

    }



}
