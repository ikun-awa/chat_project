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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import chat.duang.formtomysql.entity.chat.ChatRoom;
import chat.duang.formtomysql.repository.chat.ChatRoomRepository;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackageClasses   = ChatRoomRepository.class,
        entityManagerFactoryRef = "chatEntityManagerFactory",
        transactionManagerRef   = "chatTransactionManager"
)
public class ChatDataSourceConfig {

    /**
     * 读取 application.properties 中前缀为 chat.datasource 的配置
     */
    @Bean
    @ConfigurationProperties("chat.datasource")
    public DataSourceProperties chatDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 第二数据源 DataSource，名字叫 chatDataSource
     */
    @Bean(name = "chatDataSource")
    public DataSource chatDataSource(
            @Qualifier("chatDataSourceProperties") DataSourceProperties props) {
        return props.initializeDataSourceBuilder().build();
    }

    /**
     * chat 的 EntityManagerFactory，扫描 ChatRoom 实体
     */
    @Bean(name = "chatEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean chatEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("chatDataSource") DataSource ds) {
        return builder
                .dataSource(ds)
                // 注意这里要指向 entity.chat 包下的 ChatRoom
                .packages(ChatRoom.class)
                .persistenceUnit("chat")
                .build();
    }

    /**
     * chat 的事务管理器
     */
    @Bean(name = "chatTransactionManager")
    public PlatformTransactionManager chatTransactionManager(
            @Qualifier("chatEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}