package com.example.webapp.repository;

import com.example.webapp.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepoTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturn1User() throws Exception{
        // given
        User user = new User();
        user.setUsername("jimmy");
        user.setPassword("jim123");
        user.setRole("USER");
        testEntityManager.persist(user);
        testEntityManager.flush();

        // when
        User actual = userRepository.findByUsername("jimmy");

        // then
        assertThat(actual).isEqualTo(user);
    }

    @Test
    public void shouldSave1User() throws Exception{
        // given
        User user = new User();
        user.setUsername("jimmy");
        user.setPassword("jim123");
        user.setRole("USER");

        // when
        User actual = userRepository.save(user);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
    }
}
