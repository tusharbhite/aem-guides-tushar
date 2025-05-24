package com.tushar.aem.guides.core.elastic;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
    name = "ElasticSearch OCD Configuration",
    description = "ElasticSearch OCD Configuration description"
)
public @interface ElasticSearchConfiguration {

    @AttributeDefinition(
      name = "Elasticsearch API Index",
      description = "Elasticsearch API Index",
      type = AttributeType.STRING)
    String serverApiUsername();

    @AttributeDefinition(
      name = "Elasticsearch API Key",
      description = "Elasticsearch API Key",
      type = AttributeType.STRING)
    String serverApiPassword();

    @AttributeDefinition(
      name = "Elasticsearch API Endpoint URL",
      description = "Elasticsearch API Endpoint URL",
      type = AttributeType.STRING)
    String elasticSearchEndpointUrl();
}