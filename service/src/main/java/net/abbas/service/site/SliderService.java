package net.abbas.service.site;

import net.abbas.common.exceptions.NotFoundException;
import net.abbas.common.exceptions.ValidationExceptions;
import net.abbas.dataaccess.entity.file.File;
import net.abbas.dataaccess.entity.site.Slider;
import net.abbas.dataaccess.repository.site.SliderRepository;
import net.abbas.dto.file.FileDto;
import net.abbas.dto.site.SliderDto;
import net.abbas.service.base.CRUDService;
import net.abbas.service.base.HasValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SliderService implements CRUDService<SliderDto>
        , HasValidation<SliderDto> {
    private final SliderRepository sliderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SliderService(SliderRepository sliderRepository, ModelMapper modelMapper) {
        this.sliderRepository = sliderRepository;
        this.modelMapper = modelMapper;
    }


    public List<SliderDto> findAll() {
        return sliderRepository.findAllByEnabledIsTrueOrderByOrderNumberAsc().stream()
                .map(x->modelMapper.map(x,SliderDto.class)).toList();
    }

    @Override
    public SliderDto create(SliderDto sliderDto) {
        checkValidations(sliderDto);
        Slider slider = modelMapper.map(sliderDto, Slider.class);
        slider.setEnabled(true);

        Integer lastOrder = sliderRepository.firstLastOrderNumber();
        if (lastOrder == null) {
            lastOrder = 0;
        }
        slider.setOrderNumber(++lastOrder);
        return modelMapper.map(sliderRepository.save(slider), SliderDto.class);
    }

    @Override
    public Boolean delete(Long id) {
        sliderRepository.deleteById(id);
        return true;
    }

    public List<SliderDto> readAll() {
        return sliderRepository.findAllByEnabledIsTrueOrderByOrderNumberAsc()
                .stream().map(x -> modelMapper.map(x, SliderDto.class)).toList();
    }

    @Override
    public Page<SliderDto> readAll(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return sliderRepository.findAll(Pageable.ofSize(size).withPage(page))
                .map(x -> modelMapper.map(x, SliderDto.class));
    }

    @Override
    public SliderDto update(SliderDto dto) {
        checkValidations(dto);
        Slider oldData = sliderRepository.findById(dto.getId()).orElseThrow(NotFoundException::new);
        oldData.setOrderNumber(Optional.ofNullable(dto.getOrderNumber()).orElse(oldData.getOrderNumber()));
        oldData.setLink(Optional.ofNullable(dto.getLink()).orElse(oldData.getLink()));
        oldData.setTitle(Optional.ofNullable(dto.getTitle()).orElse(oldData.getTitle()));
        if (dto.getImage() != null) {
            oldData.setImage(Optional.ofNullable(modelMapper.map(dto.getImage(), File.class)).orElse(oldData.getImage()));
        }
        sliderRepository.save(oldData);
        return modelMapper.map(oldData, SliderDto.class);
    }

    public Boolean swapUp(Long id){
        Slider slider = sliderRepository.findById(id).orElseThrow(NotFoundException::new);
        Optional<Slider> last = sliderRepository.findFirstByOrderNumberLessThanOrderByOrderNumberDesc(slider.getOrderNumber());
        if (last.isPresent()) {
            Integer lastOrder = slider.getOrderNumber();
            slider.setOrderNumber(last.get().getOrderNumber());
            last.get().setOrderNumber(lastOrder);
            sliderRepository.save(last.get());
            sliderRepository.save(slider);
            return true;
        }
        return false;
    }

    public Boolean swapDown(Long id){
        Slider slider = sliderRepository.findById(id).orElseThrow(NotFoundException::new);
        Optional<Slider> last = sliderRepository.findFirstByOrderNumberGreaterThanOrderByOrderNumberAsc(slider.getOrderNumber());
        if (last.isPresent()) {
            Integer lastOrder = slider.getOrderNumber();
            slider.setOrderNumber(last.get().getOrderNumber());
            last.get().setOrderNumber(lastOrder);
            sliderRepository.save(last.get());
            sliderRepository.save(slider);
            return true;
        }
        return false;
    }


    @Override
    public void checkValidations(SliderDto sliderDto) {
        if (sliderDto == null) {
            throw new ValidationExceptions("sliderDto is null");
        }
        if (sliderDto.getLink() == null) {
            throw new ValidationExceptions("link is null");
        }
        if (sliderDto.getTitle() == null) {
            throw new ValidationExceptions("Title is null");
        }
    }
}
