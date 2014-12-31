package disruptor.starter.support;

/**
 * Created by hupeng on 2015/1/1.
 */
public class MyEvent {

    private long value;

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MyEvent{" +
                "value=" + value +
                '}';
    }
}
