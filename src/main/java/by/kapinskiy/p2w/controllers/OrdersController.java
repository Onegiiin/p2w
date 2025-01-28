package by.kapinskiy.p2w.controllers;

import by.kapinskiy.p2w.DTO.CreateOrderDTO;
import by.kapinskiy.p2w.DTO.OrderDTO;
import by.kapinskiy.p2w.DTO.UpdateOrderDTO;
import by.kapinskiy.p2w.models.Order;
import by.kapinskiy.p2w.services.OrdersService;
import by.kapinskiy.p2w.util.MappingUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService ordersService;
    private final MappingUtil mappingUtil;

    @Autowired
    public OrdersController(MappingUtil mappingUtil, OrdersService ordersService) {
        this.mappingUtil = mappingUtil;
        this.ordersService = ordersService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid CreateOrderDTO createOrderDTO) {
        Order createdOrder = ordersService.createOrder(createOrderDTO.getOfferId(), createOrderDTO.getQuantity());
        return new ResponseEntity<>(mappingUtil.orderToDTO(createdOrder), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable(name = "id") int id,  @RequestBody @Valid UpdateOrderDTO updateOrderDTO) {
        ordersService.updateOrder(id,  updateOrderDTO.getStatus());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
