package tech.hiramchavez.backend.dto.note;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.hiramchavez.backend.model.NoteState;
import tech.hiramchavez.backend.service.NoteService;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link tech.hiramchavez.backend.model.Note}
 */
public record NoteRequestDto(

  @NotBlank(message = "Title for note is required")
  String title,

  String description,

  @NotNull(message = "Last edited date is required")
  Date lastEdited,

  @NotNull(message = "State for the note is required")
  NoteState state,

  @NotNull(message = "Availability for the note is required")
  Boolean deleted

) implements Serializable {
}