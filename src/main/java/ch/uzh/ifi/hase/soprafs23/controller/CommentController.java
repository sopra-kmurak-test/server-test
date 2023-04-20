package com.example.accessingdatamysql;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller    // This means that this class is a Controller // entity mapping
@RequestMapping(path="/demo")
public class CommentController {

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentService commentService, CommentRepository commentRepository) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;

    }

    @PostMapping("/comments")
    public @ResponseBody Comment createComment(@RequestParam Integer user_id,
                                               @RequestParam Integer answer_id,
                                               @RequestParam String content) {

        return commentService.createNewComment(user_id,answer_id,content);

    }

    @PostMapping("/comments/reply")
    public @ResponseBody String createReply(@RequestParam Integer user_id,
                                               @RequestParam Integer answer_id,
                                               @RequestParam String content,
                                               @RequestParam Integer parent_comment_id) {


        commentService.createReply(user_id,answer_id,content,parent_comment_id);

        return "Comment inserted";

    }



}
