package com.consumer.controller;


import com.consumer.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerHandler {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    /*通过服务名获取路径端口*/
    public String getUrl(String serverName){
        List<ServiceInstance> instances = discoveryClient.getInstances(serverName);
        if(instances == null || instances.isEmpty()){
            return "不存在此服务";
        }
        ServiceInstance serviceInstance = instances.get(0);
        String url = "http://" + serviceInstance.getHost() + ":" +serviceInstance.getPort();
        return url;
    }

    @GetMapping("/findAll")
    public Collection<Student> findAll(){
        String url = this.getUrl("eurekaClient");
        return
                restTemplate.getForEntity(url + "student/findAll",Collection.class).getBody();
    }
    @GetMapping("/findAll2")
    public Collection<Student> findAll2(){
        String url = this.getUrl("eurekaClient");
        return
                restTemplate.getForObject(url + "/student/findAll",Collection.class);
    }
    @GetMapping("/findById/{id}")
    public Student findById(@PathVariable("id") long id){
        String url = this.getUrl("eurekaClient");
        return
                restTemplate.getForEntity(url + "/student/findById/{id}",Student.class,id).getBody();
    }
    @GetMapping("/findById2/{id}")
    public Student findById2(@PathVariable("id") long id){
        String url = this.getUrl("eurekaClient");
        return
                restTemplate.getForObject(url + "/student/findById/{id}",Student.class,id);
    }
    @PostMapping("/save")
    public void save(@RequestBody Student student){
        String url = this.getUrl("eurekaClient");
        restTemplate.postForEntity(url + "/student/save",student,null) .getBody();
    }
    @PostMapping("/save2")
    public void save2(@RequestBody Student student){
        String url = this.getUrl("eurekaClient");
        restTemplate.postForObject(url + "/student/save",student,null) ;
    }
    @PutMapping("/update")
    public void update(@RequestBody Student student){
        String url = this.getUrl("eurekaClient");
        restTemplate.put(url + "/student/update",student);
    }
    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") long id){
        String url = this.getUrl("eurekaClient");
        restTemplate.delete(url + "/student/deleteById/{id}",id);
    }
}
