package ie.dublinbuspal.android.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object ImageUtils {

    private val bitmapCache = HashMap<Int, BitmapDescriptor>()

    @JvmStatic
    fun drawableToBitmap(context: Context, drawableId: Int) : BitmapDescriptor {
        val cachedValue = bitmapCache[drawableId]
        if (cachedValue != null) {
            return cachedValue
        }
        val vectorDrawable = context.getDrawable(drawableId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        vectorDrawable.draw(Canvas(bitmap))
        val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap)
        bitmapCache[drawableId] = bitmapDescriptor
        return bitmapDescriptor
    }

}
