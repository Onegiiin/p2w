package by.kapinskiy.p2w.services;

import by.kapinskiy.p2w.models.Offer;
import by.kapinskiy.p2w.models.Order;
import by.kapinskiy.p2w.models.OrderStatus;
import by.kapinskiy.p2w.models.User;
import by.kapinskiy.p2w.repositories.OffersRepository;
import by.kapinskiy.p2w.repositories.OrdersRepository;
import by.kapinskiy.p2w.util.exceptions.MoneyLackException;
import by.kapinskiy.p2w.util.exceptions.NotChangedException;
import by.kapinskiy.p2w.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@PreAuthorize("hasRole('USER')")
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final UsersService usersService;
    private final OffersRepository offersRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, UsersService usersService, OffersRepository offersRepository) {
        this.ordersRepository = ordersRepository;
        this.usersService = usersService;
        this.offersRepository = offersRepository;
    }

    public Order createOrder(int offerId, int quantity) {
        User user = usersService.getCurrentUser();
        Offer offer = offersRepository.findById(offerId).orElseThrow(() -> new NotFoundException("offer not found"));
        long price = offer.getPrice() * quantity;
        if (user.getBalance() < price){
            throw new MoneyLackException("You don't have enough money");
        }

        Order order = new Order();

        order.setOffer(offer);
        offer.getOrders().add(order);

        order.setConsumer(user);
        user.getOrders().add(order);

        order.setQuantity(quantity);
        order.setStatus(OrderStatus.PROCESSING);
        order.setTotal(price);

        user.setBalance(user.getBalance() - price);

        return ordersRepository.save(order);
    }

    public void completeOrder(int orderId) {
        User user = usersService.getCurrentUser();
        Order order = ordersRepository.findById(orderId).orElseThrow(() -> new NotFoundException("order not found"));

        if(order.getStatus() != OrderStatus.PROCESSING) {
            throw new NotChangedException("order cannot be completed");
        }

        if (order.getConsumer().getId() != user.getId()) {
            throw new AccessDeniedException("You don't have permission to complete this order");
        }

        User executor = order.getOffer().getExecutor();
        executor.setBalance(executor.getBalance() + order.getTotal());
        order.setStatus(OrderStatus.COMPLETED);
    }

    public void cancelOrder(int orderId) {
        User user = usersService.getCurrentUser();
        Order order = ordersRepository.findById(orderId).orElseThrow(() -> new NotFoundException("order not found"));
        if (order.getConsumer().getId() != user.getId()) {
            throw new AccessDeniedException("You don't have permission to cancel this order");
        }

        if (order.getStatus() != OrderStatus.PROCESSING) {
            throw new NotChangedException("Order cannot be cancelled");
        }

        user.setBalance(user.getBalance() + order.getTotal());
        order.setStatus(OrderStatus.CANCELLED);
    }

    public void updateOrder(int orderId, OrderStatus newStatus){
        if (newStatus.equals(OrderStatus.CANCELLED)){
            cancelOrder(orderId);
        } else if (newStatus == OrderStatus.COMPLETED){
            completeOrder(orderId);
        }
    }
}
