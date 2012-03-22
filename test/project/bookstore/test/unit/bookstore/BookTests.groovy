package bookstore

import grails.test.mixin.*
import org.junit.*

@TestFor(Book)
class BookTests {

    void testTitleIsRequired() {
		Book book = new Book()
		book.save()
		assert "nullable" == book.errors['title'].code
    }
}
