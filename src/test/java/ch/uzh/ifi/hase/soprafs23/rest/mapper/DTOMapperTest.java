package ch.uzh.ifi.hase.soprafs23.rest.mapper;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPutDTO;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation
 * works.
 */
public class DTOMapperTest {
  @Test
  public void testCreateUser_fromUserPostDTO_toUser_success() {
    // create UserPostDTO
    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setName("name");
    userPostDTO.setUsername("username");
    userPostDTO.setPassword("123456");
    // MAP -> Create user
    User user = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

    // check content
    assertNotNull(userPostDTO);
    assertEquals(userPostDTO.getName(), user.getName());
    assertEquals(userPostDTO.getUsername(), user.getUsername());
    assertEquals(user.getPassword(), userPostDTO.getPassword());
  }

  @Test
  public void testConvertEntityToUserGetDTO() {
      // given
      User user = new User();
      user.setId(1L);
      user.setName("Firstname Lastname");
      user.setUsername("firstname@lastname");
      user.setStatus(UserStatus.OFFLINE);
      user.setToken("1");
      user.setBirthday(new Date());
      user.setCreation_date(new Date());

      // when
      UserGetDTO userGetDTO = DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);

      // then
      assertNotNull(userGetDTO);
      assertEquals(user.getId(), userGetDTO.getId());
      assertEquals(user.getName(), userGetDTO.getName());
      assertEquals(user.getUsername(), userGetDTO.getUsername());
      assertEquals(user.getStatus(), userGetDTO.getStatus());
      assertEquals(user.getToken(), userGetDTO.getToken());
      assertEquals(user.getBirthday(), userGetDTO.getBirthday());
      assertEquals(user.getCreation_date(), userGetDTO.getCreation_date());
  }

    @Test
    public void testConvertEntityToUserPutDTO() {
        // Create UserPutDTO
        UserPutDTO userPutDTO = new UserPutDTO();
        String username = "username";
        Date birthday = new Date();

        // Set username and birthday
        userPutDTO.setUsername(username);
        userPutDTO.setBirthday(birthday);

        // Check values
        assertNotNull(userPutDTO);
        assertEquals(username, userPutDTO.getUsername());
        assertEquals(birthday, userPutDTO.getBirthday());
    }
}
