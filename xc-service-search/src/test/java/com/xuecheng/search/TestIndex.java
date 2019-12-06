package com.xuecheng.search;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.POST;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestIndex {

    @Autowired
    RestHighLevelClient client;

    @Autowired
    RestClient restClient;

    //创建索引库
    @Test
    public void testCreateIndex() throws IOException {
        //创建索引对象
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("xc_course");
        //设置参数
        createIndexRequest.settings(Settings.builder().put("number_of_shards","1").put("number_of_replicas","0"));
        //指定映射

        createIndexRequest.mapping("doc"," {\n" +
                " \t\"properties\": {\n" +
                "            \"studymodel\":{\n" +
                "             \"type\":\"keyword\"\n" +
                "           },\n" +
                "            \"name\":{\n" +
                "             \"type\":\"keyword\"\n" +
                "           },\n" +
                "           \"description\": {\n" +
                "              \"type\": \"text\",\n" +
                "              \"analyzer\":\"ik_max_word\",\n" +
                "              \"search_analyzer\":\"ik_smart\"\n" +
                "           },\n" +
                "           \"pic\":{\n" +
                "             \"type\":\"text\",\n" +
                "             \"index\":false\n" +
                "           }\n" +
                " \t}\n" +
                "}", XContentType.JSON);
        //操作索引的客户端
        IndicesClient indices = client.indices();
        //执行创建索引库
        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest);
        //得到响应
        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println(acknowledged);



//        //1 创建请求
//        CreateIndexRequest createIndexRequest=new CreateIndexRequest("xc_course");
//        createIndexRequest.settings(Settings.builder().put("number_of_shards","1").put("number_of_replicas","0"));
//
//        //2执行操作
//        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest);
//
//        //3获取结果
//        boolean acknowledged = createIndexResponse.isAcknowledged();

    }

    //创建索引
//    put http://localhost:9200/xc_course
//    {
//       "settings":{
//        "index":{
//            "number_of_shards":1,
//            "number_of_replicas":0
//        }
//    },
//        "mapping":{
//        "doc":{
//            "properties": {
//                "name": {
//                    "type": "text"
//                },
//                "description": {
//                    "type": "text"
//                },
//                "studymodel": {
//                    "type": "keyword"
//                }
//            }
//        }
//    }
//    }
//}
//    {
//        "acknowledged": true,
//            "shards_acknowledged": true,
//            "index": "xc_course"
//    }
    @Test
    public void testAddIndex01() throws IOException {
        //1创建请求
        CreateIndexRequest request = new CreateIndexRequest("xc_course");
        request.settings(Settings.builder().put("number_of_shards", "1").put("number_of_replicas","0").build());
        request.mapping("doc","{\n" +
                        "\t\t\t  \t\t\"properties\": {\n" +
                        "\t\t\t\t           \"name\": {\n" +
                        "\t\t\t\t              \"type\": \"text\"\n" +
                        "\t\t\t\t           },\n" +
                        "\t\t\t\t           \"description\": {\n" +
                        "\t\t\t\t              \"type\": \"text\"\n" +
                        "\t\t\t\t           },\n" +
                        "\t\t\t\t           \"studymodel\": {\n" +
                        "\t\t\t\t              \"type\": \"keyword\"\n" +
                        "\t\t\t\t           }\n" +
                        "\t\t\t        }\n" +
                        "\t\t       }"
                , XContentType.JSON);

        //2执行
        IndicesClient indices = client.indices();
        CreateIndexResponse response = indices.create(request);
        //3获取数据
        boolean acknowledged = response.isAcknowledged();
        boolean shardsAcknowledged = response.isShardsAcknowledged();
        String index = response.index();

        System.out.println(acknowledged);
        System.out.println(shardsAcknowledged);
        System.out.println(index);
    }

//    //删除索引库
//    @Test
//    public void testDeleteIndex() throws IOException {
//        //1 创建请求
//        DeleteIndexRequest deleteIndexRequest=new DeleteIndexRequest("xc_course");
//        //2执行操作
//        DeleteIndexResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest);
//        //3获取结果
//        boolean acknowledged = deleteIndexResponse.isAcknowledged();
//        System.out.println(acknowledged);
//
//    }

    //删除索引库
//    rest api
//
//    delete http://localhost:9200/xc_course
//
//    {
//        "acknowledged": true
//    }
    @Test
    public void testDelete() throws IOException {
        //1创建请求
        DeleteIndexRequest request=new DeleteIndexRequest("xc_course");

        //2执行
        IndicesClient indices = client.indices();
        DeleteIndexResponse response = indices.delete(request);
        //3获取数据
        boolean acknowledged = response.isAcknowledged();
        System.out.println(acknowledged);

    }

    //添加文档
//    POST http://localhost:9200/xc_course/doc/1
//
//    {
//             "name":"Bootstrap开发框架",
//            "description":"Bootstrap是由Twitter推出的一个前台页面开发框架，在行业之中使用较为广泛。此开发框架包含了大量的CSS、JS程序代码，可以帮助开发者（尤其是不擅长页面开发的程序人员）轻松的实现一个不受浏览器限制的精美界面效果。",
//            "studymodel":"201001"
//    }

    @Test
    public void testAddIndex() throws IOException {
        //1创建请求
        IndexRequest  request =new IndexRequest("xc_course","doc","2");
        Map<String,String> map=new HashMap<>();
        map.put("name", "Bootstrap开发框架");
        map.put("description", "ootstrap是由Twitter推出的一个前台页面开发框架");
        map.put("studymodel", "201001");
        request.source(map);
        //2执行
        IndexResponse response = client.index(request);
        //3获取数据
        DocWriteResponse.Result result = response.getResult();
        System.out.println(result);
    }


    //添加文档
    @Test
    public void testAddDoc() throws IOException {

//        {
//            "_index": "xc_course",
//                "_type": "doc",
//                "_id": "4028e58161bcf7f40161bcf8b77c0000",
//                "_version": 1,
//                "result": "created",
//                "_shards": {
//            "total": 1,
//                    "successful": 1,
//                    "failed": 0
//        },
//            "_seq_no": 0,
//                "_primary_term": 1
//        }
        //文档内容
        //准备json数据
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "spring cloud实战");
        jsonMap.put("description", "本课程主要从四个章节进行讲解： 1.微服务架构入门 2.spring cloud 基础入门 3.实战Spring Boot 4.注册中心eureka。");
        jsonMap.put("studymodel", "201001");
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jsonMap.put("timestamp", dateFormat.format(new Date()));
        jsonMap.put("price", 5.6f);

        //创建索引创建对象
        IndexRequest indexRequest = new IndexRequest("xc_course","doc");
        //文档内容
        indexRequest.source(jsonMap);
        //通过client进行http的请求
        IndexResponse indexResponse = client.index(indexRequest);

        DocWriteResponse.Result result = indexResponse.getResult();
        System.out.println(result);

    }


    //id查询文档
//   Get http://localhost:9200/xc_course/doc/1
    @Test
    public void testGet() throws IOException {
        //1
        GetRequest request=new GetRequest("xc_course", "doc", "1");
        //2
        GetResponse response = client.get(request);
        //3
        Map<String, Object> sourceAsMap = response.getSourceAsMap();
        System.out.println(sourceAsMap);
        String sourceAsString = response.getSourceAsString();
        System.out.println(sourceAsString);
    }



    //查询文档
    @Test
    public void testGetDoc() throws IOException {
        //查询请求对象
        GetRequest getRequest = new GetRequest("xc_course","doc","tzk2-mUBGsEnDOUe482B");
        GetResponse getResponse = client.get(getRequest);
        //得到文档的内容
        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();

        System.out.println(sourceAsMap);
    }

    //局部更新文档
    @Test
    public void updateDoc() throws IOException {
        //1
        UpdateRequest updateRequest = new UpdateRequest("xc_course", "doc", "4028e581617f945f01617f9dabc40000");
        Map<String, String> map = new HashMap<>();
        map.put("name", "spring cloud实战");
        updateRequest.doc(map);
       //2
        UpdateResponse update = client.update(updateRequest);
        //3
        RestStatus status = update.status();
        System.out.println(status);
    }

    //根据id删除文档
    @Test
    public void testDelDoc() throws IOException {
        //删除文档id
        String id = "eqP_amQBKsGOdwJ4fHiC";

        //1删除索引请求对象
        DeleteRequest deleteRequest = new DeleteRequest("xc_course","doc",id);
        //2响应对象
        DeleteResponse deleteResponse = client.delete(deleteRequest);
        //3获取响应结果
        DocWriteResponse.Result result = deleteResponse.getResult();
        System.out.println(result);

        //1 创建请求



        //2执行操作

        //3获取结果


    }
}
