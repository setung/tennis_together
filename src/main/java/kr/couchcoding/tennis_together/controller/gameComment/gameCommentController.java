package kr.couchcoding.tennis_together.controller.gameComment;


import kr.couchcoding.tennis_together.controller.gameComment.dto.GCRequestDTO;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.service.GameCommentService;
import kr.couchcoding.tennis_together.domain.game.service.GameService;
import kr.couchcoding.tennis_together.domain.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class gameCommentController {

    @Autowired
    GameCommentService gameCommentService;
    @Autowired
    GameService gameService;




    //게임 댓글 등록
    @PostMapping("/{gameNo}/comments")
    public void createComment(@PathVariable(value = "gameNo") Long gameNo,
                                     @RequestBody GCRequestDTO gcRequestDTO,
                                     Authentication authentication) {

        // 어떤사용자가 어떤게임에 어떤 댓글을 남겼는지
        User user = ((User)authentication.getPrincipal());
        Game game = gameService.findGameByGameNo(gameNo);
        gameCommentService.createComment(user,game,gcRequestDTO);

    }

}
