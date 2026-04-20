package s55_22769.t_01.abdelrahman_mohamed.controllers;

import org.springframework.web.bind.annotation.*;
import s55_22769.t_01.abdelrahman_mohamed.models.Note;
import s55_22769.t_01.abdelrahman_mohamed.services.NoteService;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable String id) {
        return noteService.getNoteById(id);
    }

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return noteService.createNote(note);
    }

    @GetMapping("/search")
    public Note searchByTitle(@RequestParam String title) {
        return noteService.getNoteByTitle(title);
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable String id, @RequestBody Note note) {
        return noteService.updateNote(id, note);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable String id) {
        noteService.deleteNote(id);
    }
}
