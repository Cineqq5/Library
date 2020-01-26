package utils.converters;

import database.models.Book;
import ModelFx.BookFx;

public class ConverterBook {

    public static Book convertToBook(BookFx bookFx){
        Book book = new Book();
        book.setId(bookFx.getId());
        book.setTitle(bookFx.getTitle());
        book.setAuthor(bookFx.getAuthor());
        book.setStatus(bookFx.getStatus());
        return  book;
    }

    public static BookFx convertToBookFx(Book book){
        BookFx bookFx = new BookFx();
        bookFx.setId(book.getId());
        bookFx.setTitle(book.getTitle());
        bookFx.setAuthor(book.getAuthor());
        bookFx.setStatus(book.getStatus());
        return bookFx;
    }

}
