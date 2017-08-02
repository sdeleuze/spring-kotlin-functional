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

import com.samskivert.mustache.Mustache
import com.samskivert.mustache.Mustache.Compiler

import org.springframework.web.reactive.result.view.AbstractUrlBasedView
import org.springframework.web.reactive.result.view.UrlBasedViewResolver
import org.springframework.web.reactive.result.view.ViewResolver

/**
 * Spring WebFlux [ViewResolver] for Mustache.
 * @author Brian Clozel
 * @author Sebastien Deleuze
 */
class MustacheViewResolver(private val compiler: Compiler = Mustache.compiler()) : UrlBasedViewResolver() {

	private var charset: String? = null

	init {
		viewClass = requiredViewClass()
	}

	/**
	 * Set the charset.
	 * @param charset the charset
	 */
	fun setCharset(charset: String) {
		this.charset = charset
	}

	override fun requiredViewClass(): Class<*> {
		return MustacheView::class.java
	}

	override fun createView(viewName: String): AbstractUrlBasedView {
		val view = super.createView(viewName) as MustacheView
		view.setCompiler(this.compiler)
		this.charset?.let { view.setCharset(it) }
		return view
	}

}
