package noshow.Noshow_blue_2025.controller;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findOne(Long email,Long pass_word);

}
