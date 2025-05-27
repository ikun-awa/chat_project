package chat.duang.formtomysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(
        scanBasePackages = "chat.duang.formtomysql",   // 扫描所有组件、实体、仓库
        // 如果你额外配置了多数据源，还可以用 exclude 或额外的 @EnableJpaRepositories
)
public class FormToMysqlApplication {
    public static void main(String[] args) {
        SpringApplication.run(FormToMysqlApplication.class, args);
    }
}

