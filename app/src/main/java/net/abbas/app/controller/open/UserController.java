package net.abbas.app.controller.open;

import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.dto.user.LimitedUserDto;
import net.abbas.dto.user.LoginDto;
import net.abbas.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("login")
    public APIResponse<LimitedUserDto> login(@RequestBody LoginDto dto) {
        try {
            return APIResponse.<LimitedUserDto>builder()
                    .data(userService.login(dto))
                    .status(APIStatus.Success)
                    .message("OK")
                    .build();
        }catch (Exception e) {
            return APIResponse.<LimitedUserDto>builder()
                    .data(null)
                    .status(APIStatus.Error)
                    .message(e.getMessage())
                    .build();
        }
    }
}
