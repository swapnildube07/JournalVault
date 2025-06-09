package com.swapnil.journal.service;

import com.swapnil.journal.Entity.User;
import com.swapnil.journal.repositary.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Disabled
@ExtendWith(MockitoExtension.class)
public class UserDetailsImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    void loadUserByUserNameTest() {
        User mockUser = new User("ram", "iiifdsdf");
        mockUser.setRoles(java.util.List.of("USER")); // Add roles if your service uses them

        when(userRepository.findByUsername(anyString())).thenReturn(mockUser);

        UserDetails userDetails = userDetailsService.loadUserByUsername("ram");

        assertEquals("ram", userDetails.getUsername());
        assertEquals("iiifdsdf", userDetails.getPassword());
    }
}
