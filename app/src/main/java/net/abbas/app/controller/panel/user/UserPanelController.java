package net.abbas.app.controller.panel.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.abbas.app.annotation.CheckPermission;
import net.abbas.app.filter.JwtFilter;
import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.dto.user.UserDto;
import net.abbas.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/panel/user")
public class UserPanelController {
    private final UserService userService;

    @Autowired
    public UserPanelController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("{id}")
    @CheckPermission("info_user")
    public APIResponse<UserDto> getById(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDto user = (UserDto) request.getAttribute(JwtFilter.CURRENT_USER);
        if (!id.equals(user.getId())) {
            return APIResponse.<UserDto>builder()
                    .status(APIStatus.Error)
                    .data(null)
                    .message("اجازه ندارید")
                    .build();
        }
        return APIResponse.<UserDto>builder()
                .status(APIStatus.Success)
                .data(userService.getById(id))
                .build();
    }
    }
