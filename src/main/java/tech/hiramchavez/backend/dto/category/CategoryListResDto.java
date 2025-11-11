package tech.hiramchavez.backend.dto.category;

import java.io.Serializable;
import java.util.List;

public record CategoryListResDto(
  List<CategoryBodyResDto> categories

) implements Serializable {
}
