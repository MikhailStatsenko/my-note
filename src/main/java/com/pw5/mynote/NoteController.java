package com.pw5.mynote;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/")
    public String homePage() {
        return "index.html";
    }

    @GetMapping("/notes")
    public ResponseEntity<?> allNotes() {
        List<Note> notes = noteService.getAllNotes();
        if (notes == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(notes);
    }

    @GetMapping("/note")
    public ResponseEntity<?> getNote(@RequestParam long id) {
        Note note = noteService.getById(id);
        if (note == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(new NoteDTO(note.getTitle(), note.getContent()));
    }

    @PostMapping("/add-note")
    public ResponseEntity<Void> addNote(@RequestBody Note note) {
        noteService.save(note);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update-note")
    public ResponseEntity<?> updateNote(@RequestParam long id, @RequestBody Note note) {
        Note dbNote = noteService.getById(id);
        if (dbNote == null)
            return ResponseEntity.notFound().build();

        dbNote.setContent(note.getContent());
        noteService.save(dbNote);
        return ResponseEntity.ok().body(new NoteDTO(dbNote.getTitle(), dbNote.getContent()));
    }

    @DeleteMapping("/delete-note")
    public ResponseEntity<Void> deleteNote(@RequestParam long id) {
        if (noteService.getById(id) == null)
            return ResponseEntity.notFound().build();

        noteService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}


