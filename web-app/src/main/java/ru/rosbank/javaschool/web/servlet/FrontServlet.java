package ru.rosbank.javaschool.web.servlet;

import ru.rosbank.javaschool.util.SQLTemplate;
import ru.rosbank.javaschool.web.constant.Constants;
import ru.rosbank.javaschool.web.model.*;
import ru.rosbank.javaschool.web.repository.*;
import ru.rosbank.javaschool.web.service.BurgerAdminService;
import ru.rosbank.javaschool.web.service.BurgerUserService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

import static ru.rosbank.javaschool.web.constant.Constants.*;


public class FrontServlet extends HttpServlet {
    private BurgerUserService burgerUserService;
    private BurgerAdminService burgerAdminService;

    @Override
    public void init() throws ServletException {
        log("Init");

        try {
            // TODO: неплохо бы: чтобы это было автоматически
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/db");
            SQLTemplate sqlTemplate = new SQLTemplate();
            ProductRepository productRepository = new ProductRepositoryJdbcImpl(dataSource, sqlTemplate);
            OrderRepository orderRepository = new OrderRepositoryJdbcImpl(dataSource, sqlTemplate);
            OrderPositionRepository orderPositionRepository = new OrderPositionRepositoryJdbcImpl(dataSource, sqlTemplate);
            burgerUserService = new BurgerUserService(productRepository, orderRepository, orderPositionRepository);
            burgerAdminService = new BurgerAdminService(productRepository, orderRepository, orderPositionRepository);

            if (productRepository.getAll().size() == 0) {
                insertInitialData(productRepository);
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    private void insertInitialData(ProductRepository productRepository) {
        productRepository.save(new BurgerModel(0, "Hamburger", 48, 1, "https://mcdonalds.ru/upload/iblock/121/Gamburger.png", "Beef", "Chopped beef steak made from natural whole beef on a caramelized bun, seasoned with mustard, ketchup, onion and a piece of pickled cucumber."));
        productRepository.save(new BurgerModel(0, "Chickenburger", 50, 2, "https://mcdonalds.ru/upload/iblock/c05/CHikenburger.png", "Chicken", "Fried chicken cutlet, breaded in breadcrumbs, on a caramelized bun, seasoned with fresh salad and special Ros Chicken ® sauce."));
        productRepository.save(new DrinkModel(0, "Coca-Cola", 65, 1, "https://alibaba-online.ru/wp-content/uploads/T2eq0RXchbXXXXXXXX_699261563-457x457.jpg", "Small", "Soft carbonated drink."));
        productRepository.save(new DrinkModel(0, "Fanta", 65, 2, "https://images.ctfassets.net/a9odgsv44wmq/67MMKsdKPSeO6AoeuOq20i/cddb326caefab89da565e734ff015dd2/17_FantaOrange.png", "Medium", "Soft carbonated drink."));
        productRepository.save(new PotatoModel(0, "French Fries", 59, 1, "https://sushi-live.ru/wp-content/uploads/2019/08/%D0%BA%D0%B0%D1%80%D1%82-%D1%84%D1%80%D0%B8-768x768.jpg", "Standart", "Tasty, deep-fried and lightly salted potato sticks."));
        productRepository.save(new PotatoModel(0, "Rustic Potatoes", 77, 2, "https://imageup.ru/img196/3512605/selopotato.jpg", "Standart", "Tasty, deep fried vegetable slices of potato with spices."));
        productRepository.save(new StarterModel(0, "Chicken RosNuggets", 99, 1, "https://mcdonalds.ru/upload/iblock/edd/newnuggets.png", 9, "Deep-fried chicken, breadcrumbs"));
        productRepository.save(new StarterModel(0, "Spicy Chicken RosNuggets", 77, 2, "https://imageup.ru/img196/3512610/chickenrosnuggets.jpg", 6, "Appetizing Spicy Chicken RosNuggets of chopped chicken, deep-fried and seasoned with burning spices."));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String rootUrl = req.getContextPath().isEmpty() ? "/" : req.getContextPath();
        String url = req.getRequestURI().substring(req.getContextPath().length());

        if (url.startsWith("/admin")) {
            if (url.equals("/admin")) {

                if (req.getMethod().equals("GET")) {
                    req.setAttribute(Constants.ITEMS, burgerAdminService.getAll());
                    req.getRequestDispatcher("/WEB-INF/admin/frontpage.jsp").forward(req, resp);
                    return;
                }

                if (req.getMethod().equals("POST")) {

                    int id = Integer.parseInt(req.getParameter("id"));
                    String name = req.getParameter("name");
                    int price = Integer.parseInt(req.getParameter("price"));
                    int quantity = Integer.parseInt(req.getParameter("quantity"));
                    String imageUrl = req.getParameter("image-url");
                    String about = req.getParameter("about");

                    burgerAdminService.save(new ProductModel(id, name, price, quantity, imageUrl, about));
                    resp.sendRedirect(url);
                    return;
                }
            }

            if (url.startsWith("/admin/edit")) {
                if (req.getMethod().equals("GET")) {

                    int id = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute(Constants.ITEM, burgerAdminService.getById(id));
                    req.setAttribute(Constants.ITEMS, burgerAdminService.getAll());
                    req.getRequestDispatcher("/WEB-INF/admin/frontpage.jsp").forward(req, resp);
                    return;
                }
            }

            return;
        }

        if (url.equals("/")) {

            if (req.getMethod().equals("GET")) {
                HttpSession session = req.getSession();
                if (session.isNew()) {
                    int orderId = burgerUserService.createOrder();
                    session.setAttribute(ORDER_ID, orderId);
                }

                int orderId = (Integer) session.getAttribute(ORDER_ID);
                int totalQuantity = burgerUserService.getQuantityOfProductsInBasket(orderId);
                int totalAmount = burgerUserService.getTotalAmountOfBasket(orderId);
                req.setAttribute(TOTAL_QUANTITY, totalQuantity);
                req.setAttribute(TOTAL_AMOUNT, totalAmount);
                req.setAttribute(Constants.ORDERED_ITEMS, burgerUserService.getAllOrderPosition(orderId));
                req.setAttribute(Constants.ITEMS, burgerUserService.getAll());
                req.getRequestDispatcher("/WEB-INF/frontpage.jsp").forward(req, resp);
                return;
            }

            if (req.getMethod().equals("POST")) {
                HttpSession session = req.getSession();
                if (session.isNew()) {
                    int orderId = burgerUserService.createOrder();
                    session.setAttribute(ORDER_ID, orderId);
                }

                int orderId = (Integer) session.getAttribute(ORDER_ID);
                int id = Integer.parseInt(req.getParameter("id"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));

                burgerUserService.order(orderId, id, quantity);
                burgerUserService.productsForBasket(orderId);
                resp.sendRedirect(url);
                return;
            }

        }


        if (url.startsWith("/product")) {

            if (url.startsWith("/product/item")) {
                if (req.getMethod().equals("GET")) {

                    int id = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute(Constants.ITEM, burgerUserService.getById(id));
                    req.setAttribute("dto-item", burgerUserService.getById(id));
                    req.getRequestDispatcher("/WEB-INF/productDetails/frontpage.jsp").forward(req, resp);
                    return;
                }
            }

            return;
        }

        if (url.startsWith("/basket")) {


            if (url.equals("/basket")) {
                if (req.getMethod().equals("GET")) {
                    HttpSession session = req.getSession();
                    if (session.isNew()) {
                        int orderId = burgerUserService.createOrder();
                        session.setAttribute(ORDER_ID, orderId);
                    }

                    int orderId = (Integer) session.getAttribute(ORDER_ID);
                    burgerUserService.productsForBasket(orderId);
                    int totalQuantity = burgerUserService.getQuantityOfProductsInBasket(orderId);
                    int totalAmount = burgerUserService.getTotalAmountOfBasket(orderId);
                    req.setAttribute(TOTAL_QUANTITY, totalQuantity);
                    req.setAttribute(TOTAL_AMOUNT, totalAmount);
                    req.setAttribute(Constants.ORDERED_ITEMS, burgerUserService.getAllOrderPosition(orderId));
                    req.setAttribute(Constants.ITEMS, burgerUserService.getPartOfAllForBasket(orderId));
                    req.getRequestDispatcher("/WEB-INF/basket/frontpage.jsp").forward(req, resp);
                    return;

                }

                if (req.getMethod().equals("POST")) {
                    HttpSession session = req.getSession();
                    if (session.isNew()) {
                        int orderId = burgerUserService.createOrder();
                        session.setAttribute(ORDER_ID, orderId);
                    }


                    int id = Integer.parseInt(req.getParameter("order-position-id"));
                    int orderId = (Integer) session.getAttribute(ORDER_ID);
                    int productId = Integer.parseInt(req.getParameter("product-id"));
                    String productName = req.getParameter("product-name");
                    int productPrice = Integer.parseInt(req.getParameter("product-price"));
                    int productQuantity = Integer.parseInt(req.getParameter("quantity"));
                    OrderPositionModel model = new OrderPositionModel(
                            id,
                            orderId,
                            productId,
                            productName,
                            productPrice,
                            productQuantity
                    );

                    System.out.println("model = " + model);
                    if (productQuantity == 0) {
                        burgerUserService.removeOrderPosition(model);
                    }

                    if (productQuantity > 0) {
                        burgerUserService.changeOrderPosition(model);
                    }

                    burgerUserService.productsForBasket(orderId);
                    resp.sendRedirect(url);
                    return;
                }


            }

            if (url.startsWith("/basket/delete")) {
                if (req.getMethod().equals("GET")) {

                    HttpSession session = req.getSession();
                    if (session.isNew()) {
                        int orderId = burgerUserService.createOrder();
                        session.setAttribute(ORDER_ID, orderId);
                    }

                    int id = Integer.parseInt(req.getParameter("id"));
                    burgerUserService.removeOrderPositionById(id);
                    resp.sendRedirect("/basket");
                    return;
                }

            }
        }
    }


        @Override
        public void destroy () {
            log("destroy");
        }
    }
