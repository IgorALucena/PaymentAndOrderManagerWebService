package com.educandoweb.course.services;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.educandoweb.course.entities.Order;
import com.educandoweb.course.entities.User;
import com.educandoweb.course.entities.enums.OrderStatus;
import com.educandoweb.course.repositories.OrderRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@SpringBootTest
public class OrderServiceTests {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllShouldReturnListOfOrders() {
        
        User user1 = new User(1L, "John Doe", "john@example.com", "1234567890", "password");
        User user2 = new User(2L, "Jane Smith", "jane@example.com", "0987654321", "password");
        
        Order order1 = new Order(1L, Instant.parse("2024-01-01T10:00:00Z"), OrderStatus.PAID, user1);
        Order order2 = new Order(2L, Instant.parse("2024-02-01T11:00:00Z"), OrderStatus.WAITING_PAYMENT, user2);
        
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
        
        List<Order> result = orderService.findAll();
        
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).containsExactlyInAnyOrder(order1, order2);
    }

    @Test
    public void findByIdShouldReturnOrderWhenIdExists() {
        
        User mockUser = new User(1L, "John Doe", "john@example.com", "1234567890", "password");
        Order mockOrder = new Order(1L, Instant.parse("2024-01-01T10:00:00Z"), OrderStatus.PAID, mockUser);
        
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        
        Order result = orderService.findById(1L);
        
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getMoment()).isEqualTo(Instant.parse("2024-01-01T10:00:00Z"));
        assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.PAID);
    }

}
