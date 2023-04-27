package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.Chat;
import ch.uzh.ifi.hase.soprafs23.rest.dto.ChatDTO;
import ch.uzh.ifi.hase.soprafs23.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChatController.class)
public class ChatControllerTest {

    @InjectMocks
    private ChatController chatController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService chatService;

    @Test
    public void testSend() {
        // given
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setUserId1(1L);
        chatDTO.setMessage("Hello World!");

        // send
        ChatDTO result = chatController.send(chatDTO);

        // assert
        assertEquals(chatDTO.getUserId1(), result.getUserId1());
        assertEquals(chatDTO.getMessage(), result.getMessage());
    }

    @Test
    public void testCreateChat() throws Exception {
        // given
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setUserId1(1L);
        chatDTO.setUserId2(2L);

        Chat chat = new Chat();
        chat.setUserId1(1L);
        chat.setUserId2(2L);

        given(chatService.createChat(Mockito.any())).willReturn(chat);

        // post
        MockHttpServletRequestBuilder postRequest = post("/user/{userId}/chat", chat.getUserId1())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(chatDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId1", is(chat.getUserId1().intValue())))
                .andExpect(jsonPath("$.userId2", is(chat.getUserId2().intValue())));
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