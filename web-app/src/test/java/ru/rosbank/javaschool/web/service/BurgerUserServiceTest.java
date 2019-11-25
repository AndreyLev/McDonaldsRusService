package ru.rosbank.javaschool.web.service;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.rosbank.javaschool.web.dto.ProductDetailsDto;
import ru.rosbank.javaschool.web.exception.NotFoundException;
import ru.rosbank.javaschool.web.model.ProductModel;
import ru.rosbank.javaschool.web.repository.OrderPositionRepository;
import ru.rosbank.javaschool.web.repository.OrderRepository;
import ru.rosbank.javaschool.web.repository.ProductRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BurgerUserServiceTest {

    @Test
    void getByIdShouldThrowExceptionWhenNoItemsInRepository() {
        val repository = mock(ProductRepository.class);
        val orderRepository = mock(OrderRepository.class);
        val orderPositionRepository = mock(OrderPositionRepository.class);
        doReturn(Optional.empty()).when(repository).getById(1);
        val service = new BurgerUserService(repository, orderRepository, orderPositionRepository);

        assertThrows(NotFoundException.class, () -> service.getById(1));
    }

    @Test
    void getByIdShouldThrowExceptionWhenNoSuchItemsInRepository() {
        val repository = mock(ProductRepository.class);
        val orderRepository = mock(OrderRepository.class);
        val orderPositionRepository = mock(OrderPositionRepository.class);
        doReturn(Optional.empty()).when(repository).getById(anyInt());
        doReturn(Optional.of(new ProductModel())).when(repository).getById(1);
        val service = new BurgerUserService(repository, orderRepository, orderPositionRepository);

        assertThrows(NotFoundException.class, () -> service.getById(2));
    }

    @Test
    void getByIdShouldTReturnDtoWhenItemPresentInRepo() {
        val repository = mock(ProductRepository.class);
        val orderRepository = mock(OrderRepository.class);
        val orderPositionRepository = mock(OrderPositionRepository.class);
        doReturn(Optional.of(new ProductModel())).when(repository).getById(1);
        val service = new BurgerUserService(repository, orderRepository, orderPositionRepository);

        assertNotNull(service.getById(1));
    }


}