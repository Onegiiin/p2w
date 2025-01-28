package by.kapinskiy.p2w.controllers;

import by.kapinskiy.p2w.DTO.CategoryDTO;
import by.kapinskiy.p2w.DTO.CreateLotDTO;
import by.kapinskiy.p2w.DTO.LotDTO;
import by.kapinskiy.p2w.DTO.OfferDTO;
import by.kapinskiy.p2w.models.Lot;
import by.kapinskiy.p2w.services.LotsService;
import by.kapinskiy.p2w.services.OffersService;
import by.kapinskiy.p2w.util.MappingUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lots")
public class LotsController {
    private final LotsService lotsService;
    private final MappingUtil mappingUtil;
    private final OffersService offersService;

    @Autowired
    public LotsController(LotsService lotsService, MappingUtil mappingUtil, OffersService offersService) {
        this.lotsService = lotsService;
        this.mappingUtil = mappingUtil;
        this.offersService = offersService;
    }

    @PostMapping
    public ResponseEntity<LotDTO> createLot(@RequestBody @Valid CreateLotDTO createLotDTO) {
        Lot createdLot = lotsService.createLot(mappingUtil.createLotDTOToLot(createLotDTO), createLotDTO.getCategoryId());
        return new ResponseEntity<>(mappingUtil.lotToDTO(createdLot), HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public List<LotDTO> findLots(@RequestParam(name = "name")String name) {
        return mappingUtil.lotsListToDTO(lotsService.findByName(name));
    }

    @GetMapping("/{id}")
    public LotDTO findLot(@PathVariable int id) {
        return mappingUtil.lotToDTO(lotsService.getLotById(id));
    }

    @GetMapping("/{id}/offers")
    public List<OfferDTO> getOffers(@PathVariable int id) {
        return mappingUtil.offersListToDTO(offersService.getAllOffersByLotId(id));
    }

    @GetMapping("/{id}/category")
    public CategoryDTO getCategory(@PathVariable("id") int id) {
        return mappingUtil.categoryToDTO(lotsService.getCategory(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLot(@PathVariable("id") int id) {
        lotsService.deleteLotById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
