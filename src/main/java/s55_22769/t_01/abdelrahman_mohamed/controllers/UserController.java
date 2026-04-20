package s55_22769.t_01.abdelrahman_mohamed.controllers;

import org.springframework.web.bind.annotation.*;
import s55_22769.t_01.abdelrahman_mohamed.models.Note;
import s55_22769.t_01.abdelrahman_mohamed.models.User;
import s55_22769.t_01.abdelrahman_mohamed.services.NoteService;
import s55_22769.t_01.abdelrahman_mohamed.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final NoteService noteService;

    public UserController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @GetMapping("/search")
    public User searchByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/{id}/notes")
    public List<Note> getNotesByUserId(@PathVariable String id) {
        return noteService.getNotesByUserId(id);
    }
}
