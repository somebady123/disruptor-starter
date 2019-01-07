package disruptor.starter.support;

import com.lmax.disruptor.WorkHandler;

/**
 * @author ding
 */
public class MyEventWorkHandler implements WorkHandler<DisruptorEvent> {

    private String workerName;

    public MyEventWorkHandler(String workerName) {
        this.workerName = workerName;
    }

    @Override
    public void onEvent(DisruptorEvent event) throws Exception {
        System.out.println(workerName + " handle event:" + event);
    }
}
