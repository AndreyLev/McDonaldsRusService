package ru.rosbank.javaschool.web.service;

import lombok.RequiredArgsConstructor;
import ru.rosbank.javaschool.web.dto.ProductDetailsDto;
import ru.rosbank.javaschool.web.exception.NotFoundException;
import ru.rosbank.javaschool.web.model.OrderModel;
import ru.rosbank.javaschool.web.model.OrderPositionModel;
import ru.rosbank.javaschool.web.model.ProductModel;
import ru.rosbank.javaschool.web.repository.OrderPositionRepository;
import ru.rosbank.javaschool.web.repository.OrderRepository;
import ru.rosbank.javaschool.web.repository.ProductRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BurgerUserService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderPositionRepository orderPositionRepository;

    public List<ProductModel> getAll() {
        return productRepository.getAll();
    }

    public ProductModel getById(int id) {
        return productRepository.getById(id).orElseThrow(NotFoundException::new);
    }

    public ProductDetailsDto getByProductId(int productId) {
        for (ProductModel model : productRepository.getAll()) {
            if (model.getId() == productId) {
                System.out.println(model);
                System.out.println(ProductDetailsDto.from(model));
                return ProductDetailsDto.from(model);
            }
        }
        return null;
    }

    public List<ProductModel> getPartOfAllForBasket(int orderId) {
        List<OrderPositionModel> list = getAllOrderPosition(orderId);
        List<ProductModel> desiredList = new LinkedList<>();

        for (OrderPositionModel orderPositionModel : list) {

            for (ProductModel productModel : productRepository.getAll()) {
                if (orderPositionModel.getProductId() == productModel.getId())
                {
                    desiredList.add(productModel);
                }
            }
        }
        return desiredList;
    }

    public int createOrder() {
        OrderModel model = new OrderModel(0);
        orderRepository.save(model);
        return model.getId();
    }

    public List<ProductDetailsDto> getProductDetailsDto() {
        return productRepository.getAll().stream()
                .filter(o -> o.getId() > 0)
                .map(ProductDetailsDto::from)
                .collect(Collectors.toList());
    }

    public void order(int orderId, int id, int quantity) {
        ProductModel productModel = productRepository.getById(id).orElseThrow(NotFoundException::new);
        OrderPositionModel orderPositionModel = new OrderPositionModel(
                0,
                orderId,
                productModel.getId(),
                productModel.getName(),
                productModel.getPrice(),
                quantity
        );
        orderPositionRepository.save(orderPositionModel);
    }

    public void changeOrderPosition(OrderPositionModel model) {
        orderPositionRepository.save(model);
    }

    public void removeOrderPosition(OrderPositionModel model) {
        orderPositionRepository.removeById(model.getId());
    }

    public void removeOrderPositionById(int id) {
        orderPositionRepository.removeById(id);
    }

    public OrderPositionModel getOrderPositionModelById(int id) {
        return  orderPositionRepository.getById(id).orElseThrow(NotFoundException::new);
    }

    public List<OrderPositionModel> getAllOrderPosition(int orderId) {
        return orderPositionRepository.getAllByOrderId(orderId);
    }

    public List<OrderPositionModel> productsForBasket(int orderId) {
        List<OrderPositionModel> desiredList = new LinkedList<>();
        List<Integer> list = new ArrayList<>();
        OrderPositionModel tempOrder = null;

        for (OrderPositionModel orderPositionModel : orderPositionRepository.getAllByOrderId(orderId)) {
            list.add(orderPositionModel.getProductId());
        }

        List<Integer> productIdWithoutDublicates = list.stream()
                .distinct()
                .collect(Collectors.toList());


        int count = 0;
        for (Integer productId : productIdWithoutDublicates) {
        count = 0;
            for (OrderPositionModel orderPosition : orderPositionRepository.getAllByOrderId(orderId)) {
                if (productId == orderPosition.getProductId()) {

                    if (count > 0) {
                        tempOrder.setProductQuantity(tempOrder.getProductQuantity() + orderPosition.getProductQuantity());
                        orderPositionRepository.removeById(orderPosition.getId());
                    }

                    if (count == 0) {
                        count++;
                        tempOrder = orderPosition;
                    }

                }
                desiredList.add(tempOrder);
                orderPositionRepository.save(tempOrder);
            }
        }


            return desiredList;
    }

    public int getQuantityOfProductsInBasket(int orderId) {
        int quantity = 0;

        for (OrderPositionModel model : getAllOrderPosition(orderId)) {
            quantity += model.getProductQuantity();
        }

        return quantity;
    }

    public int getTotalAmountOfBasket(int orderId) {
        int totalAmount = 0;

        for (OrderPositionModel model : getAllOrderPosition(orderId)) {
            totalAmount += model.getProductQuantity() * model.getProductPrice();
        }

        return totalAmount;
    }
}
