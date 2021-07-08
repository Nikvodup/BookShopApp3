package com.example.data;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;







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


    public Page<Book> getBooksByAuthorId(Integer authorId, Integer page, Integer limit) {
        Pageable nextPage = PageRequest.of(page, limit);
        return bookRepository.findBooksByAuthorId(nextPage,authorId);
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

    //--------------------Popular books---------------

 /**   public Page<Book> getPageOfBestsellers(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return  bookRepository.getBestsellers(nextPage);
    }  **/

      public Page<Book> getPopularBooks(Integer offset, Integer limit){
          Pageable nextPage = PageRequest.of(offset,limit);
          return bookRepository.findBooksByPopRating(nextPage);
      }

   //-------------Recent Books-----------------

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

