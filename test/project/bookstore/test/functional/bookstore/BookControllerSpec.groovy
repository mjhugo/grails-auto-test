package bookstore

import spock.lang.Specification
import grails.test.mixin.TestFor

@TestFor(BookController)
class BookControllerSpec extends Specification {

    def "book controller index"() {
        when:
        controller.index()

        then:
        response.status == 200
    }

}
