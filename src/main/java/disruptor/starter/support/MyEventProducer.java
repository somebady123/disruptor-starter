package disruptor.starter.support;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * Created by hupeng on 2015/1/1.
 */
public class MyEventProducer {

    private RingBuffer<MyEvent> ringBuffer;

    public MyEventProducer(RingBuffer<MyEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg TRANSLATOR = new EventTranslatorOneArg<MyEvent, Long>() {

        @Override
        public void translateTo(MyEvent event, long sequence, Long value) {
            event.setValue(value);
        }
    };
    
    public void onData(final Long value) {
        ringBuffer.publishEvent(TRANSLATOR,value);
    }
}
