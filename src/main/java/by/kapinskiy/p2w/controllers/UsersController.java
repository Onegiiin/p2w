package by.kapinskiy.p2w.controllers;


import by.kapinskiy.p2w.DTO.OfferDTO;
import by.kapinskiy.p2w.DTO.OrderDTO;
import by.kapinskiy.p2w.services.OffersService;
import by.kapinskiy.p2w.services.OrdersService;
import by.kapinskiy.p2w.util.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController {
    private final OrdersService ordersService;
    private final OffersService offersService;
    private final MappingUtil mappingUtil;

    @Autowired
    public UsersController(OffersService offersService, OrdersService ordersService, MappingUtil mappingUtil) {
        this.offersService = offersService;
        this.ordersService = ordersService;
        this.mappingUtil = mappingUtil;
    }

    @GetMapping("/{username}/offers")
    public ResponseEntity<List<OfferDTO>> getUserOffers(@PathVariable String username) {
        return new ResponseEntity<>(mappingUtil.offersListToDTO(offersService.getAllOffersByUsername(username)), HttpStatus.OK);
    }

    @GetMapping("/my_orders")
    public ResponseEntity<List<OrderDTO>> getUserOrder() {
        return new ResponseEntity<>(mappingUtil.ordersListToDTO(ordersService.getUserOrders()), HttpStatus.OK);
    }
}
