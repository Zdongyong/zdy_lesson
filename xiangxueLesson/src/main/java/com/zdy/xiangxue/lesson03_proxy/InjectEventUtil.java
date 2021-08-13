package com.zdy.xiangxue.lesson03_proxy;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Handler;

/**
 * 创建日期：2020-04-29 on 19:35
 * 描述：
 * 作者：zhudongyong
 */
public class InjectEventUtil {


    public static void injectEvent(final Activity activity){
        Class<? extends Activity> clazz = activity.getClass();//反射获取
        Method[] declaredMethods = clazz.getDeclaredMethods();//获取全部方法
        for(Method method:declaredMethods){
//            方法1：
//            if (method.isAnnotationPresent(OnClick.class)){//是否标记OnClick注解
//                OnClick injectValue = method.getAnnotation(OnClick.class);//获取注解
//                int[] ids = injectValue.id();//获取当前的id
//                for (int id : ids) {
//                    final View view = activity.findViewById(id);
//                    final Object o = Proxy.newProxyInstance(InjectEventUtil.class.getClassLoader(),
//                            new Class[]{View.OnClickListener.class},
//                            new InvocationHandler() {
//                        @Override
//                        public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
//                            return method.invoke(activity, view);
//                        }
//                    });
//                    view.setOnClickListener((View.OnClickListener) o);
//                }
//            }

//            方法2：
            //获取方法上的所有注解
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation:annotations){
                //获取注解上的注解
                Class<? extends Annotation> annotationType = annotation.annotationType();
                //判断是不是属于OnClickListen.class
                if (annotationType.isAnnotationPresent(OnClickListen.class)){
                    //获取类型
                    OnClickListen injectValue = annotationType.getAnnotation(OnClickListen.class);
                    Class listenType = injectValue.listenType();
                    String listenSet = injectValue.listenSet();
                    try {
                        Method idsMethod = annotationType.getDeclaredMethod("id");
                        int[] viewIds = (int[]) idsMethod.invoke(annotation);

                        idsMethod.setAccessible(true);
                        ListenerInvocationHandler handler = new ListenerInvocationHandler(activity,method);
                        Object proxyInstance = Proxy.newProxyInstance(listenType.getClassLoader(),
                                new Class[]{listenType}, handler);
                        for (int id:viewIds){
                            View view = activity.findViewById(id);
                            Method viewMethod = view.getClass().getMethod(listenSet,listenType);
                            viewMethod.invoke(view,proxyInstance);
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }


                }
            }
        }
    }

    /**
     * 还可能在自定义view注入，所以是泛型： T = Activity/View
     * @param <T>
     */
    static class ListenerInvocationHandler<T> implements InvocationHandler {

        private Method method;
        private T target;

        public ListenerInvocationHandler(T target, Method method) {
            this.target = target;
            this.method = method;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (Boolean.class == method.getReturnType()) {
                return null;
            }else {
                return this.method.invoke(target, args);
            }
        }
    }

}
