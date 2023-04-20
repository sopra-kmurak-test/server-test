package com.example.accessingdatamysql;

import com.example.accessingdatamysql.DTO.AnswerGetDTO;
import com.example.accessingdatamysql.mapper.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller    // This means that this class is a Controller // entity mapping
@RequestMapping(path="/demo")
public class AnswerController {

    private final AnswerService answerService;

    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerController(AnswerService answerService, AnswerRepository answerRepository) {
        this.answerService = answerService;
        this.answerRepository = answerRepository;

    }
    @PostMapping("/answers")
    public @ResponseBody Answer createAnswer(@RequestParam Integer user_id,
                                               @RequestParam Integer question_id,
                                               @RequestParam String content) {


        return answerService.createAnswer(user_id,question_id,content);


    }

    @GetMapping("/answers/all/q/{questionId}")
    public @ResponseBody Iterable<Answer> getAnswerByQuestionId(@PathVariable Integer questionId) {
        return answerRepository.findByQuestionId(questionId);
    }
//    @GetMapping("/answers/all/{questionId}")
//    public @ResponseBody Iterable<AnswerGetDTO> getAnswerByUserId(@PathVariable Integer questionId) {
//        List<Answer> answers = answerRepository.findByQuestionId(questionId);
//        return DTOMapper.toDtoList(answers);
//    }
    @GetMapping("/answers/all/u/{userId}")
    public @ResponseBody Iterable<Answer> getAnswerByUserId(@PathVariable Integer userId) {
        return answerRepository.findByUserId(userId);
    }

    @PutMapping("/answers/update/{answerId}")
    public @ResponseBody String updateAnswerContent(@PathVariable Integer answerId,
                                      @RequestParam String newContent) {
        answerService.updateAnswerContent(answerId,newContent);
        return "Answer updated successfully";

    }

    @DeleteMapping(path="/answers/delete/{id}")
    public @ResponseBody String deleteQuestion(@PathVariable Integer id) {
        answerService.deleteOneAnswer(id);
        return "Answer " + id + " deleted";
    }



}
