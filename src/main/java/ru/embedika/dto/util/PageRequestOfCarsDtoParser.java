package ru.embedika.dto.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import ru.embedika.dto.PageRequestOfCarsDto;
import ru.embedika.dto.SortOfCarsDto;
import ru.embedika.exception.BusinessException;

import java.text.MessageFormat;

public class PageRequestOfCarsDtoParser {
    private PageRequestOfCarsDto dto;

    public PageRequestOfCarsDtoParser(@NonNull PageRequestOfCarsDto dto) {
        this.dto = dto;
    }

    public PageRequest pars() throws BusinessException {
        PageRequest pageRequest = null;
        if (dto.getPage() < 0) {
            throw new BusinessException(
                    MessageFormat.format(
                            "The parameter \"page\" value must be greater than zero: {0}", dto.getPage()
                    )
            );
        }
        if (dto.getSize() < 0) {
            throw new BusinessException(
                    MessageFormat.format(
                            "The parameter \"size\" value must be greater than one: {0}", dto.getSize()
                    )
            );
        }
        Sort sort = null;
        if (dto.getOrders().length > 0) {
            for (SortOfCarsDto sortOfCarsDto : dto.getOrders()) {
                Sort.Order order = Sort.Order.by(sortOfCarsDto.getProperty());
                order.with(Sort.Direction.fromString(sortOfCarsDto.getDirection()));
                if (sortOfCarsDto.isIgnoreCase()) {
                    order = order.ignoreCase();
                }
                if (sortOfCarsDto.getNullHandling() != null) {
                    order = order.with(Sort.NullHandling.valueOf(sortOfCarsDto.getNullHandling()));
                }
                if (sort == null) {
                    sort = Sort.by(order);
                } else {
                    sort = sort.and(Sort.by(order));
                }
            }
        }
        if (sort == null) {
            return PageRequest.of(dto.getPage(), dto.getSize());
        }
        return PageRequest.of(dto.getPage(), dto.getSize(), sort);
    }
}
