package com.HP50.be.domain.memoryGraph.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NodesDto {
    private String id;
    private String label;
}
