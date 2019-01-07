package disruptor.starter.support;


/**
 * @author ding
 */
public class DisruptorEvent {

    private long value;

    void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MyEvent{" + "value=" + value + '}';
    }
}
