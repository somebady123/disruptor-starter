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
 * @author ding
 */
public class TestMain {

    /**
     * output:
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
     * <p>
     * one msg processed by all the handler..
     */
    public static void main(String[] args) throws InterruptedException {

        /**
         * 构建Disruptor 参数
         */
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        int bufferSize = 1024;

        /**
         * 初始化Disruptor
         */
        Disruptor<DisruptorEvent> disruptor = new Disruptor<DisruptorEvent>(new MyEventFactory(), bufferSize, executorService, ProducerType.SINGLE, new YieldingWaitStrategy());
        /**
         * 注册事件处理器，可放多个
         */
        disruptor.handleEventsWith(new MyEventHandler(), new MyEventHandler());
        /**
         * 注册事件处理器（Pipeline）
         */
//        disruptor.handleEventsWith(new MyEventHandler()).then(new MyEventHandler());

        /**
         * 注册异常处理器
         */
        disruptor.handleExceptionsWith(new IgnoreExceptionHandler());

        /**
         * start
         */
        RingBuffer<DisruptorEvent> ringBuffer = disruptor.start();

        MyEventProducer producer = new MyEventProducer(ringBuffer);
        for (long i = 0; i < 10; i++) {
            producer.onData(i);
            // wait for task execute....
            Thread.sleep(1000);
        }

        disruptor.shutdown();

        ExecutorsUtils.shutdownAndAwaitTermination(executorService, 60, TimeUnit.SECONDS);
    }
}
