package com.windear.app.functional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.windear.app.controller.InternalBookController;
import com.windear.app.dto.AddInternalBookRequestDTO;
import com.windear.app.entity.InternalBook;
import com.windear.app.service.InternalBookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InternalBookController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("Use Case 1: Add Book Tests")
class AddBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InternalBookService internalBookService;

    @Test
    @DisplayName("TC1.1: Admin adds a new book successfully")
    void testAddBook_Success() throws Exception {
        InternalBook book = new InternalBook();
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setIsbn13("978-0132350884");
        book.setReleaseDate(LocalDate.of(2008, 8, 1));
        
        AddInternalBookRequestDTO request = new AddInternalBookRequestDTO(book, "Programming, Software Engineering");

        when(internalBookService.add(any(AddInternalBookRequestDTO.class))).thenReturn(book);

        mockMvc.perform(post("/api/db/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"));

        verify(internalBookService).add(any(AddInternalBookRequestDTO.class));
    }

    @Test
    @DisplayName("TC1.2: Admin adds a new book with invalid fields should fail")
    void testAddBook_whenInvalidField_thenShouldFail() throws Exception {
        InternalBook book = new InternalBook();
        book.setTitle(null);
        book.setAuthor(null);
        book.setIsbn13("978-");
        book.setReleaseDate(LocalDate.of(2008, 8, 1));
        
        AddInternalBookRequestDTO request = new AddInternalBookRequestDTO(book, "NOt, H");

        when(internalBookService.add(any(AddInternalBookRequestDTO.class))).thenReturn(book);

        mockMvc.perform(post("/api/db/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());

        verify(internalBookService).add(any(AddInternalBookRequestDTO.class));
    }
}
