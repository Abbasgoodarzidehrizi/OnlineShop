package net.abbas.service.site;

import net.abbas.common.exceptions.NotFoundException;
import net.abbas.common.exceptions.ValidationExceptions;
import net.abbas.dataaccess.entity.site.Blog;
import net.abbas.dataaccess.enums.BlogStatus;
import net.abbas.dataaccess.repository.site.BlogRepository;
import net.abbas.dto.site.BlogDto;
import net.abbas.dto.site.LimitedBlogDto;
import net.abbas.dto.site.SingleBlogDto;
import net.abbas.service.base.CRUDService;
import net.abbas.service.base.HasValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService implements CRUDService<BlogDto> , HasValidation<BlogDto> {
    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BlogService(BlogRepository blogRepository, ModelMapper modelMapper) {
        this.blogRepository = blogRepository;
        this.modelMapper = modelMapper;
    }

    public Page<LimitedBlogDto> readByPublished(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 16;
        }
        return blogRepository.findAllPublished(Pageable.ofSize(size).withPage(page))
                .map(x -> modelMapper.map(x, LimitedBlogDto.class));
    }

    public SingleBlogDto readById(Long id){
        Blog blog = blogRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(blog, SingleBlogDto.class);
    }


    @Override
    public BlogDto create(BlogDto dto) {
        checkValidations(dto);
        Blog blog = modelMapper.map(dto, Blog.class);
        if (dto.getPublishDate()==null) {
            blog.setPublishDate(LocalDateTime.now());
        }
        if (dto.getStatus()==null) {
            blog.setStatus(BlogStatus.published);
        }
        blog.setVisitCount(0L);
        return modelMapper.map(blogRepository.save(blog), BlogDto.class);
    }

    @Override
    public Boolean delete(Long id) {
        blogRepository.deleteById(id);
        return true;
    }

    @Override
    public void checkValidations(BlogDto blogDto) {

    }

    @Override
    public Page<BlogDto> readAll(Integer page, Integer size) {
       return blogRepository.findAll(Pageable.ofSize(size).withPage(page))
                .map(x->modelMapper.map(x,BlogDto.class));
    }

    @Override
    public BlogDto update(BlogDto blogDto) {
        checkValidations(blogDto);
        if (blogDto.getId()==null || blogDto.getId()<=0) {
            throw new ValidationExceptions("blog is required");
        }
        Blog dto = blogRepository.findById(blogDto.getId()).orElseThrow(NotFoundException::new);
        dto.setDescription(Optional.ofNullable(blogDto.getDescription()).orElse(dto.getDescription()));
        dto.setTitle(Optional.ofNullable(blogDto.getTitle()).orElse(dto.getTitle()));
        dto.setSubTitle(Optional.ofNullable(blogDto.getSubtitle()).orElse(dto.getSubTitle()));
        dto.setStatus(Optional.ofNullable(blogDto.getStatus()).orElse(dto.getStatus()));
        blogRepository.save(dto);
        return modelMapper.map(dto,BlogDto.class);
    }
}
