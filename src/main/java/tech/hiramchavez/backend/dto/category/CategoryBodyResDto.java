package tech.hiramchavez.backend.dto.category;

import java.io.Serializable;

public record CategoryBodyResDto(
  Long id,
  String name

) implements Serializable {
}
