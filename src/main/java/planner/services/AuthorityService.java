package planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planner.dao.AuthorityRepository;
import planner.entity.basic.Authority;

import static planner.entity.basic.supplementary.AuthorityType.ROLE_ADMIN;
import static planner.entity.basic.supplementary.AuthorityType.ROLE_USER;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository repository;

    public Authority findUserAuthority() {
        return repository.findByName(ROLE_USER);
    }

    public Authority findAdminAuthority() {
        return repository.findByName(ROLE_ADMIN);
    }
}
