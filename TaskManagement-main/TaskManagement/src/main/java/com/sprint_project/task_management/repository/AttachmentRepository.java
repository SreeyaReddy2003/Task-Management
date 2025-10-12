package com.sprint_project.task_management.repository;

import com.sprint_project.task_management.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}