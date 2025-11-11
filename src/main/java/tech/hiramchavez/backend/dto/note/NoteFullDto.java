package tech.hiramchavez.backend.dto.note;

import tech.hiramchavez.backend.dto.category.CategoryBodyResDto;
import tech.hiramchavez.backend.dto.user.SignedUserDTO;
import tech.hiramchavez.backend.model.NoteState;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * DTO for {@link tech.hiramchavez.backend.model.Note}
 */
public record NoteFullDto(

  Long id,
  String title,
  String description,
  Date lastEdited,
  NoteState state,
  boolean deleted,
  SignedUserDTO user,
  Set<CategoryBodyResDto> categories

) implements Serializable {
}