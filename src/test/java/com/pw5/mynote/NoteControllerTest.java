package com.pw5.mynote;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteControllerTest {
    @Mock
    NoteService noteService;
    @InjectMocks
    NoteController noteController;


    @Test
    void testGetAllNotes() {
        List<Note> mockNotes = new ArrayList<>();
        mockNotes.add(new Note(1L, "Заметка 1", "Содержание 1"));
        mockNotes.add(new Note(2L, "Заметка 2", "Содержание 2"));
        when(noteService.getAllNotes()).thenReturn(mockNotes);

        ResponseEntity<?> response = noteController.allNotes();
        List<Note> body = (List<Note>) response.getBody();

        assertEquals(2, body.size());
        assertEquals("Заметка 1", body.get(0).getTitle());
        assertEquals("Содержание 2", body.get(1).getContent());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testGetAllNotesNull() {
        when(noteService.getAllNotes()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = noteController.allNotes();

        assertNull(response.getBody());
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void testGetNote() {
        Note mockNote = new Note(1L, "Заметка", "Содержание");
        when(noteService.getById(1L)).thenReturn(mockNote);

        ResponseEntity<?> response = noteController.getNote(1L);
        NoteDTO result = (NoteDTO) response.getBody();

        assertNotNull(result);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Заметка", result.title());
        assertEquals("Содержание", result.content());
    }

    @Test
    void testGetNoteNull() {
        when(noteService.getById(1L)).thenReturn(null);

        ResponseEntity<?> response = noteController.getNote(1L);

        assertNull(response.getBody());
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testAddNote() {
        ResponseEntity<Void> response = noteController.addNote(new Note());

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
    }


    @Test
    void testUpdateNote() {
        Note mockNote = new Note(1L, "Заметка", "Содержание");
        Note updateNote = new Note();
        updateNote.setContent("Новое содержание");
        when(noteService.getById(1L)).thenReturn(mockNote);

        ResponseEntity<?> response = noteController.updateNote(1L, updateNote);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Новое содержание", ((NoteDTO) response.getBody()).content());
    }

    @Test
    void testUpdateNoteNull() {
        Note updateNote = new Note();
        updateNote.setContent("Новое содержание");
        when(noteService.getById(1L)).thenReturn(null);

        ResponseEntity<?> response = noteController.updateNote(1L, updateNote);

        assertNull(response.getBody());
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testDeleteNote() {
        Note mockNote = new Note(1L, "Заметка", "Содержание");
        when(noteService.getById(1L)).thenReturn(mockNote);

        ResponseEntity<?> response = noteController.deleteNote(1L);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testDeleteNoteNull() {
        doThrow(EntityNotFoundException.class).when(noteService).deleteById(1L);

        ResponseEntity<?> response = noteController.deleteNote(1L);

        assertEquals(404, response.getStatusCode().value());
    }
}