package com.HP50.be.domain.memoryGraph.repository;

import com.HP50.be.domain.memoryGraph.dto.MemoRequestDto;
import com.HP50.be.domain.memoryGraph.dto.MemoResponseDto;

public interface MemoCustomRepository {
    void onlyMemoSave(Integer memberId, String memoId);
    void setMemoToMemoRelationShip(Integer memberId, String firstMemoId, String secondMemoId);
    String deleteMemo(String memoId);
    String updateMemo(MemoRequestDto memoRequestDto);
}
