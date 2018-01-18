package svg2png.repository.impl

import com.google.appengine.api.datastore.DatastoreService
import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.Key
import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.Query
import org.springframework.stereotype.Repository
import svg2png.getString
import svg2png.model.SvgUrl
import svg2png.repository.SvgUrlRepository

@Repository
class DatastoreSvgUrlRepository(
        private val datastore: DatastoreService
) : SvgUrlRepository {

    private val kind = "SvgUrl"

    override fun create(svgUrl: SvgUrl) {
        val entity = modelToEntity(model = svgUrl)
        datastore.put(entity)
    }

    override fun delete(id: Long) {
        val key = KeyFactory.createKey(kind, id)
        datastore.delete(key)
    }

    override fun findAll(): List<SvgUrl> {
        val query = Query(kind)
        val prepare = datastore.prepare(query)
        val results = prepare.asQueryResultIterator()

        return results.asSequence().map { entityToModel(it) }.toList()
    }

    override fun find(id: Long): SvgUrl? {
        val key = KeyFactory.createKey(kind, id)
        val entity = datastore.get(key)
        return if(entity != null) { entityToModel(entity) } else { null }
    }

    private fun modelToEntity(key: Key? = null, model: SvgUrl): Entity {
        val entity = if(key != null) Entity(key) else Entity(kind)
        entity.setProperty(SvgUrl::url.name, model.url)
        return entity
    }

    private fun entityToModel(entity: Entity): SvgUrl {
        return SvgUrl(
                id = entity.key.id,
                url = entity.getString(SvgUrl::url)
        )
    }
}
