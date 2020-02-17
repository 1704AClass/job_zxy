package com.ningmeng.manage_cms.dao;

import com.ningmeng.framework.domain.cms.CmsPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Lenovo on 2020/2/11.
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    //根据页面名称查询
    CmsPage findByPageName(String pageName);

    //根据页面名称和类型查询
    CmsPage findByPageNameAndPageType(String pageName, String pageType);

    //根据站点和页面类型分页查询
    int countBySiteIdAndPageType(String siteId, String pageType);

    //根据站点和页面类型分页查询
    Page<CmsPage> findBySiteIdAndPageType(String siteId, String pageType, Pageable pageable);

    //根据页面名称，站点id,页面访问路径查询
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String SiteId, String pageWebPath);

    //使用 Spring Data提供的findById和Save(edit),delete方法完成根据主键查询 [回显，修改]。


}
