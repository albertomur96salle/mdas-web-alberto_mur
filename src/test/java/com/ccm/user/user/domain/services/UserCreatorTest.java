package com.ccm.user.user.domain.services;

import com.ccm.user.user.domain.aggregate.User;
import com.ccm.user.user.domain.exceptions.UserAlreadyExistsException;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@QuarkusTest
public class UserCreatorTest {
    @Inject
    UserCreator tested;

    static User user;
    static UserRepository userRepository;

    @BeforeAll
    public static void setUp() {
        user = UserMother.random();
    }

    @BeforeEach
    public void setMocks() {
        userRepository = mock(InMemoryUserRepository.class);
        QuarkusMock.installMockForType(userRepository, UserRepository.class);
    }

    @Test
    public void verify_createUser_callsToMethods() throws UserAlreadyExistsException {
        Mockito.doNothing().when(userRepository).create(user);

        tested.createUser(user);
        Mockito.verify(userRepository, Mockito.times(1)).create(user);
    }

    @Test()
    public void verify_createUser_throwsUserAlreadyExistsException_whenUserIsAlreadyCreated() {
        doReturn(true).when(userRepository).exists(user.getUserId());

        assertThrows(UserAlreadyExistsException.class, () -> {tested.createUser(user);});
    }
}
