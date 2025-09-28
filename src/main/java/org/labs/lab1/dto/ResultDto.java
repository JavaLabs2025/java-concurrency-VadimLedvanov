package org.labs.lab1.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultDto {
    private int min;
    private int max;
    private int leftSoups;
}
