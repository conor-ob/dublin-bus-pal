package ie.dublinbuspal.base

class CoordinateTest : AbstractDataClassTest<Coordinate>() {

    override fun newInstance1() = Coordinate(53.34782, -6.25933)

    override fun newInstance2() = Coordinate(53.12398, -6.20941)

}
