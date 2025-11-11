package tech.hiramchavez.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.hiramchavez.backend.model.Note;
import tech.hiramchavez.backend.model.NoteState;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Page<Note> findAllByStateAndUserId(NoteState archived, Long id, Pageable pageable);

    Note getNoteByIdAndDeletedFalse(Long id);

    Note getNoteByIdAndUserIdAndDeletedFalse(Long id, Long userId);

}
