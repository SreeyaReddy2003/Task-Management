package com.sprint_project.task_management.testing_service;

import com.sprint_project.task_management.entity.Comment;
import com.sprint_project.task_management.repository.CommentRepository;
import com.sprint_project.task_management.serviceImpl.CommentServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    private Comment sampleComment;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleComment = new Comment();
        sampleComment.setCommentId(1);
        sampleComment.setContent("This is a test comment");
        sampleComment.setCreatedAt(LocalDateTime.now());
    }

    @Test
    public void testAddComment() {
        when(commentRepository.save(any(Comment.class))).thenReturn(sampleComment);
        Comment result = commentService.addComment(sampleComment);
        assertNotNull(result);
        assertEquals("This is a test comment", result.getContent());
    }

    @Test
    public void testGetAllComments() {
        when(commentRepository.findAll()).thenReturn(List.of(sampleComment));
        List<Comment> result = commentService.getAllComments();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetCommentById_Found() {
        when(commentRepository.findById(1)).thenReturn(Optional.of(sampleComment));
        Comment result = commentService.getCommentById(1);
        assertNotNull(result);
        assertEquals(1, result.getCommentId());
    }

    @Test
    public void testGetCommentById_NotFound() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            commentService.getCommentById(1);
        });
        assertEquals("Comment doesn't exist", exception.getMessage());
    }

    @Test
    public void testUpdateComment() {
        Comment updated = new Comment();
        updated.setContent("Updated content");

        when(commentRepository.findById(1)).thenReturn(Optional.of(sampleComment));
        when(commentRepository.save(any(Comment.class))).thenReturn(updated);

        Comment result = commentService.updateComment(1, updated);
        assertEquals("Updated content", result.getContent());
    }

    @Test
    public void testDeleteComment() {
        when(commentRepository.findById(1)).thenReturn(Optional.of(sampleComment));
        doNothing().when(commentRepository).delete(sampleComment);

        assertDoesNotThrow(() -> commentService.deleteComment(1));
    }
}

