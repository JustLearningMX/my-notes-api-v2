package tech.hiramchavez.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tech.hiramchavez.backend.dto.ResponseDeleteDTO;
import tech.hiramchavez.backend.dto.note.NoteFullDto;
import tech.hiramchavez.backend.dto.note.NoteRequestDto;
import tech.hiramchavez.backend.dto.note.NoteResponseDto;
import tech.hiramchavez.backend.dto.note.NoteToUpdateDto;
import tech.hiramchavez.backend.service.NoteService;

import java.net.URI;

@Tag(name = "Notes", description = "Manage all endpoints about Notes")
@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @Operation(
      summary = "Create a Note.",
      description = "Let a user create a note with title and description."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "201", description = "Note created successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = NoteResponseDto.class))
        }),
      @ApiResponse(responseCode = "400", description = "Bad request.", content = {@Content}),
      @ApiResponse(responseCode = "401", description = "Unauthorized.", content = {@Content}),
      @ApiResponse(responseCode = "403", description = "Forbidden.", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "Not found.", content = {@Content}),
      @ApiResponse(responseCode = "500", description = "Server error.", content = {@Content}),

    })
    @PostMapping
    @Transactional
    public ResponseEntity<NoteResponseDto> createNote(
      @RequestBody @Valid NoteRequestDto noteRequestDto,
      HttpServletRequest request,
      UriComponentsBuilder uriComponentsBuilder
    ) {

        NoteResponseDto noteResponseDto = noteService.create(noteRequestDto, request);

        URI location = uriComponentsBuilder
          .path("/notes/{id}")
          .buildAndExpand(noteResponseDto.id())
          .toUri();

        return ResponseEntity
          .created(location)
          .body(noteResponseDto);
    }

    @Operation(
      summary = "Update a Note.",
      description = "Let a user update a note total o partially."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "Note updated successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = NoteFullDto.class))
        }),
      @ApiResponse(responseCode = "400", description = "Bad request.", content = {@Content}),
      @ApiResponse(responseCode = "401", description = "Unauthorized.", content = {@Content}),
      @ApiResponse(responseCode = "403", description = "Forbidden.", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "Note not found.", content = {@Content}),
      @ApiResponse(responseCode = "500", description = "Server error.", content = {@Content})
    })
    @PutMapping
    @Transactional
    public ResponseEntity<NoteFullDto> updateNote(
      @RequestBody @Valid NoteToUpdateDto noteToUpdateDto,
      HttpServletRequest request
    ) {
        return ResponseEntity
          .status(200)
          .body(noteService.update(noteToUpdateDto, request));
    }

    @Operation(
      summary = "Delete a Note.",
      description = "Let a user apply a logical delete of a Note."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "Note deleted successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = ResponseDeleteDTO.class))
        }),
      @ApiResponse(responseCode = "400", description = "Bad request.", content = {@Content}),
      @ApiResponse(responseCode = "401", description = "Unauthorized.", content = {@Content}),
      @ApiResponse(responseCode = "403", description = "Forbidden.", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "Note not found.", content = {@Content}),
      @ApiResponse(responseCode = "500", description = "Server error.", content = {@Content})
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseDeleteDTO> deleteNote(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity
          .status(200)
          .body(noteService.delete(id, request));
    }

    @Operation(
      summary = "Archived a Note.",
      description = "Let a user archived a Note."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "Note archived successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = ResponseDeleteDTO.class))
        }),
      @ApiResponse(responseCode = "400", description = "Bad request.", content = {@Content}),
      @ApiResponse(responseCode = "401", description = "Unauthorized.", content = {@Content}),
      @ApiResponse(responseCode = "403", description = "Forbidden.", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "Note not found.", content = {@Content}),
      @ApiResponse(responseCode = "500", description = "Server error.", content = {@Content})
    })
    @PutMapping("/archived/{id}")
    @Transactional
    public ResponseEntity<NoteResponseDto> archivedNote(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity
          .status(200)
          .body(noteService.archived(id, request));
    }

    @Operation(
      summary = "Unarchived a Note.",
      description = "Let a user unarchived a Note."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "Note unarchived successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = ResponseDeleteDTO.class))
        }),
      @ApiResponse(responseCode = "400", description = "Bad request.", content = {@Content}),
      @ApiResponse(responseCode = "401", description = "Unauthorized.", content = {@Content}),
      @ApiResponse(responseCode = "403", description = "Forbidden.", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "Note not found.", content = {@Content}),
      @ApiResponse(responseCode = "500", description = "Server error.", content = {@Content})
    })
    @PutMapping("/unarchived/{id}")
    @Transactional
    public ResponseEntity<NoteResponseDto> unarchivedNote(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity
          .status(200)
          .body(noteService.unarchived(id, request));
    }

    @Operation(
      summary = "Get a Note by its ID.",
      description = "Let a user get a Note using the ID."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "Note found successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = NoteFullDto.class))
        }),
      @ApiResponse(responseCode = "400", description = "Bad request.", content = {@Content}),
      @ApiResponse(responseCode = "401", description = "Unauthorized.", content = {@Content}),
      @ApiResponse(responseCode = "403", description = "Forbidden.", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "Note not found.", content = {@Content}),
      @ApiResponse(responseCode = "500", description = "Server error.", content = {@Content})
    })
    @GetMapping("/{id}")
    public ResponseEntity<NoteFullDto> getNoteById(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity
          .status(200)
          .body(noteService.getNoteById(id, request));
    }

    @Operation(
      summary = "Get all Archived Notes.",
      description = "Let a user get a list with all Archived Notes."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "Archived Notes found successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = NoteFullDto.class))
        }),
      @ApiResponse(responseCode = "400", description = "Bad request.", content = {@Content}),
      @ApiResponse(responseCode = "401", description = "Unauthorized.", content = {@Content}),
      @ApiResponse(responseCode = "403", description = "Forbidden.", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "Note not found.", content = {@Content}),
      @ApiResponse(responseCode = "500", description = "Server error.", content = {@Content})
    })
    @GetMapping("/archived")
    public ResponseEntity<Page<NoteFullDto>> getArchivedNotes(Pageable pageable, HttpServletRequest request) {
        return ResponseEntity
          .status(200)
          .body(noteService.getArchivedNotes(pageable, request));
    }

    @Operation(
      summary = "Get all Unarchived Notes.",
      description = "Let a user get a list with all Unarchived Notes."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", description = "Unarchived Notes found successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = NoteFullDto.class))
        }),
      @ApiResponse(responseCode = "400", description = "Bad request.", content = {@Content}),
      @ApiResponse(responseCode = "401", description = "Unauthorized.", content = {@Content}),
      @ApiResponse(responseCode = "403", description = "Forbidden.", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "Note not found.", content = {@Content}),
      @ApiResponse(responseCode = "500", description = "Server error.", content = {@Content})
    })
    @GetMapping("/unarchived")
    public ResponseEntity<Page<NoteFullDto>> getUnarchivedNotes(Pageable pageable, HttpServletRequest request) {
        return ResponseEntity
          .status(200)
          .body(noteService.getUnarchivedNotes(pageable, request));
    }

}
