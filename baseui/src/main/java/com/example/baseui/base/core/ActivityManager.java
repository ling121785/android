package com.example.baseui.base.core;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;


public class ActivityManager {
    private static List<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {

    }

    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void finishActivity(Activity activity) {
        activityStack.remove(activity);
    }

    public void finishActivity(Class cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i) != null
                    && activityStack.get(i).getClass().equals(cls)
                    && !activityStack.get(i).isFinishing()) {
                activityStack.get(i).finish();
                break;
            }

        }
    }


    public Activity currentActivity() {
        if (activityStack.size() == 0) {
            return null;
        }
        Activity activity = activityStack.get(0);
        return activity;
    }

    public boolean isTopActivity(Activity activity) {
        if (activity == null)
            return false;
        return activity.equals(currentActivity());
    }

    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new ArrayList<Activity>();
        }
        activityStack.add(0, activity);
    }

    public void finishAllActivityUntileFindOne(Class cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                continue;
            }
            if (!activity.isFinishing())
                activity.finish();
            else
                finishActivity(activity);
        }
    }

    public void finishAllActivityExceptOne(Class cls) {
        ArrayList<Activity> newStack = new ArrayList<Activity>();
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                newStack.add(activity);
                finishActivity(activity);
                continue;
            }
            if (!activity.isFinishing())
                activity.finish();
            else
                finishActivity(activity);
        }
        activityStack = newStack;

    }

    public boolean hasActivity(Class cls){
        if(activityStack==null)
            return false;
        for(int i=0;i<activityStack.size();i++){
            if (activityStack.get(i).getClass().equals(cls)&&!activityStack.get(i).isFinishing()) {
                return true;
            }
        }
        return false;
    }


    public void finishAllActivity() {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (!activity.isFinishing())
                activity.finish();
            else
                finishActivity(activity);
        }
    }

    public Activity getActivity(Class cls) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (activity == null)
                continue;
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }

        return null;
    }


    public int getStackCount() {
        if (activityStack != null) {
            return activityStack.size();
        }
        return 0;
    }

    public List<Activity> getActivityStack() {
        if (activityStack != null) {
            return activityStack;
        }
        return null;
    }
}
