package disruptor.starter.eventhandler;

import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import disruptor.starter.support.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by hupeng on 2015/1/1.
 */
public class MyEventMain {

    /**
     *output:
     * MyEvent{value=0}
     * MyEvent{value=0}
     * MyEvent{value=1}
     * MyEvent{value=1}
     * MyEvent{value=2}
     * MyEvent{value=2}
     * MyEvent{value=3}
     * MyEvent{value=3}
     * MyEvent{value=4}
     * MyEvent{value=4}
     * MyEvent{value=5}
     * MyEvent{value=5}
     * MyEvent{value=6}
     * MyEvent{value=6}
     * MyEvent{value=7}
     * MyEvent{value=7}
     * MyEvent{value=8}
     * MyEvent{value=8}
     * MyEvent{value=9}
     * MyEvent{value=9}
     *
     * one msg processed by all the handler..
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        int bufferSize = 1024;

        Disruptor<MyEvent> disruptor = new Disruptor<MyEvent>(new MyEventFactory(),
                bufferSize, executorService, ProducerType.SINGLE, new YieldingWaitStrategy());
        disruptor.handleExceptionsWith(new IgnoreExceptionHandler());

        disruptor.handleEventsWith(new MyEventHandler(),new MyEventHandler());
//        disruptor.handleEventsWith(new MyEventHandler()).then(new MyEventHandler());  //Pipeline
        RingBuffer<MyEvent> ringBuffer = disruptor.start();

        MyEventProducer producer = new MyEventProducer(ringBuffer);
        for (long i = 0; i < 10; i++) {
            producer.onData(i);
            Thread.sleep(1000);// wait for task execute....
        }

        disruptor.shutdown();

        ExecutorsUtils.shutdownAndAwaitTermination(executorService, 60, TimeUnit.SECONDS);
    }
}
