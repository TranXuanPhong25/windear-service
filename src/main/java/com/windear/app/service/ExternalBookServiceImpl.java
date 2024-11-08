package com.windear.app.service;

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

    public String getQueryResultAsString(String query) {
        Map<String, String> graphqlQuery = new HashMap<>();
        graphqlQuery.put("query", query);

        String response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(graphqlQuery)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;
    }

    @Override
    public String findById(String id) {
        String query = "{\n" +
                "  books(where: {id: {_eq: " + id + "}}) {\n" +
                "    image {\n" +
                "      url\n" +
                "    }\n" +
                "    rating\n" +
                "    ratings_count\n" +
                "    users_count\n" +
                "    users_read_count\n" +
                "    release_date\n" +
                "    release_year\n" +
                "    pages\n" +
                "    alternative_titles\n" +
                "    contributions {\n" +
                "      author {\n" +
                "        id\n" +
                "        image {\n" +
                "          url\n" +
                "        }\n" +
                "        name\n" +
                "      }\n" +
                "    }\n" +
                "    editions_count\n" +
                "    ratings_distribution\n" +
                "    title\n" +
                "    reviews_count\n" +
                "    taggings(distinct_on: tag_id) {\n" +
                "      tag {\n" +
                "        tag\n" +
                "        slug\n" +
                "        tag_category {\n" +
                "          category\n" +
                "        }\n" +
                "        count\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        String response = getQueryResultAsString(query);
        if (response.contains("\"books\":[]")) {
            throw new BookNotFoundException("Book with id not found: " + id);
        }
        return getQueryResultAsString(query);
    }

    @Override
    public String findByTitle(String title, int pageNo) {
        String query = "{\n" +
                "  books(\n" +
                "    where: {title: {_ilike: \"%" + title + "%\"}}\n" +
                "    order_by: {users_read_count: desc_nulls_last}\n" +
                "    limit: 10\n"+
                "    offset: " + pageNo * 10 + "\n" +
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
        String response = getQueryResultAsString(query);
        if (response.contains("\"books\":[]")) {
            throw new BookNotFoundException("Book with title not found: " + title);
        }
        return getQueryResultAsString(query);
    }
}
