package functional

import org.springframework.web.reactive.function.server.ServerRequest
import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream

fun ServerRequest.locale() =
		this.headers().asHttpHeaders().acceptLanguageAsLocales.firstOrNull() ?: Locale.ENGLISH!!

fun LocalDate.formatDate(): String = this.format(englishDateFormatter)

private val daysLookup: Map<Long, String> =
		IntStream.rangeClosed(1, 31).boxed().collect(Collectors.toMap(Int::toLong, ::getOrdinal))

private val englishDateFormatter = DateTimeFormatterBuilder()
		.appendPattern("MMMM")
		.appendLiteral(" ")
		.appendText(ChronoField.DAY_OF_MONTH, daysLookup)
		.appendLiteral(" ")
		.appendPattern("yyyy")
		.toFormatter(Locale.ENGLISH)

private fun getOrdinal(n: Int) =
		when {
			n in 11..13 -> "${n}th"
			n % 10 == 1 -> "${n}st"
			n % 10 == 2 -> "${n}nd"
			n % 10 == 3 -> "${n}rd"
			else -> "${n}th"
		}