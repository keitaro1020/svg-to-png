package svg2png.controller

import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpResponse
import com.google.api.client.http.apache.ApacheHttpTransport
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView
import svg2png.model.SvgUrl
import svg2png.repository.SvgUrlRepository
import javax.validation.Valid

val httpFactory = ApacheHttpTransport().createRequestFactory()

@Controller
class RootController(
        private val svgUrlRepository: SvgUrlRepository
) {
    enum class ContentType(val mimeType: String) {
        IMAGE_SVG_XML("image/svg+xml")
    }


    @GetMapping("/")
    fun index() : ModelAndView {
        return ModelAndView("index", "urls", svgUrlRepository.findAll())
    }

    @PostMapping("")
    fun create(@Valid @ModelAttribute("form")url: SvgUrl): String {

        var res: HttpResponse? = null
        try {
            val req = httpFactory.buildGetRequest(GenericUrl(url.url))
            res = req.execute()
            res?.let {
                println("url:${url.url}, contentType:${res.contentType}")
                if (res.contentType == ContentType.IMAGE_SVG_XML.mimeType) {
                    svgUrlRepository.create(url)
                }
            }
        } catch(e: Exception) {
            println("illegal url:${url.url}")
        } finally {
            res?.disconnect()
        }
        return "redirect:/"
    }
}