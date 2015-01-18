package org.asciidoctorj.groovy.converter

import groovy.text.Template
import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration
import groovy.util.logging.Log
import org.asciidoctor.ast.AbstractNode
import org.asciidoctor.converter.AbstractConverter

import java.util.logging.Level

@Log
class TemplateConverter extends AbstractConverter {

    MarkupTemplateEngine engine

    TemplateConverter(String backend, Map<Object, Object> opts) {
        super(backend, opts)
        TemplateConfiguration templateConfiguration = new TemplateConfiguration()
        engine = new MarkupTemplateEngine(templateConfiguration)
    }

    @Override
    @SuppressWarnings('CatchException')
    Object convert(AbstractNode abstractNode, String transform, Map<Object, Object> options) {

        String templateName = transform != null ? transform : abstractNode.nodeName

        String resourceName = "templates/${templateName}.groovytpl"
        InputStream templateStream = TemplateConverter.classLoader.getResourceAsStream(resourceName)

        if (templateStream == null) {
            throw new IllegalArgumentException("Missing template ${resourceName}")
        }

        try {
            Reader templateReader = new InputStreamReader(templateStream)
            Template template = engine.createTemplate(templateReader)
            StringWriter out = new StringWriter()
            // The current node is available in the template under the name "node"
            // The current options under the name "options"
            template.make([node: abstractNode, options: options]).writeTo(out)

            return out.toString()
        } catch (Exception e) {
            // Print stack trace here because Asciidoctor sometimes swallows exceptions
            // and does not show the real cause
            log.log Level.SEVERE, "Exception with template ${resourceName}", e
            throw e
        }

    }
}