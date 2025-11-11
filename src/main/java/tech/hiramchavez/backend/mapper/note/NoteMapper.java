package tech.hiramchavez.backend.mapper.note;

import org.mapstruct.*;
import tech.hiramchavez.backend.dto.note.NoteFullDto;
import tech.hiramchavez.backend.dto.note.NoteRequestDto;
import tech.hiramchavez.backend.dto.note.NoteResponseDto;
import tech.hiramchavez.backend.dto.note.NoteToUpdateDto;
import tech.hiramchavez.backend.mapper.category.CategoryMapper;
import tech.hiramchavez.backend.mapper.user.UserMapper;
import tech.hiramchavez.backend.model.Note;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, CategoryMapper.class})
public interface NoteMapper {
    Note toEntity(NoteRequestDto noteRequestDto);

    NoteRequestDto noteToNoteRequestDto(Note note);

    Note toEntity(NoteResponseDto noteResponseDto);

    NoteResponseDto noteToNoteResponseDto(Note note);

    Note toEntity(NoteFullDto noteFullDto);

    NoteFullDto noteToNoteFullDto(Note note);

    Note toEntity(NoteToUpdateDto noteToUpdateDto);

    NoteToUpdateDto noteToNoteToUpdateDto(Note note);
}