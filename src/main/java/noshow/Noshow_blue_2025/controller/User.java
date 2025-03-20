package noshow.Noshow_blue_2025.controller;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
    @Column(name="id",nullable = false)
    private Long Id;

    @Column(name="password",nullable = false,length = 100)
    private Long Pass_word;

    @Column(name="email",nullable = false,length = 100)
    private Long Email;

    @Id
    @Column(name="student_id",nullable = false)
    private Long Student_ID;


    public User(Long email, Long pass_word){
        this.Email=email;
        this.Pass_word=pass_word;
    }

}
