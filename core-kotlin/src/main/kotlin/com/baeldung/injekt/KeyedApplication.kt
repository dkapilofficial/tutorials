package com.baeldung.injekt

import org.slf4j.LoggerFactory
import uy.kohesive.injekt.*
import uy.kohesive.injekt.api.*

class KeyedApplication {
    companion object : InjektMain() {
        private val LOG = LoggerFactory.getLogger(KeyedApplication::class.java)
        @JvmStatic fun main(args: Array<String>) {
            KeyedApplication().run()
        }

        override fun InjektRegistrar.registerInjectables() {
            val configs = mapOf(
                    "google" to Config("googleClientId", "googleClientSecret"),
                    "twitter" to Config("twitterClientId", "twitterClientSecret")
            )
            addPerKeyFactory<Config, String> {key -> configs[key]!! }

            addSingletonFactory { App() }
        }
    }

    data class Config(val clientId: String, val clientSecret: String)

    class App {
        fun run() {
            LOG.info("Google config: {}", Injekt.get<Config>("google"))
            LOG.info("Twitter config: {}", Injekt.get<Config>("twitter"))
        }
    }

    fun run() {
        Injekt.get<App>().run()
    }
}
