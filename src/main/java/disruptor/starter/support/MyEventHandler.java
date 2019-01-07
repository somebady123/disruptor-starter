package disruptor.starter.support;

import com.lmax.disruptor.EventHandler;


/**
 * @author ding
 */
public class MyEventHandler implements EventHandler<DisruptorEvent> {

    @Override
    public void onEvent(DisruptorEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("消费者输出打印：" + event);
    }
}
