package com.educandoweb.course.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;

@SpringBootTest
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "John Doe", "john@example.com", "1234567890", "password");
    }

    @Test
    public void findAllShouldReturnAllUsers() {
    	
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getId()).isEqualTo(user.getId());
    }

    @Test
    public void findByIdShouldReturnUserWhenIdExists() {
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("John Doe");
    }

    @Test
    public void insertShouldReturnUser() {
        
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.insert(user);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getName()).isEqualTo("John Doe");
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        
        doNothing().when(userRepository).deleteById(1L);

        userService.delete(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void updateShouldReturnUpdatedUser() {
        
        User updatedUser = new User(1L, "John Updated", "john.updated@example.com", "0987654321", "newpassword");
        when(userRepository.getReferenceById(1L)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(updatedUser);

        User result = userService.update(1L, updatedUser);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John Updated");
        assertThat(result.getEmail()).isEqualTo("john.updated@example.com");
    }
}
