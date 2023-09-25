package com.pw5.mynote;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {
    @Mock
    NoteRepository noteRepository;
    @InjectMocks
    NoteService noteService;
    @Test
    public void testGetAllNotes() {
        List<Note> mockNotes = new ArrayList<>();
        mockNotes.add(new Note(1L, "Заметка 1", "Содержание 1"));
        mockNotes.add(new Note(2L, "Заметка 2", "Содержание 2"));
        when(noteRepository.findAll()).thenReturn(mockNotes);

        List<Note> result = noteService.getAllNotes();

        assertEquals(2, result.size());
        assertEquals("Заметка 1", result.get(0).getTitle());
        assertEquals("Содержание 2", result.get(1).getContent());
    }

    @Test
    public void testGetAllNotesNull() {
        when(noteRepository.findAll()).thenReturn(null);

        List<Note> result = noteService.getAllNotes();

        assertNull(result);
    }

    @Test
    public void testGetById() {
        Note mockNote = new Note(1L, "Заметка", "Содержание");
        when(noteRepository.getReferenceById(1L)).thenReturn(mockNote);

        Note result = noteService.getById(1L);

        assertNotNull(result);
        assertEquals("Заметка", result.getTitle());
        assertEquals("Содержание", result.getContent());
    }

    @Test
    public void testGetByIdNull() {
        when(noteRepository.getReferenceById(1L)).thenReturn(null);

        Note result = noteService.getById(1L);

        assertNull(result);
    }
}