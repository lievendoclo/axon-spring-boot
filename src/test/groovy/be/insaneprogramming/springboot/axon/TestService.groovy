package be.insaneprogramming.springboot.axon

interface TestService {
    def doSomethingWithCommand();
    def doSomethingWithEvent()
}