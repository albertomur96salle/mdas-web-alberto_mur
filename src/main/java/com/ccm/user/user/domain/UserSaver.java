package com.ccm.user.user.domain;

import javax.inject.Inject;
import javax.inject.Named;

public class UserSaver {

    @Inject
    @Named("InMemory")
    UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.update(user);
    }
}