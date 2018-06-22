package ie.dublinbuspal.android.data.local.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

public abstract class AbstractEntityTest<T> {

    protected abstract T newInstance1();

    protected abstract T newInstance2();

    @Test
    public void testNotSame() {
        assertNotSame(newInstance1(), newInstance1());
        assertNotSame(newInstance2(), newInstance2());
    }

    @Test
    public void testEquals() {
        assertEquals(newInstance1(), newInstance1());
        assertEquals(newInstance2(), newInstance2());
    }

    @Test
    public void testNotEquals() {
        assertNotEquals(newInstance1(), newInstance2());
    }

    @Test
    public void testEqualsHashCode() {
        assertEquals(newInstance1().hashCode(), newInstance1().hashCode());
        assertEquals(newInstance2().hashCode(), newInstance2().hashCode());
    }

    @Test
    public void testHashCodeNotEquals() {
        assertNotEquals(newInstance1().hashCode(), newInstance2().hashCode());
    }

}
