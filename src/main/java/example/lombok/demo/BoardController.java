package example.lombok.demo;

import example.lombok.demo.dtos.BoardView;
import example.lombok.demo.models.Board;
import example.lombok.demo.repositories.AssigneeRepo;
import example.lombok.demo.repositories.BoardRepo;
import example.lombok.demo.repositories.TaskRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@RestController
@RequestMapping("/boards")
public class BoardController {
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

    private final BoardRepo boardRepo;
    private final AssigneeRepo assigneeRepo;
    private final TaskRepo taskRepo;

    public BoardController(BoardRepo boardRepo, AssigneeRepo assigneeRepo, TaskRepo taskRepo) {
        this.boardRepo = boardRepo;
        this.assigneeRepo = assigneeRepo;
        this.taskRepo = taskRepo;
    }

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
