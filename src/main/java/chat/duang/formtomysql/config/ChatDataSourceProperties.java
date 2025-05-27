package chat.duang.formtomysql.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration                // 将其注册为 Spring Bean
@ConfigurationProperties(prefix = "chat.datasource")  // 对应 application.yml 中 chat.datasource.*
public class ChatDataSourceProperties {
    private String url;
    private String username;
    private String password;
    // getters and setters …
}
