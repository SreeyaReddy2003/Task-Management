package com.sprint_project.task_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint_project.task_management.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}