package example.lombok.demo;

import example.lombok.demo.dtos.BoardView;
import example.lombok.demo.models.Board;
import example.lombok.demo.repositories.AssigneeRepo;
import example.lombok.demo.repositories.BoardRepo;
import example.lombok.demo.repositories.TaskRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardRepo boardRepo;
    private final AssigneeRepo assigneeRepo;
    private final TaskRepo taskRepo;

    @GetMapping
    public List<BoardView> getAllBoards() {
        log.info("getAllBoards");
        List<BoardView> result =  boardRepo
                .findAll()
                .stream()
                .map(BoardView::from)
                .toList();
        log.info("getAllBoards: {}", result);
        return result;
    }

    @Transactional
    @PostMapping
    public BoardView createBoard(@RequestBody Board board) {
        log.info("createBoard");
        boardRepo.save(board);
        Stream.ofNullable(board.getTasks())
                .flatMap(Set::stream)
                .map(task -> task.withBoard(board))
                .forEach(taskRepo::save);
        Stream.ofNullable(board.getAssignees())
                .flatMap(Set::stream)
                .map(assignee -> assignee.withBoard(board))
                .forEach(assigneeRepo::save);
        return BoardView.from(board);
    }

    @GetMapping("/{id}")
    public BoardView getBoard(@PathVariable Long id) {
        log.info("getBoard");
        Optional<Board> board = boardRepo
                .findByIdWithDetails(id);
        BoardView bv = board
                .map(BoardView::from)
                .orElse(null);
        log.info("getBoard: {}", bv);
        return bv;
    }

    @Transactional
    @PutMapping("/{id}")
    public BoardView updateBoard(@PathVariable Long id, @RequestBody Board board) {
        log.info("updateBoard");
        board.setId(id);
        boardRepo.save(board);
        Stream.ofNullable(board.getTasks())
                .flatMap(Set::stream)
                .map(task -> task.withBoard(board))
                .forEach(taskRepo::save);
        Stream.ofNullable(board.getAssignees())
                .flatMap(Set::stream)
                .map(assignee -> assignee.withBoard(board))
                .forEach(assigneeRepo::save);
        return BoardView.from(board);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        log.info("deleteBoard");
        boardRepo.deleteById(id);
    }
}
