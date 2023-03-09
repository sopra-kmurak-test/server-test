package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Date;
//import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the UserController works.
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
      // given
      User user1 = new User();
      user1.setId(1L);
      user1.setUsername("firstname1@lastname1");
      user1.setPassword("Password1");
      user1.setStatus(UserStatus.OFFLINE);

      User user2 = new User();
      user2.setId(2L);
      user2.setUsername("firstname2@lastname2");
      user2.setPassword("Password2");
      user2.setStatus(UserStatus.ONLINE);

      List<User> allUsers = Arrays.asList(user1, user2);

      // mock the UserService to return the list of users
      given(userService.getUsers()).willReturn(allUsers);

      // when
      MockHttpServletRequestBuilder getRequest = get("/users").contentType(MediaType.APPLICATION_JSON);

      // then
      mockMvc.perform(getRequest).andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(2)))
              .andExpect(jsonPath("$[0].id", is(user1.getId().intValue())))
              .andExpect(jsonPath("$[0].username", is(user1.getUsername())))
              .andExpect(jsonPath("$[0].token", is(user1.getToken())))
              .andExpect(jsonPath("$[0].status", is(user1.getStatus().toString())))
              .andExpect(jsonPath("$[0].creation_date", is(user1.getCreation_date())))
              .andExpect(jsonPath("$[0].birthday", is(user1.getBirthday())))
              .andExpect(jsonPath("$[1].id", is(user2.getId().intValue())))
              .andExpect(jsonPath("$[1].username", is(user2.getUsername())))
              .andExpect(jsonPath("$[1].token", is(user2.getToken())))
              .andExpect(jsonPath("$[1].status", is(user2.getStatus().toString())))
              .andExpect(jsonPath("$[1].creation_date", is(user2.getCreation_date())))
              .andExpect(jsonPath("$[1].birthday", is(user2.getBirthday())));
  }


  @Test
  public void createUser_validInput_userCreated() throws Exception {
    // given
    User user = new User();
    user.setId(1L);
    user.setUsername("testUsername");
    user.setPassword("123456");
    user.setToken("1");
    user.setStatus(UserStatus.ONLINE);

    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setPassword("123456");
    userPostDTO.setUsername("testUsername");

    given(userService.createUser(any())).willReturn(user);

    // when/then -> do the request + validate the result
    MockHttpServletRequestBuilder postRequest = post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(userPostDTO));

    // then
    mockMvc.perform(postRequest)
        .andExpect(status().isCreated())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(user.getId().intValue())))
            .andExpect(jsonPath("$.username", is(user.getUsername())))
            .andExpect(jsonPath("$.token", is(user.getToken())))
            .andExpect(jsonPath("$.status", is(user.getStatus().toString())))
            .andExpect(jsonPath("$.creation_date", is(user.getCreation_date())))
            .andExpect(jsonPath("$.birthday", is(user.getBirthday())));
  }


    @Test
    public void createUser_notUniqueName_returnConflict() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
        user.setPassword("123456");
        user.setToken("1");
        user.setStatus(UserStatus.ONLINE);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("123456");
        userPostDTO.setUsername("testUsername");

        Exception conflict_excp = new ResponseStatusException(HttpStatus.CONFLICT);
        given(userService.createUser(Mockito.any())).willThrow(conflict_excp);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder getRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(getRequest).andExpect(status().isConflict());
    }

    @Test
    public void givenUser_whenGetUser_thenReturnJsonArray() throws Exception {
        // given
        User user = new User();

        user.setId(1L);
        user.setUsername("firstname@lastname");
        user.setPassword("Password");
        user.setToken("1");
        user.setStatus(UserStatus.ONLINE);


        // this mocks the UserService -> we define above what the userService should
        // return when getUsers() is called
        given(userService.getUserByUserId(user.getId())).willReturn(user);

        // when
        MockHttpServletRequestBuilder getRequest = get("/users/"+user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user));

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.token", is(user.getToken())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())))
                .andExpect(jsonPath("$.creation_date", is(user.getCreation_date())))
                .andExpect(jsonPath("$.birthday", is(user.getBirthday())));
    }


    @Test
    public void givenUser_whenGetUser_thenReturnNotFound() throws Exception {
        Exception not_found_excp = new ResponseStatusException(HttpStatus.NOT_FOUND);
        given(userService.getUserByUserId(1L)).willThrow(not_found_excp);

        MockHttpServletRequestBuilder getRequest = get("/users/"+1L)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest).andExpect(status().isNotFound());
    }
    @Test
    public void updateUser_validInput_userUpdated() throws Exception {
        // given
        User user = new User();

        user.setId(1L);
        user.setUsername("firstname@lastname");
        user.setPassword("Password");
        user.setToken("1");
        user.setStatus(UserStatus.ONLINE);

        Date birthDate = new Date();
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setBirthday(birthDate);
        userPostDTO.setUsername("testUsername");

        User inputUser = new User();
        inputUser.setBirthday(birthDate);
        inputUser.setUsername("testUsername");

        // this mocks the UserService -> we define above what the userService should
        // return when getUsers() is called
        given(userService.updateUser2(user.getId(),inputUser)).willReturn(user);

        // when
        MockHttpServletRequestBuilder putRequest = put("/users/"+user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(putRequest).andExpect(status().isNoContent());
    }

    @Test
    public void updateUser_whenGetUser_thenReturnNotFound() throws Exception {

        Date birthDate = new Date();
        User inputUser = new User();
        inputUser.setBirthday(birthDate);
        inputUser.setUsername("testUsername");
        inputUser.setId(10L);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setBirthday(inputUser.getBirthday());
        userPostDTO.setUsername(inputUser.getUsername());

        Exception not_found_excp = new ResponseStatusException(HttpStatus.NOT_FOUND);
        given(userService.updateUser2(anyLong(), any())).willThrow(not_found_excp);

        // when
        MockHttpServletRequestBuilder putRequest = put("/users/"+11L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        mockMvc.perform(putRequest).andExpect(status().isNotFound());
    }


    @Test
    public void givenValidCredentials_whenLogin_thenReturnUser() throws Exception {
        // given
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setUsername("username@example.com");
        userPostDTO.setPassword("password123");

        User user = new User();
        user.setId(1L);
        user.setUsername("username@example.com");
        user.setPassword("password123");
        user.setStatus(UserStatus.OFFLINE);

        given(userService.login(any(User.class))).willReturn(user);

        // when
        MockHttpServletRequestBuilder postRequest = post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.token", is(user.getToken())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())))
                .andExpect(jsonPath("$.creation_date", is(user.getCreation_date())))
                .andExpect(jsonPath("$.birthday", is(user.getBirthday())));
    }


    @Test
    public void givenUserId_whenLogoutUser_thenStatusOk() throws Exception {
        // given
        long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setStatus(UserStatus.ONLINE);

        given(userService.logout(userId)).willReturn(user);

        // when
        MockHttpServletRequestBuilder putRequest = put("/logout/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isOk());
        verify(userService, times(1)).logout(userId);
    }







  /**
   * Helper Method to convert userPostDTO into a JSON string such that the input
   * can be processed
   * Input will look like this: {"name": "Test User", "username": "testUsername"}
   * 
   * @param object
   * @return string
   */
  private String asJsonString(final Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          String.format("The request body could not be created.%s", e.toString()));
    }
  }
}