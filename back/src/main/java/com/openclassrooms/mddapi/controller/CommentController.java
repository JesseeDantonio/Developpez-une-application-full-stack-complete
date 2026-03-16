package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.in.CreateCommentDTO;
import com.openclassrooms.mddapi.dto.out.CommentDTO;
import com.openclassrooms.mddapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Récupère un commentaire par son ID.
     * @param id L'ID du commentaire
     * @return La réponse HTTP avec les détails du commentaire
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Integer id) {
        CommentDTO commentDTO = commentService.getCommentById(id);
        return ResponseEntity.ok(commentDTO);
    }

    /**
     * Récupère tous les commentaires.
     * @return La réponse HTTP avec la liste des commentaires
     */
    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<CommentDTO> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    /**
     * Crée un nouveau commentaire.
     * @param commentDto Les données du nouveau commentaire
     * @return La réponse HTTP avec les détails du commentaire créé
     */
    @PostMapping
    public ResponseEntity<CreateCommentDTO> createComment(@RequestBody CreateCommentDTO commentDto) {
        CreateCommentDTO createdComment = commentService.createComment(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    /**
     * Supprime un commentaire.
     * @param id L'ID du commentaire à supprimer
     * @return La réponse HTTP
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
