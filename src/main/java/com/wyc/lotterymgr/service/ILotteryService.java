package com.wyc.lotterymgr.service;

import com.wyc.core.base.exception.BaseException;
import com.wyc.core.base.service.IBaseService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */
public interface ILotteryService extends IBaseService {
    Map<String,Object> getLotteryUserNum(Map param) throws BaseException;

    void addPrize( List<Map> datas,String lotteryId);

    List<Map> findPrize(String lotteryId);

    List<Map> findAllLottery();

    List<Map> findDecide(Map param);

    List<Map> findUnDecide(Map param);

    List<Map> findJoin(Map param);

    void setDecide(Map param);

    void setUnDecide(Map param);

    Map getData(String lotteryId);

    void resetLottery(String lotteryId);

    void setUserPrize(Map param);

    void setCurrentPrize(String currentPrizeId,String lotteryId);

    List<Map> findWinUser(Map param);

    Map queryForBarChart(Map param);

    void deleteUser(Map param);

    void saveImg(Map<String, Object> param,MultipartFile uploadFile) throws BaseException;

    void deleteImg(String id) throws BaseException;

    List<Map> findImg(Map param) throws BaseException;

    void setImgUse(String id);

    String selectUseImg();
}
