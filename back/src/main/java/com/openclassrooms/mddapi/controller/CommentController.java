package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.in.CreateCommentDTO;
import com.openclassrooms.mddapi.dto.out.CommentDTO;
import com.openclassrooms.mddapi.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // GET /api/comments/{id}
    @GetMapping("/{id}")
    public CommentDTO getCommentById(@PathVariable Integer id) {
        return commentService.getCommentById(id);
    }

    // GET /api/comments
    @GetMapping
    public List<CommentDTO> getAllComments() {
        return commentService.getAllComments();
    }

    // POST /api/comments
    @PostMapping
    public CreateCommentDTO createComment(@RequestBody CreateCommentDTO commentDto) {
        return commentService.createComment(commentDto);
    }

    // DELETE /api/comments/{id}
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
    }
}
