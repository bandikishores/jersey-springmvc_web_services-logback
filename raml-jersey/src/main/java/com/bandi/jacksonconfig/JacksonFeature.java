package com.bandi.jacksonconfig;

import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.internal.InternalProperties;
import org.glassfish.jersey.internal.util.PropertiesHelper;

import com.fasterxml.jackson.jaxrs.base.JsonMappingExceptionMapper;
import com.fasterxml.jackson.jaxrs.base.JsonParseExceptionMapper;

/**
 * Feature used to register Jackson JSON providers.
 * 
 * This Class provides configuration about stream converters, exception mappers that needs to be used for JSON
 * conversion.
 * 
 * @author Kishore.Bandi
 */
public class JacksonFeature implements Feature {

    private static final String JSON_FEATURE = JacksonFeature.class.getSimpleName();

    @Override
    public boolean configure(final FeatureContext context) {

        final Configuration config = context.getConfiguration();
        final String jsonFeature = CommonProperties.getValue(config.getProperties(), config.getRuntimeType(),
                InternalProperties.JSON_FEATURE, JSON_FEATURE, String.class);

        // Other JSON providers registered.
        if (!JSON_FEATURE.equalsIgnoreCase(jsonFeature)) {
            return false;
        }

    // Disable other JSON providers.
    context.property(
            PropertiesHelper.getPropertyNameForRuntime(InternalProperties.JSON_FEATURE, config.getRuntimeType()),
            JSON_FEATURE);

    context.register(JsonParseExceptionMapper.class);
    context.register(JsonMappingExceptionMapper.class);
    context.register(JacksonJaxbJsonProvider.class, MessageBodyReader.class, MessageBodyWriter.class);

    return true;
}

}
