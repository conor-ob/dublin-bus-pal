package ie.dublinbuspal.repository

import com.nytimes.android.external.store3.base.RecordProvider
import com.nytimes.android.external.store3.base.RecordState
import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import com.nytimes.android.external.store3.base.room.RoomPersister
import ie.dublinbuspal.data.dao.PersisterDao
import ie.dublinbuspal.data.entity.PersisterEntity
import ie.dublinbuspal.util.InternetManager
import ie.dublinbuspal.util.TimeUtils
import java.util.concurrent.TimeUnit

abstract class LegacyAbstractPersister<Raw, Parsed, Key>(
        private val memoryPolicy: MemoryPolicy,
        private val persisterDao: PersisterDao,
        private val internetManager: InternetManager
) : RoomPersister<Raw, Parsed, Key>, RecordProvider<Key> {

    private val lifespan: Long by lazy { memoryPolicy.expireAfterTimeUnit.toSeconds(memoryPolicy.expireAfterWrite) }

    override fun getRecordState(key: Key): RecordState {
        val persisterEntity = persisterDao.select(key.toString()).blockingGet()
        return getRecordStateFromTimestamp(persisterEntity)
    }

    private fun getRecordStateFromTimestamp(persisterEntity: PersisterEntity?): RecordState {
        if (persisterEntity == null) {
            return RecordState.MISSING
        }
        val elapsedSeconds = TimeUtils.now().epochSecond - persisterEntity.lastUpdated.epochSecond
        if (elapsedSeconds > lifespan) {
            return RecordState.STALE
        }
        if (elapsedSeconds > TimeUnit.DAYS.toSeconds(1) && internetManager.isConnectedToWiFi()) {
            return RecordState.STALE
        }
        return RecordState.FRESH
    }

}
