package com.aebiz.app.web.modules.controllers.open.api.cms;

import com.aebiz.app.cms.modules.models.Cms_article;
import com.aebiz.app.cms.modules.services.CmsArticleService;
import com.aebiz.baseframework.base.Result;
import com.aebiz.baseframework.page.Pagination;
import com.aebiz.baseframework.view.annotation.SJson;
import org.nutz.json.Json;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by heliu on 2017/4/10.
 */
@Controller
@RequestMapping("/open/api/cms/article")
public class ApiCmsArticleController {
    private static final Log log = Logs.get();
    @Autowired
    private CmsArticleService cmsArticleService;


    /**
     * @api {post} /open/api/cms/article/list?appid=appid&token=token 测试API
     * @apiGroup cms
     * @apiVersion 1.0.0
     * @apiPermission token
     * @apiParam {Object}	data 				    数据对象
     * @apiParamExample {json} 示例
     * POST /open/api/cms/article/list?appid=appid&token=token
     * {
     *  "parentId":""
     * }
     * @apiSuccess {number} code 			         code
     * @apiSuccess {String} msg 			         msg
     * @apiSuccessExample {json} 示例
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": "ok"
     * }
     * @apiError (失败) {number} code 不等于0
     * @apiError (失败) {string} msg 错误文字描述
     * @apiErrorExample {json} 示例
     * HTTP/1.1 200 OK
     * {
     * "code": 1,
     * "msg": "fail"
     * }
     */


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @SJson
    public Object list(@RequestBody NutMap map, HttpServletRequest req) {

        try {
            log.debug("map::" + Json.toJson(map));
            String parentId= map.getString("parentId","");
            List<Cms_article> cmsArticlelList= cmsArticleService.list(parentId);
            return Result.success("ok",cmsArticlelList);
        } catch (Exception e) {
            return Result.error("fail");
        }
    }
    /**
     * @api {post} /open/api/cms/article/get?appid=appid&token=token 测试API
     * @apiGroup cms
     * @apiVersion 1.0.0
     * @apiPermission token
     * @apiParam {Object}	data 				    数据对象
     * @apiParamExample {json} 示例
     * POST /open/api/cms/channel/get?appid=appid&token=token
     * {
     *  "id":""
     * }
     * @apiSuccess {number} code 			         code
     * @apiSuccess {String} msg 			         msg
     * @apiSuccessExample {json} 示例
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": "ok"
     * }
     * @apiError (失败) {number} code 不等于0
     * @apiError (失败) {string} msg 错误文字描述
     * @apiErrorExample {json} 示例
     * HTTP/1.1 200 OK
     * {
     * "code": 1,
     * "msg": "fail"
     * }
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @SJson
    public Object get(@RequestBody NutMap map, HttpServletRequest req) {

        try {
            log.debug("map::" + Json.toJson(map));
            String id= map.getString("id","");
            Cms_article cmsArticle= cmsArticleService.get(id);
            return Result.success("ok",cmsArticle);
        } catch (Exception e) {
            return Result.error("fail");
        }
    }
    /**
     * @api {post} /open/api/cms/article/listPage?appid=appid&token=token 测试API
     * @apiGroup cms
     * @apiVersion 1.0.0
     * @apiPermission token
     * @apiParam {Object}	data 				    数据对象
     * @apiParamExample {json} 示例
     * POST /open/api/cms/article/listPage?appid=appid&token=token
     * {
     *  "pageNumber":"",
     *  "pageSize":"",
     *  "channelId":"",
     * }
     * @apiSuccess {number} code 			         code
     * @apiSuccess {String} msg 			         msg
     * @apiSuccessExample {json} 示例
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": "ok"
     * }
     * @apiError (失败) {number} code 不等于0
     * @apiError (失败) {string} msg 错误文字描述
     * @apiErrorExample {json} 示例
     * HTTP/1.1 200 OK
     * {
     * "code": 1,
     * "msg": "fail"
     * }
     */
    @RequestMapping(value = "/listPage", method = RequestMethod.POST)
    @SJson
    public Object listPage(@RequestBody NutMap map, HttpServletRequest req) {

        try {
            log.debug("map::" + Json.toJson(map));
            String pageNumber= map.getString("pageNumber","1");
            String pageSize= map.getString("pageSize","0");
            String channelId= map.getString("channelId","");
            Pagination listPage= cmsArticleService.listPage(pageNumber,pageSize,channelId);
            return Result.success("ok",listPage.getList());
        } catch (Exception e) {
            return Result.error("fail");
        }
    }


}
