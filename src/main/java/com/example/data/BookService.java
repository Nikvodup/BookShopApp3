package com.example.data;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    List<String> period= List.of("1 month","2 months","3 months","6 months","9 months","12 months");




    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;

    }



    public List<Book> getBooksData() {
        return bookRepository.findAll();
    }

    //NEW BOOK SEVICE METHODS
    public List<Book> getBooksByAuthor(String authorName){
        return bookRepository.findBooksByAuthorFirstNameContaining(authorName);
    }

 /**   public List<Book> getBooksByTitle(String title) throws BookstoreApiWrongParameterException {
        if (title.equals("") || title.length() <= 1){
            throw new BookstoreApiWrongParameterException("Wrong values passed to one or more parameters");
        }else {
            List<Book> data = bookRepository.findBooksByTitleContaining(title);
            if (data.size() > 0){
                return data;
            }else {
                throw new BookstoreApiWrongParameterException("No data found with specified parameters...");
            }
        }
    }  **/



 /**   @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handleMissingServletRequestParameterException(Exception exception) {
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST, "Missing required parameters",
                exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookstoreApiWrongParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handleBookstoreApiWrongParameterException(Exception exception) {
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST, "Bad parameter value...", exception)
                , HttpStatus.BAD_REQUEST);
    }  **/



    public List<Book> getBooksWithPriceBetween(Integer min, Integer max){
        return bookRepository.findBooksByPriceOldBetween(min,max);
    }

    public List<Book> getBooksWithPrice(Integer price){
        return bookRepository.findBooksByPriceOldIs(price);
    }

    public List<Book> getBooksWithMaxPrice(){
        return bookRepository.getBooksWithMaxDiscount();
    }

    public Page<Book> getPageOfBestsellers(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return  bookRepository.getBestsellers(nextPage);
    }

   //-------------Recent Books-----------------
    //------------Carousal of recent books on the main page-----------
   public Page<Book> getRecentBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of( offset,limit);
        return bookRepository.getRecent(nextPage);
    }
   //-----------List of books on the Recent page-----------------

 /**  public Page<Book> getRecentPage(LocalDate since,Integer offset,Integer limit){
      // List<String> period= List.of("1 month","2 months","3 months","6 months","9 months","12 months");
        for(String m : period) {
            if (m == "1 months") {
                since = LocalDate.now().minusMonths(1);
            } else if (m =="2 months") {
                since = LocalDate.now().minusMonths(2);
            } else if (m =="3 months") {
                since = LocalDate.now().minusMonths(3);
            } else if (m =="6 months") {
                since = LocalDate.now().minusMonths(6);
            } else if (m =="9 months") {
                since = LocalDate.now().minusMonths(9);
            } else if (m =="12 months") {
                since = LocalDate.now().minusMonths(12);
            }
        }

        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findBooksByPubDateGreaterThan(since,nextPage);
    }

    public List<String> getPeriod() {return period;}

    public void setPeriod(List<String> period) {this.period = period;}

  **/

 public Page<Book> getPageOfRecentBooksData(Date dateFrom, Date dateTo, Integer offset, Integer limit){
     Pageable nextPage = PageRequest.of(offset, limit);
     return bookRepository.findPageOfBooksByPubDateBetweenOrderByPubDate(dateFrom, dateTo, nextPage);
 }


    //-----------------------Recommended Books----------------------

    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findAll(nextPage);
    }

    //-----------------------Searching---------------------

    public Page<Book> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findBookByTitleContaining(searchWord,nextPage);
    }

    //----------------Counting Books by Tags-------------

    public Integer getTagCount(String tag){
        return bookRepository.countBooksByTag(tag);
    }



   // -------------------Counting Books By Genres---------------
    public Integer getCount(String genre){
        return bookRepository.countBooksByGenre(genre);
    }

    //-------------------Counting Books By Id----------------

    public Integer getCount(Integer id){
        return bookRepository.countBooksByAuthorId(id);
    }

    //------------------Finding Books By Id-----------------

    public Page<Book> findBooksByAuthorId(Integer offset, Integer limit, Integer id){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findBooksByAuthorId(nextPage,id);

    }

    public Page<Book> findbooksByTag(Integer offset, Integer limit, String tag){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findBooksByTag(tag,nextPage);
    }


}

