package org.asciidoctor.groovytemplate

import org.asciidoctor.Asciidoctor
import org.asciidoctor.OptionsBuilder
import org.asciidoctor.ast.Inline
import org.asciidoctor.ast.ListNode
import org.asciidoctor.internal.JRubyAsciidoctor
import org.asciidoctor.internal.JRubyRuntimeContext
import org.asciidoctorj.groovy.converter.TemplateConverter
import org.jruby.Ruby
import org.jruby.RubyArray
import org.jruby.RubyClass
import org.jruby.RubyModule
import org.jruby.RubyString
import org.jruby.embed.ScriptingContainer
import org.jruby.javasupport.JavaEmbedUtils
import org.jruby.runtime.ObjectAllocator
import org.jruby.runtime.ThreadContext
import org.jruby.runtime.builtin.IRubyObject
import org.junit.Test

import static org.junit.Assert.assertTrue

class ConverterTest {

    @Test
    void shouldRegisterConverter() {

        File sourceFile = new File('src/test/resources/test.ad')
        assertTrue(sourceFile.exists())

        Asciidoctor asciidoctor = Asciidoctor.Factory.create()
        asciidoctor.javaConverterRegistry().register(TemplateConverter, 'test')


        Ruby ruby = JRubyRuntimeContext.get()

        String s = asciidoctor.convertFile(sourceFile, OptionsBuilder.options().backend('test'))

        println s
    }

}