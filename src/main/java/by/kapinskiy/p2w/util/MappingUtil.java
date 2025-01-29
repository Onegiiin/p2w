package by.kapinskiy.p2w.util;

import by.kapinskiy.p2w.DTO.*;
import by.kapinskiy.p2w.models.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MappingUtil {
    private final ModelMapper modelMapper;

    @Autowired
    public MappingUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoryDTO categoryToDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public Category createCategoryDTOToCategory(CreateCategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        return category;
    }

    public List<CategoryDTO> categoriesListToDTO(List<Category> categories) {
        return categories.stream().map(this::categoryToDTO).collect(Collectors.toList());
    }

    public LotDTO lotToDTO(Lot lot) {
        return modelMapper.map(lot, LotDTO.class);
    }

    public Lot createLotDTOToLot(CreateLotDTO createLotDTO) {
        Lot lot = new Lot();
        lot.setName(createLotDTO.getName());
        return lot;
    }

    public List<LotDTO> lotsListToDTO(List<Lot> lots) {
        return lots.stream().map(l -> modelMapper.map(l, LotDTO.class)).collect(Collectors.toList());
    }

    public OfferDTO offerToDto(Offer offer) {
        return modelMapper.map(offer, OfferDTO.class);
    }

    public Offer createOfferDTOToOffer(CreateOfferDTO createOfferDTO) {
        Offer offer = new Offer();
        offer.setDescription(createOfferDTO.getDescription());
        offer.setQuantity(createOfferDTO.getQuantity());
        offer.setPrice(createOfferDTO.getPrice());
        return offer;
    }

    public UserDTO userToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public List<OfferDTO> offersListToDTO(List<Offer> offers) {
        return offers.stream().map(o -> modelMapper.map(o, OfferDTO.class)).collect(Collectors.toList());
    }

    public OrderDTO orderToDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

    public List<OrderDTO> ordersListToDTO(List<Order> orders) {
        return orders.stream().map(o -> modelMapper.map(o, OrderDTO.class)).collect(Collectors.toList());
    }
}
