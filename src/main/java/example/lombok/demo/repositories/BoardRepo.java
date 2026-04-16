package example.lombok.demo.repositories;

import example.lombok.demo.models.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepo extends JpaRepository<Board, Long> {
    @Query("""
            SELECT b
              FROM Board b
   LEFT JOIN FETCH b.assignees
   LEFT JOIN FETCH b.tasks
             WHERE b.id = :id""")
    Optional<Board> findByIdWithDetails(Long id);
}
