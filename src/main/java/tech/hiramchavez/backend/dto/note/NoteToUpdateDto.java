package tech.hiramchavez.backend.dto.note;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.hiramchavez.backend.dto.category.CategoryBodyReqDto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * DTO for {@link tech.hiramchavez.backend.model.Note}
 */
public record NoteToUpdateDto(

  @NotNull(message = "Note ID is required to update")
  Long id,
  @NotBlank(message = "Title is required")
  String title,
  @NotBlank(message = "Description is required")
  String description,
  Date lastEdited,
  Set<CategoryBodyReqDto> categories

) implements Serializable {
}