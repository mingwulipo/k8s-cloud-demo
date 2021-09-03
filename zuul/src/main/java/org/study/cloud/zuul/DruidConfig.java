package org.study.cloud.zuul;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
//@MapperScan(basePackages = {"org.study.cloud.*.mapper"})
public class DruidConfig {

    @Autowired
    private DruidProperties druidProperties;

    @Bean
    public DataSource druid() {
        DruidDataSource dds = new DruidDataSource();
        dds.setUrl(druidProperties.getUrl());
        dds.setUsername(druidProperties.getUsername());
        dds.setPassword(druidProperties.getPassword());
        dds.setDriverClassName(druidProperties.getDriverClassName());

        dds.setInitialSize(druidProperties.getInitialSize());
        dds.setMinIdle(druidProperties.getMinIdle());
        dds.setMaxActive(druidProperties.getMaxActive());
        dds.setMaxWait(druidProperties.getMaxWait());
        dds.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
        dds.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
        dds.setValidationQuery(druidProperties.getValidationQuery());

        dds.setTestWhileIdle(druidProperties.getTestWhileIdle());
        dds.setTestOnBorrow(druidProperties.getTestOnBorrow());
        dds.setTestOnReturn(druidProperties.getTestOnReturn());
        dds.setPoolPreparedStatements(druidProperties.getPoolPreparedStatements());
        dds.setMaxPoolPreparedStatementPerConnectionSize(druidProperties.getMaxPoolPreparedStatementPerConnectionSize());

        try {
            dds.setFilters(druidProperties.getFilters());
            dds.init();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        return dds;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory funSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        // 指定数据源(这个必须有，否则报错)
        fb.setDataSource(dataSource);
        // 指定基包
        fb.setTypeAliasesPackage("org.study.cloud.common.entity");
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));

        return fb.getObject();
    }

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * session连接模板
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 配置Druid的监控
     * 1、配置一个管理后台的Servlet
     * @author lipo
     * @date 2019-11-05 18:17
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<>();
        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","123456");
        //默认就是允许所有访问
        initParams.put("allow","");
        initParams.put("deny","192.168.15.21");
        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     * 2、配置一个web监控的filter
     * @author lipo
     * @date 2019-11-05 18:18
     */
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return  bean;
    }

}
