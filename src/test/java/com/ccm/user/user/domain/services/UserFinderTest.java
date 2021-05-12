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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@QuarkusTest
public class UserFinderTest {
    @Inject
    UserFinder tested;

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
        userRepository = mock(InMemoryUserRepository.class);
        QuarkusMock.installMockForType(userRepository, UserRepository.class);
    }

    @Test
    public void verify_findUser_callsToMethods() throws UserNotFoundException {
        Mockito.when(userRepository.find(userId)).thenReturn(user);
        Mockito.when(userRepository.exists(userId)).thenReturn(true);

        tested.findUser(userId);

        verify(userRepository, times(1)).find(any());
    }

    @Test
    public void verify_createUser_throwsUserNotFoundException() {
        doReturn(false).when(userRepository).exists(userId);

        assertThrows(UserNotFoundException.class, () -> {tested.findUser(userId);});
    }
}
