package com.baeldung.spring.data.es;

import com.baeldung.spring.data.es.config.Config;
import com.baeldung.spring.data.es.model.Article;
import com.baeldung.spring.data.es.model.Author;
import com.baeldung.spring.data.es.service.ArticleService;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.elasticsearch.index.query.MatchQueryBuilder.Operator.AND;
import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class}, loader = AnnotationConfigContextLoader.class)
public class ElasticSearchQueryTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private Client client;

    private final Author johnSmith = new Author("John Smith");
    private final Author johnDoe = new Author("John Doe");

    @Before
    public void before() {
        elasticsearchTemplate.deleteIndex(Article.class);
        elasticsearchTemplate.createIndex(Article.class);
        elasticsearchTemplate.putMapping(Article.class);
        elasticsearchTemplate.refresh(Article.class, true);

        Article article = new Article("Spring Data Elasticsearch");
        article.setAuthors(asList(johnSmith, johnDoe));
        article.setTags("elasticsearch", "spring data");
        articleService.save(article);

        article = new Article("Search engines");
        article.setAuthors(asList(johnDoe));
        article.setTags("search engines", "tutorial");
        articleService.save(article);

        article = new Article("Second Article About Elasticsearch");
        article.setAuthors(asList(johnSmith));
        article.setTags("elasticsearch", "spring data");
        articleService.save(article);

        article = new Article("Elasticsearch Tutorial");
        article.setAuthors(asList(johnDoe));
        article.setTags("elasticsearch");
        articleService.save(article);
    }

    @Test
    public void givenFullTitle_whenRunMatchQuery_thenDocIsFound() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(matchQuery("title", "Search engines").operator(AND))
            .build();
        List<Article> articles = elasticsearchTemplate
            .queryForList(searchQuery, Article.class);
        assertEquals(1, articles.size());
    }

    @Test
    public void givenOneTermFromTitle_whenRunMatchQuery_thenDocIsFound() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(matchQuery("title", "Engines Solutions"))
            .build();
        List<Article> articles = elasticsearchTemplate
            .queryForList(searchQuery, Article.class);
        assertEquals(1, articles.size());
        assertEquals("Search engines", articles.get(0).getTitle());
    }

    @Test
    public void givenPartTitle_whenRunMatchQuery_thenDocIsFound() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(matchQuery("title", "elasticsearch data"))
            .build();
        List<Article> articles = elasticsearchTemplate
            .queryForList(searchQuery, Article.class);
        assertEquals(3, articles.size());
    }

    @Test
    public void givenFullTitle_whenRunMatchQueryOnVerbatimField_thenDocIsFound() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(matchQuery("title.verbatim", "Second Article About Elasticsearch"))
            .build();
        List<Article> articles = elasticsearchTemplate
            .queryForList(searchQuery, Article.class);
        assertEquals(1, articles.size());

        searchQuery = new NativeSearchQueryBuilder()
            .withQuery(matchQuery("title.verbatim", "Second Article About"))
            .build();
        articles = elasticsearchTemplate
            .queryForList(searchQuery, Article.class);
        assertEquals(0, articles.size());
    }

    @Test
    public void givenNestedObject_whenQueryByAuthorsName_thenFoundArticlesByThatAuthor() {
        QueryBuilder builder = nestedQuery("authors",
            boolQuery().must(termQuery("authors.name", "smith")));

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(builder).build();
        List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);

        assertEquals(2, articles.size());
    }

    @Test
    public void givenAnalyzedQuery_whenMakeAggregationOnTermCount_thenEachTokenCountsSeparately() {
        TermsBuilder aggregation = AggregationBuilders.terms("top_tags").field("title");
        SearchResponse response = client.prepareSearch("blog").setTypes("article")
            .addAggregation(aggregation).execute().actionGet();

        Map<String, Aggregation> results = response.getAggregations().asMap();
        StringTerms topTags = (StringTerms) results.get("top_tags");

        List<String> keys = topTags.getBuckets().stream().map(b -> b.getKey()).collect(toList());
        Collections.sort(keys);
        assertEquals(asList("about", "article", "data", "elasticsearch",
                            "engines", "search", "second", "spring", "tutorial"), keys);
    }

    @Test
    public void givenNotAnalyzedQuery_whenMakeAggregationOnTermCount_thenEachTermCountsIndividually() {
        TermsBuilder aggregation = AggregationBuilders.terms("top_tags").field("tags")
            .order(Terms.Order.aggregation("_count", false));
        SearchResponse response = client.prepareSearch("blog").setTypes("article")
            .addAggregation(aggregation).execute().actionGet();

        Map<String, Aggregation> results = response.getAggregations().asMap();
        StringTerms topTags = (StringTerms) results.get("top_tags");

        List<String> keys = topTags.getBuckets().stream().map(b -> b.getKey()).collect(toList());
        assertEquals(asList("elasticsearch", "spring data", "search engines", "tutorial"), keys);
    }

    @Test
    public void givenNotExactPhrase_whenUseSlop_thenQueryMatches() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(matchPhraseQuery("title", "spring elasticsearch").slop(1))
            .build();
        List<Article> articles = elasticsearchTemplate
            .queryForList(searchQuery, Article.class);
        assertEquals(1, articles.size());
    }

    @Test
    public void givenPhraseWithType_whenUseFuzziness_thenQueryMatches() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(matchQuery("title", "spring date elasticserch")
                .operator(AND)
                .fuzziness(Fuzziness.ONE)
                .prefixLength(3))
            .build();

        List<Article> articles = elasticsearchTemplate
            .queryForList(searchQuery, Article.class);
        assertEquals(1, articles.size());
    }

    @Test
    public void givenMultimatchQuery_whenDoSearch_thenAllProvidedFieldsMatch() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(multiMatchQuery("tutorial")
                .field("title")
                .field("tags")
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
            .build();

        List<Article> articles = elasticsearchTemplate
            .queryForList(searchQuery, Article.class);
        assertEquals(2, articles.size());
    }
}
