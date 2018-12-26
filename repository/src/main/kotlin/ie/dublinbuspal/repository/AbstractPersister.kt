package ie.dublinbuspal.repository

import com.nytimes.android.external.store3.base.RecordProvider
import com.nytimes.android.external.store3.base.RecordState
import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.data.dao.PersisterDao
import ie.dublinbuspal.util.TimeUtils
import org.threeten.bp.Instant

abstract class AbstractPersister<Raw, Parsed, Key>(
        private val memoryPolicy: MemoryPolicy,
        private val persisterDao: PersisterDao
) : RoomPersister<Raw, Parsed, Key>, RecordProvider<Key> {

    private val lifespan: Long by lazy { memoryPolicy.expireAfterTimeUnit.toSeconds(memoryPolicy.expireAfterWrite) }

    override fun getRecordState(key: Key): RecordState {
        val lastUpdated = persisterDao.select(key.toString())
                .blockingGet()
                .lastUpdated
        return getRecordStateFromTimestamp(lastUpdated)
    }

    private fun getRecordStateFromTimestamp(lastUpdated: Instant): RecordState {
        val elapsedSeconds = TimeUtils.now().epochSecond - lastUpdated.epochSecond
        if (elapsedSeconds > lifespan) {
            return RecordState.STALE
        }
        return RecordState.FRESH
    }

}
