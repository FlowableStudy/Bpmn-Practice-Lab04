

------

环境：

[jkd8+]()

[mysql5.6+]()



## 一、引入liquibase依赖

```xml
<!-- https://mvnrepository.com/artifact/org.liquibase/liquibase-core -->
<dependency>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-core</artifactId>
    <version>3.6.2</version>
</dependency>
```



## 二、数据源配置(dataBase.xml)



```xml-dtd
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans.xsd">
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName">
            <value>com.mysql.jdbc.Driver</value>
        </property>
        <property name="url">
            <value>jdbc:mysql://127.0.0.1:3306/flowable?useUnicode=true&amp;characterEncoding=UTF-8
        </value>
        </property>
        <property name="username">
            <value>root</value>
        </property>
        <property name="password" value="root" />
</bean>
</beans>
```



## 三、changelog配置

```xml-dtd
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog 
                        http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.0.xsd
                        http://www.liquibase.org/xml/ns/dbchangelog-ext 
                        http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">

    <changeSet id="1" author="flowable">

        <createTable tableName="ACT_APP_DEPLOYMENT">
            <column name="ID_" type="varchar(255)">
                <constraints primaryKey="true" nullable="false" />
            </column>
            <column name="NAME_" type="varchar(255)" />
            <column name="CATEGORY_" type="varchar(255)">
                <constraints nullable="true" />
            </column>
            <column name="KEY_" type="varchar(255)">
                <constraints nullable="true" />
            </column>
            <column name="DEPLOY_TIME_" type="datetime">
                <constraints nullable="true" />
            </column>
            <column name="TENANT_ID_" type="varchar(255)" defaultValue="" />
        </createTable>

        <createTable tableName="ACT_APP_DEPLOYMENT_RESOURCE">
            <column name="ID_" type="varchar(255)">
                <constraints primaryKey="true" nullable="false" primaryKeyName="PK_APP_DEPLOYMENT_RESOURCE" />
            </column>
            <column name="NAME_" type="varchar(255)" />
            <column name="DEPLOYMENT_ID_" type="varchar(255)" />
            <column name="RESOURCE_BYTES_" type="longblob">
                <constraints nullable="true" />
            </column>
        </createTable>
        
        <addForeignKeyConstraint constraintName="ACT_FK_APP_RSRC_DPL" 
            referencedTableName="ACT_APP_DEPLOYMENT"
            referencedColumnNames="ID_" 
            baseTableName="ACT_APP_DEPLOYMENT_RESOURCE" 
            baseColumnNames="DEPLOYMENT_ID_" />
            
        <createIndex tableName="ACT_APP_DEPLOYMENT_RESOURCE" indexName="ACT_IDX_APP_RSRC_DPL">
            <column name="DEPLOYMENT_ID_" />
        </createIndex>            

        <createTable tableName="ACT_APP_APPDEF">
            <column name="ID_" type="varchar(255)">
                <constraints primaryKey="true" nullable="false" />
            </column>
            <column name="REV_" type="integer">
                <constraints nullable="false" />
            </column>
            <column name="NAME_" type="varchar(255)" />
            <column name="KEY_" type="varchar(255)">
                <constraints nullable="false" />
            </column>
            <column name="VERSION_" type="integer">
                <constraints nullable="false" />
            </column>
            <column name="CATEGORY_" type="varchar(255)" />
            <column name="DEPLOYMENT_ID_" type="varchar(255)" />
            <column name="RESOURCE_NAME_" type="varchar(4000)" />
            <column name="DESCRIPTION_" type="varchar(4000)" />
            <column name="TENANT_ID_" type="varchar(255)" defaultValue="" />
        </createTable>
        
        <addForeignKeyConstraint constraintName="ACT_FK_APP_DEF_DPLY" 
            referencedTableName="ACT_APP_DEPLOYMENT"
            referencedColumnNames="ID_" 
            baseTableName="ACT_APP_APPDEF" 
            baseColumnNames="DEPLOYMENT_ID_" />
            
        <createIndex tableName="ACT_APP_APPDEF" indexName="ACT_IDX_APP_DEF_DPLY">
            <column name="DEPLOYMENT_ID_" />
        </createIndex>

    </changeSet>

    <changeSet id="2" author="flowable" dbms="mysql">

        <modifyDataType tableName="ACT_APP_DEPLOYMENT" columnName="DEPLOY_TIME_" newDataType="datetime(3)" />

    </changeSet>

    <changeSet id="3" author="flowable">

        <createIndex tableName="ACT_APP_APPDEF" indexName="ACT_IDX_APP_DEF_UNIQ" unique="true">
            <column name="KEY_" />
            <column name="VERSION_" />
            <column name="TENANT_ID_" />
        </createIndex>

    </changeSet>

</databaseChangeLog>
```





## 四、创建测试数据库

```
CREATE DATABASE IF NOT EXISTS testliquibase  default charset utf8 COLLATE utf8_general_ci;
```



## 五、实践测试



- 运行主类
- 查看数据库表

