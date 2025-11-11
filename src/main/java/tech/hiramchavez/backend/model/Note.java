package tech.hiramchavez.backend.model;

import jakarta.persistence.*;
import lombok.*;
import tech.hiramchavez.backend.dto.note.NoteResponseDto;
import tech.hiramchavez.backend.dto.note.NoteToUpdateDto;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "notes")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(name = "last_edited", columnDefinition = "TIMESTAMP")
    private Date lastEdited;

    @Enumerated(EnumType.STRING)
    private NoteState state;

    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinTable(
      name = "notes_categories",
      joinColumns = @JoinColumn(name = "note_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    /* Helpers methods */
    public void addCategory(Category category) {
        this.categories.add(category);
        category.getNotes().add(this);
    }

    public void clearCategories() {
        for (Category category : categories) {
            category.getNotes().remove(this);
        }
        categories.clear();
    }

    public Note update(NoteToUpdateDto noteToUpdateDto) {
        if (noteToUpdateDto.title() != null) {
            this.title = noteToUpdateDto.title();
        }

        if (noteToUpdateDto.description() != null) {
            this.description = noteToUpdateDto.description();
        }

        this.lastEdited = new Date();

        return this;
    }

    public void delete() {
        this.deleted = true;
    }

    public void archived() {
        this.state = NoteState.ARCHIVED;
    }

    public void unarchived() {
        this.state = NoteState.ACTIVE;
    }

    @Override
    public String toString() {
        return "Note{" +
          "id=" + id +
          ", title='" + title + '\'' +
          ", description='" + description + '\'' +
          ", lastEdited=" + lastEdited +
          ", state=" + state +
          ", deleted=" + deleted +
          '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note note)) return false;
        return deleted == note.deleted && Objects.equals(id, note.id) && Objects.equals(title, note.title) && Objects.equals(description, note.description) && Objects.equals(lastEdited, note.lastEdited) && state == note.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, lastEdited, state, deleted);
    }

}
