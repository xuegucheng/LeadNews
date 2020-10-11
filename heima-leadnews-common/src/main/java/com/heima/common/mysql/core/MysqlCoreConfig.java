package com.heima.common.mysql.core;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 自动化配置核心数据库的连接配置
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "mysql.core")
@PropertySource("classpath:mysql-core-jdbc.properties")
@MapperScan(basePackages = "com.heima.model.mappers", sqlSessionFactoryRef = "mysqlCoreSqlSessionFactory")
public class MysqlCoreConfig {
    String jdbcUrl;
    String jdbcUserName;
    String jdbcPassword;
    String jdbcDriver;
    String rootMapper;//mapper文件在classpath下存放的根路径
    String aliasesPackage;//别名包
    String helperDialect = "mysql";// 分页语言
    Boolean helperReasonable = false;//分页合理化
    Boolean supportMethodsArguments = false;//自动根据上面 params 配置的字段中取值
    String params;//pageNum,pageSize,count,pageSizeZero,reasonable，不配置映射的用默认值， 默认值为pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero=pageSizeZero

    /**
     * 这是最快的数据库连接池
     *
     * @return
     */
    @Bean
    public DataSource mysqlCoreDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setUsername(this.getJdbcUserName());
        hikariDataSource.setPassword(this.getRealPassword());
        hikariDataSource.setJdbcUrl(this.getJdbcUrl());
        //最大连接数
        hikariDataSource.setMaximumPoolSize(50);
        //最小连接数
        hikariDataSource.setMinimumIdle(5);
        hikariDataSource.setDriverClassName(this.getJdbcDriver());
        return hikariDataSource;
    }

    /**
     * 这是Mybatis的Session
     *
     * @return
     * @throws IOException
     */
    @Bean
    public SqlSessionFactoryBean mysqlCoreSqlSessionFactory(@Qualifier("mysqlCoreDataSource") DataSource mysqlCoreDataSource) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mysqlCoreDataSource);
        sessionFactory.setMapperLocations(resolver.getResources(this.getMapperFilePath()));
        sessionFactory.setTypeAliasesPackage(this.getAliasesPackage());
        org.apache.ibatis.session.Configuration mybatisConf = new org.apache.ibatis.session.Configuration();
        mybatisConf.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(mybatisConf);
        return sessionFactory;
    }

    /**
     * 密码反转，简单示意密码在配置文件中的加密处理
     *
     * @return
     */
    public String getRealPassword() {
        return StringUtils.reverse(this.getJdbcPassword());
    }

    /**
     * 拼接Mapper.xml文件的存放路径
     *
     * @return
     */
    public String getMapperFilePath() {
        return new StringBuffer().append("classpath:").append(this.getRootMapper()).append("/**/*.xml").toString();
    }


}
