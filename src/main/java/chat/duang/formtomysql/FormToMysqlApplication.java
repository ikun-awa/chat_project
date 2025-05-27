package chat.duang.formtomysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("chat.duang.formtomysql.model")
public class FormToMysqlApplication {
    public static void main(String[] args) {
        SpringApplication.run(FormToMysqlApplication.class, args);
    }
}

