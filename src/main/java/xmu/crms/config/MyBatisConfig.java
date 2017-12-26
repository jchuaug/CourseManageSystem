package xmu.crms.config;


import java.sql.SQLException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class MyBatisConfig {
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
//        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean
                .setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DruidDataSource dataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/spring");
        druidDataSource.setUsername("javaee");
        druidDataSource.setPassword("javaee123");
//		 druidDataSource.setUrl("jdbc:mysql://120.77.173.98:3306/xmu8205");
//		 druidDataSource.setUsername("xmu8205");
//		 druidDataSource.setPassword("dd0cce7e");
        // 初始化连接大小
        druidDataSource.setInitialSize(0);
        // 连接池最大使用连接数量
        druidDataSource.setMaxActive(20);
        // 连接池最小空闲
        druidDataSource.setMinIdle(0);
        // 获取连接最大等待时间
        druidDataSource.setMaxWait(60000);
        druidDataSource.setValidationQuery("SELECT 1");
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setTestWhileIdle(true);
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        druidDataSource.setMinEvictableIdleTimeMillis(25200000);

        // 打开removeAbandoned功能
        // 1800秒，也就是30分钟
        druidDataSource.setRemoveAbandonedTimeout(1800);
        // 关闭abanded连接时输出错误日志
        druidDataSource.setLogAbandoned(true);

        // 监控数据库
        druidDataSource.setFilters("mergeStat");

        return druidDataSource;
    }

    
  

    // Mapper接口所在包名，Spring会自动查找其下的类
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        //因为有两个模块所以。。
        mapperScannerConfigurer.setBasePackage("xmu.crms.dao");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }

    // (事务管理)transaction manager, use JtaTransactionManager for global tx
    @Bean
    public DataSourceTransactionManager transactionManager() throws SQLException {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }
}

