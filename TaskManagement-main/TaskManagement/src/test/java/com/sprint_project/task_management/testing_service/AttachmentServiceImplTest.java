//package com.sprint_project.task_management.testing_service;
//import com.sprint_project.task_management.entity.Attachment;
//import com.sprint_project.task_management.entity.Task;
//import com.sprint_project.task_management.repository.AttachmentRepository;
//import com.sprint_project.task_management.repository.TaskRepository;
//import com.sprint_project.task_management.serviceImpl.AttachmentServiceImpl;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class AttachmentServiceImplTest {
//
//    @InjectMocks
//    private AttachmentServiceImpl attachmentService;
//
//    @Mock
//    private AttachmentRepository attachmentRepository;
//
//    @Mock
//    private TaskRepository taskRepository;
//
//    @Mock
//    private MultipartFile multipartFile;
//
//    private Task task;
//    private Attachment attachment;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        task = new Task();
//        task.setTaskId(1);
//
//        attachment = new Attachment();
//        attachment.setAttachmentId(1);
//        attachment.setFileName("test.txt");
//        attachment.setFileType("text/plain");
//        attachment.setFileSize(100L);
//        attachment.setUploadedAt(LocalDateTime.now());
//        attachment.setTask(task);
//    }
//
//    @Test
//    void testUploadFile() {
//        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
//        when(multipartFile.getOriginalFilename()).thenReturn("test.txt");
//        when(multipartFile.getContentType()).thenReturn("text/plain");
//        when(multipartFile.getSize()).thenReturn(100L);
//        when(attachmentRepository.save(any(Attachment.class))).thenReturn(attachment);
//
//        Attachment result = attachmentService.uploadFile(multipartFile, 1);
//
//        assertNotNull(result);
//        assertEquals("test.txt", result.getFileName());
//        verify(attachmentRepository, times(1)).save(any(Attachment.class));
//    }
//
//    @Test
//    void testGetAllAttachments() {
//        when(attachmentRepository.findAll()).thenReturn(List.of(attachment));
//
//        List<Attachment> result = attachmentService.getAllAttachments();
//
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    void testGetAttachmentById() {
//        when(attachmentRepository.findById(1)).thenReturn(Optional.of(attachment));
//
//        Attachment result = attachmentService.getAttachmentById(1);
//
//        assertNotNull(result);
//        assertEquals(1, result.getAttachmentId());
//    }
//
//    @Test
//    void testUpdateAttachment() {
//        Attachment updated = new Attachment();
//        updated.setFileName("updated.txt");
//        updated.setFileType("text/plain");
//        updated.setFileSize(200L);
//
//        when(attachmentRepository.findById(1)).thenReturn(Optional.of(attachment));
//        when(attachmentRepository.save(any(Attachment.class))).thenReturn(updated);
//
//        Attachment result = attachmentService.updateAttachment(1, updated);
//
//        assertNotNull(result);
//        assertEquals("updated.txt", result.getFileName());
//    }
//
//    @Test
//    void testDeleteAttachment() {
//        doNothing().when(attachmentRepository).deleteById(1);
//
//        attachmentService.deleteAttachment(1);
//
//        verify(attachmentRepository, times(1)).deleteById(1);
//    }
//}