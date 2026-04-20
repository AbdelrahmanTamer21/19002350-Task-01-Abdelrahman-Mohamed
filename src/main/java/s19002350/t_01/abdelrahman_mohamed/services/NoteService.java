package s19002350.t_01.abdelrahman_mohamed.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import s19002350.t_01.abdelrahman_mohamed.models.Note;
import s19002350.t_01.abdelrahman_mohamed.repositories.NoteRepository;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteById(String id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found with id: " + id));
    }

    public List<Note> getNotesByUserId(String userId) {
        List<Note> userNotes = noteRepository.findByUserId(userId);
        if (userNotes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No notes found for user id: " + userId);
        }
        return userNotes;
    }

    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    public Note updateNote(String id, Note note) {
        return noteRepository.update(id, note)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found with id: " + id));
    }

    public void deleteNote(String id) {
        boolean deleted = noteRepository.deleteById(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found with id: " + id);
        }
    }
}
