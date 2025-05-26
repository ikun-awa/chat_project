package chat.duang.formtomysql.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import chat.duang.formtomysql.entity.user.UserMessage;
import chat.duang.formtomysql.repository.user.UserMessageRepository;

@Configuration
@EnableTransactionManagement
// 扫描 user-message 相关的仓库
@EnableJpaRepositories(
        basePackageClasses      = UserMessageRepository.class,
        entityManagerFactoryRef = "userEntityManagerFactory",
        transactionManagerRef   = "userTransactionManager"
)
public class UserDataSourceConfig {

    /** 读取 spring.datasource.* 的配置 */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties userDataSourceProperties() {
        return new DataSourceProperties();
    }

    /** 默认（主）数据源：user_info 库 */
    @Bean(name = "userDataSource")
    @Primary
    public DataSource userDataSource(
            @Qualifier("userDataSourceProperties") DataSourceProperties props) {
        return props.initializeDataSourceBuilder().build();
    }

    /** user_info 的 EntityManagerFactory，扫描 user 包下的实体 */
    @Bean(name = "userEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean userEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("userDataSource") DataSource ds) {
        return builder
                .dataSource(ds)
                // 扫描 UserMessage 实体
                .packages(UserMessage.class)
                .persistenceUnit("user_info")
                .build();
    }

    /** user_info 的事务管理器 */
    @Bean(name = "userTransactionManager")
    @Primary
    public PlatformTransactionManager userTransactionManager(
            @Qualifier("userEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}