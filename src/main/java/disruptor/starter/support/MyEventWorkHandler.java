package disruptor.starter.support;

import com.lmax.disruptor.WorkHandler;

/**
 * Created by hupeng on 2015/1/1.
 */
public class MyEventWorkHandler implements WorkHandler<MyEvent> {

    private String workerName;

    public MyEventWorkHandler(String workerName) {
        this.workerName = workerName;
    }

    @Override
    public void onEvent(MyEvent event) throws Exception {
        System.out.println(workerName + " handle event:" + event);
    }
}
