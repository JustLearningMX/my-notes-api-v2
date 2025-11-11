package tech.hiramchavez.backend.dto.category;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record CategoryBodyReqDto(
  Long id,
  @NotBlank(message = "Category Name is required")
  String name

) implements Serializable {
}
