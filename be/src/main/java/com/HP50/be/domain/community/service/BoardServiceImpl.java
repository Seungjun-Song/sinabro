package com.HP50.be.domain.community.service;

import com.HP50.be.domain.code.dto.SubCategoryResponseDto;
import com.HP50.be.domain.code.dto.TagDto;
import com.HP50.be.domain.code.entity.SubCategory;
import com.HP50.be.domain.code.service.SubCategoryService;
import com.HP50.be.domain.community.dto.*;
import com.HP50.be.domain.community.entity.Board;
import com.HP50.be.domain.community.repository.BoardCustomRepository;
import com.HP50.be.domain.community.repository.BoardRepository;
import com.HP50.be.domain.member.entity.Member;
import com.HP50.be.domain.member.repository.MemberRepository;
import com.HP50.be.domain.project.entity.Project;
import com.HP50.be.domain.project.repository.ProjectRepository;
import com.HP50.be.domain.project.service.ProjectService;
import com.HP50.be.global.common.BaseResponse;
import com.HP50.be.global.common.StatusCode;
import com.HP50.be.global.exception.BaseException;
import com.HP50.be.global.jwt.JwtUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final SubCategoryService subCategoryService;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final BoardCustomRepository boardCustomRepository;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<BaseResponse<StatusCode>> insertBoard(String token, BoardInsertRequestDto boardInsertRequestDto) {

        Board board = this.transferToBoard(token, boardInsertRequestDto);
        // 기존에 존재하는 엔티티라면 update
        // 존재하지 않았던 엔티티라면 save
        boardRepository.save(board);
        return ResponseEntity.ok().body(new BaseResponse<>(StatusCode.SUCCESS));
    }

    @Override
    public ResponseEntity<BaseResponse<BoardDetailResponseDto>> findBoardDetail(Integer boardId) {
        Board board = this.findById(boardId);

        // 문자열로 넘어온 json 처럼 생긴 데이터를 TagDto로 변환하는 로직
        TagDto[] tagDtoArray = new Gson().fromJson(board.getBoardTag(), TagDto[].class);
        List<TagDto> tagDtoList = List.of(tagDtoArray);

        String toJson = new Gson().toJson(board.getBoardTag());
        BoardDetailResponseDto boardDetailResponseDto = BoardDetailResponseDto.builder()
                .boardId(board.getBoardId())
                .memberName(board.getMember().getMemberName())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .subCategory(board.getSubCategory())
                .communityProgress(board.isCommunityProgress())
                .tagDtos(tagDtoList)
                .commentResponseDtos(board.getComments().stream()
                        .map(entity -> CommentResponseDto.builder()
                                .memberId(entity.getMember().getMemberId())
                                .commentContent(entity.getCommentContent())
                                .memberName(entity.getMember().getMemberName())
                                .memberImg(entity.getMember().getMemberImg())
                                .createdDttm(entity.getCreatedDttm())
                                .build()).toList())
                .createdDttm(board.getCreatedDttm())
                .updatedDttm(board.getUpdatedDttm())
                .build();



        return ResponseEntity.ok().body(new BaseResponse<>(boardDetailResponseDto));
    }

    @Override
    public BoardPaginationResponseDto findByConditions(Integer catBoard,
                                                       Integer calCalender,
                                                       Integer catJob,
                                                       String keyword,
                                                       int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Slice<Board> boards = boardCustomRepository.findByConditions(catBoard, calCalender, catJob, keyword, pageRequest);
        List<BoardListResponseDto> boardListResponseDtos = new ArrayList<>();

        for(Board board: boards){
            BoardListResponseDto boardListResponseDto = BoardListResponseDto.builder()
                    .boardId(board.getBoardId())
                    .memberName(board.getMember().getMemberName())
                    .boardTitle(board.getBoardTitle())
                    .boardContent(board.getBoardContent())
                    .communityProgress(board.isCommunityProgress())
                    .createdDttm(board.getCreatedDttm())
                    .updatedDttm(board.getUpdatedDttm())
                    .build();
            boardListResponseDtos.add(boardListResponseDto);
        }

        BoardPaginationResponseDto boardPaginationResponseDto = BoardPaginationResponseDto.builder()
                .hasNext(boards.hasNext())
                .boardListResponseDto(boardListResponseDtos)
                .build();

        return boardPaginationResponseDto;
    }

    @Override
    public Board findById(Integer boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_BOARD));
    }

    public Board transferToBoard(String token, BoardInsertRequestDto boardInsertRequestDto){
        Integer memberId = jwtUtil.getMemberId(token);
        // board 가 0 이라면 save
        // 0 이 아니라면 update 분기처리
        Integer boardId = boardInsertRequestDto.getBoardId();
        SubCategory subCategory = subCategoryService.findById(boardInsertRequestDto.getSubCategoryId());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_MEMBER));
        Project project = projectRepository.findById(boardInsertRequestDto.getProjectId())
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_PROJECT));

        String toJson = new Gson().toJson(boardInsertRequestDto.getBoardTag());


        // builder 패턴 진행 중에 boardId 를 넣어주고와 넣어주지 않음의 차이
        if(boardId == 0) return Board.builder()
                .member(member)
                .boardTitle(boardInsertRequestDto.getBoardTitle())
                .boardContent(boardInsertRequestDto.getBoardContent())
                .boardTag(toJson)
                .boardImg(boardInsertRequestDto.getBoardImg())
                .communityProgress(true)
                .project(project)
                .subCategory(subCategory)
                .build();

        else return Board.builder()
                .boardId(boardInsertRequestDto.getBoardId()) // 여기서 update 함을 처리
                .member(member)
                .boardTitle(boardInsertRequestDto.getBoardTitle())
                .boardContent(boardInsertRequestDto.getBoardContent())
                .boardTag(toJson)
                .boardImg(boardInsertRequestDto.getBoardImg())
                .communityProgress(true)
                .project(project)
                .subCategory(subCategory)
                .build();

    }

}
