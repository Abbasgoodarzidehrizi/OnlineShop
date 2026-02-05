package net.abbas.service.site;

import net.abbas.common.exceptions.NotFoundException;
import net.abbas.common.exceptions.ValidationExceptions;
import net.abbas.dataaccess.entity.site.Nav;
import net.abbas.dataaccess.repository.site.NavRepository;
import net.abbas.dto.site.NavDto;
import net.abbas.service.base.CRUDService;
import net.abbas.service.base.HasValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NavService implements CRUDService<NavDto> , HasValidation<NavDto> {
    private final NavRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public NavService(NavRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public List<NavDto> readAll()
    {
        return repository.findAllByEnabledIsTrueOrderByOrderNumberAsc()
                .stream().map(x->mapper.map(x,NavDto.class)).toList();
    }
    @Override
    public NavDto create(NavDto nav) {
        checkValidations(nav);
        Nav blog = mapper.map(nav, Nav.class);
        repository.save(blog);
        return mapper.map(blog, NavDto.class);
    }
    @Override
    public Page<NavDto> readAll(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return repository.findAll(Pageable.ofSize(size).withPage(page))
                .map(x->mapper.map(x,NavDto.class));
    }

    @Override
    public NavDto update(NavDto dto) {
        checkValidations(dto);
        if (dto.getId()==null || dto.getId()<=0) {
            throw new ValidationExceptions("blog is required");
        }
        Nav nav = repository.findById(dto.getId()).orElseThrow(NotFoundException::new);
        nav.setTitle(Optional.ofNullable(dto.getTitle()).orElse(nav.getTitle()));
        nav.setLink(Optional.ofNullable(dto.getLink()).orElse(nav.getLink()));
        nav.setOrderNumber(Optional.ofNullable(dto.getOrderNumber()).orElse(nav.getOrderNumber()));
        repository.save(nav);
        return mapper.map(nav,NavDto.class);
    }


    @Override
    public void checkValidations(NavDto dto) {
        if (dto == null) {
            throw new ValidationExceptions("nav is required");
        }
        if (dto.getTitle()==null || dto.getTitle().isEmpty()) {
            throw new ValidationExceptions("value Title is required");
        }

        if (dto.getLink()==null || dto.getLink().isEmpty()) {
            throw new ValidationExceptions("value Link is required");
        }
    }

    @Override
    public Boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }

    @Transactional
    public Boolean swapUp(Long id){
        Nav nav = repository.findById(id).orElseThrow(NotFoundException::new);
        Optional<Nav> orderDown = repository.findFirstByOrderNumberLessThanOrderByOrderNumberDesc(nav.getOrderNumber());
        if (orderDown.isPresent()) {
            Integer orderNumber = nav.getOrderNumber();
            nav.setOrderNumber(orderDown.get().getOrderNumber());
            orderDown.get().setOrderNumber(orderNumber);
            repository.save(orderDown.get());
            repository.save(nav);
            return true;
        }
        return false;
    }


    @Transactional
    public Boolean swapDown(Long id){
        Nav nav = repository.findById(id).orElseThrow(NotFoundException::new);
        Optional<Nav> orderDown = repository.findFirstByOrderNumberGreaterThanOrderByOrderNumberAsc(nav.getOrderNumber());
        if (orderDown.isPresent()) {
            Integer orderNumber = nav.getOrderNumber();
            nav.setOrderNumber(orderDown.get().getOrderNumber());
            orderDown.get().setOrderNumber(orderNumber);
            repository.save(orderDown.get());
            repository.save(nav);
            return true;
        }
        return false;
    }
}
