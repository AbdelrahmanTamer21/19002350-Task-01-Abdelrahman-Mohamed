package s19002350.t_01.abdelrahman_mohamed.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import s19002350.t_01.abdelrahman_mohamed.models.Note;
import s19002350.t_01.abdelrahman_mohamed.services.NoteService;

import java.util.List;
import java.util.stream.Collectors;

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
    @ResponseStatus(HttpStatus.CREATED)
    public Note createNote(@RequestBody Note note) {
        return noteService.createNote(note);
    }

    @GetMapping("/search")
    public List<Note> searchByTitle(@RequestParam String title) {
        return noteService.getAllNotes().stream()
                .filter(n -> n.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable String id, @RequestBody Note note) {
        return noteService.updateNote(id, note);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable String id) {
        noteService.deleteNote(id);
    }
}
