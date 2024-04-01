package de.sambalmueslie.openevent.infrastructure.search


//
//class NopSearchClient<T> : SearchClient {
//
//    companion object {
//        private val logger: Logger = LoggerFactory.getLogger(NopSearchClient::class.java)
//    }
//
//    override fun delete(key: String) {
//        logger.info("delete $key")
//    }
//
//    override fun deleteAll() {
//        logger.info("delete all")
//    }
//
//    override fun get(key: String): SolrDocument? {
//        logger.info("get $key")
//        return null
//    }
//
//    override fun save(input: SolrInputDocument) {
//        logger.info("save $input")
//    }
//
//    override fun search(query: String, pageable: Pageable): Page<SolrDocument> {
//        logger.info("search $query")
//        return Page.empty()
//    }
//
//
//}
