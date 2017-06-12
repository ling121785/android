package com.example.businessmodule.core;
import com.example.businessmodule.business.BaseBusiness;
import com.example.businessmodule.event.BaseEvent;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 任务管理器 <br/>
 * 管理所有任务，包含中断所有任务，中断特定任务等 <br/>
 * <p/>
 * 添加任务等待队列，最多同时执行20个任务。
 */
public class BusinessManager {

    private static final int MAX_EXECTURE_BUSINESS = 20;// 最多同时执行20个任务

    private static BusinessManager instance = null;

    public static BusinessManager getInstance() {
        if (instance == null) {
            instance = new BusinessManager();
        }
        return instance;
    }

    private List<BaseBusiness> execBusinessList;
    private List<BaseBusiness> waitBusinessList;

    private BusinessManager() {
        execBusinessList = new ArrayList<BaseBusiness>();
        waitBusinessList = new ArrayList<BaseBusiness>();
    }

    /**
     * 插入新任务
     * @param business
     */
    private synchronized void insertBusiness(BaseBusiness business) {
        if (execBusinessList.size() < MAX_EXECTURE_BUSINESS) {
             business.execute();
            execBusinessList.add(business);
        } else {
            waitBusinessList.add(business);
        }
    }

    /**
     * 移除执行完成的任务
     * @param event
     */
    private synchronized void removeExecBusiness(Object event) {
        Iterator<BaseBusiness> iterator = execBusinessList.iterator();
        while (iterator.hasNext()) {
            BaseBusiness business = iterator.next();
            if (business.getEvent().equals(event)) {
                execBusinessList.remove(business);
                return;
            }
        }
    }

    /**
     * 处理等待的任务，任务有空闲，则移动到执行队列
     */
    private synchronized void execWaitBusiness() {
        if (waitBusinessList.size() > 0 && execBusinessList.size() < MAX_EXECTURE_BUSINESS) {
            waitBusinessList.get(0).execute();
            execBusinessList.add(waitBusinessList.get(0));
            waitBusinessList.remove(0);
        }
    }

    /**
     * 中断正在执行的任务
     *
     * @param event
     */
    public synchronized void abortBusiness(Object event) {
        try{
            for (BaseBusiness business : waitBusinessList) {
                if (business.getEvent().equals(event)) {
                    waitBusinessList.remove(business);
                    return;
                }
            }
            for (BaseBusiness business : execBusinessList) {
                if (business.getEvent().equals(event)) {
                    business.abort();
                    execBusinessList.remove(business);
                    return;
                }
            }
        }catch (Exception e){
            Logger.i(e.getMessage());
        }

    }

    /**
     * 是否有任务
     *
     *
     */
    public synchronized BaseEvent getEvent(long eventId) {
        for (BaseBusiness business : waitBusinessList) {
            if (business.getEvent().getEventId()==(eventId)) {
                return business.getEvent();
            }
        }
        for (BaseBusiness business : execBusinessList) {
            if (business.getEvent().getEventId()==(eventId)) {
                return business.getEvent();
            }
        }
        return null;
    }



    /**
     * 销毁所有任务
     */
    public synchronized void abortAllBusiness() {
        waitBusinessList.clear();
        for (BaseBusiness business : execBusinessList) {
            business.abort();
        }
        execBusinessList.clear();
    }

    /**
     * 上层调用这个接口执行任务
     *
     * @param business
     * @param callback
     */
    public void executeBusiness(BaseBusiness business, final BaseBusiness.BusinessCallback callback) {
        business.setCallback(business.new BusinessCallback() {
            @Override
            public void response(BaseEvent event) {
                callback.response(event);

                removeExecBusiness(event);
                execWaitBusiness();
            }
        });
        insertBusiness(business);
    }
}
