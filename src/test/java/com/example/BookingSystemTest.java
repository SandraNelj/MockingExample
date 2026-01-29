package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingSystemTest {

    @Mock
    private TimeProvider timeProvider;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private NotificationService notificationService;
    @InjectMocks
    private BookingSystem bookingSystem;

    @Test
    @DisplayName("Successful booking if room is available")
    void bookRoom_successfulBooking() {
        LocalDateTime now = LocalDateTime.of(2026, 01, 25, 10, 00);
        LocalDateTime startTime = now.plusHours(1);
        LocalDateTime endTime = startTime.plusHours(2);

        Room room = mock (Room.class);

        when(timeProvider.getCurrentTime()).thenReturn(now);
        when(roomRepository.findById("A1")).thenReturn(Optional.of(room));
        when(room.isAvailable(startTime, endTime)).thenReturn(true);

        boolean result = bookingSystem.bookRoom("A1", startTime, endTime);

        assertThat(result).isTrue();
        verify(room).addBooking(any(Booking.class));
        verify(roomRepository).save(room);
    }

    @Test
    @DisplayName("Booking fails if room is not available")
    void bookRoom_failsBooking() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1);

        Room room = mock (Room.class);

        when(timeProvider.getCurrentTime()).thenReturn(now);
        when(roomRepository.findById("A1")).thenReturn(Optional.of(room));
        when(room.isAvailable(startTime, endTime)).thenReturn(false);

        boolean result = bookingSystem.bookRoom("A1", startTime, endTime);

        assertThat(result).isFalse();
        verify(roomRepository, never()).save(any());
    }

    @Test
    @DisplayName("Returns available rooms only")
    void getAvailableRooms() {
        Room availableRoom = mock (Room.class);
        Room unavailableRoom = mock (Room.class);

        when(availableRoom.isAvailable(any(), any())).thenReturn(true);
        when(unavailableRoom.isAvailable(any(), any())).thenReturn(false);

        when(roomRepository.findAll()).thenReturn(List.of(availableRoom, unavailableRoom));

        List <Room> result = bookingSystem.getAvailableRooms(
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1));

        assertThat(result)
                .hasSize(1)
                .containsExactly(availableRoom);

    }

    @Test
    @DisplayName("Successful cancel for future booking")
    void cancelBooking_successful() throws NotificationException {
        LocalDateTime now = LocalDateTime.of(2026, 01, 25, 10, 00);
        Booking booking = new Booking("B1", "A1", now.plusHours(1), now.plusHours(2));

        Room room = mock (Room.class);

        when(timeProvider.getCurrentTime()).thenReturn(now);
        when(room.hasBooking("B1")).thenReturn(true);
        when(room.getBooking("B1")).thenReturn(booking);
        when(roomRepository.findAll()).thenReturn(List.of(room));

        doNothing().when(notificationService).sendCancellationConfirmation(booking);

        boolean result =  bookingSystem.cancelBooking("B1");

        assertThat(result).isTrue();
        verify(room).removeBooking("B1");
        verify(roomRepository).save(room);
    }

    @Test
    @DisplayName("Returns false if booking does not exist")
    void cancelBooking_fails()  {
        when(roomRepository.findAll()).thenReturn(List.of());

        boolean result = bookingSystem.cancelBooking("X1");
        assertThat(result).isFalse();
    }
}