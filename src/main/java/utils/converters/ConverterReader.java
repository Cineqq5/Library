package utils.converters;

import ModelFx.ReaderFx;
import database.models.Reader;

public class ConverterReader {


    public static Reader convertToReader(ReaderFx authorFx){
        Reader reader = new Reader();
        reader.setId(authorFx.getId());
        reader.setName(authorFx.getName());
        reader.setSurname(authorFx.getSurname());
        reader.setIndeks(authorFx.getIndeks());
        return reader;
    }

    public static ReaderFx convertToReaderFx(Reader author){
        ReaderFx readerFx = new ReaderFx();
        readerFx.setId(author.getId());
        readerFx.setName(author.getName());
        readerFx.setSurname(author.getSurname());
        readerFx.setIndeks(author.getIndeks());
        return readerFx;
    }
}
