package chat.duang.formtomysql.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Configuration
@EnableTransactionManagement
// 1⃣️ 指定哪个包下的 Repository 要用这个数据源
@EnableJpaRepositories(
        basePackages        = "chat.duang.formtomysql.repository.chat",
        entityManagerFactoryRef = "chatEntityManagerFactory",
        transactionManagerRef   = "chatTransactionManager"
)
// 2⃣️ 指定实体扫描的位置
@EntityScan(basePackages = "chat.duang.formtomysql.entity.chat")
public class ChatDataSourceConfig {

    // 3⃣️ 把 application.properties 中的 chat.datasource.* 装载到这个 Bean
    @Bean
    @Primary  // 如果你想让它成为默认，可以加，否则去掉
    @ConfigurationProperties("chat.datasource")
    public DataSourceProperties chatDataSourceProperties() {
        return new DataSourceProperties();
    }

    // 4⃣️ 根据上面的 properties 构建真正的 DataSource
    @Bean
    public DataSource chatDataSource() {
        return chatDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    // 5⃣️ 创建 EntityManagerFactory
    @Bean
    public LocalContainerEntityManagerFactoryBean chatEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(chatDataSource())
                // 实体类所在包
                .packages("chat.duang.formtomysql.entity.chat")
                .persistenceUnit("chat")
                .build();
    }

    // 6⃣️ 创建事务管理器
    @Bean
    public PlatformTransactionManager chatTransactionManager(
            @Qualifier("chatEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
