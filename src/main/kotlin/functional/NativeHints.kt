package functional

import org.springframework.aot.hint.*

class NativeHints : RuntimeHintsRegistrar {

	override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
		hints.resources().registerPattern("messages.properties")

		// @RegisterReflectionForBinding(UserDto::class, User:class) can be used instead when using kotlin-reflection
		hints.reflection().registerType<UserDto>(MemberCategory.INVOKE_DECLARED_METHODS)
		hints.reflection().registerType<User>(MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.DECLARED_FIELDS)
		hints.reflection().registerType<User.Companion>(MemberCategory.INVOKE_DECLARED_METHODS)

		// TODO Remove when GraalVM reachability 0.2.8 is released
		hints.reflection().registerType(TypeReference.of("kotlin.internal.jdk8.JDK8PlatformImplementations"), MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
	}
}
