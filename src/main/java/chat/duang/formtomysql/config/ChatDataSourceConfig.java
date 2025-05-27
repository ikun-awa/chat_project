package chat.duang.formtomysql.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "chat.duang.formtomysql.model")  // ← 确保扫描到 ChatMessage
@EnableJpaRepositories(
        basePackages        = "chat.duang.formtomysql.repository.chat",  // 你的聊天 repo 包
        entityManagerFactoryRef = "chatEntityManagerFactory",
        transactionManagerRef   = "chatTransactionManager"
)
public class ChatDataSourceConfig {

    @Bean(name = "chatDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.chat")
    public DataSource chatDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "chatEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean chatEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("chatDataSource") DataSource ds) {
        return builder
                .dataSource(ds)
                .packages("chat.duang.formtomysql.model")  // ← 这里也要加上
                .persistenceUnit("chat")
                .build();
    }

    @Bean(name = "chatTransactionManager")
    public PlatformTransactionManager chatTransactionManager(
            @Qualifier("chatEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
