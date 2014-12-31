package disruptor.starter.workhandler;

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
public class WorkerPoolStarter {
    /**
     * output:
     * worker-1 handle event:MyEvent{value=0}
     * worker-2 handle event:MyEvent{value=1}
     * worker-1 handle event:MyEvent{value=2}
     * worker-2 handle event:MyEvent{value=3}
     * worker-1 handle event:MyEvent{value=4}
     * worker-2 handle event:MyEvent{value=5}
     * worker-1 handle event:MyEvent{value=6}
     * worker-2 handle event:MyEvent{value=7}
     * worker-1 handle event:MyEvent{value=8}
     * worker-2 handle event:MyEvent{value=9}
     * one msg can only be processed by one handler....
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        int bufferSize = 1024;

        Disruptor<MyEvent> disruptor = new Disruptor<MyEvent>(new MyEventFactory(),
                bufferSize, executorService, ProducerType.SINGLE, new YieldingWaitStrategy());
        disruptor.handleExceptionsWith(new IgnoreExceptionHandler());
        disruptor.handleEventsWithWorkerPool(new MyEventWorkHandler("worker-1"),new MyEventWorkHandler("worker-2"));
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
