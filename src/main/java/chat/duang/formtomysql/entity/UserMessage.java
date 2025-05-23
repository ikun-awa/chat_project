package chat.duang.formtomysql.entity;

import javax.persistence.*;


@Entity
@Table(name = "user_message")     // 映射到你刚才改名后的表
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;         // 原来叫 content，这里改为 password

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Gender gender;          // 用枚举来约束值域

    @Column(nullable = false)
    private Integer age;

    @Column(columnDefinition = "TEXT")
    private String comment;

    // --- getters & setters ---

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}
