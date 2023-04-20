package com.example.accessingdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller    // This means that this class is a Controller // entity mapping
@RequestMapping(path="/demo")
public class QuestionController {
    private final QuestionService questionService;
    private final UserRepository userRepository;

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionController(QuestionService questionService, UserRepository userRepository,QuestionRepository questionRepository) {
        this.questionService = questionService;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }


    @PostMapping("/questions")
    public @ResponseBody String createQuestion(@RequestParam Integer user_id,
                                               @RequestParam String title,
                                               @RequestParam String description) {
        User user = userRepository.findUserByUserId(user_id);
        Question question = new Question();
        question.setUser(user);
        question.setTitle(title);
        question.setDescription(description);
        question.setCreated_at(new Date());
        question.setUpdated_at(new Date());
        question.setAnswer_count(0);

        questionService.createQuestion(question);

        return "Question created";
    }

//    @GetMapping(path="/aq")
//    public @ResponseBody Iterable<Question> getAllQUESTIONS() {
//        Iterable<Question> questions = questionRepository.findAll();
////        for (Question q : questions) {
////            System.out.println(q.toString());
////        }
//        return questions;
//    }
    @GetMapping(path="/questions/all")
    public @ResponseBody Iterable<Question> getAllQUESTIONS() {
        // This returns a JSON or XML with the users
        return questionRepository.findAll();
    }

    @GetMapping("/questions/all/{userId}")
    public @ResponseBody Iterable<Question> getQuestionByUserId(@PathVariable Integer userId) {
        return questionRepository.findByUserId(userId);
    }

    @PutMapping (path="/questions/update/{id}") // Map ONLY PUT Requests
    public @ResponseBody Question updateUser (@RequestParam String newTitle,@RequestParam String newDescription
            , @PathVariable Integer id) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        questionService.updateQuestion(newTitle,newDescription,id);

        //userRepository.save(n);
        return questionRepository.findQuestionById(id);
    }

    @DeleteMapping(path="/questions/delete/{id}")
    public @ResponseBody String deleteQuestion(@PathVariable Integer id) {
        questionRepository.deleteById(id);
        return "Question " + id + " deleted";
    }

}
