package com.winning.batjx.core.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/1/29
 */
@Configuration
/**
 * 扫描Mapper接口并实现容器管理（类似Spring.xml配置）
 */
@MapperScan(basePackages = QyjxDataSourceConfig.PACKGE, sqlSessionFactoryRef = "qyjxSqlSessionFactory")
public class QyjxDataSourceConfig {

    /**
     * 精确到mapper(Dao)目录，以便跟其他数据库隔离
     */
    public static final String PACKGE = "com.winning.batjx.core.*.mapper";
    /**
     * 精确到mapper(xml)目录，以便跟其他数据库隔离
     */
    public static final String MAPPER_LOCATION = "classpath:mapper/qyjx/**/*.xml";

    @Value("${spring.datasource.qyjx.url}")
    private String url;

    @Value("${spring.datasource.qyjx.username}")
    private String user;

    @Value("${spring.datasource.qyjx.password}")
    private String password;

    @Value("${spring.datasource.qyjx.driverClass}")
    private String driverClass;

    /***
     * @Description: 
     * @Param:
     * @return:
     * @Author: huchengwei
     * @Date: 2021/1/29
     */
    @Bean(name = "qyjxDataSource")
    @Primary
    public DataSource qyjxDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    /***
     * @Description: 
     * @Param:
     * @return:
     * @Author: huchengwei
     * @Date: 2021/1/29
     */
    @Bean(name = "qyjxTransactionManager")
    @Primary
    public DataSourceTransactionManager qyjxTransactionManager(){
        return new DataSourceTransactionManager(qyjxDataSource());
    }

    /***
     * @Description: 
     * @Param:
     * @return:
     * @Author: huchengwei
     * @Date: 2021/1/29
     */
    @Bean(name = "qyjxSqlSessionFactory")
    @Primary
    public SqlSessionFactory qyjxSqlSessionFactory(@Qualifier("qyjxDataSource") DataSource qyjxDataSource) throws Exception{
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(qyjxDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                        .getResources(QyjxDataSourceConfig.MAPPER_LOCATION));
        return sqlSessionFactoryBean.getObject();
    }


}
