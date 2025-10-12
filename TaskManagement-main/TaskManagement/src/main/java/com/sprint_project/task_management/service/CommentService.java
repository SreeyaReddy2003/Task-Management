package com.sprint_project.task_management.service;

import java.util.List;

import com.sprint_project.task_management.entity.Comment;

public interface CommentService {
    Comment addComment(Comment comment);
    List<Comment> getAllComments();
    Comment getCommentById(Integer id);
    Comment updateComment(Integer id, Comment comment);
    void deleteComment(Integer id);
}