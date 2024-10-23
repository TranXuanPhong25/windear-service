package com.windear.app.service;

import com.windear.app.entity.ExternalBook;
import com.windear.app.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExternalBookServiceImpl implements ExternalBookService {
    private WebClient webClient;
    private BookService bookService;

    @Autowired
    public ExternalBookServiceImpl(WebClient webClient, BookService bookService) {
        this.webClient = webClient;
        this.bookService = bookService;
    }

    public List<Map<String, Object>> getQueryResult(String query) {
        Map<String, String> graphqlQuery = new HashMap<>();
        graphqlQuery.put("query", query);

        Map<String, Object> response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(graphqlQuery)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return (List<Map<String, Object>>) ((Map<String, Object>) response.get("data")).get("books");
    }

    public List<ExternalBook> mapResponseToEntityList(List<Map<String, Object>> booksData) {
        return booksData.stream().map(bookData -> {
            ExternalBook book = new ExternalBook();
            book.setId((Integer) bookData.get("id"));
            book.setTitle((String) bookData.get("title"));
            book.setRating((Double) bookData.get("rating"));
            List<Map<String, Object>> contributions = (List<Map<String, Object>>) bookData.get("contributions");
            String authors = contributions.stream()
                    .map(contribution -> (String) ((Map<String, Object>) contribution.get("author")).get("name"))
                    .collect(Collectors.joining(", "));
            book.setAuthors(authors);
            return book;
        }).collect(Collectors.toList());
        
    }

    @Override
    public List<ExternalBook> findById(int id) {
        String query = "{\n" +
                "  books(where: {id: {_eq: "+ id + "}}) {\n" +
                "    id\n" +
                "    title\n" +
                "    release_date\n" +
                "    rating\n" +
                "    contributions {\n" +
                "      author {\n" +
                "        name\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        List<Map<String, Object>> booksData = getQueryResult(query);
        List<ExternalBook> externalBookList = mapResponseToEntityList(booksData);
        if (externalBookList.isEmpty()) {
            throw new BookNotFoundException("Book with id not found: " + id);
        }
        return externalBookList;
    }

    @Override
    public List<ExternalBook> findByTitle(String title) {
        String query = "{\n" +
                "  books(\n" +
                "    where: {title: {_ilike: \"%" + title + "%\"}}\n" +
                "    order_by: {users_read_count: desc_nulls_last}\n" +
                "  ) {\n" +
                "    id\n" +
                "    title\n" +
                "    release_date\n" +
                "    rating\n" +
                "    contributions {\n" +
                "      author {\n" +
                "        id\n" +
                "        name\n" +
                "      }\n" +
                "    }\n" +
                "    \n" +
                "  }\n" +
                "}";
        List<Map<String, Object>> booksData = getQueryResult(query);
        List<ExternalBook> externalBookList = mapResponseToEntityList(booksData);
        if (externalBookList.isEmpty()) {
            throw new BookNotFoundException("Book with title not found: " + title);
        }
        return externalBookList;
    }
}
