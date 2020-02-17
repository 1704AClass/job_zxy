package com.ningmeng.manage_cms;

import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.manage_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Lenovo on 2020/2/11.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    CmsPageRepository cmsPageRepository;

    @Test
    public void  testFindPage(){
        int page=0;//从0开始
        int size=10;//每页显示条数
        Pageable pageable= PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.print(123456);
        System.out.print(all+"+11111111111111111111111111111");

    }
    //添加
    @Test
    public void  testInsert(){
        //定义实体类
        CmsPage cmsPage=new CmsPage();
        cmsPage.setSiteId("s01");
        cmsPage.setTemplateId("t01");
        cmsPage.setPageName("测试页面");
        cmsPage.setPageCreateTime(new Date());
        cmsPageRepository.save(cmsPage);
    }
    //修改
    @Test
    public void testUpdate(){
        Optional<CmsPage> optional = cmsPageRepository.findById("5e4258d7c9cb6c44e019d988");
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            cmsPage.setPageName("测试页面001");
            cmsPageRepository.save(cmsPage);
        }

    }
    @Test
    public void testDelet(){

        cmsPageRepository.deleteById("5e4258d7c9cb6c44e019d988");
    }

    //条件匹配器
    @Test
    public  void testFindAll(){
        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("pageAlise", ExampleMatcher.GenericPropertyMatchers.contains());

        //页面别名模糊查询，需要自定义字符串的匹配器实现模糊查询
        //ExampleMatcher.GenericPropertyMatchers.contains() 包含
        // ExampleMatcher.GenericPropertyMatchers.startsWith()//开头匹配

        //条件值
        CmsPage cmsPage = new CmsPage();
        //站点ID
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        //模板ID
        cmsPage.setTemplateId("5a962c16b00ffc514038fafd");
        //创建条件实例
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        Pageable pageable = PageRequest.of(1, 5);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        System.out.print(all+"+条件匹配器查询");
    }
}
