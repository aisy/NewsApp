package com.example.aisy.newsapp.helper;

/**
 * Created by aisy on 26/07/17.
 */

import com.example.aisy.newsapp.Model.Article;
import java.util.List;
import java.util.Vector;

public class ArticleHelper {
    private static List<Article.ListArticle> articles;

    public static List<Article.ListArticle> getArticles(){
        if (articles == null){
            articles = new Vector<>();
        }
        return articles;
    }
}

