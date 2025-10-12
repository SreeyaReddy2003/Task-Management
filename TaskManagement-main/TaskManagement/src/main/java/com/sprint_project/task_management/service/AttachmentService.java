package com.sprint_project.task_management.service;

import com.sprint_project.task_management.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {
    Attachment uploadFile(MultipartFile file, Integer taskId);
    List<Attachment> getAllAttachments();
    Attachment getAttachmentById(Integer id);
    Attachment updateAttachment(Integer id, Attachment updatedAttachment);
    void deleteAttachment(Integer id);
}