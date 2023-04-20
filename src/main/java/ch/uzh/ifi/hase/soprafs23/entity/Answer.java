package com.example.accessingdatamysql;

import jakarta.persistence.*;

import java.util.Date;
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date answer_created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date answer_updated;

    private Integer vote_count;

    private Integer comment_count;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAnswer_created() {
        return answer_created;
    }

    public void setAnswer_created(Date answer_created) {
        this.answer_created = answer_created;
    }

    public Date getAnswer_updated() {
        return answer_updated;
    }

    public void setAnswer_updated(Date answer_updated) {
        this.answer_updated = answer_updated;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count= comment_count;
    }
}
