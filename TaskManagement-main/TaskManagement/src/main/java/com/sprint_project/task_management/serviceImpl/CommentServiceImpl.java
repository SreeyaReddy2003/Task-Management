package com.sprint_project.task_management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint_project.task_management.entity.Comment;
import com.sprint_project.task_management.repository.CommentRepository;
import com.sprint_project.task_management.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment addComment(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment doesn't exist"));
    }

    @Override
    public Comment updateComment(Integer id, Comment comment) {
        Comment existing = getCommentById(id);
        existing.setContent(comment.getContent());
        return commentRepository.save(existing);
    }

    @Override
    public void deleteComment(Integer id) {
        Comment comment = getCommentById(id);
        commentRepository.delete(comment);
    }
}