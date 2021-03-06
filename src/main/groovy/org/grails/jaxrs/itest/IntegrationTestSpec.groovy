package org.grails.jaxrs.itest

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import org.grails.jaxrs.JaxrsController
import org.springframework.boot.test.IntegrationTest
import spock.lang.Shared
import spock.lang.Specification

import javax.servlet.http.HttpServletResponse

/**
 * @author Noam Y. Tenne
 */
@Integration
@Rollback
abstract class IntegrationTestSpec extends Specification implements JaxRsIntegrationTest {

    @Shared
    def grailsApplication

    @Shared
    def testEnvironment

    JaxrsController controller

    JaxRsIntegrationTest defaultMixin

    def setupSpec() {
        testEnvironment = null
    }

    def setup() {
        grailsApplication.config.org.grails.jaxrs.dowriter.require.generic.collections = false
        grailsApplication.config.org.grails.jaxrs.doreader.disable = false
        grailsApplication.config.org.grails.jaxrs.dowriter.disable = false

        controller = new JaxrsController()
        defaultMixin = new JaxRsIntegrationTestMixin(controller)

        if (!testEnvironment) {
            testEnvironment = new IntegrationTestEnvironment(getContextLocations(), getJaxrsImplementation(),
                    getJaxrsClasses(), isAutoDetectJaxrsClasses())
        }

        controller.jaxrsContext = testEnvironment.jaxrsContext
    }

    @Override
    void setRequestUrl(String url) {
        defaultMixin.setRequestUrl(url)
    }

    @Override
    void setRequestMethod(String method) {
        defaultMixin.setRequestMethod(method)
    }

    @Override
    void setRequestContent(byte[] content) {
        defaultMixin.setRequestContent(content)
    }

    @Override
    void addRequestHeader(String key, Object value) {
        defaultMixin.addRequestHeader(key, value)
    }

    @Override
    void resetResponse() {
        defaultMixin.resetResponse()
    }

    @Override
    HttpServletResponse getResponse() {
        defaultMixin.response
    }

    @Override
    HttpServletResponse sendRequest(String url, String method, byte[] content) {
        defaultMixin.sendRequest(url, method, content)
    }

    HttpServletResponse sendRequest(String url, String method) {
        defaultMixin.sendRequest(url, method, ''.bytes)
    }

    @Override
    HttpServletResponse sendRequest(String url, String method, Map<String, Object> headers, byte[] content) {
        defaultMixin.sendRequest(url, method, headers, content)
    }

    HttpServletResponse sendRequest(String url, String method, Map<String, Object> headers) {
        defaultMixin.sendRequest(url, method, headers, ''.bytes)
    }

    @Override
    String getContextLocations() {
        defaultMixin.contextLocations
    }

    @Override
    String getJaxrsImplementation() {
        defaultMixin.jaxrsImplementation
    }

    @Override
    List getJaxrsClasses() {
        defaultMixin.jaxrsClasses
    }

    @Override
    boolean isAutoDetectJaxrsClasses() {
        defaultMixin.autoDetectJaxrsClasses
    }
}
