package com.ningmeng.manage_cms.service;

import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.cms.request.QueryPageRequest;
import com.ningmeng.framework.domain.cms.response.CmsCode;
import com.ningmeng.framework.domain.cms.response.CmsPageResult;
import com.ningmeng.framework.exception.CustomExceptionCast;
import com.ningmeng.framework.model.response.CommonCode;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.QueryResult;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.manage_cms.dao.CmsPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CmsPageService {
    @Autowired
    CmsPageRepository cmsPageRepository;
    /**
     * 页面列表分页查询
     * @param page 当前页码
     * @param size 页面显示个数
     * @param queryPageRequest 查询条件
     * @return 页面列表
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }
        if (page <= 0) {
            page = 1;
        }
        page = page - 1;//为了适应mongodb的接口将页码减1
        if (size <= 0) {
            size = 20;
        }
        //分页对象
        Pageable pageable = new PageRequest(page, size);

        //构建条件构建器
        CmsPage cmsPage = new CmsPage();
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        if(queryPageRequest.getPageAliase() != null){
            exampleMatcher = exampleMatcher.withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        if(queryPageRequest.getSiteId() != null){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if(queryPageRequest.getTemplateId() != null){
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }

        //构建条件
        Example<CmsPage> example =Example.of(cmsPage,exampleMatcher);

        //分页查询
        Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);

        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<CmsPage>();
        cmsPageQueryResult.setList(all.getContent());
        cmsPageQueryResult.setTotal(all.getTotalElements());
        //返回结果
        return new QueryResponseResult(CommonCode.SUCCESS,cmsPageQueryResult);
    }

    //添加页面
    public CmsPageResult add(CmsPage cmsPage) {
        if (cmsPage == null){
            //向外抛出异常 页面对象为空异常
            CustomExceptionCast.cast(CommonCode.FAIL);
        }
        //校验页面是否存在，根据页面名称、站点Id、页面webpath查询，如果三个条件一起还有数据的话证明页面已经存在
        String pageName = cmsPage.getPageName();
        String siteId = cmsPage.getSiteId();
        String pageWebPath = cmsPage.getPageWebPath();
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(pageName, siteId, pageWebPath);
        if(cmsPage1 != null){
            //已经存在 提示错误 异常
            CustomExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        //不存在才添加
        cmsPage.setPageId(null);//添加页面主键由spring data 自动生成
        cmsPageRepository.save(cmsPage);
        //返回结果
        return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
    }
    //根据id查询页面
    public CmsPage getById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }
    //更新页面信息
    public CmsPageResult update(String id, CmsPage cmsPage) {
        CmsPage cmsPage1 = this.getById(id);
        if(cmsPage1 != null){
            CmsPage save = cmsPageRepository.save(cmsPage);
            return  new CmsPageResult(CommonCode.SUCCESS,save);
        }
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    //删除页面
    public ResponseResult delete(String id){
        CmsPage one = this.getById(id);
        if(one != null){
            //删除页面
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
