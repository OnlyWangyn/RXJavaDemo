package rx.com.wyn.rxjavademo.demo;

/**
 * Created by wangyn on 19/6/23.
 */

public class MockService {
   public void getName(String url, StringCallback stringCallBack){
        stringCallBack.callString("Hello world!");
    }

    public void getAge(String url, IntCallback intCallback){
        intCallback.callInt(18);
    }
}
