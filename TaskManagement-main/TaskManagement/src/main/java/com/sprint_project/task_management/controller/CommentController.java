package com.sprint_project.task_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sprint_project.task_management.entity.Comment;
import com.sprint_project.task_management.service.CommentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post")
    public ResponseEntity<?> addComment(@RequestBody Comment comment) {
        Comment saved = commentService.addComment(comment);
        return ResponseEntity.ok(Map.of("code", "POSTSUCCESS", "message", "Comments added successfully", "data", saved));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        if (comments.isEmpty()) {
            throw new RuntimeException("Comment list is empty");
        }
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Integer id, @RequestBody Comment comment) {
        Comment updated = commentService.updateComment(id, comment);
        return ResponseEntity.ok(Map.of("code", "UPDATESUCCESS", "message", "Comment updated successfully", "data", updated));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(Map.of("code", "DELETESUCCESS", "message", "Comment deleted successfully"));
    }
}