package com.feign.feignsevice.impl;


import com.feign.Model.Student;
import com.feign.feignsevice.FeignProviderClient;
import org.springframework.stereotype.Component;
import java.util.Collection;
/*创建 FeignProviderClient 接⼝的实现类 FeignError，定义容错处理逻辑，通过 @Component 注
解将 FeignError 实例注⼊ IoC 容器中。*/
@Component
public class FeignError implements FeignProviderClient {
    @Override
    public Collection<Student> findAll() {
        return null;
    }
    @Override
    public String index() {
        return "服务器维护中......";
    }
}
