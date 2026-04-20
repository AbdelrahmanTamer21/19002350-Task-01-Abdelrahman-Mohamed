package s19002350.t_01.abdelrahman_mohamed.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import s19002350.t_01.abdelrahman_mohamed.models.Note;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class NoteRepository {

    private List<Note> notes;
    private java.io.File jsonFile;

    public NoteRepository() {
        InputStream inputStream = getClass().getResourceAsStream("/notes.json");
        if (inputStream == null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unable to read notes.json");
        }
        try {
            this.jsonFile = new java.io.File(getClass().getResource("/notes.json").toURI());
        } catch (Exception e) {
            this.jsonFile = new java.io.File("/data/notes.json");
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            this.notes = objectMapper.readValue(inputStream, new TypeReference<List<Note>>() {});
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading notes.json: " + e.getMessage());
        }
    }

    public List<Note> findAll() {
        return notes;
    }

    public Optional<Note> findById(String id) {
        return notes.stream().filter(n -> n.getId().equals(id)).findFirst();
    }

    public List<Note> findByUserId(String userId) {
        return notes.stream().filter(n -> n.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public Note save(Note note) {
        note.setId(UUID.randomUUID().toString());
        notes.add(note);
        try {
            new ObjectMapper().registerModule(new JavaTimeModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .writeValue(jsonFile, notes);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving note");
        }
        return note;
    }

    public Optional<Note> update(String id, Note updated) {
        return findById(id).map(note -> {
            note.setTitle(updated.getTitle());
            note.setContent(updated.getContent());
            note.setUserId(updated.getUserId());
            try {
                new ObjectMapper().registerModule(new JavaTimeModule())
                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                        .writeValue(jsonFile, notes);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating note");
            }
            return note;
        });
    }

    public boolean deleteById(String id) {
        boolean removed = notes.removeIf(n -> n.getId().equals(id));
        if (removed) {
            try {
                new ObjectMapper().registerModule(new JavaTimeModule())
                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                        .writeValue(jsonFile, notes);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting note");
            }
        }
        return removed;
    }
}
