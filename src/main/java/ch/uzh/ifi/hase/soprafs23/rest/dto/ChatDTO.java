package ch.uzh.ifi.hase.soprafs23.rest.dto;

import java.util.Date;

public class ChatDTO {

  private Long chatId;
  private Long userId1;
  private Long userId2;
  private String message;
  private Date createTime;

  public Long getChatId() {
    return chatId;
  }
  public void setChatId(Long chatId) {
    this.chatId = chatId;
  }
  public Long getUserId1() {
        return userId1;
    }
  public void setUserId1(Long userId1) { this.userId1 = userId1; }
  public Long getUserId2() {
        return userId2;
    }
  public void setUserId2(Long userId2) { this.userId2 = userId2; }
  public String getMessage(){ return message; }
  public void setMessage(String message){ this.message = message; }
  public Date getCreateTime(){ return createTime; }
  public void setCreateTime(Date createTime){ this.createTime = createTime; }

}
