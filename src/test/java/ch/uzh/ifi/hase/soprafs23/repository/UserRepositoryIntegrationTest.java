package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void findByName_success() {
    // given
    User user = new User();
    user.setName("Firstname Lastname");
    user.setUsername("firstname@lastname");
    user.setStatus(UserStatus.OFFLINE);
    user.setToken("1");

    entityManager.persist(user);
    entityManager.flush();

    // when
    User found = userRepository.findByName(user.getName());

    // then
    assertNotNull(found.getId());
    assertEquals(found.getName(), user.getName());
    assertEquals(found.getUsername(), user.getUsername());
    assertEquals(found.getToken(), user.getToken());
    assertEquals(found.getStatus(), user.getStatus());
  }

    @Test
    public void findByUsername_success() {
        // given
        User user = new User();
        user.setName("Firstname Lastname");
        user.setUsername("firstname@lastname");
        user.setStatus(UserStatus.OFFLINE);
        user.setToken("1");

        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findByUsername(user.getUsername());

        // then
        assertNotNull(found.getId());
        assertEquals(found.getName(), user.getName());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getToken(), user.getToken());
        assertEquals(found.getStatus(), user.getStatus());
    }


    @Test
    public void findByUsernameAndPassword_success() {
        // given
        User user = new User();
        user.setName("Firstname Lastname");
        user.setUsername("firstname@lastname");
        user.setPassword("123456");
        user.setStatus(UserStatus.OFFLINE);
        user.setToken("1");

        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());

        // then
        assertNotNull(found.getId());
        assertEquals(found.getName(), user.getName());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getToken(), user.getToken());
        assertEquals(found.getStatus(), user.getStatus());
    }


    @Test
    public void findById_success() {
        // given
        User user = new User();
        user.setName("Firstname Lastname");
        user.setUsername("firstname@lastname");
        user.setPassword("123456");
        user.setStatus(UserStatus.OFFLINE);
        user.setToken("1");

        entityManager.persist(user);
        entityManager.flush();

        // when
        Optional<User> optionalUser = userRepository.findById(user.getId());

        // then
        assertTrue(optionalUser.isPresent());
        User foundUser = optionalUser.get();
        assertNotNull(foundUser.getId());
        assertEquals(foundUser.getName(), user.getName());
        assertEquals(foundUser.getUsername(), user.getUsername());
        assertEquals(foundUser.getToken(), user.getToken());
        assertEquals(foundUser.getStatus(), user.getStatus());
    }
}