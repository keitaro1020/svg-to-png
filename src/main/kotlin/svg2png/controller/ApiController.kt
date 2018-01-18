package svg2png.controller

import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpResponse
import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import svg2png.repository.SvgUrlRepository
import java.io.ByteArrayOutputStream
import java.io.OutputStream


@RestController
@RequestMapping("/api")
class ApiController(
        private val svgUrlRepository: SvgUrlRepository

) {

    @GetMapping("/png/{id}", produces = ["image/png"])
    fun getPng(@PathVariable id: Long): ResponseEntity<ByteArray> {

        val url = svgUrlRepository.find(id) ?: return ResponseEntity.notFound().build()
        var res: HttpResponse? = null
        try {
            val req = httpFactory.buildGetRequest(GenericUrl(url.url))
            res = req.execute()
            res?.let {
                println("url:${url.url}, contentType:${res}")
                if (res.contentType == RootController.ContentType.IMAGE_SVG_XML.mimeType) {
                    val outputStream = ByteArrayOutputStream()
                    val input = TranscoderInput(res.content)
                    val output = TranscoderOutput(outputStream)
                    val pngTranscoder = PNGTranscoder()

                    pngTranscoder.transcode(input, output)

                    return ResponseEntity.ok(outputStream.toByteArray())
                }
            }


        } catch(e: Exception) {
            println("illegal url:${url.url}")
        } finally {
            res?.disconnect()
        }

        return ResponseEntity.notFound().build()
    }
}