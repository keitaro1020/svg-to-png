package svg2png.model

import com.google.apphosting.api.ApiProxy
import svg2png.isProduction

data class SvgUrl(var id: Long = 0, val url: String) {
    fun getPngUrl(): String {
        val hostname = ApiProxy
                .getCurrentEnvironment()
                .attributes["com.google.appengine.runtime.default_version_hostname"]
        val schema = if(isProduction()) { "https" } else { "http" }
        val servicename = if(isProduction()) { "svg-to-png-dot-" } else { "" }
        return "${schema}://${servicename}${hostname}/api/png/${id}"
    }
}
