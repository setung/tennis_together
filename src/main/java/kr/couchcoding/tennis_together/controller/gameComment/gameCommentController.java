package kr.couchcoding.tennis_together.controller.gameComment;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.couchcoding.tennis_together.controller.gameComment.dto.GCRequestDTO;
import kr.couchcoding.tennis_together.controller.gameComment.dto.GCResponseDTO;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.service.GameCommentService;
import kr.couchcoding.tennis_together.domain.game.service.GameService;
import kr.couchcoding.tennis_together.domain.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Game(모집글)에 댓글관련 API"})
@RestController
@RequestMapping("/games/{gameNo}/comments")
public class gameCommentController {

    @Autowired
    GameCommentService gameCommentService;

    @Autowired
    GameService gameService;

    //게임 댓글 등록
    @ApiOperation(value = "게임 댓글 등록 API")
    @PostMapping("")
    public void createComment(@PathVariable(value = "gameNo") Long gameNo,
                                     @RequestBody GCRequestDTO gcRequestDTO,
                                     Authentication authentication) {

        // 어떤사용자가 어떤게임에 어떤 댓글을 남길지
        User user = ((User)authentication.getPrincipal());
        Game game = gameService.findGameByNo(gameNo);
        gameCommentService.createComment(user,game,gcRequestDTO);

    }

    //게임 댓글 전체조회
    @ApiOperation(value = "게임 댓글 조회 API")
    @GetMapping("")
    public Page<GCResponseDTO> getGameCommentList(@PathVariable(value = "gameNo") Long gameNo, Pageable pageable) {
        Game game = gameService.findGameByNo(gameNo);
        return gameCommentService.getGameCommentList(game,pageable).map(GameComment -> new GCResponseDTO(GameComment));
    }

    //게임 댓글 삭제
    @ApiOperation(value = "게임 댓글 삭제 API")
    @DeleteMapping("/{commentNo}")
    public void deleteComment(@PathVariable(value = "gameNo")Long gameNo,
                              @PathVariable(value = "commentNo")Long commentNo,
                              Authentication autentication) {
        // 어떤사용자가 어떤게임에 어떤 댓글을 삭제할지
        User user = ((User)autentication.getPrincipal());
        gameCommentService.deleteComment(user,gameNo,commentNo);
    }

    //게임 댓글 수정
    @ApiOperation(value = "게임 댓글 수정 API")
     @PatchMapping("/{commentNo}")
    public void updateComment(@PathVariable Long gameNo,
                              @PathVariable Long commentNo,
                              @RequestBody GCRequestDTO updatedGameCommentDTO,
                              Authentication autentication) {
        //어떤 사용자가 어떤 게임에 어떤 댓글 수정
        User user = ((User)autentication.getPrincipal());
        gameCommentService.updateGameComment(user, gameNo, commentNo, updatedGameCommentDTO);
    }



    //게임 대댓글 등록
    @ApiOperation(value = "게임 대댓글 등록 API")
    @PostMapping("/{commentNo}")
    public void createReplyComment(@PathVariable(value = "gameNo") Long gameNo,
                                   @PathVariable(value = "commentNo") Long commentNo,
                                   @RequestBody GCRequestDTO gcRequestDTO,
                                   Authentication authentication) {
        // 어떤 사용자가 어떤 게임에 어떤 대댓글을 남길지
        User user = ((User)authentication.getPrincipal());
        Game game = gameService.findGameByNo(gameNo);
        gameCommentService.createReplyComment(user,game,commentNo,gcRequestDTO);
    }



}
