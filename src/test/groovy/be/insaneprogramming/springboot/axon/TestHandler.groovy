package be.insaneprogramming.springboot.axon

import org.axonframework.commandhandling.annotation.CommandHandler
import org.axonframework.domain.GenericEventMessage
import org.axonframework.eventhandling.EventBus
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TestHandler {

    TestService testService;

    @Autowired
    EventBus eventBus;

    @CommandHandler
    def handleCommand(TestCommand testCommand) {
        testService.doSomethingWithCommand()
        eventBus.publish(new GenericEventMessage(new TestEvent()))
    }

    @EventHandler
    def handleEvent(TestEvent testEvent) {
        testService.doSomethingWithEvent()
    }

}
