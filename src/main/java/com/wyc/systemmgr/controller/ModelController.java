package com.wyc.systemmgr.controller;

import com.wyc.core.base.exception.BaseException;
import com.wyc.core.util.CommonUtility;
import com.wyc.systemmgr.service.IModelService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */

@RestController
@RequestMapping(value = "/SystemMgr/model")
public class ModelController {

    private static final Logger logger = Logger.getLogger(ModelController.class);

    @Autowired
    private IModelService modelService;


    @PostMapping("/save")
    public String save(@RequestParam Map<String, Object> param) throws BaseException {
        modelService.save(param);
        return CommonUtility.constructResultJson("0","操作成功！");
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) throws BaseException {
        try{
            modelService.delete(id);
            return CommonUtility.constructResultJson("0","操作成功！");
        }catch (BaseException e){
            e.printStackTrace();
            return CommonUtility.constructResultJson("-1",e.getMessage());
        }
    }
    @GetMapping("/find")
    public List<Map> find() throws BaseException {
        List<Map> menuList = modelService.find(null);
        return menuList;
    }

    @GetMapping("/findForMenu")
    public List<Map> findForMenu(){
        List<Map> menuList = modelService.findForMenu();
        return menuList;
    }


    @GetMapping("/findByUserId/{userId}")
    public List<Map> findByUserId(@PathVariable("userId") String userId){
        List<Map> modelList = modelService.findByUserId(userId);
        return modelList;
    }


    @PostMapping("/saveUserModel")
    public String saveUserModel(@RequestBody List<Map> datas) throws BaseException {
        if(datas.size()>0){
            modelService.saveUserModel(datas);
        }
        return CommonUtility.constructResultJson("0","操作成功！");
    }



}
