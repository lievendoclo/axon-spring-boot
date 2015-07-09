# Spring Boot Axon auto configuration

This module enables auto configuration for the Axon event sourcing framework.
It will automatically add an eventbus, commandbus, command gateway and the necessary
infrastructure to enable the annotation based handling offered by Axon.

## Overriding beans

If you define your own Axon `CommandBus` or `EventBus` beans, the auto configuration will use
those instead of the default ones.