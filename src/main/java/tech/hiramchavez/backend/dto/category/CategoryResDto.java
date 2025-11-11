package tech.hiramchavez.backend.dto.category;

import java.io.Serializable;
import java.util.List;

public record CategoryResDto(
  Long note_id,
  List<CategoryBodyResDto> categories

) implements Serializable {
}
