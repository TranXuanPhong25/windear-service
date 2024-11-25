package com.windear.app.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.json.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
@Qualifier("externalBookReal")
public class ExternalBookServiceImpl implements ExternalBookService {
    private final WebClient goodreadsWebClient;

    public ExternalBookServiceImpl(@Qualifier("goodreadsWebClient") WebClient goodreadsWebClient) {
        this.goodreadsWebClient = goodreadsWebClient;
    }

    public String getQueryResultAsString(String query) {
        Map<String, String> graphqlQuery = new HashMap<>();
        graphqlQuery.put("query", query);

        return goodreadsWebClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(graphqlQuery)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public String getBasicGenres() {
        String query = "{\n" +
                "  getBasicGenres {\n" +
                "    genres {\n" +
                "      name\n" +
                "    }\n" +
                "  }\n" +
                "}";
        return getQueryResultAsString(query);
    }

    @Override
    public String getTaggedBooks(String tagName) {
        String query = "{\n" +
                "getTaggedBooks(tagName: \"" + tagName + "\") {\n" +
                "    edges {\n" +
                "      node {\n" +
                "        legacyId\n" +
                "        imageUrl\n" +
                "        title\n" +
                "        primaryContributorEdge {\n" +
                "          node {\n" +
                "            name\n" +
                "          }\n" +
                "        }\n" +
                "        stats {\n" +
                "          averageRating\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "    pageInfo {\n" +
                "      hasNextPage\n" +
                "      hasPrevPage\n" +
                "      nextPageToken\n" +
                "      prevPageToken\n" +
                "    }\n" +
                "  }" +
                "}";
        return getQueryResultAsString(query);
    }

    @Override
    public String getReviews(String workId) {
        String query = "{\n" +
                "getReviews(\n" +
                "    filters: {resourceId: \"" + workId + "\", resourceType: WORK, sort: DEFAULT}\n" +
                "    pagination: {limit:8}\n" +
                "  ) {\n" +
                "    pageInfo {\n" +
                "      hasNextPage\n" +
                "      hasPrevPage\n" +
                "      nextPageToken\n" +
                "      prevPageToken\n" +
                "    }\n" +
                "    edges {\n" +
                "      node {\n" +
                "        rating\n" +
                "        text\n" +
                "        createdAt\n" +
                "        creator {\n" +
                "          imageUrlSquare\n" +
                "          name\n" +
                "          isAuthor\n" +
                "        }\n" +
                "        shelving {\n" +
                "          taggings {\n" +
                "            tag {\n" +
                "              name\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "    totalCount\n" +
                "  }" +
                "}";
        return getQueryResultAsString(query);
    }

    @Override
    public String getBookByLegacyId(int legacyId) {
        String query = "{\n" +
                "   getBookByLegacyId(legacyId: " + legacyId + ") {\n" +
                "    id\n" +
                "    stats {\n" +
                "      ratingsCount\n" +
                "      averageRating\n" +
                "    }\n" +
                "    title\n" +
                "    details {\n" +
                "      asin\n" +
                "      format\n" +
                "      isbn\n" +
                "      isbn13\n" +
                "      language {\n" +
                "        name\n" +
                "      }\n" +
                "      numPages\n" +
                "      officialUrl\n" +
                "      publicationTime\n" +
                "      publisher\n" +
                "    }\n" +
                "    description\n" +
                "    primaryContributorEdge {\n" +
                "      role\n" +
                "      node {\n" +
                "        name\n" +
                "        profileImageUrl\n" +
                "        description\n" +
                "        followers {\n" +
                "          totalCount\n" +
                "        }\n" +
                "        works {\n" +
                "          totalCount\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "    secondaryContributorEdges {\n" +
                "      node {\n" +
                "        name\n" +
                "      }\n" +
                "      role\n" +
                "    }\n" +
                "    bookGenres {\n" +
                "      genre {\n" +
                "        name\n" +
                "      }\n" +
                "    }\n" +
                "    links {\n" +
                "      primaryAffiliateLink {\n" +
                "        url\n" +
                "        name\n" +
                "        ... on BookLink {\n" +
                "          url\n" +
                "        }\n" +
                "      }\n" +
                "      secondaryAffiliateLinks {\n" +
                "        name\n" +
                "        url\n" +
                "      }\n" +
                "    }\n" +
                "    imageUrl\n" +
                "    socialSignals(shelfStatus: ALL) {\n" +
                "      count\n" +
                "      name\n" +
                "      users {\n" +
                "        node {\n" +
                "          imageUrl\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "    bookSeries {\n" +
                "      seriesPlacement\n" +
                "      series {\n" +
                "        title\n" +
                "      }\n" +
                "    }\n" +
                "    webUrl\n" +
                "    work {\n" +
                "       id\n" +
                "      details {\n" +
                "        booksCount\n" +
                "        originalTitle\n" +
                "        characters {\n" +
                "          name\n" +
                "          webUrl\n" +
                "        }\n" +
                "        awardsWon {\n" +
                "          awardedAt\n" +
                "          name\n" +
                "          webUrl\n" +
                "        }\n" +
                "      }\n" +
                "      reviews {\n" +
                "        totalCount\n" +
                "      }\n" +
                "    }\n" +
                "    bookSeries {\n" +
                "      seriesPlacement\n" +
                "      series {\n" +
                "        title\n" +
                "      }\n" +
                "    }\n" +
                "  }" +
                "}";
        return getQueryResultAsString(query);
    }

    @Override
    public String getSearchSuggestions(String q) {
        String query = "{" +
                "   getSearchSuggestions(query: \"" + q + "\") {\n" +
                "    edges {\n" +
                "      ... on SearchBookEdge {\n" +
                "        node {\n" +
                "          title\n" +
                "          legacyId\n" +
                "          imageUrl\n" +
                "          stats {\n" +
                "            ratingsCount\n" +
                "            averageRating\n" +
                "          }\n" +
                "          work {\n" +
                "            reviews {\n" +
                "              totalCount\n" +
                "            }\n" +
                "          }\n" +
                "          primaryContributorEdge{\n" +
                "            node{\n" +
                "               name\n" +
                "            }\n" +
                "           }\n" +
                "           \n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }" +
                "}";
        return getQueryResultAsString(query);
    }

    @Override
    public String getFeaturedBookLists() {
        String query = "{" +
                "getFeaturedBookLists {\n" +
                "    edges {\n" +
                "      node {\n" +
                "        title\n" +
                "        books {\n" +
                "          edges {\n" +
                "            node {\n" +
                "              imageUrl\n" +
                "              legacyId\n" +
                "              title\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "        description\n" +
                "      }\n" +
                "    }\n" +
                "  }" +
                "}";
        return getQueryResultAsString(query);
    }

    @Override
    public String getPopularBookLists() {
        String query = "{" +
                "getPopularBookLists {\n" +
                "    edges {\n" +
                "      node {\n" +
                "        title\n" +
                "        books {\n" +
                "          edges {\n" +
                "            node {\n" +
                "              title\n" +
                "              imageUrl\n" +
                "              legacyId\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }" +
                "}";
        return getQueryResultAsString(query);
    }

    @Override
    public String getSimilarBooks(String id) {
        String query = "{" +
                "getSimilarBooks(\n" +
                "    id: \"" + id + "\"\n" +
                "    pagination: {limit: 12}\n" +
                "  ) {\n" +
                "    edges {\n" +
                "      node {\n" +
                "        imageUrl\n" +
                "        legacyId\n" +
                "        title\n" +
                "        primaryContributorEdge {\n" +
                "           node {\n" +
                "               name\n" +
                "           }\n" +
                "       }\n" +
                "       stats {\n" +
                "           averageRating\n" +
                "       }\n" +
                "     }\n" +
                "    }\n" +
                "    pageInfo {\n" +
                "      hasNextPage\n" +
                "      hasPrevPage\n" +
                "      nextPageToken\n" +
                "      prevPageToken\n" +
                "    }\n" +
                "  }" +
                "}";
        return getQueryResultAsString(query);
    }

    @Override
    public String getEditions(String id) {
        String query = "{" +
                "   getEditions(\n" +
                "    id: \"" + id + "\"\n" +
                "    pagination: {limit: 20}\n" +
                "  ) {\n" +
                "    edges {\n" +
                "      node {\n" +
                "        legacyId\n" +
                "        imageUrl\n" +
                "        details {\n" +
                "          publisher\n" +
                "          publicationTime\n" +
                "          format\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }" +
                "}";
        return getQueryResultAsString(query);
    }

    @Override
    public Boolean isIsbnExist(String isbn) {
        String query = "{\n" +
                "  getSearchSuggestions(query: \"" + isbn + "\") {\n" +
                "    totalCount\n" +
                "  }\n" +
                "}";
        String result = getQueryResultAsString(query);
        JSONObject jsonObject = new JSONObject(result);
        int totalCount = jsonObject.getJSONObject("data").getJSONObject("getSearchSuggestions").getInt("totalCount");
        System.out.println(isbn + " " + totalCount);
        return totalCount != 0;
    }
}
