package com.wyc.lotterymgr.entity;


import java.sql.Timestamp;
import java.util.Date;

public class Lottery {
    private  String id;


    private  String lottery_name;


    private Date lottery_date;


    private  String lottery_address;

    //0
    private  long total_join;

    //"0"
    private  String is_end;


    private  String created_user_id;


    private  Timestamp created_time;


    public  String  getId(){
        return  this.id;
    };
    public  void  setId(String id){
        this.id=id;
    }

    public  String  getLottery_name(){
        return  this.lottery_name;
    };
    public  void  setLottery_name(String lottery_name){
        this.lottery_name=lottery_name;
    }

    public  Date  getLottery_date(){
        return  this.lottery_date;
    };
    public  void  setLottery_date(Date lottery_date){
        this.lottery_date=lottery_date;
    }

    public  String  getLottery_address(){
        return  this.lottery_address;
    };
    public  void  setLottery_address(String lottery_address){
        this.lottery_address=lottery_address;
    }

    public  long  getTotal_join(){
        return  this.total_join;
    };
    public  void  setTotal_join(long total_join){
        this.total_join=total_join;
    }

    public  String  getIs_end(){
        return  this.is_end;
    };
    public  void  setIs_end(String is_end){
        this.is_end=is_end;
    }

    public  String  getCreated_user_id(){
        return  this.created_user_id;
    };
    public  void  setCreated_user_id(String created_user_id){
        this.created_user_id=created_user_id;
    }

    public  Timestamp  getCreated_time(){
        return  this.created_time;
    };
    public  void  setCreated_time(Timestamp created_time){
        this.created_time=created_time;
    }
}