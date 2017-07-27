/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package functional.web.view

import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.Reader
import java.nio.charset.Charset
import java.util.Locale
import java.util.Optional

import com.samskivert.mustache.Mustache.Compiler
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.web.reactive.result.view.AbstractUrlBasedView
import org.springframework.web.reactive.result.view.View
import org.springframework.web.server.ServerWebExchange

/**
 * Spring WebFlux [View] using the Mustache template engine.
 * @author Brian Clozel
 * @author Sebastien Deleuze
 */
class MustacheView : AbstractUrlBasedView() {

	private var compiler: Compiler? = null

	private var charset: String? = null

	/**
	 * Set the JMustache compiler to be used by this view. Typically this property is not
	 * set directly. Instead a single [Compiler] is expected in the Spring
	 * application context which is used to compile Mustache templates.
	 * @param compiler the Mustache compiler
	 */
	fun setCompiler(compiler: Compiler) {
		this.compiler = compiler
	}

	/**
	 * Set the charset used for reading Mustache template files.
	 * @param charset the charset to use for reading template files
	 */
	fun setCharset(charset: String) {
		this.charset = charset
	}

	@Throws(Exception::class)
	override fun checkResourceExists(locale: Locale): Boolean {
		return resolveResource() != null
	}

	override fun renderInternal(model: Map<String, Any>, contentType: MediaType?,
								exchange: ServerWebExchange): Mono<Void> {
		val resource = resolveResource() ?: return Mono.error(IllegalStateException("Could not find Mustache template with URL [$url]"))
		val dataBuffer = exchange.response.bufferFactory().allocateBuffer()
		try {
			getReader(resource).use { reader ->
				val template = this.compiler!!.compile(reader)
				val charset = getCharset(contentType).orElse(defaultCharset)
				OutputStreamWriter(dataBuffer.asOutputStream(),
						charset).use { writer ->
					template.execute(model, writer)
					writer.flush()
				}
			}
		} catch (ex: Throwable) {
			return Mono.error<Void>(ex)
		}

		return exchange.response.writeWith(Flux.just(dataBuffer))
	}

	private fun resolveResource(): Resource? {
		val resource = applicationContext!!.getResource(url!!)
		if (resource == null || !resource.exists()) {
			return null
		}
		return resource
	}
	
	private fun getReader(resource: Resource): Reader {
		if (this.charset != null) {
			return InputStreamReader(resource.inputStream, this.charset!!)
		}
		return InputStreamReader(resource.inputStream)
	}

	private fun getCharset(mediaType: MediaType?): Optional<Charset> {
		return Optional.ofNullable(mediaType?.charset)
	}

}
