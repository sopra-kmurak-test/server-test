package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.Chat;
import ch.uzh.ifi.hase.soprafs23.rest.dto.ChatDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

@RestController
public class ChatController {

  private final ChatService chatService;

  ChatController(ChatService chatService) {
    this.chatService = chatService;
  }

  @PostMapping("/user/{userId}/chat")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public ChatDTO createChat(@RequestBody ChatDTO chatDTO) {
      // convert API chat to internal representation
      Chat chatInput = DTOMapper.INSTANCE.convertChatDTOtoEntity(chatDTO);
      // create chat
      Chat createdChat = chatService.createChat(chatInput);
      // convert internal representation of user back to API
      return DTOMapper.INSTANCE.convertEntityToChatDTO(createdChat);
  }

  @MessageMapping("/chat")
  @SendTo("/receive/chat")
  public ChatDTO send(ChatDTO chatDTO){
      ChatDTO newChatDTO = new ChatDTO();
      newChatDTO.setUserId1(chatDTO.getUserId1());
      newChatDTO.setMessage(chatDTO.getMessage());
      return newChatDTO;
  }

}
