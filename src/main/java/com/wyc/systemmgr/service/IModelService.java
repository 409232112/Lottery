package com.wyc.systemmgr.service;

import com.wyc.core.base.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface IModelService extends IBaseService {
    List<Map> findForMenu();

    List<Map> findByUserId(String userId);

    void saveUserModel(List<Map> datas);

}