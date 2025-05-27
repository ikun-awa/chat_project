package chat.duang.formtomysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "chat.duang.formtomysql")
@EntityScan(basePackages = {
        "chat.duang.formtomysql.model",
        "chat.duang.formtomysql.entity.chat"
})
public class FormToMysqlApplication {
    public static void main(String[] args) {
        SpringApplication.run(FormToMysqlApplication.class, args);
    }
}

