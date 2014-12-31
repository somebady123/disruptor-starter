package disruptor.starter.support;

import com.lmax.disruptor.EventFactory;

/**
 * Created by hupeng on 2015/1/1.
 */
public class MyEventFactory implements EventFactory<MyEvent> {
    @Override
    public MyEvent newInstance() {
        return new MyEvent();
    }
}
