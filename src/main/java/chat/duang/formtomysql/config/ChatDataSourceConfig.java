package chat.duang.formtomysql.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
// 只扫描 chat_room 相关的 Entity 和 Repository
@EnableJpaRepositories(
        basePackages         = "chat.duang.formtomysql.repository",   // 这里放 ChatRoomRepository 的包
        includeFilters       = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = ChatRoomRepository.class),
        entityManagerFactoryRef = "chatEntityManagerFactory",
        transactionManagerRef   = "chatTransactionManager"
)
public class ChatDataSourceConfig {

    // 读取前缀为 chat.datasource 的属性
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

    @Bean(name = "chatEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean chatEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(chatDataSource())
                .packages("chat.duang.formtomysql.entity")   // ChatRoom 实体所在包
                .persistenceUnit("chatPU")
                .build();
    }

    @Bean
    public PlatformTransactionManager chatTransactionManager(
            @Qualifier("chatEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
