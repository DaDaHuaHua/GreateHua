package com.example.song.demo.hook;

import android.app.Application;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by SongH on 2018/7/12.
 */

public class HookUtil {
    public void hookStartActivity(Application application) {
        try {
            Class<?> amnClass = Class.forName("android.app.ActivityManagerNative");
            Field gDefaultField = amnClass.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null);

            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField  = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            Object activityManagerObj = mInstanceField.get(gDefault);

            Class<?> iActivityManagerClass = Class.forName("android.app.IActivityManager");

            StartActivity startActivity = new StartActivity(activityManagerObj);
            Object activityManagerResult = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{iActivityManagerClass, View.OnClickListener.class}, startActivity);
            mInstanceField.set(gDefault,activityManagerResult);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    class StartActivity implements InvocationHandler{

        private  Object iActivityManagerObject;

        public StartActivity(Object iActivityManagerObject) {
            this.iActivityManagerObject = iActivityManagerObject;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.i("INFO","invoke    "+method.getName());
            if ("startActivity".equals(method.getName())) {
                Log.i("INFO", "-----------------startActivity--------------------------");
            }
            return method.invoke(iActivityManagerObject,args);
        }
    }
}
