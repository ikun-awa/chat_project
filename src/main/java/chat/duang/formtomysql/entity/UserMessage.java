package chat.duang.formtomysql.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "user_message",
        uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class UserMessage {
    /*
     用户名，密码：1-20
     评价：1-100
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 20, message = "bad boy: LengthException, between 1-20")
    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @NotNull
    @Size(min = 1, max = 20, message = "bad boy: LengthException, between 1-20")
    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Gender gender;

    @NotBlank
    @Column(nullable = false)
    private Integer age;

    @NotBlank
    @Size(min = 1, max = 100, message = "bad boy: LengthException, between 1-100")
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
