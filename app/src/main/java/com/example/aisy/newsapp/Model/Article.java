package com.example.aisy.newsapp.Model;

import java.util.List;

/**
 * Created by aisy on 26/07/17.
 */

public class Article {

    private String status;
    private String source;
    private String sortBy;
    private List<ListArticle> articles;

    public String getStatus() {
        return status;
    }

    public String getSource() {
        return source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public List<ListArticle> getArticles() {
        return articles;
    }

    public static class ListArticle {
        private String author;
        private String title;
        private String description;
        private String url;
        private String urlToImage;
        private String publishedAt;

        public String getAuthor() {
            return author;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }

        public String getUrlToImage() {
            return urlToImage;
        }

        public String getPublishedAt() {
            return publishedAt;
        }
    }

}
