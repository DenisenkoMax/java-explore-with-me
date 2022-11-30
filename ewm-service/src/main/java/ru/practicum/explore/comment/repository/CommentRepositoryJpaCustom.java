package ru.practicum.explore.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore.comment.model.Comment;

import java.util.List;

public interface CommentRepositoryJpaCustom extends JpaRepository<Comment, Long> {
    @Query(
            "SELECT c FROM Comment c WHERE (c.event.initiator.id = ?1) " +
                    "AND (c.published = ?2)")
    List<Comment> getAllCommentsPrivate(Long ownerId, Boolean onlyPublished, Pageable pageable);

    @Query(
            "SELECT c FROM Comment c WHERE c.published = true")
    List<Comment> getAllCommentsPublic(Pageable pageable);

    @Query(
            "SELECT c FROM Comment c WHERE (c.event.initiator.id = ?1) AND " +
                    "(c.commenter.id = ?2) AND (c.published = ?3)")
    List<Comment> getAllByOwnerAndCommenterOnlyPubAdmin(Long ownerId, Long commenterId,
                                                        Boolean onlyPublished, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE (c.event.initiator.id = ?1) AND (c.published = ?2)")
    List<Comment> getAllByOwnerOnlyPubAdmin(Long ownerId, Boolean onlyPublished, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE (c.commenter.id = ?1) AND (c.published = ?2)")
    List<Comment> getAllByCommenterOnlyPubAdmin(Long commenterId, Boolean onlyPublished, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.published = ?1")
    List<Comment> getAllByOnlyPubAdmin(Boolean onlyPublished, Pageable pageable);
}