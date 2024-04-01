package de.sambalmueslie.openevent.infrastructure.search

import com.jillesvangurp.ktsearch.SearchClient

interface SearchService {

//    fun <T> getClient(name: String): SearchClient<T>

    fun getClient(): SearchClient
}
