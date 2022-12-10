package main.services.users;

import main.modules.users.ThirdParty;
import main.repositories.users.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThirdPartyService {
    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    public ThirdParty addThirdParty (ThirdParty thirdParty) {
        return thirdPartyRepository.save(thirdParty);
    }

}
