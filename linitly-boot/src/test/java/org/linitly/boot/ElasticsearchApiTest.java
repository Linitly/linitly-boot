package org.linitly.boot;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linitly.boot.base.entity.SysQuartzJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchApiTest {

    @Autowired
    private RestHighLevelClient client;

    /**
     * @author linxiunan
     * @date 15:20 2020/11/10
     * @description 集群健康
     */
    @Test
    public void testClusterHealth() throws IOException {
        ClusterHealthRequest request = new ClusterHealthRequest();
        ClusterHealthResponse health = client.cluster().health(request, RequestOptions.DEFAULT);
        System.out.println(health.toString());
    }

    /**
     * @author linxiunan
     * @date 15:20 2020/11/10
     * @description 创建索引
     */
    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("linitly_springboot_index");
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * @author linxiunan
     * @date 15:20 2020/11/10
     * @description 索引是否存在
     */
    @Test
    public void testExistsIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("linitly_springboot_index");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * @author linxiunan
     * @date 15:20 2020/11/10
     * @description 删除索引
     */
    @Test
    public void testDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("linitly_springboot_index");
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }

    /**
     * @author linxiunan
     * @date 15:20 2020/11/10
     * @description 添加文档
     */
    @Test
    public void testInsertDoc() throws IOException {
        SysQuartzJob job = new SysQuartzJob();
        job.setStatus(0);
        job.setJobName("test-job");
        job.setCronExpression("dasdads");
        job.setJobClassName("test-class-name");
        job.setDescription("test-desc");

        IndexRequest request = new IndexRequest("test1");
        // 可以手动添加文档id，也可以不指定id，自动生成
        request.id("1326546545");
        request.source(JSON.toJSONString(job), XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());

    }

    /**
     * @author linxiunan
     * @date 15:57 2020/11/10
     * @description 判断文档是否存在
     */
    @Test
    public void testExistsDoc() throws IOException {
        GetRequest request = new GetRequest("test1", "1326546545");
        // 不获取文档数据
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * @author linxiunan
     * @date 15:57 2020/11/10
     * @description 获取文档
     */
    @Test
    public void testGetDoc() throws IOException {
        GetRequest request = new GetRequest("test1", "1326546545");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());
        System.out.println(response);
    }

    /**
     * @author linxiunan
     * @date 15:57 2020/11/10
     * @description 更新文档
     */
    @Test
    public void testUpdateDoc() throws IOException {
        SysQuartzJob job = new SysQuartzJob();
        job.setStatus(0);
        job.setJobName("test-job");
        job.setCronExpression("5455646565654");
        job.setJobClassName("test-class-name");
        job.setDescription("test-desc");

        UpdateRequest request = new UpdateRequest("test1", "1326546545");
        request.doc(JSON.toJSONString(job), XContentType.JSON);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * @author linxiunan
     * @date 15:57 2020/11/10
     * @description 删除文档
     */
    @Test
    public void testDeleteDoc() throws IOException {
        DeleteRequest request = new DeleteRequest("test1", "1326546545");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * @author linxiunan
     * @date 15:57 2020/11/10
     * @description 批量操作文档
     */
    @Test
    public void testBulkInsertDoc() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        List<SysQuartzJob> jobList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SysQuartzJob job = new SysQuartzJob();
            job.setStatus(0);
            job.setJobName("test-job" + i);
            job.setCronExpression("5455646565654" + i);
            job.setJobClassName("test-class-name" + i);
            job.setDescription("test-desc" + i);
            jobList.add(job);
            bulkRequest.add(new IndexRequest("test1").id("dasdasd" + i).source(JSON.toJSONString(job), XContentType.JSON));
        }
        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(response.hasFailures());
    }

    /**
     * @author linxiunan
     * @date 15:57 2020/11/10
     * @description 查询文档；更多查询使用时查看文档
     */
    @Test
    public void testSearchDoc() throws IOException {
        SearchRequest request = new SearchRequest("test1");
        // 搜索条件构造器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("jobName", "test-job2");
        searchSourceBuilder.query(matchQueryBuilder);
        request.source(searchSourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(response.getHits().getHits()));
        System.out.println("----------------------------------");
        for (SearchHit documentFields : response.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsString());
        }
    }

    /**
     * @author linxiunan
     * @date 15:57 2020/11/10
     * @description 高亮
     */
    @Test
    public void testHighLight() throws IOException {
        SearchRequest request = new SearchRequest("test1");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("jobName", "test-job2");

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span color='red'>");
        highlightBuilder.postTags("</span>");
        highlightBuilder.field("jobName");

        searchSourceBuilder.highlighter(highlightBuilder);
        searchSourceBuilder.query(matchQueryBuilder);

        request.source(searchSourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(response.getHits().getHits()));
        System.out.println("----------------------------------");
        for (SearchHit hit : response.getHits().getHits()) {
            // 解析高亮字段，替换原来的字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField field = highlightFields.get("jobName");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            if (field != null) {
                Text[] fragments = field.fragments();
                String newJobName = "";
                for (Text fragment : fragments) {
                    newJobName += fragment;
                }
                sourceAsMap.put("jobName", newJobName);
            }
            System.out.println(hit.getSourceAsMap());
        }
    }
}
