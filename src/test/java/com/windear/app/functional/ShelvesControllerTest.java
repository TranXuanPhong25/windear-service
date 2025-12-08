package com.windear.app.functional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.windear.app.controller.ShelvesController;
import com.windear.app.dto.UpdateShelvesRequest;
import com.windear.app.entity.Shelves;
import com.windear.app.service.ShelvesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ShelvesController.class)
@DisplayName("Use case 6: Shelves Tests")
class ShelvesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShelvesService shelvesService;

    private String userId;
    private Shelves shelves;

    private static final int STATUS_WANT_TO_READ = 0;
    private static final int STATUS_READING = 1;
    private static final int STATUS_FINISHED = 2;

    @BeforeEach
    void setUp() {
        userId = "auth0|user123";
        shelves = new Shelves();
        shelves.setUserId(userId);
    }

    @Test
    @DisplayName("TC6.1: Update book status (Add to reading-list/shelves)")
    void testUpdateBookStatus_ToFinished() throws Exception {
        UpdateShelvesRequest request = new UpdateShelvesRequest("Finished", 2, STATUS_FINISHED);

        when(shelvesService.updateBookStatusInShelves(
                anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(shelves);

        mockMvc.perform(put("/api/shelves/" + userId + "/mutate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(shelvesService).updateBookStatusInShelves(
                eq(userId),
                eq("Finished"),
                eq(2),
                eq(STATUS_FINISHED)
        );
    }

    @Test
    @DisplayName("TC6.2: Get shelves that book is contained in")
    void testGetShelfNamesContainingBook_MultipleResults() throws Exception {
        List<String> shelfNames = List.of(
                "Want to Read",
                "Fiction"
        );
        
        when(shelvesService.getShelfNamesContainsBook(anyString(), anyInt()))
                .thenReturn(shelfNames);

        mockMvc.perform(get("/api/shelves/" + userId + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Want to Read"))
                .andExpect(jsonPath("$.length()").value(2));
        verify(shelvesService).getShelfNamesContainsBook(eq(userId), eq(1));
    }

    @Test
    @DisplayName("TC6.3: User with no shelves - returns empty list")
    void testGetAllShelfNames_NoShelves() throws Exception {
        when(shelvesService.getAllShelvesNamesOfUser(anyString()))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/shelves/shelfName/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(shelvesService).getAllShelvesNamesOfUser(userId);
    }

    @Test
    @DisplayName("TC6.4: Add book to new shelf (if not exists)")
    void testAddBookToShelf_NewShelf() throws Exception {
        UpdateShelvesRequest request = new UpdateShelvesRequest("New Shelf", 5, STATUS_WANT_TO_READ);

        when(shelvesService.updateBookStatusInShelves(
                anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(shelves);

        mockMvc.perform(put("/api/shelves/" + userId + "/mutate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(shelvesService).updateBookStatusInShelves(
                eq(userId),
                eq("New Shelf"),
                eq(5),
                eq(STATUS_WANT_TO_READ)
        );
    }
}
