package controler;

import ModelFx.BookService;
import ModelFx.ReaderService;
import ModelFx.RentalService;
public class DataBaseController implements Runnable {


    public void openDataBase() {
        openReader();
        openBook();
        openRental();
    }


    public static ReaderService readerService;

    public void openReader() {
        readerService = new ReaderService();
        readerService.init();
    }


    public static BookService bookService;

    public void openBook() {
        bookService = new BookService();
        bookService.init();
    }


    public static RentalService rentalService;

    public void openRental() {
        rentalService = new RentalService();
        rentalService.init();
    }


    @Override
    public void run() {
    }
}
