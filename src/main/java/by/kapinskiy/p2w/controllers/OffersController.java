package by.kapinskiy.p2w.controllers;

import by.kapinskiy.p2w.DTO.CreateOfferDTO;
import by.kapinskiy.p2w.DTO.OfferDTO;
import by.kapinskiy.p2w.models.Offer;
import by.kapinskiy.p2w.services.OffersService;
import by.kapinskiy.p2w.util.MappingUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/offers")
public class OffersController {
    private final OffersService offersService;
    private final MappingUtil mappingUtil;

    @Autowired
    public OffersController(OffersService offersService, MappingUtil mappingUtil) {
        this.offersService = offersService;
        this.mappingUtil = mappingUtil;
    }

    @PostMapping
    public ResponseEntity<OfferDTO> createOffer(@RequestBody @Valid CreateOfferDTO createOfferDTO) {
        Offer createdOffer = offersService.createOffer(mappingUtil.createOfferDTOToOffer(createOfferDTO), createOfferDTO.getLotId());
        return new ResponseEntity<>(mappingUtil.offerToDto(createdOffer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfferDTO> updateOffer(@RequestBody @Valid CreateOfferDTO createOfferDTO, @PathVariable(name = "id") int id) {
        Optional<Offer> createdOffer = offersService.updateOffer(mappingUtil.createOfferDTOToOffer(createOfferDTO), id);
        return createdOffer.isPresent()
                ? new ResponseEntity<>(mappingUtil.offerToDto(createdOffer.get()), HttpStatus.CREATED)
                : ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLot(@PathVariable("id") int id) {
        offersService.deleteOfferById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
