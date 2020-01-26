package utils.converters;

import database.models.Rental;
import ModelFx.RentalFx;

public class ConverterRental {

    public static RentalFx convertToRentalFx(Rental rental){
        RentalFx rentalFx = new RentalFx();
        rentalFx.setId(rental.getId());
        rentalFx.setBookFx(ConverterBook.convertToBookFx(rental.getBook()));
        rentalFx.setReaderFx(ConverterReader.convertToReaderFx(rental.getReader()));
        rentalFx.setAddedDate(rental.getRentDate());
        return rentalFx;
    }

    public static Rental convertToRental(RentalFx rentalFx){
        Rental rental = new Rental();
        rental.setId(rentalFx.getId());
        rental.setBook(ConverterBook.convertToBook(rentalFx.getBookFx()));
        rental.setReader(ConverterReader.convertToReader(rentalFx.getReaderFx()));
        rental.setRentDate(rentalFx.getAddedDate());
        return rental;
    }
}
