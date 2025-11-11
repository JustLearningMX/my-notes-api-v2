package tech.hiramchavez.backend.dto.category;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

public record CategoryReqDto(
  @NotNull(message = "Note id is required to add categories")
  Long note_id,
  @Valid
  List<CategoryBodyReqDto> categories

) implements Serializable {
}
