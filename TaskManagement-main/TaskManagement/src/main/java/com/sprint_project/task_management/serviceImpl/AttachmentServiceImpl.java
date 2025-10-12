package com.sprint_project.task_management.serviceImpl;

import com.sprint_project.task_management.entity.Attachment;
import com.sprint_project.task_management.entity.Task;
import com.sprint_project.task_management.repository.AttachmentRepository;
import com.sprint_project.task_management.repository.TaskRepository;
import com.sprint_project.task_management.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Attachment uploadFile(MultipartFile file, Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Attachment attachment = new Attachment();
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFileType(file.getContentType());
        attachment.setFileSize(file.getSize());
        attachment.setTask(task);

        return attachmentRepository.save(attachment);
    }

    @Override
    public List<Attachment> getAllAttachments() {
        return attachmentRepository.findAll();
    }

    @Override
    public Attachment getAttachmentById(Integer id) {
        return attachmentRepository.findById(id).orElse(null);
    }

    @Override
    public Attachment updateAttachment(Integer id, Attachment updatedAttachment) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment existing = optionalAttachment.get();
            existing.setFileName(updatedAttachment.getFileName());
            existing.setFileType(updatedAttachment.getFileType());
            existing.setFileSize(updatedAttachment.getFileSize());
            return attachmentRepository.save(existing);
        }
        return null;
    }

    @Override
    public void deleteAttachment(Integer id) {
        attachmentRepository.deleteById(id);
    }
}