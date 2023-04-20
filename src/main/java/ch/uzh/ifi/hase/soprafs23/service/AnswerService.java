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
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AnswerService {
    private final Logger log = LoggerFactory.getLogger(AnswerService.class);

    private final UserRepository userRepository;

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(UserRepository userRepository,QuestionRepository questionRepository,AnswerRepository answerRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public Answer createAnswer(Integer user_id, Integer question_id, String content){
        check_if_user_answerd_this_question(user_id,question_id);/* We do not allow one user to answer one same question twice. If he thinks the original user is not appropriate anymore,
                                                                    he can use the User center to edit his old answer.*/
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setAnswer_created(new Date());
        answer.setAnswer_updated(new Date());
        answer.setVote_count(0);
        answer.setComment_count(0);

        User user = userRepository.findUserByUserId(user_id);
        answer.setUser(user);

        Question question = questionRepository.findQuestionById(question_id);
        answer.setQuestion(question);

        updateAnswerCount(question,Boolean.TRUE);

        Answer savedAnswer = answerRepository.save(answer);

        return savedAnswer;

    }

    public void updateAnswerCount(Question question,Boolean indictor){
        Integer answerCount;
        if (indictor) {
            answerCount = question.getAnswer_count() + 1;
        }
        else{
            answerCount = question.getAnswer_count() - 1;
        }
        question.setAnswer_count(answerCount);
        questionRepository.save(question);
    }

    public void check_if_user_answerd_this_question(Integer user_id,Integer question_id){
        Integer existingCount = answerRepository.findByUserAndQuestion(user_id, question_id);
        if (existingCount > 0){
            throw new RuntimeException("User has already answered this question.");
        }
    }

    public void updateAnswerContent(Integer answerId, String newContent){
        Answer answer = answerRepository.findAnswerById(answerId);

        answer.setContent(newContent);
        answer.setAnswer_updated(new Date());

        answerRepository.save(answer);
    }

    public void deleteOneAnswer(Integer id){
        Question question = answerRepository.findQuestionByAnswerId(id);

        updateAnswerCount(question,Boolean.FALSE);
        answerRepository.deleteById(id);

    }

}
