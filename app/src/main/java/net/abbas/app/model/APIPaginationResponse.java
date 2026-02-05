package net.abbas.app.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class APIPaginationResponse <T> extends APIResponse<T> {
    private Long totalCount =0L;
    private Integer totalPage = 0;
}
