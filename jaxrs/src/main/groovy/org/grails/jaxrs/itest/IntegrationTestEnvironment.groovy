/*
 * Copyright 2009 - 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.jaxrs.itest

import grails.core.GrailsApplication
import grails.spring.BeanBuilder
import grails.web.servlet.context.GrailsWebApplicationContext

import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener

import grails.util.Holders
import org.jaxrs.ProviderArtefactHandler
import org.jaxrs.ResourceArtefactHandler
import org.grails.jaxrs.provider.DomainObjectReader
import org.grails.jaxrs.provider.DomainObjectWriter
import org.jaxrs.provider.JSONReader
import org.jaxrs.provider.JSONWriter
import org.jaxrs.web.JaxrsContext
import org.jaxrs.web.JaxrsListener
import org.jaxrs.web.JaxrsUtils

/**
 * @author Martin Krasser
 */
class IntegrationTestEnvironment {

    private JaxrsContext jaxrsContext

    private String contextConfigLocations
    private String jaxrsProviderName

    private List jaxrsClasses

    private boolean autoDetectJaxrsClasses

    IntegrationTestEnvironment(String contextConfigLocations, String jaxrsProviderName, List jaxrsClasses, boolean autoDetectJaxrsClasses) {
        this.contextConfigLocations = contextConfigLocations
        this.jaxrsProviderName = jaxrsProviderName
        this.jaxrsClasses = jaxrsClasses
        this.autoDetectJaxrsClasses = autoDetectJaxrsClasses
    }

    synchronized JaxrsContext getJaxrsContext() {
        if (!jaxrsContext) {
            GrailsApplication application = Holders.grailsApplication
            GrailsWebApplicationContext applicationContext = application.mainContext

            BeanBuilder beanBuilder = new BeanBuilder(applicationContext)
            beanBuilder.importBeans "org/grails/jaxrs/itest/integrationTestEnvironment.xml, ${contextConfigLocations}"

            ServletContextListener jaxrsListener = new JaxrsListener()

            if (autoDetectJaxrsClasses) {
                application.getArtefacts(ResourceArtefactHandler.TYPE).each { gc ->
                     jaxrsClasses << gc.clazz
                }
                application.getArtefacts(ProviderArtefactHandler.TYPE).each { gc ->
                     jaxrsClasses << gc.clazz
                }
            }

            if (jaxrsProviderName == JaxrsContext.JAXRS_PROVIDER_NAME_RESTLET) {
                jaxrsClasses << JSONReader
                jaxrsClasses << JSONWriter
                jaxrsClasses << DomainObjectReader
                jaxrsClasses << DomainObjectWriter
            }

            beanBuilder.beans {
                jaxrsClasses.each { clazz ->
                   "${clazz.name}"(clazz) { bean->
                        bean.autowire = true
                    }
                }
            }.registerBeans(applicationContext.beanFactory)

            jaxrsListener.contextInitialized(new ServletContextEvent(applicationContext.servletContext))
            jaxrsContext = JaxrsUtils.getRequiredJaxrsContext(applicationContext.servletContext)
            jaxrsContext.jaxrsServletContext = applicationContext.servletContext
            jaxrsClasses.each { jaxrsContext.jaxrsConfig.classes << it }

            jaxrsContext.jaxrsProviderName = jaxrsProviderName
            jaxrsContext.init()
        }
        jaxrsContext
    }
}
