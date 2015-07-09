package be.insaneprogramming.springboot.axon
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import spock.lang.Specification

class AxonAutoConfigurationTests extends Specification {
    static ConfigurableApplicationContext context

    def setupSpec() {
        context = SpringApplication.run(AxonTestApplication)
    }

    def "check axon is configured"() {
        when:
        def commandBus = context.getBean(CommandBus)
        then:
        commandBus != null
    }

    def "check axon command and event handling is enabled"() {
        given:
        def mockService = Mock(TestService)
        context.getBean(TestHandler).testService = mockService
        when:
        def commandGateway = context.getBean(CommandGateway)
        commandGateway.sendAndWait(new TestCommand())
        then:
        1 * mockService.doSomethingWithCommand()
        1 * mockService.doSomethingWithEvent()
    }
}