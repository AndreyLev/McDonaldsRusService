package ru.rosbank.javaschool.web.integration;

import org.junit.jupiter.api.Test;
import ru.rosbank.javaschool.util.SQLTemplate;
import ru.rosbank.javaschool.web.repository.*;
import ru.rosbank.javaschool.web.service.BurgerAdminService;
import ru.rosbank.javaschool.web.service.BurgerUserService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class BurgerUserServiceTest {

    @Test
    void productsForBasket() throws NamingException {

         BurgerUserService burgerUserService;
        InitialContext initialContext = new InitialContext();
        DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/db");
        SQLTemplate sqlTemplate = new SQLTemplate();
        ProductRepository productRepository = new ProductRepositoryJdbcImpl(dataSource, sqlTemplate);
        OrderRepository orderRepository = new OrderRepositoryJdbcImpl(dataSource, sqlTemplate);
        OrderPositionRepository orderPositionRepository = new OrderPositionRepositoryJdbcImpl(dataSource, sqlTemplate);
        burgerUserService = new BurgerUserService(productRepository, orderRepository, orderPositionRepository);


    }
}