package disruptor.starter.support;

import com.lmax.disruptor.EventFactory;


/**
 * 初始化event factory
 *
 * @author ding
 */
public class MyEventFactory implements EventFactory<DisruptorEvent> {
    @Override
    public DisruptorEvent newInstance() {
        return new DisruptorEvent();
    }
}
