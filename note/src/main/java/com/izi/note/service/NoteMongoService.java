package com.izi.note.service;

import com.izi.note.common.dto.ContentDTO;
import com.izi.note.common.dto.LikeDTO;
import com.izi.note.common.dto.NoteDTO;
import com.izi.note.common.dto.NotesListDTO;
import com.izi.note.model.Note;
import com.izi.note.model.User;
import com.izi.note.repository.NoteMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoteMongoService {

    private final NoteMongoRepository noteRepository;
    private final UserMongoService userService;

    @Autowired
    public NoteMongoService(NoteMongoRepository noteRepository, UserMongoService userService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    protected List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public NotesListDTO getAllNotesDTO() {
        return new NotesListDTO(getAllNotes().stream()
                .sorted((note1, note2) -> note2.getUpdated().compareTo(note1.getUpdated())).collect(Collectors.toList()));
    }

    public NoteDTO getNoteDtoById(String id) {
        return convertNoteToNoteDTO(getNoteById(id).orElse(new Note()));
    }

    public NoteDTO updateNote(String noteId, String updaterUserLogin, ContentDTO contentDTO) {
        Optional<Note> optionalNote = getNoteById(noteId);
        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            if (note.getAuthorId() != null) {
                User user = userService.findUserByLogin(updaterUserLogin);
                if (user != null && note.getAuthorId().equals(user.getId())) {
                    note.setContent(contentDTO.getContent());
                    return convertNoteToNoteDTO(saveNote(note));
                }
            }
            return convertNoteToNoteDTO(note);
        }
        return convertNoteToNoteDTO(new Note());
    }

    public NoteDTO deleteNote(String noteId, String updaterUserLogin) {
        Optional<Note> optionalNote = getNoteById(noteId);
        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            if (note.getAuthorId() != null) {
                User user = userService.findUserByLogin(updaterUserLogin);
                if (user != null && note.getAuthorId().equals(user.getId())) {
                    noteRepository.delete(note);
                    return convertNoteToNoteDTO(new Note());
                }
            }
            return convertNoteToNoteDTO(note);
        }
        return convertNoteToNoteDTO(new Note());
    }

    protected Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    public NoteDTO saveNote(ContentDTO contentDTO, String currentUserLogin) {
        User user = userService.findUserByLogin(currentUserLogin);
        Note note = saveNote(createNote(contentDTO.getContent(), user == null ? null : user.getId()));
        return convertNoteToNoteDTO(note);
    }

    private Note createNote(String content, String authorUserId) {
        Note note = new Note();
        note.setId(UUID.randomUUID().toString());
        note.setContent(content);
        note.setLikes(new HashSet<>());
        note.setAuthorId(authorUserId);
        return note;
    }

    private NoteDTO convertNoteToNoteDTO(Note note) {
        return new NoteDTO(note.getId(), note.getContent(), note.getCreated(), note.getUpdated(), note.getLikes(), note.getAuthorId());
    }

    protected Optional<Note> getNoteById(String noteId) {
        return noteRepository.findById(noteId);
    }

    public NoteDTO like(LikeDTO likeDTO) {
        return likeOrDislike(likeDTO, false);
    }

    public NoteDTO dislike(LikeDTO likeDTO) {
        return likeOrDislike(likeDTO, true);
    }

    private NoteDTO likeOrDislike(LikeDTO likeDTO, boolean isDislike) {
        Optional<User> optionalUser = userService.findUserById(likeDTO.getUserId());
        if (optionalUser.isPresent()) {
            Optional<Note> optionalNote = getNoteById(likeDTO.getNoteId());
            if (optionalNote.isPresent()) {
                Note note = optionalNote.get();
                User user = optionalUser.get();
                if (isDislike) {
                    note.getLikes().remove(user.getId());
                } else {
                    note.getLikes().add(note.getId());
                }
                return convertNoteToNoteDTO(saveNote(note));
            }
        }
        return convertNoteToNoteDTO(new Note());
    }
}
