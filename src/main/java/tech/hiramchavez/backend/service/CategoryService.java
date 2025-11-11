package tech.hiramchavez.backend.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.hiramchavez.backend.dto.category.*;
import tech.hiramchavez.backend.exception.category.CategoryEmptyException;
import tech.hiramchavez.backend.exception.note.NoteNotFoundException;
import tech.hiramchavez.backend.mapper.category.CategoryMapper;
import tech.hiramchavez.backend.model.Category;
import tech.hiramchavez.backend.model.Note;
import tech.hiramchavez.backend.model.User;
import tech.hiramchavez.backend.repository.CategoryRepository;
import tech.hiramchavez.backend.repository.NoteRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final NoteRepository noteRepository;
    private final CategoryMapper categoryMapper;
    private final UserService userService;

    public CategoryResDto createCategories(CategoryReqDto categoryReqDto, HttpServletRequest request) {

        if (categoryReqDto.categories().isEmpty())
            throw new CategoryEmptyException("The list of categories received to create is empty. Add at least one category");

        User user = userService.getUserByEmailFromDatabase(request);

        Note note = noteRepository.getNoteByIdAndUserIdAndDeletedFalse(categoryReqDto.note_id(), user.getId());

        if (note == null) {
            throw new NoteNotFoundException("Note not found");
        }

        note.clearCategories();

        List<CategoryBodyResDto> categoryBodyResDto = categoryReqDto.categories().stream()
          .map( categoryBodyDto -> {
              boolean exists = categoryRepository.existsByName(categoryBodyDto.name());

              Category category;

              //If the category does not exist, create it
              if (!exists) {
                  category = categoryMapper.categoryBodyReqDtoToCategory(categoryBodyDto);
                  category = categoryRepository.save(category);
                  category.setNotes( new HashSet<>());
              } else { //If the category exists, get it from the database
                  category = categoryRepository.getCategoryByName(categoryBodyDto.name());
              }

              note.addCategory(category);

              return categoryMapper.categoryToCategoryBodyResDto(category);
          })
          .toList();

        return new CategoryResDto(
          note.getId(),
          categoryBodyResDto
        );

    }

    public CategoryListResDto getCategories(HttpServletRequest request) {

        User user = userService.getUserByEmailFromDatabase(request);

        var data = user.getNotes().stream()
          .flatMap(note -> note.getCategories().stream())
          .collect(Collectors.toSet());

        return new CategoryListResDto(
          data.stream()
            .map(categoryMapper::categoryToCategoryBodyResDto)
            .toList()
        );
    }

    public void updateCategoriesFromNote(Note note, Set<CategoryBodyReqDto> categoryBodyReqDto) {

        if (categoryBodyReqDto.isEmpty())
            throw new CategoryEmptyException("The list of categories received to update is empty. Add at least one category");

        note.clearCategories();

        categoryBodyReqDto
          .forEach( categoryBodyDto -> {
              boolean exists = categoryRepository.existsByName(categoryBodyDto.name());

              Category category;

              //If the category does not exist, create it
              if (!exists) {
                  category = categoryMapper.categoryBodyReqDtoToCategory(categoryBodyDto);
                  category = categoryRepository.save(category);
                  category.setNotes( new HashSet<>());
              } else { //If the category exists, get it from the database
                  category = categoryRepository.getCategoryByName(categoryBodyDto.name());
              }

              note.addCategory(category);

              categoryMapper.categoryToCategoryBodyResDto(category);
          });

    }
}
