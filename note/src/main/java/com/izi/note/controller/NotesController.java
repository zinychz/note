package com.izi.note.controller;

import com.izi.note.common.Constants;
import com.izi.note.common.dto.ContentDTO;
import com.izi.note.common.dto.LikeDTO;
import com.izi.note.common.dto.NoteDTO;
import com.izi.note.common.dto.NotesListDTO;
import com.izi.note.service.NoteMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.URL.NOTES_CONTROLLER_ROOT_URL)
public class NotesController {

    private final NoteMongoService notesService;

    @Autowired
    public NotesController(NoteMongoService notesService) {
        this.notesService = notesService;
    }

    @GetMapping(value = Constants.URL.NOTE)
    public NotesListDTO getAll() {
        return notesService.getAllNotesDTO();
    }

    @GetMapping(value = Constants.URL.NOTE_ID)
    public NoteDTO getById(@PathVariable(value = Constants.PathVariable.ID) String id) {
        return notesService.getNoteDtoById(id);
    }

    @PostMapping(value = Constants.URL.NOTE)
    public NoteDTO create(@RequestBody ContentDTO contentDTO) {
        return notesService.saveNote(contentDTO, getCurrentUserLogin());
    }

    @PutMapping(value = Constants.URL.NOTE_ID)
    public NoteDTO update(@PathVariable(value = Constants.PathVariable.ID) String id, @RequestBody ContentDTO contentDTO) {
        return notesService.updateNote(id, getCurrentUserLogin(), contentDTO);
    }

    @DeleteMapping(value = Constants.URL.NOTE_ID)
    public NoteDTO delete(@PathVariable(value = Constants.PathVariable.ID) String id) {
        return notesService.deleteNote(id, getCurrentUserLogin());
    }

    @PostMapping(value = Constants.URL.LIKE)
    public NoteDTO like(@RequestBody LikeDTO likeDTO) {
        return notesService.like(likeDTO);
    }

    @PostMapping(value = Constants.URL.DISLIKE)
    public NoteDTO dislike(@RequestBody LikeDTO likeDTO) {
        return notesService.dislike(likeDTO);
    }

    private String getCurrentUserLogin() {
        String currentUserLogin = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserLogin = authentication.getName();
        }
        return currentUserLogin;
    }
}
