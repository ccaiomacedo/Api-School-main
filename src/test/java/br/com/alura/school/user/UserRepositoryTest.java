package br.com.alura.school.user;

import br.com.alura.school.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    private String existingName;
    private String nonExistingName;
    private User user;

    @BeforeEach
    void setUp() throws Exception {
        existingName = "flutter-cores-dinamicas";
        nonExistingName = "PHP";
        user = Factory.createUser();
    }

    @Test
    public void save_should_persist_with_auto_increment_when_id_is_null() {
        userRepository.save(user);

        Assertions.assertNotNull(user.getId());
    }

    @Test
    void find_by_username_should_Return_object_when_code_exists() {
        Optional<User> user = userRepository.findByUsername(existingName);

        Assertions.assertNotNull(user);
    }
    @Test
    void find_by_username_should_Return_null_when_code_does_not_exists() {
        Optional<User> user = userRepository.findByUsername(nonExistingName);

        Assertions.assertFalse(user.isPresent());
    }
}
