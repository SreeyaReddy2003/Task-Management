package com.sprint_project.task_management.controller;

import com.sprint_project.task_management.entity.Attachment;
import com.sprint_project.task_management.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    // Upload a file and link it to a task
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("taskId") Integer taskId) {
        Attachment saved = attachmentService.uploadFile(file, taskId);
        return ResponseEntity.ok(Map.of(
                "code", "POSTSUCCESS",
                "message", "Attachment added successfully",
                "data", saved
        ));
    }

    // Get all attachments
    @GetMapping("/all")
    public ResponseEntity<?> getAllAttachments() {
        List<Attachment> list = attachmentService.getAllAttachments();
        if (list.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of(
                    "code", "GETALLFAILS",
                    "message", "Attachment list is empty"
            ));
        }
        return ResponseEntity.ok(list);
    }

    // Get attachment by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAttachmentById(@PathVariable Integer id) {
        Attachment attachment = attachmentService.getAttachmentById(id);
        if (attachment == null) {
            return ResponseEntity.status(404).body(Map.of(
                    "code", "GETFAILS",
                    "message", "Attachment doesn't exist"
            ));
        }
        return ResponseEntity.ok(attachment);
    }

    // Update attachment details
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAttachment(@PathVariable Integer id,
                                              @RequestBody Attachment updatedAttachment) {
        Attachment updated = attachmentService.updateAttachment(id, updatedAttachment);
        if (updated == null) {
            return ResponseEntity.status(404).body(Map.of(
                    "code", "UPDTFAILS",
                    "message", "Attachment doesn't exist"
            ));
        }
        return ResponseEntity.ok(Map.of(
                "code", "UPDATESUCCESS",
                "message", "Attachment updated successfully",
                "data", updated
        ));
    }

    // Delete attachment
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAttachment(@PathVariable Integer id) {
        Attachment existing = attachmentService.getAttachmentById(id);
        if (existing == null) {
            return ResponseEntity.status(404).body(Map.of(
                    "code", "DLTFAILS",
                    "message", "Attachment doesn't exist"
            ));
        }
        attachmentService.deleteAttachment(id);
        return ResponseEntity.ok(Map.of(
                "code", "DELETESUCCESS",
                "message", "Attachment deleted successfully"
        ));
    }
}