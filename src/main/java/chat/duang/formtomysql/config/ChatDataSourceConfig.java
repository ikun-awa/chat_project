package chat.duang.formtomysql.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "chat.duang.formtomysql.repository.chat",
        entityManagerFactoryRef = "chatEntityManagerFactory",
        transactionManagerRef = "chatTransactionManager"
)
@EntityScan(basePackages = "chat.duang.formtomysql.model")
public class ChatDataSourceConfig {

    @Value("${chat.jpa.hibernate.ddl-auto:update}")
    private String ddlAuto;

    @Value("${chat.jpa.database-platform:org.hibernate.dialect.MySQL8Dialect}")
    private String dialect;

    @Value("${chat.jpa.show-sql:true}")
    private boolean showSql;

    @Bean
    @ConfigurationProperties("chat.datasource")
    public DataSourceProperties chatDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource chatDataSource() {
        return chatDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean chatEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", ddlAuto);
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.show_sql", showSql);

        return builder
                .dataSource(chatDataSource())
                .packages("chat.duang.formtomysql.model")
                .persistenceUnit("chat")
                .properties(properties)
                .build();
    }

    @Bean
    public PlatformTransactionManager chatTransactionManager(
            @Qualifier("chatEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
