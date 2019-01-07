package disruptor.starter.support;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * 事件生产者
 *
 * @author ding
 */
public class MyEventProducer {

    private RingBuffer<DisruptorEvent> ringBuffer;

    public MyEventProducer(RingBuffer<DisruptorEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 定义事件转换器
     * 定义事件的源头，里面的事件转换器（EventTranslatorOneArg）会把输出的参数转为我们的事件类型
     */
    private static final EventTranslatorOneArg TRANSLATOR = new EventTranslatorOneArg<DisruptorEvent, Long>() {
        @Override
        public void translateTo(DisruptorEvent event, long sequence, Long value) {
            event.setValue(value);
        }
    };

    public void onData(final Long value) {
        System.out.println("生产者打印：" + value);
        ringBuffer.publishEvent(TRANSLATOR, value);
    }
}
