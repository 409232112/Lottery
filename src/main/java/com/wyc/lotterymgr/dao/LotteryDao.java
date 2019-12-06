package com.wyc.lotterymgr.dao;

import com.wyc.core.base.dao.BaseDao;
import com.wyc.core.base.exception.BaseException;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface LotteryDao extends BaseDao {

    void deletePrizeBylotteryId(String id);

    void deleteUserBylotteryId(String id);

    Map<String,Object> getLotteryUserNum(Map param);

    void insertLotteryUser(Map param);

    void addLotteryTotalJoin(String lotteryId);

    void subLotteryTotalJoin(String lotteryId);



    void addPrize(@Param("datas") List<Map> datas);

    void deletePrize(String id);

    void deleteDecidePrize(String prize_id);


    void updatePrize(Map data);

    List<Map> findPrize(String lotteryId);

    List<Map> findAllLottery();

    List<Map> findDecide(Map param);

    List<Map> findUnDecide(Map param);

    List<Map> findJoin(Map param);

    void setDecide(Map param);

    void setUnDecide(Map param);

    int getTotalJoin(String lotteryId);

    List<Map> getLotteryUserNumForWeb(String lotteryId);

    List<Map> findPrizeForWeb(String lotteryId);

    void resetLottery (String lotteryId);

    void resetCurrentPrize (String lotteryId);

    void setUserPrize(Map param);

    void setCurrentPrize(@Param("currentPrizeId")String currentPrizeId,@Param("lotteryId")String lotteryId);

    String selectCurrentPrize(String lotteryId);

    List<Map> findWinUser(Map param);

    Map queryForBarChart(Map param);

    List<Map> selectDecideNum(String lotteryId);

    void deleteUser(String id);

    void insertImg(Map<String, Object> param) ;

    void updateImg(Map<String, Object> param) ;

    String getFilePath(String id);

    void deleteImg(String id);

    List<Map> findImg(Map param);

    void setImgUseNull();

    void setImgUse(String id);

    String selectUseImg();





}