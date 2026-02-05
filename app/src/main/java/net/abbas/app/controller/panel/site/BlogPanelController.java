package net.abbas.app.controller.panel.site;

import net.abbas.app.annotation.CheckPermission;
import net.abbas.app.controller.base.CRUDController;
import net.abbas.app.model.APIPaginationResponse;
import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.dto.site.BlogDto;
import net.abbas.service.site.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/panel/blog")
public class BlogPanelController implements CRUDController<BlogDto> {
    private final BlogService blog;

    @Autowired
    public BlogPanelController(BlogService blog) {
        this.blog = blog;
    }

    @CheckPermission("blog_add")
    @Override
    public APIResponse<BlogDto> create(BlogDto dto) {
        try {
            return APIResponse.<BlogDto>builder()
                    .status(APIStatus.Success)
                    .message("OK")
                    .data(blog.create(dto))
                    .build();
        } catch (Exception e) {
            return APIResponse.<BlogDto>builder()
                    .status(APIStatus.Error)
                    .message(e.getMessage())
                    .build();
        }
    }

    @CheckPermission("blog_delete")
    @Override
    public APIResponse<Boolean> delete(long id) {
        return APIResponse.<Boolean>builder()
                .message("Ok")
                .data(blog.delete(id))
                .status(APIStatus.Success)
                .build();
    }

    @CheckPermission("blog_list")
    @Override
    public APIPaginationResponse<List<BlogDto>> getAll(Integer page, Integer size) {
        Page<BlogDto> BlogDto = blog.readAll(page, size);
        return APIPaginationResponse.<List<BlogDto>>builder()
                .status(APIStatus.Success)
                .message("OK")
                .data(BlogDto.getContent())
                .totalCount(BlogDto.getTotalElements())
                .totalPage(BlogDto.getTotalPages())
                .build();
    }

    @CheckPermission("blog_edit")
    @Override
    public APIResponse<BlogDto> update(BlogDto dto) {
        try {
            return APIResponse.<BlogDto>builder()
                    .message("Ok")
                    .data(blog.update(dto))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<BlogDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();

        }
    }
}
