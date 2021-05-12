package com.ccm.user.user.domain.services;

import com.ccm.user.user.domain.aggregate.User;
import com.ccm.user.user.domain.exceptions.UserNotFoundException;
import com.ccm.user.user.domain.interfaces.UserRepository;
import com.ccm.user.user.domain.vo.UserId;
import com.ccm.user.user.domain.vo.UserName;
import com.ccm.user.user.infrastructure.repositories.InMemoryUserRepository;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@QuarkusTest
public class UserSaverTest {
    @Inject
    UserSaver tested;

    static UserId userId;
    static UserName userName;
    static User user;
    static UserRepository userRepository;

    @BeforeAll
    public static void setUp() {
        userId = new UserId(1);
        userName = new UserName("keko");
        user = new User(userName, userId);
    }

    @BeforeEach
    public void setMocks() {
        userRepository = Mockito.mock(InMemoryUserRepository.class);
        QuarkusMock.installMockForType(userRepository, UserRepository.class);
    }

    @Test
    public void verify_saveUser_callsToMethods() throws UserNotFoundException {
        when(userRepository.update(user)).thenReturn(user);
        doReturn(true).when(userRepository).exists(user.getUserId());

        tested.saveUser(user);
        verify(userRepository, Mockito.times(1)).update(user);
    }

    @Test
    public void verify_saveUser_throwsUserNotFoundException() {
        doReturn(false).when(userRepository).exists(user.getUserId());

        assertThrows(UserNotFoundException.class, () -> {
            tested.saveUser(user);
        });
    }
}
