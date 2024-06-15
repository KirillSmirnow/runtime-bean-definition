package rbd.httpclient

object ReflectionUtility {

    fun <A : Annotation> findTypesAnnotatedWith(annotation: Class<A>, basePackage: String): List<Class<*>> {
        val basePackagePath = basePackage.replace('.', '/')
        return ClassLoader.getSystemResourceAsStream(basePackagePath)!!.reader().readLines()
            .map { filename -> filename.removeSuffix(".class") }
            .map { className -> Class.forName("$basePackage.$className") }
            .filter { type -> type.isAnnotationPresent(annotation) }
    }
}
