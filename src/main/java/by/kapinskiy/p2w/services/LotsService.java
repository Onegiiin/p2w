package by.kapinskiy.p2w.services;

import by.kapinskiy.p2w.models.Category;
import by.kapinskiy.p2w.models.Lot;
import by.kapinskiy.p2w.repositories.CategoriesRepository;
import by.kapinskiy.p2w.repositories.LotsRepository;
import by.kapinskiy.p2w.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LotsService {
    private final LotsRepository lotsRepository;
    private final CategoriesRepository categoriesRepository;

    @Autowired
    public LotsService( LotsRepository lotsRepository, CategoriesRepository categoriesRepository) {
        this.lotsRepository = lotsRepository;
        this.categoriesRepository = categoriesRepository;
    }



    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public Lot createLot(Lot lot, int categoryId) {
        Category category = categoriesRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));
        lot.setCategory(category);
        category.getLots().add(lot);
        return lotsRepository.save(lot);
    }

    public List<Lot> findByName(String name) {
        return lotsRepository.findByNameContainsIgnoreCase(name);
    }

    public Lot getLotById(int lotId) {
        return lotsRepository.findById(lotId)
                .orElseThrow(() -> new NotFoundException("Lot with id " + lotId + " not found"));
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public void deleteLotById(int lotId) {
        if (lotsRepository.existsById(lotId)) {
            lotsRepository.deleteById(lotId);
        } else {
            throw new NotFoundException("Lot with id " + lotId + " not found");
        }
    }

    public Category getCategory(int id) {
        return lotsRepository.findById(id).orElseThrow(() -> new NotFoundException("Lot not found")).getCategory();
    }

}
