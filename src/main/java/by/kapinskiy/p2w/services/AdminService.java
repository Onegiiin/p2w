package by.kapinskiy.p2w.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@PreAuthorize("hasRole('Admin')")
public class AdminService {
    //TODO
    public void changeUserRoles(){

    }


}
