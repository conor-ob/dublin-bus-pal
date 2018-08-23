package ie.dublinbuspal

import org.junit.Assert.*
import org.junit.Test

abstract class AbstractDataClassTest<T> {

    abstract fun newInstance1(): T

    abstract fun newInstance2(): T

    @Test
    fun testNotSame() {
        assertNotSame(newInstance1(), newInstance1())
        assertNotSame(newInstance2(), newInstance2())
    }

    @Test
    fun testEquals() {
        assertEquals(newInstance1(), newInstance1())
        assertEquals(newInstance2(), newInstance2())
    }

    @Test
    fun testNotEquals() {
        assertNotEquals(newInstance1(), newInstance2())
    }

    @Test
    fun testEqualsHashCode() {
        assertEquals(newInstance1()!!.hashCode(), newInstance1()!!.hashCode())
        assertEquals(newInstance2()!!.hashCode(), newInstance2()!!.hashCode())
    }

    @Test
    fun testHashCodeNotEquals() {
        assertNotEquals(newInstance1()!!.hashCode(), newInstance2()!!.hashCode())
    }

}
