package net.abbas.app.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import net.abbas.app.annotation.CheckPermission;
import net.abbas.app.filter.JwtFilter;
import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.dto.user.PermissionDto;
import net.abbas.dto.user.UserDto;
import net.abbas.utill.JwtUtill;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class PermissionAspect {
    private final HttpServletRequest request;

    @Autowired
    public PermissionAspect(HttpServletRequest request) {
        this.request = request;
    }

    @SneakyThrows
    @Around("@annotation(checkPermission)")
    public Object checkUserPermission(ProceedingJoinPoint joinPoint, CheckPermission checkPermission) {
        System.out.println("PermissionAspect triggered for: " + checkPermission.value());
        UserDto dto = (UserDto) request.getAttribute(JwtFilter.CURRENT_USER);
        if (dto == null) {
            return APIResponse.builder()
                    .message("Please Login First!")
                    .status(APIStatus.Forbidden)
                    .build();
        }
        List<String> permissionName = dto.getRoles().stream().flatMap(x -> x.getPermissions().stream().map(PermissionDto::getName)).toList();
        if (!permissionName.contains(checkPermission.value())) {
            return APIResponse.builder().status(APIStatus.Forbidden).message("Access Denied").build();
        }
        return joinPoint.proceed();
    }
}
