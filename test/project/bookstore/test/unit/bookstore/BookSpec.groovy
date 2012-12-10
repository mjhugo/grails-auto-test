package bookstore

import grails.test.mixin.*
import spock.lang.Specification

@TestFor(Book)
class BookSpec extends Specification {

    def "save a book"() {
        given:
		Book book = new Book()

        when:
		book.save()

        then:
		assert "nullable" == book.errors['title'].code
    }

}
