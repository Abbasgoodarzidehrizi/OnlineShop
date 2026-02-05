package net.abbas.app.controller.open;

import net.abbas.app.model.APIPaginationResponse;
import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.common.exceptions.NotFoundException;
import net.abbas.dto.site.LimitedBlogDto;
import net.abbas.dto.site.SingleBlogDto;
import net.abbas.service.site.BlogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    private final BlogService service;
    private final ModelMapper mapper;

    @Autowired
    public BlogController(BlogService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    @Cacheable(cacheNames = "apiCache15min", key = "'all_blog_' + #page + '_' + #size")
    public APIResponse<List<LimitedBlogDto>> getAllBlog(
            @RequestParam(required = false) int page
            , @RequestParam(required = false) int size) {

        Page<LimitedBlogDto> getAllBlogs = service.readByPublished(page, size);
        return APIPaginationResponse.<List<LimitedBlogDto>>builder()
                .status(APIStatus.Success)
                .data(getAllBlogs.getContent())
                .totalCount(getAllBlogs.getTotalElements())
                .totalPage(getAllBlogs.getTotalPages())
                .message("OK")
                .build();
    }

    @GetMapping("{id}")
    public APIResponse<SingleBlogDto> getBlogId(@PathVariable Long id) {
        try {
            return APIResponse.<SingleBlogDto>builder()
                    .data(service.readById(id))
                    .message("Ok")
                    .status(APIStatus.Success)
                    .build();
        } catch (NotFoundException e) {
            return APIResponse.<SingleBlogDto>builder()
                    .data(null)
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();
        }
    }

}
