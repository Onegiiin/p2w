package by.kapinskiy.p2w.services;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@PreAuthorize("hasRole('Moderator')")
//TODO
public class ModeratorService {
    public void cancelOrder(int orderId) {

    }

    public void banUser(int userId) {

    }

    public void getReportsList() {

    }

    public void changeReportStatus() {

    }

    public void deleteOffer(int offerId) {

    }


}
