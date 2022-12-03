package ru.practicum.explore.comment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.user.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
    @Column(name = "published", nullable = false)
    private Boolean published;
    @Column(name = "comment_text", nullable = false)
    private String commentText;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @ToString.Exclude
    private Event event;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commenter_id")
    @ToString.Exclude
    private User commenter;
}

