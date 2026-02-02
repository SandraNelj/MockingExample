package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingSystemTest {

    private static final LocalDateTime fixed_dateTime =
            LocalDateTime.of(2026, 01, 25, 10, 0, 0);

    @Mock
    private TimeProvider timeProvider;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private NotificationService notificationService;
    @Mock
    private Room room;
    @InjectMocks
    private BookingSystem bookingSystem;

    private LocalDateTime startTime() {
        return fixed_dateTime.plusHours(1);
    }
    private LocalDateTime endTime() {
        return fixed_dateTime.plusHours(2);
    }

    @Test
    @DisplayName("Successful booking if room is available")
    void bookRoom_successfulBooking() {
        when(timeProvider.getCurrentTime()).thenReturn(fixed_dateTime);
        when(roomRepository.findById("A1")).thenReturn(Optional.of(room));
        when(room.isAvailable(startTime(), endTime())).thenReturn(true);

        boolean result = bookingSystem.bookRoom("A1", startTime(), endTime());

        assertThat(result).isTrue();
        verify(room).addBooking(any(Booking.class));
        verify(roomRepository).save(room);
    }

    @Test
    @DisplayName("Booking fails if room is not available")
    void bookRoom_failsBooking() {
        when(timeProvider.getCurrentTime()).thenReturn(fixed_dateTime);
        when(roomRepository.findById("A1")).thenReturn(Optional.of(room));
        when(room.isAvailable(startTime(), endTime())).thenReturn(false);

        boolean result = bookingSystem.bookRoom("A1", startTime(), endTime());

        assertThat(result).isFalse();
        verify(roomRepository, never()).save(any());
        verify(room, never()).addBooking(any());
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
                startTime(), endTime());

        assertThat(result)
                .hasSize(1)
                .containsExactly(availableRoom);

    }

    @Test
    @DisplayName("Successful cancel for future booking")
    void cancelBooking_successful() throws NotificationException {
        Booking booking = new Booking("B1", "A1", startTime(), endTime());

        when(timeProvider.getCurrentTime()).thenReturn(fixed_dateTime);
        when(room.hasBooking("B1")).thenReturn(true);
        when(room.getBooking("B1")).thenReturn(booking);
        when(roomRepository.findAll()).thenReturn(List.of(room));

        boolean result =  bookingSystem.cancelBooking("B1");

        assertThat(result).isTrue();
        verify(room).removeBooking("B1");
        verify(roomRepository).save(room);
        verify(notificationService).sendCancellationConfirmation(booking);
    }

    @Test
    @DisplayName("Returns false if booking does not exist")
    void cancelBooking_fails()  {
        when(roomRepository.findAll()).thenReturn(List.of());

        boolean result = bookingSystem.cancelBooking("X1");
        assertThat(result).isFalse();
    }
    @ParameterizedTest
    @NullSource
    @DisplayName("Booking fails when roomId is null")
    void bookRoom_nullRoomId_throwsException(String roomId) {

        assertThatThrownBy(()->
                bookingSystem.bookRoom(roomId, startTime(), endTime())
        ).isInstanceOf(IllegalArgumentException.class);
    }
}