package com.xielaoban.aicode;

import dev.langchain4j.community.store.embedding.redis.spring.RedisEmbeddingStoreAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


//@EnableAspectJAutoProxy(exposeProxy = true)
@EnableCaching  // 支持Spring Data缓存注解
@SpringBootApplication(exclude = {RedisEmbeddingStoreAutoConfiguration.class})
@MapperScan("com.xielaoban.aicode.mapper")
public class AiCodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiCodeApplication.class, args);
    }

}
