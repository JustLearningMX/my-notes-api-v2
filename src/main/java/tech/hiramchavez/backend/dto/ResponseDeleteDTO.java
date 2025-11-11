package tech.hiramchavez.backend.dto;

public record ResponseDeleteDTO(
  Boolean error,
  String message
) {
}
