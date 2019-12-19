package com.wyc.lotterymgr.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.wyc.core.base.entity.Pagination;
import com.wyc.core.base.exception.BaseException;
import com.wyc.core.util.CommonUtility;
import com.wyc.core.util.PaginationUtil;
import com.wyc.lotterymgr.service.ILotteryService;
import com.wyc.lotterymgr.util.QRCodeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */

@RestController
@RequestMapping(value = "/LotteryMgr/lottery")
public class LotteryController {

    private static final Logger logger = Logger.getLogger(LotteryController.class);


    private static String port;

    @Value("${server.port}")
    public void setPort(String port) {
        this.port = port;
    }

    private static String ip;

    @Value("${server.ip}")
    public void setIp(String ip) {
        this.ip = ip;
    }


    @Autowired
    private ILotteryService lotteryService;


    @PostMapping("/save")
    public String save(@RequestParam Map<String, Object> param) throws BaseException {
        try{
            lotteryService.save(param);
        }catch (BaseException e){
            return CommonUtility.constructResultJson("-1",e.getMessage());
        }
        return CommonUtility.constructResultJson("0","操作成功！");
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) throws BaseException {
        lotteryService.delete(id);
        return CommonUtility.constructResultJson("0","操作成功！");
    }

    @PostMapping("/find")
    public Pagination find(@RequestParam Map<String, Object> param)throws BaseException {
        int pageNum = Integer.valueOf(param.get("page").toString());
        int pageSize = Integer.valueOf(param.get("rows").toString());
        PageHelper.startPage(pageNum, pageSize);
        List<Map> userList = lotteryService.find(param);
        Pagination result = PaginationUtil.getPageList(userList);
        return result;
    }
    @GetMapping("/findAllLottery")
    public String findAllLottery(){
        List<Map> datas = lotteryService.findAllLottery();
        return JSON.toJSONString(datas);
    }

    @GetMapping("/getQRCode/{id}/{lottery_name}")
    public void getQRCode(@PathVariable("id") String lottery_id,@PathVariable("lottery_name") String lottery_name, HttpServletResponse response) throws Exception {
        String url = "http://"+ip+":"+port+"/LotteryMgr/LotteryUser.html?lottery_id="+lottery_id+"&lottery_name="+ URLEncoder.encode(URLEncoder.encode(lottery_name, "utf-8"));
        QRCodeUtil.creatRrCode(url,500,500,response);
    }

    @GetMapping("/getDecideQRCode/{id}/{prize_id}/{lottery_name}")
    public void getDecideQRCode(@PathVariable("id") String lottery_id,@PathVariable("prize_id") String prize_id,@PathVariable("lottery_name") String lottery_name, HttpServletResponse response) throws Exception {
        String url = "http://"+ip+":"+port+"/LotteryMgr/LotteryUser.html?lottery_id="+lottery_id+"&prize_id="+prize_id+"&lottery_name="+ URLEncoder.encode(URLEncoder.encode(lottery_name, "utf-8"));
        QRCodeUtil.creatRrCode(url,500,500,response);
    }

    @GetMapping("/resetLottery/{id}")
    public String resetLottery(@PathVariable("id") String lottery_id) throws BaseException {
        lotteryService.resetLottery(lottery_id);
        return CommonUtility.constructResultJson("0","操作成功！");
    }

    @PostMapping("/LotteryUser")
    public String LotteryUser(@RequestBody Map<String, Object> param) throws BaseException {
        try {
            Map data = lotteryService.getLotteryUserNum(param);
            return CommonUtility.constructResultJson("0", "操作成功！", data);
        }catch (Exception e){
            e.printStackTrace();
            return CommonUtility.constructResultJson("-1","服务器繁忙，请重试！");
        }
    }

    @PostMapping("/addPrize")
    public String addPrize(@RequestBody  Map param )throws BaseException {
        String lotteryId =String.valueOf(param.get("lottery_id"));
        List<Map> datas = (List)param.get("datas");
        lotteryService.addPrize(datas, lotteryId);
        return CommonUtility.constructResultJson("0","操作成功！");
    }

    @PostMapping("/findPrize")
    public Pagination finddPrize(@RequestParam Map<String, Object> param)throws BaseException {
        PageHelper.startPage(1, 99);
        String lotteryId = String.valueOf(param.get("lottery_id"));
        List<Map> userList = lotteryService.findPrize(lotteryId);
        Pagination result = PaginationUtil.getPageList(userList);
        return result;
    }

    @PostMapping("/findDecide")
    public Pagination findDecide(@RequestParam Map<String, Object> param)throws BaseException {
        PageHelper.startPage(1, 99);
        List<Map> userList = lotteryService.findDecide(param);
        Pagination result = PaginationUtil.getPageList(userList);
        return result;
    }

    @PostMapping("/findUnDecide")
    public Pagination findUnDecide(@RequestParam Map<String, Object> param)throws BaseException {
        int pageNum = Integer.valueOf(param.get("page").toString());
        int pageSize = Integer.valueOf(param.get("rows").toString());
        PageHelper.startPage(pageNum, pageSize);
        List<Map> userList = lotteryService.findUnDecide(param);
        Pagination result = PaginationUtil.getPageList(userList);
        return result;
    }
    @PostMapping("/findJoin")
    public Pagination findJoin(@RequestParam Map<String, Object> param)throws BaseException {
        int pageNum = Integer.valueOf(param.get("page").toString());
        int pageSize = Integer.valueOf(param.get("rows").toString());
        PageHelper.startPage(pageNum, pageSize);
        List<Map> userList = lotteryService.findJoin(param);
        Pagination result = PaginationUtil.getPageList(userList);
        return result;
    }



    @PostMapping("/setDecide")
    public String setDecide(@RequestBody Map<String, Object> param)throws BaseException {
        lotteryService.setDecide(param);
        return CommonUtility.constructResultJson("0","操作成功！");
    }

    @PostMapping("/setUnDecide")
    public String setUnDecide(@RequestBody Map<String, Object> param)throws BaseException {
        lotteryService.setUnDecide(param);
        return CommonUtility.constructResultJson("0","操作成功！");
    }

    @GetMapping("/lotteryData/getData/{lottery}")
    public String getData(@PathVariable("lottery") String lottery_id) throws Exception {
        Map data = lotteryService.getData(lottery_id);
        return CommonUtility.constructResultJson("0","操作成功！",data);
    }

    @PostMapping("/lotteryData/setUserPrize")
    public void setUserPrize(@RequestBody Map<String, Object> param)  throws Exception {
        lotteryService.setUserPrize(param);
    }


    @GetMapping("/lotteryData/setCurrentPrize/{currentPrizeId}/{lotteryId}")
    public void setCurrentPrize(@PathVariable("currentPrizeId") String currentPrizeId,@PathVariable("lotteryId") String lotteryId) throws Exception {
        lotteryService.setCurrentPrize(currentPrizeId,lotteryId);
    }


    @PostMapping("/findWinUser")
    public Pagination findWinUser(@RequestParam Map<String, Object> param)throws BaseException {
        PageHelper.startPage(1, 200);
        List<Map> userList = lotteryService.findWinUser(param);
        Pagination result = PaginationUtil.getPageList(userList);
        return result;
    }

    @PostMapping("/queryForBarChart")
    public String queryForBarChart(Map param) throws BaseException{
        Map chartInfo = lotteryService.queryForBarChart(param);
        return CommonUtility.constructResultJson("0","操作成功！",chartInfo);
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestBody Map<String, Object> param) throws BaseException {
        lotteryService.deleteUser(param);
        return CommonUtility.constructResultJson("0","操作成功！");
    }


    @PostMapping("/saveImg")
    public String saveImg(@RequestParam Map<String, Object> param,@RequestParam("file") MultipartFile file) throws Exception{
        lotteryService.saveImg(param,file);
        return CommonUtility.constructResultJson("0","操作成功！");
    }

    @GetMapping("/deleteImg/{id}")
    public String deleteImg(@PathVariable("id") String id) throws BaseException{
        lotteryService.deleteImg(id);
        return CommonUtility.constructResultJson("0","操作成功！");
    }

    @PostMapping("/findImg")
    public Pagination findImg(@RequestParam Map<String, Object> param)throws BaseException{
        int pageNum = Integer.valueOf(param.get("page").toString());
        int pageSize = Integer.valueOf(param.get("rows").toString());
        PageHelper.startPage(pageNum, pageSize);
        List<Map> imgList = lotteryService.findImg(param);
        Pagination result = PaginationUtil.getPageList(imgList);
        return result;
    }


    @GetMapping("/setImgUse/{id}")
    public String setImgUse(@PathVariable("id") String id) throws BaseException{
        lotteryService.setImgUse(id);
        return CommonUtility.constructResultJson("0","操作成功！");
    }


    @GetMapping("/selectUseImg")
    public String selectUseImg() throws BaseException{
        String path = lotteryService.selectUseImg();
        Map retData = new HashMap();
        retData.put("path",path);
        return CommonUtility.constructResultJson("0","操作成功！",retData);
    }

    @PostMapping("/findBlack")
    public Pagination findBlack(@RequestParam Map<String, Object> param)throws BaseException {
        PageHelper.startPage(1, 99);
        List<Map> userList = lotteryService.findBlack(param);
        Pagination result = PaginationUtil.getPageList(userList);
        return result;
    }

    @PostMapping("/setBlack")
    public String setBlack(@RequestBody Map<String, Object> param)throws BaseException {
        lotteryService.setBlack(param);
        return CommonUtility.constructResultJson("0","操作成功！");
    }

    @PostMapping("/setUnBlack")
    public String setUnBlack(@RequestBody Map<String, Object> param)throws BaseException {
        lotteryService.setUnBlack(param);
        return CommonUtility.constructResultJson("0","操作成功！");
    }
}
