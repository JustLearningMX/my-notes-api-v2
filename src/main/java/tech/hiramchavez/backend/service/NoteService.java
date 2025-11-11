package tech.hiramchavez.backend.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.hiramchavez.backend.dto.ResponseDeleteDTO;
import tech.hiramchavez.backend.dto.category.CategoryBodyReqDto;
import tech.hiramchavez.backend.dto.note.NoteFullDto;
import tech.hiramchavez.backend.dto.note.NoteRequestDto;
import tech.hiramchavez.backend.dto.note.NoteResponseDto;
import tech.hiramchavez.backend.dto.note.NoteToUpdateDto;
import tech.hiramchavez.backend.exception.note.NoteNotFoundException;
import tech.hiramchavez.backend.mapper.note.NoteMapper;
import tech.hiramchavez.backend.model.Note;
import tech.hiramchavez.backend.model.NoteState;
import tech.hiramchavez.backend.model.User;
import tech.hiramchavez.backend.repository.NoteRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final UserService userService;
    private final CategoryService categoryService;

    public NoteResponseDto create(NoteRequestDto noteRequestDto, HttpServletRequest request) {

        //Get the user from the database using the token in the request
        //to validate the user
        User user = userService.getUserByEmailFromDatabase(request);

        Note note = noteMapper.toEntity(noteRequestDto); //Convert the note request dto to an entity

        user.addNote(note); //Add the note to the user

        return noteMapper.noteToNoteResponseDto(
          noteRepository.save(note)
        );
    }

    public NoteFullDto update(NoteToUpdateDto noteToUpdateDto, HttpServletRequest request) {
        User user = userService.getUserByEmailFromDatabase(request);

        Note note = user.getNoteById(noteToUpdateDto.id()); //Get the note from the user by id

        if (note == null) //If the note is null
            throw new NoteNotFoundException("Note not found"); //Throw an exception

        Set<CategoryBodyReqDto> categoryBodyReqDto = noteToUpdateDto.categories();
        categoryService.updateCategoriesFromNote(note, categoryBodyReqDto);

        return noteMapper.noteToNoteFullDto(note.update(noteToUpdateDto)); //Return the note response dto
    }

    public ResponseDeleteDTO delete(Long id, HttpServletRequest request) {

            User user = userService.getUserByEmailFromDatabase(request);

            Note note = user.getNoteById(id); //Get the note from the user by id

            if (note == null) //If the note is null
                throw new NoteNotFoundException("Note not found"); //Throw an exception

            note.delete(); //Delete the note

            return new ResponseDeleteDTO(false, "Note deleted successfully"); //Return the response delete dto
    }

    public NoteResponseDto archived(Long id, HttpServletRequest request) {
        User user = userService.getUserByEmailFromDatabase(request);

        Note note = user.getNoteById(id); //Get the note from the user by id

        if (note == null) //If the note is null
            throw new NoteNotFoundException("Note not found"); //Throw an exception

        note.archived(); //Archived the note

        return noteMapper.noteToNoteResponseDto(note); //Return the note response dto
    }

    public NoteResponseDto unarchived(Long id, HttpServletRequest request) {
        User user = userService.getUserByEmailFromDatabase(request);

        Note note = user.getNoteById(id); //Get the note from the user by id

        if (note == null) //If the note is null
            throw new NoteNotFoundException("Note not found"); //Throw an exception

        note.unarchived(); //Unarchived the note

        return noteMapper.noteToNoteResponseDto(note); //Return the note response dto
    }

    public NoteFullDto getNoteById(Long id, HttpServletRequest request) {
        User user = userService.getUserByEmailFromDatabase(request);

        Note note = user.getNoteById(id); //Get the note from the user by id

        if (note == null) //If the note is null
            throw new NoteNotFoundException("Note not found"); //Throw an exception

        return noteMapper.noteToNoteFullDto(note); //Return the note response dto
    }


    public Page<NoteFullDto> getArchivedNotes(Pageable pageable, HttpServletRequest request) {
        User user = userService.getUserByEmailFromDatabase(request);

        return noteRepository.findAllByStateAndUserId(NoteState.ARCHIVED, user.getId(), pageable)
          .map(noteMapper::noteToNoteFullDto);
    }

    public Page<NoteFullDto> getUnarchivedNotes(Pageable pageable, HttpServletRequest request) {
        User user = userService.getUserByEmailFromDatabase(request);

        return noteRepository.findAllByStateAndUserId(NoteState.ACTIVE, user.getId(), pageable)
          .map(noteMapper::noteToNoteFullDto);
    }
}
