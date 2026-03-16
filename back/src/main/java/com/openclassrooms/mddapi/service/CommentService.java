package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.in.CreateCommentDTO;
import com.openclassrooms.mddapi.dto.out.CommentDTO;
import com.openclassrooms.mddapi.entity.ArticleEntity;
import com.openclassrooms.mddapi.entity.CommentEntity;
import com.openclassrooms.mddapi.entity.UserEntity;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    /**
     * Récupère tous les commentaires.
     * @return Une liste de CommentDTO représentant les commentaires
     */
    public List<CommentDTO> getAllComments() {
        List<CommentDTO> comments = new ArrayList<>();
        commentRepository.findAll().forEach(article -> {
            CommentDTO dto = toDTO(article);
            comments.add(dto);
        });

        return comments;
    }

    /**
     * Récupère un commentaire par son ID.
     * @param id L'ID du commentaire à récupérer
     * @return Le CommentDTO représentant le commentaire
     */
    public CommentDTO getCommentById(Integer id) {
        Optional<CommentEntity> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new ResourceNotFoundException("Comment not found");
        }

        return toDTO(comment.get());
    }

    /**
     * Crée un nouveau commentaire.
     * @param commentDto Le DTO contenant les données du commentaire à créer
     * @return Le DTO représentant le commentaire créé
     */
    public CreateCommentDTO createComment(CreateCommentDTO commentDto) {
        commentDto.setCreatedAt(LocalDate.now().toString());
        commentRepository.save(toEntity(commentDto));
        return commentDto;
    }

    /**
     * Supprime un commentaire par son ID.
     * @param id L'ID du commentaire à supprimer
     */
    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }

    /**
     * Convertit un CreateCommentDTO en CommentEntity.
     * @param dto Le DTO à convertir
     * @return L'entité correspondante
     */
    private CommentEntity toEntity(CreateCommentDTO dto) {
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        UserEntity owner = userRepository.findById(dto.getUserId().intValue())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        entity.setUser(owner);
        Optional<ArticleEntity> article = articleRepository.findById(dto.getArticleId().intValue());
        entity.setArticle(article.orElseThrow(() -> new ResourceNotFoundException("Article not found")));
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }

    /**
     * Convertit un CommentEntity en CommentDTO.
     * @param entity L'entité à convertir
     * @return Le DTO correspondant
     */
    private CommentDTO toDTO(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setArticleId(entity.getArticle().getId());
        dto.setContent(entity.getContent());
        dto.setUserId(entity.getUser().getId());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
