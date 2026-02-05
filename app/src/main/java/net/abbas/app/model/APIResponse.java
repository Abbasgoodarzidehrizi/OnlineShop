package net.abbas.app.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.abbas.app.model.enums.APIStatus;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse <T>{
    private String message = "";
    private APIStatus status;
    private T data;
}
