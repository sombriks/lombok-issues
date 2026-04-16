package example.lombok.demo.repositories;

import example.lombok.demo.models.Assignee;
import example.lombok.demo.models.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AssigneeRepo extends JpaRepository<Assignee, Long> {
    Set<Assignee> findByBoard(Board b);
}
