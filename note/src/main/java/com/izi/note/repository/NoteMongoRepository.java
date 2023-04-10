package com.izi.note.repository;

import com.izi.note.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteMongoRepository extends MongoRepository<Note, String> {
}
