package ru.practicum.explore.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore.comment.model.Comment;
import ru.practicum.explore.event.model.State;

import java.util.List;

public interface CommentRepositoryJpaCustom extends JpaRepository<Comment, Long> {
    @Query(
            "SELECT c FROM Comment c WHERE (c.event.initiator.id = :ownerId) " +
                    "AND (c.published IN :listPublished)")
    List<Comment> getAllCommentsPrivate(Long ownerId, List<Boolean> listPublished, Pageable pageable);

    @Query(
            "SELECT c FROM Comment c WHERE c.published = true AND c.event.state = :state AND c.event.id= :eventId")
    List<Comment> getAllCommentsPublic(Long eventId, State state, Pageable pageable);

    @Query(
            "SELECT c FROM Comment c WHERE c.id = :commentId AND c.event.state = :state")
    Comment findById(Long commentId, State state);

    @Query(
            "SELECT c FROM Comment c WHERE (c.event.initiator.id = :ownerId) AND " +
                    "(c.commenter.id = :commenterId) AND (c.published = :onlyPublished)")
    List<Comment> getAllByOwnerAndCommenterOnlyPubAdmin(Long ownerId, Long commenterId,
                                                        Boolean onlyPublished, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE (c.event.initiator.id = :ownerId) AND (c.published = :onlyPublished)")
    List<Comment> getAllByOwnerOnlyPubAdmin(Long ownerId, Boolean onlyPublished, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE (c.commenter.id = :commenterId) AND (c.published = :onlyPublished)")
    List<Comment> getAllByCommenterOnlyPubAdmin(Long commenterId, Boolean onlyPublished, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.published = :onlyPublished")
    List<Comment> getAllByOnlyPubAdmin(Boolean onlyPublished, Pageable pageable);
}