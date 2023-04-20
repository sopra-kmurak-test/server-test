package com.example.accessingdatamysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
@Service
@Transactional
public class CommentService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final AnswerRepository answerRepository;

    @Autowired
    public CommentService(@Qualifier("commentRepository") CommentRepository commentRepository, AnswerRepository answerRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
    }

    public Comment createNewComment(Integer user_id, Integer answer_id, String content) {
        User user = userRepository.findUserByUserId(user_id);
        Answer answer = answerRepository.findAnswerById(answer_id);

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setAnswer(answer);
        comment.setContent(content);
        comment.setCreatedAt(new Date());

        return commentRepository.save(comment);
    }

    public Comment createReply(Integer user_id, Integer answer_id, String content, Integer parent_comment_id) {
        User user = userRepository.findUserByUserId(user_id);
        Answer answer = answerRepository.findAnswerById(answer_id);
        Comment parentComment = commentRepository.findCommentById(parent_comment_id);

        Comment replyComment = new Comment();
        replyComment.setUser(user);
        replyComment.setAnswer(answer);
        replyComment.setContent(content);
        replyComment.setParentComment(parentComment);
        replyComment.setCreatedAt(new Date());

        return commentRepository.save(replyComment);




    }
}
