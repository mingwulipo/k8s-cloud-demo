package org.study.cloud.common;

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
     * ?????????????????????SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory funSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        // ???????????????(??????????????????????????????)
        fb.setDataSource(dataSource);
        // ????????????
        fb.setTypeAliasesPackage("org.study.cloud.common.entity");
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));

        return fb.getObject();
    }

    /**
     * ?????????????????????
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * session????????????
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * ??????Druid?????????
     * 1??????????????????????????????Servlet
     * @author lipo
     * @date 2019-11-05 18:17
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<>();
        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","123456");
        //??????????????????????????????
        initParams.put("allow","");
        initParams.put("deny","192.168.15.21");
        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     * 2???????????????web?????????filter
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
