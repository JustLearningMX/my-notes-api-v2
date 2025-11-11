package tech.hiramchavez.backend.dto.note;

import tech.hiramchavez.backend.model.NoteState;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link tech.hiramchavez.backend.model.Note}
 */
public record NoteResponseDto(

  boolean isError,
  Long id,
  String title,
  String description,
  Date lastEdited,
  NoteState state,
  Boolean deleted

) implements Serializable {

    public NoteResponseDto {
        isError = false;
    }

}