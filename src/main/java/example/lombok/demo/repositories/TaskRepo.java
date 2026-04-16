package example.lombok.demo.repositories;

import example.lombok.demo.models.Assignee;
import example.lombok.demo.models.Board;
import example.lombok.demo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TaskRepo extends JpaRepository<Task, Long> {
    Set<Task> findByBoard(Board b);
    Set<Task> findByAssignee(Assignee assignee);
}
