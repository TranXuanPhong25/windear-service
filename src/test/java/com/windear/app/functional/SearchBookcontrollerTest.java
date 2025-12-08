package com.windear.app.functional;

import com.windear.app.controller.SearchController;
import com.windear.app.service.ExternalBookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SearchController.class)
@DisplayName("Use Case 2: Search Book")
class SearchBookcontrollerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("externalBookProxy")
    private ExternalBookService externalBookService;

    @Test
    @DisplayName("TC2.1: User searches for a book by keyword - with results")
    void testSearchBook_Success() throws Exception {
        String keyword = "Harry Potter";
        String mockResponse = "[{\"title\":\"Harry Potter and the Sorcerer's Stone\",\"available\":true}]";
        
        when(externalBookService.getSearchSuggestions(keyword)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/search")
                        .param("q", keyword))
                .andExpect(status().isOk())
                .andExpect(content().string(mockResponse));

        verify(externalBookService).getSearchSuggestions(keyword);
    }


    @Test
    @DisplayName("TC2.3: Search for unavailable book")
    void testSearchBook_NeverExist() throws Exception {
        String keyword = "akdjfakldjf;alsdjlfasjd";
        String mockResponse = "[]";
        
        when(externalBookService.getSearchSuggestions(keyword)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/search")
                        .param("q", keyword))
                .andExpect(status().isOk())
                .andExpect(content().string(mockResponse));

        verify(externalBookService).getSearchSuggestions(keyword);
    }
}
