package svg2png

import com.google.appengine.api.datastore.DatastoreService
import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.EntityNotFoundException
import com.google.appengine.api.datastore.Key
import com.google.appengine.api.utils.SystemProperty
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KProperty

fun String.toLong(invalid: Long) : Long {
    val a = this.toLongOrNull()
    return a ?: invalid
}

fun Entity.getString(property: KProperty<*>, default: String = ""): String {
    return this.getProperty(property.name) as? String ?: default
}

fun Entity.getLong(property: KProperty<*>, default: Long = -1): Long {
    return this.getProperty(property.name) as? Long ?: default
}

fun Entity.getLongList(property: KProperty<*>): MutableList<Long> {
    val value = this.getProperty(property.name) as? List<*> ?: mutableListOf<Long>()
    return value.mapNotNull { it as? Long }.toMutableList()
}

fun DatastoreService.get(key: Key): Entity? =
    try {
        this.get(key)
    } catch (e: EntityNotFoundException) {
        null
    }

fun <T> List<T>.shuffle() : List<T> {
    var list = mutableListOf<T>()
    for(ite in this) list.add(ite)
    java.util.Collections.shuffle(list)
    return list
}

fun isProduction(): Boolean {
    return SystemProperty.environment.value() == SystemProperty.Environment.Value.Production
}
