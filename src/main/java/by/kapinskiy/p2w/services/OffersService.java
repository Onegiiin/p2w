package by.kapinskiy.p2w.services;

import by.kapinskiy.p2w.models.Lot;
import by.kapinskiy.p2w.models.Offer;
import by.kapinskiy.p2w.models.User;
import by.kapinskiy.p2w.repositories.LotsRepository;
import by.kapinskiy.p2w.repositories.OffersRepository;
import by.kapinskiy.p2w.util.exceptions.NotChangedException;
import by.kapinskiy.p2w.util.exceptions.NotFoundException;
import by.kapinskiy.p2w.util.exceptions.OfferAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OffersService {
    private final OffersRepository offersRepository;
    private final UsersService usersService;
    private final LotsRepository lotsRepository;

    @Autowired
    public OffersService(OffersRepository offersRepository, UsersService usersService, LotsRepository lotsRepository) {
        this.offersRepository = offersRepository;
        this.usersService = usersService;
        this.lotsRepository = lotsRepository;
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public void deleteOfferById(int offerId) {
       User user = usersService.getCurrentUser();
       Offer offer = offersRepository.findById(offerId).orElseThrow(() -> new NotFoundException("Offer not found"));
       if (offer.getExecutor().getId() != user.getId()) {
           throw new AccessDeniedException("You do not have permission to delete this offer");
       }
       offersRepository.delete(offer);
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public Offer createOffer(Offer offer, int lotId) {
        User user = usersService.getCurrentUser();
        Lot lot = lotsRepository.findById(lotId).orElseThrow(() -> new NotFoundException("lot not found"));

        if (!lot.isEnableMultipleOffer() && offersRepository.existsByLot_IdAndExecutor_Id(lotId, user.getId())) {
            throw new OfferAlreadyExistsException("You have already added this offer");
        }

        offer.setExecutor(user);
        offer.setLot(lot);
        user.getOffers().add(offer);
        lot.getOfferList().add(offer);
        return offersRepository.save(offer);
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public Optional<Offer> updateOffer(Offer offer, int offerId) {
        User user = usersService.getCurrentUser();
        Offer existindOffer = offersRepository.findById(offerId).orElseThrow(() -> new NotFoundException("offer not found"));
        if (existindOffer.getExecutor().getId() != user.getId()) {
            throw new NotChangedException("offer does not belong to user");
        }

        existindOffer.setDescription(offer.getDescription());
        existindOffer.setPrice(offer.getPrice());
        existindOffer.setQuantity(offer.getQuantity());

        if (existindOffer.getQuantity() == 0){
            offersRepository.delete(existindOffer);
            return Optional.empty();
        } else {
            return Optional.of(offersRepository.save(existindOffer));
        }
    }

    public List<Offer> getAllOffersByLotId(int lotId) {
        return offersRepository.findByLot_Id(lotId);
    }

}
