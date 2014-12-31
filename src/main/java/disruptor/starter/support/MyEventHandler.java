package disruptor.starter.support;

import com.lmax.disruptor.EventHandler;

/**
 * Created by hupeng on 2015/1/1.
 */
public class MyEventHandler implements EventHandler<MyEvent> {
    @Override
    public void onEvent(MyEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(event);
    }
}
