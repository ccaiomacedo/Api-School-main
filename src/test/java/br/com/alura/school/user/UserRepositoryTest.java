package br.com.alura.school.user;

import br.com.alura.school.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;


    @Test
    public void save_should_persist_with_auto_increment_when_id_is_null() {
        User user = Factory.createUser();

        repository.save(user);

        Assertions.assertNotNull(user.getId());
    }
}
