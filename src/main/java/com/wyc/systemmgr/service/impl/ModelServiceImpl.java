package com.wyc.systemmgr.service.impl;

import com.wyc.core.base.exception.BaseException;
import com.wyc.core.shiro.CurrentUserHelper;
import com.wyc.core.util.BeanUtil;
import com.wyc.core.util.DataConvertUtil;
import com.wyc.core.util.StringUtil;
import com.wyc.systemmgr.dao.ModelDao;
import com.wyc.systemmgr.entity.Model;
import com.wyc.systemmgr.service.IModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ModelServiceImpl implements IModelService {
    @Autowired
    private ModelDao mdeolDao;

    @Override
    public void save(Map<String, Object> param) throws BaseException {
        String mode = String.valueOf(param.get("mode"));
        String seq = String.valueOf(param.get("seq"));
        if(seq.equals("")){
            param.put("seq","1");
        }
        param.put("beanName",Model.class.getCanonicalName());
        Model model = (Model) BeanUtil.convertToBean(param);
        if ("insert".equals(mode)){
            model.setId(StringUtil.getUUID());
            model.setCreated_user_id(CurrentUserHelper.getId());
            mdeolDao.insert(model);
        }else if("update".equals(mode)){
            model.setUpdate_user_id(CurrentUserHelper.getId());
            mdeolDao.update(model);
        }
    }

    @Override
    public void delete(String id) throws BaseException {
        int count = mdeolDao.hasChildren(id);
        if(count==0){
            mdeolDao.delete(id);
        }else{
            throw new BaseException(-1, "当前模块有子模块，请先删除子模块！");
        }
    }

    @Override
    public List<Map> find(Map params){
        List<Map> datas  = mdeolDao.find(params);
        return DataConvertUtil.convertResultToTreeData(datas);

    }

    @Override
    public List<Map> findForMenu(){
        List<Map> datas = mdeolDao.findForMenu(CurrentUserHelper.getId());
        return DataConvertUtil.convertResultToTreeData(datas);
    }
    @Override
    public List<Map> findByUserId(String userId){
        List<Map> datas = mdeolDao.findByUserId(userId);
        return DataConvertUtil.convertResultToTreeData(datas);
    }

    @Transactional
    @Override
    public void saveUserModel(List<Map> datas){
        mdeolDao.delUserModel(String.valueOf(datas.get(0).get("userId")));
        mdeolDao.insertUserModel(datas);
    }


}