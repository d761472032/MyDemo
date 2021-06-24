package com.demo.skill;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

public class CacheDemo {

    /**
     * 第三步，使用Cacheable注解获取数据
     */
    @Service
    static public class CategoryService {

        //category是缓存名称,#type是具体的key，可支持el表达式
        @Cacheable(value = "category", key = "#type")
        public CategoryModel getCategory(Integer type) {
            return getCategoryByType(type);
        }

        private CategoryModel getCategoryByType(Integer type) {
            System.out.println("根据不同的type:" + type + "获取不同的分类数据");
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setId(1L);
            categoryModel.setParentId(0L);
            categoryModel.setName("电器");
            categoryModel.setLevel(3);
            return categoryModel;
        }
    }

    /**
     * 第二步，配置CacheManager，开启EnableCaching
     */
    @Configuration
    @EnableCaching
    static public class CacheConfig {
        @Bean
        public CacheManager cacheManager(){
            CaffeineCacheManager cacheManager = new CaffeineCacheManager();
            //Caffeine配置
            Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                    //最后一次写入后经过固定时间过期
                    .expireAfterWrite(10, TimeUnit.SECONDS)
                    //缓存的最大条数
                    .maximumSize(1000);
            cacheManager.setCaffeine(caffeine);
            return cacheManager;
        }
    }

    static class CategoryModel {

        private long id;

        private long parentId;

        private String name;

        private int level;

        public void setId(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public long getParentId() {
            return parentId;
        }

        public void setParentId(long parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }

}
