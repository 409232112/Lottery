package com.wyc.lotterymgr.service.impl;

import com.wyc.core.base.exception.BaseException;
import com.wyc.core.shiro.CurrentUserHelper;
import com.wyc.core.util.BeanUtil;
import com.wyc.core.util.FileUtil;
import com.wyc.core.util.StringUtil;
import com.wyc.lotterymgr.dao.LotteryDao;
import com.wyc.lotterymgr.entity.Lottery;
import com.wyc.lotterymgr.service.ILotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * Created by wangyc on 2019/10/31.
 */
@Service
public class LotteryServiceImpl implements ILotteryService {

    @Autowired
    private LotteryDao lotteryDao;

    private static String uploadFileDir;

    @Value("${filesDir.uploadFileDir}")
    public void setUploadFileDir(String uploadFileDir) {
        this.uploadFileDir = uploadFileDir;
    }

    @Override
    public void save(Map<String, Object> param) throws BaseException {
        String mode = String.valueOf(param.get("mode"));
        if ("insert".equals(mode)){
            param.put("id",StringUtil.getUUID());
            param.put("created_user_id",CurrentUserHelper.getId());
            lotteryDao.insert(param);
        }else if("update".equals(mode)){
            lotteryDao.update(param);
        }
    }

    @Transactional
    @Override
    public void delete(String id){
        lotteryDao.deletePrizeBylotteryId(id);
        lotteryDao.deleteUserBylotteryId(id);
        lotteryDao.delete(id);
    }

    @Override
    public List<Map> find(Map params){
        return lotteryDao.find(params);
    }

    @Override
    public List<Map> findAllLottery(){
        return lotteryDao.findAllLottery();
    }

    @Transactional
    @Override
    public Map<String,Object> getLotteryUserNum(Map param) throws BaseException{

        String lottery_id = String.valueOf(param.get("lottery_id"));

        Map userNum = lotteryDao.getLotteryUserNum(param);

        if(userNum == null){
            String id = StringUtil.getUUID();
            param.put("id",id);
            lotteryDao.insertLotteryUser(param);
            lotteryDao.addLotteryTotalJoin(lottery_id);
            userNum = lotteryDao.getLotteryUserNum(param);
        }
        return userNum;
    }

    @Transactional
    @Override
    public void addPrize( List<Map> datas,String lotteryId){
        List<Map> addList = new ArrayList<>();
        for(int i=0;i<datas.size();i++) {
            if (String.valueOf(datas.get(i).get("model")).equals("insert")) {
                addList.add(datas.get(i));
            } else if (String.valueOf(datas.get(i).get("model")).equals("update")) {
                lotteryDao.updatePrize(datas.get(i));
            } else if (String.valueOf(datas.get(i).get("model")).equals("delete")) {
                lotteryDao.deletePrize(String.valueOf(datas.get(i).get("id")));
                lotteryDao.deleteDecidePrize(String.valueOf(datas.get(i).get("id")));
            }
        }
        if(addList.size()>0){
            lotteryDao.addPrize(addList);
        }
    }

    @Override
    public List<Map> findPrize(String lotteryId){
        return lotteryDao.findPrize(lotteryId);
    }

    @Override
    public List<Map> findDecide(Map param){
        return lotteryDao.findDecide(param);
    }

    @Override
    public List<Map> findUnDecide(Map param){
        return lotteryDao.findUnDecide(param);
    }

    @Override
    public List<Map> findJoin(Map param){
        return lotteryDao.findJoin(param);
    }

    @Override
    public void setDecide(Map param){
        lotteryDao.setDecide(param);
    }

    @Override
    public void setUnDecide(Map param){
        lotteryDao.setUnDecide(param);
    }

    @Override
    public Map getData(String lotteryId){
        Map retData = new HashMap();
        String currentPrizeId = lotteryDao.selectCurrentPrize(lotteryId);
        if(String.valueOf(currentPrizeId).equals("no_start") || String.valueOf(currentPrizeId).equals("no_start") ){
            currentPrizeId="null";
        }
        List<Map> prizes = lotteryDao.findPrizeForWeb(lotteryId);

        for(int i=0;i<prizes.size();i++){
            String lottery_num = String.valueOf(prizes.get(i).get("lottery_num"));
            int prize_count = Integer.valueOf(String.valueOf(prizes.get(i).get("prize_count")));

            String[] strArray = lottery_num.split(",");
            List<String> lottery_nums_= Arrays.asList(strArray);
            List<String> lottery_nums = new ArrayList<>();

            for(int j = 0;j<lottery_nums_.size();j++){
                if(!String.valueOf(lottery_nums_.get(j)).equals("null")){
                    lottery_nums.add(lottery_nums_.get(j));
                }

            }
            int addCount = prize_count-lottery_nums.size();
            for(int j=0;j<addCount;j++){
                lottery_nums.add("??");
            }
            prizes.get(i).put("lottery_num",lottery_nums);
        }


        List<Map> decideNums = lotteryDao.selectDecideNum(lotteryId);
        Map decideNosMap = new HashMap();

        for(int i=0;i<decideNums.size();i++){
            String devide_t_prize_id = String.valueOf(decideNums.get(i).get("decide_t_prize_id"));
            String lottery_nums = String.valueOf(decideNums.get(i).get("lottery_num"));

            String[] strArray = lottery_nums.split(",");
            List<String> decide_nums= Arrays.asList(strArray);
            decideNosMap.put(devide_t_prize_id,decide_nums);


        }


        List<Map> lotteryUsers = lotteryDao.getLotteryUserNumForWeb(lotteryId);
        List<String> t_lottery_user_ids = new ArrayList<>();
        List<String> lottery_nums = new ArrayList<>();
        List<String> usernames = new ArrayList<>();
        for(int i =0;i<lotteryUsers.size();i++){
            t_lottery_user_ids.add(String.valueOf(lotteryUsers.get(i).get("id")));
            usernames.add(String.valueOf(lotteryUsers.get(i).get("username")));
            lottery_nums.add(String.valueOf(lotteryUsers.get(i).get("lottery_num")));
        }

        String lotteryImg = lotteryDao.selectUseImg();
        retData.put("lotteryImg",lotteryImg);
        retData.put("currentPrizeId",currentPrizeId);
        retData.put("decideNosMap",decideNosMap);
        retData.put("prizes",prizes);
        retData.put("t_lottery_user_ids",t_lottery_user_ids);
        retData.put("lottery_nums",lottery_nums);
        retData.put("usernames",usernames);

        return retData;
    }

    @Transactional
    @Override
    public void resetLottery(String lotteryId){
        lotteryDao.resetLottery(lotteryId);
        lotteryDao.resetCurrentPrize(lotteryId);
    }
    @Override
    public void setUserPrize(Map param){
        lotteryDao.setUserPrize(param);
    }


    @Override
    public void setCurrentPrize(String currentPrizeId,String lotteryId){
        lotteryDao.setCurrentPrize(currentPrizeId,lotteryId);
    }

    @Override
    public List<Map> findWinUser(Map param){
        return lotteryDao.findWinUser(param);
    }

    @Override
    public Map queryForBarChart(Map param){
        return lotteryDao.queryForBarChart(param);
    }

    @Transactional
    @Override
    public void deleteUser(Map param){
        String t_lottery_user_id = String.valueOf(param.get("id"));
        String lotteryId = String.valueOf(param.get("lottery_id"));
        lotteryDao.deleteUser(t_lottery_user_id);
        lotteryDao.subLotteryTotalJoin(lotteryId);
    }

    @Override
    public void saveImg(Map<String, Object> param,MultipartFile uploadFile) throws BaseException {
        String fileName = uploadFile.getOriginalFilename();

        if(!fileName.equals("")){
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            FileUtil.touchDir(uploadFileDir+CurrentUserHelper.getId()+ java.io.File.separator);

            String  server_name = StringUtil.getUUID()+suffixName;
            String filePath = uploadFileDir+ java.io.File.separator+server_name;
            try {
                uploadFile.transferTo(new java.io.File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
            param.put("file_path",server_name);

            if("update".equals(String.valueOf(param.get("mode")))){
                String oldFilePath = lotteryDao.getFilePath(String.valueOf(param.get("id")));
                FileUtil.deleteFile(oldFilePath);
            }
        }

        String mode = String.valueOf(param.get("mode"));

        if ("insert".equals(mode)){
            param.put("id",StringUtil.getUUID());
            lotteryDao.insertImg(param);
        }else if("update".equals(mode)){
            lotteryDao.updateImg(param);
        }
    }

    @Override
    public void deleteImg(String id) throws BaseException{
        String filePath = lotteryDao.getFilePath(id);
        FileUtil.deleteFile(filePath);
        lotteryDao.deleteImg(id);

    }

    @Override
    public List<Map> findImg(Map params){
        return lotteryDao.findImg(params);
    }

    @Transactional
    @Override
    public void setImgUse(String id){
        lotteryDao.setImgUseNull();
        lotteryDao.setImgUse(id);

    }

    @Override
    public String selectUseImg(){
        return lotteryDao.selectUseImg();
    }


}
