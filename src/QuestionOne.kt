fun main() {
    println(stripUrlParams("https://google.com?a=1&b=2&a=2&a=4"))
    println(stripUrlParams("https://google.com?a=1&b=2&a=2", arrayOf("b")))
    println(stripUrlParams("https://google.com"))
}

fun stripUrlParams(url: String, paramsToStrip: Array<String> = arrayOf<String>()): String? {
    var resultURL = ""

    if (url.contains("?")) {
        val splitParams = url.split("?").toTypedArray()
        val domain = splitParams[0]
        val queryParams = splitParams[1].split("&").toTypedArray()
        val map: MutableMap<String, String> = HashMap()
        val params = arrayListOf<String>()
        var formattedParams = ""
        var index = 0

        for (param in queryParams) {
            val name = param.split("=").toTypedArray()[0]
            val value = param.split("=").toTypedArray()[1]
            map[name] = value
        }

        for ((key, value) in map) {
            params.add("$key=$value")
        }

        if (paramsToStrip.isNotEmpty()) {
            for (param in paramsToStrip) {
                for ((key, value) in map) {
                    if (param == key) {
                        params.remove("$key=$value")
                    }
                }
            }
        }

        if (params.isEmpty()) {
            resultURL = domain.replace("?", "")
        } else {
            while (index < params.size) {
                formattedParams += params[index]
                index++
                if (index < params.size) {
                    formattedParams = "$formattedParams&"
                }
            }
            resultURL = "$domain?$formattedParams"
        }
    } else {
        resultURL = url
    }

    return resultURL
}