package ru.relex.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.relex.dao.AppUserDAO;
import ru.relex.entity.AppUser;
import ru.relex.service.UserActivationService;
import ru.relex.utils.CryptoTool;

import java.util.Optional;
@RequiredArgsConstructor
@Service
public class UserActivationServiceImpl implements UserActivationService {
    private final AppUserDAO appUserDAO;

    private final CryptoTool cryptoTool;



    @Override
    public boolean activation(String cryptoUserId) {
        Long userId = cryptoTool.idOf(cryptoUserId);
        Optional<AppUser> optional = appUserDAO.findById(userId);
        if(optional.isPresent()){
            AppUser user = optional.get();
            user.setActive(true);
            appUserDAO.save(user);
            return true;
        }

        return false;
    }
}
