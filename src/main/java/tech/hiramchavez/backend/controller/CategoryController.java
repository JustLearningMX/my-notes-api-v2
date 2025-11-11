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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.hiramchavez.backend.dto.category.CategoryListResDto;
import tech.hiramchavez.backend.dto.category.CategoryReqDto;
import tech.hiramchavez.backend.dto.category.CategoryResDto;
import tech.hiramchavez.backend.service.CategoryService;

@Tag(name = "Categories", description = "Manage all endpoints about Categories")
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
      summary = "Create Categories based on a list.",
      description = "Let a user create categories of a note from a list."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "201", description = "Categories created successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = CategoryResDto.class))
        }),
      @ApiResponse(responseCode = "400", description = "Bad request.", content = {@Content}),
      @ApiResponse(responseCode = "401", description = "Unauthorized.", content = {@Content}),
      @ApiResponse(responseCode = "403", description = "Forbidden.", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "Note not found.", content = {@Content}),
      @ApiResponse(responseCode = "500", description = "Server error.", content = {@Content})
    })
    @PostMapping
    @Transactional
    public ResponseEntity<CategoryResDto> createCategories(
      @RequestBody @Valid CategoryReqDto categoryReqDto,
      HttpServletRequest request
      ) {

        CategoryResDto categoryResDto = categoryService.createCategories(categoryReqDto, request);

        return ResponseEntity
          .status(201)
          .body(categoryResDto);
    }

    @Operation(
      summary = "Get a list of all Categories of a Note.",
      description = "Let a user get all categories of a Note on a list."
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "201", description = "Categories found successfully.",
        content = {
          @Content(mediaType = "application/json",
            schema = @Schema(implementation = CategoryListResDto.class))
        }),
      @ApiResponse(responseCode = "500", description = "Server error.", content = {@Content})
    })
    @GetMapping
    public ResponseEntity<CategoryListResDto> getCategories(HttpServletRequest request) {

        CategoryListResDto categoryListResDto = categoryService.getCategories(request);

        return ResponseEntity
          .status(200)
          .body(categoryListResDto);
    }

}
