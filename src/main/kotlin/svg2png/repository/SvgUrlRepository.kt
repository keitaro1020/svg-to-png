package svg2png.repository

import svg2png.model.SvgUrl

interface SvgUrlRepository {
    fun findAll(): List<SvgUrl>
    fun find(id: Long): SvgUrl?
    fun create(svgUrl: SvgUrl)
    fun delete(id: Long)
}