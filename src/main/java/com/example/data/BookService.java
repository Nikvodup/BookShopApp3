package com.example.data;


import com.example.annotations.MethodDurationLoggable;
import com.example.data.Genre.Genre;
import com.example.data.Book2Type.TypeStatus;
import com.example.security.BookstoreUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;

@Service
public class BookService {

    // Лимит 7 дней для недавно просмотренных книг
    public static Timestamp LIMIT_DATE_TIME_RECENTLY_VIEWED = Timestamp.valueOf(now().minusMinutes(60L * 24L * 7L));

    // Лимит 1 день для недавно просмотренных книг для отображения в рекомендованных книгах
    public static Timestamp LIMIT_DATE_TIME_RECENTLY_RECOMMEND = Timestamp.valueOf(now().minusMinutes(60L * 24L));



    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final Book2UserRepository book2UserRepository;



    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, Book2UserRepository book2UserRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;

        this.book2UserRepository = book2UserRepository;
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
        return bookRepository.findByAuthorId(authorId,nextPage);
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
        return bookRepository.findByAuthorId(id,nextPage);

    }

    public Page<Book> findbooksByTag(Integer offset, Integer limit, String tag){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findBooksByTag(tag,nextPage);
    }

    public Page<Book> getBooksByTag(Integer tagId, Integer page, Integer limit) {
        Pageable nextPage = PageRequest.of(page, limit);
        return bookRepository.findBooksByTag(tagId, nextPage);
    }

    public Book findBookBySlug(String slug) {
        return bookRepository.findBookBySlug(slug);
    }


    public Page<Book> getPageOfRecentlyViewedBooks(Integer offset, Integer limit, Integer userId) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.getPageOfRecentlyViewed(LIMIT_DATE_TIME_RECENTLY_VIEWED, userId, nextPage);
    }

    //--------------------------------------------------- GENRE-------------------------
    public Page<Book> getPageBookByGenreType(Genre.GenreType genreType, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAllByGenre_GenreType(genreType, nextPage);
    }

    public Page<Book> getPageBookByGenre(Genre genre, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAllByGenre(genre, nextPage);
    }

    public Page<Book> getPageBookByGenreId(Integer genreId, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAllByGenreId(genreId, nextPage);
    }




    public List<Book> getPaidBooks(Integer id) {
        return bookRepository.getPaidBooks(id);
    }


    public boolean isPaid(Book book, Integer id, Boolean checkArchive) {
        Book2User book2User = book2UserRepository.findByUserIdAndBookIdAndBook2Type_TypeStatusIn(id, book.getId(), asList(TypeStatus.PAID, TypeStatus.ARCHIVED));
        if (checkArchive && nonNull(book2User)) {
            return !book2User.getBook2Type().getTypeStatus().equals(TypeStatus.ARCHIVED);
        }
        return nonNull(book2User);
    }


    public List<Book> getArchiveBooks(Integer id) {
        return bookRepository.getArchiveBooks(id);
    }


    @MethodDurationLoggable(className = "BookService", timeThreshold = 1200)
    public void saveBook2User(Book book, BookstoreUser user, TypeStatus typeStatus) {
        Book2User book2User = book2UserRepository.findByUserIdAndBookId(user.getId(), book.getId());

        if (nonNull(book2User) && !book2User.getBook2Type().getTypeStatus().equals(typeStatus) &&
                !book2User.getBook2Type().getTypeStatus().equals(TypeStatus.PAID)
                && !book2User.getBook2Type().getTypeStatus().equals(TypeStatus.ARCHIVED)) {
            book2User.getBook2Type().setTypeStatus(typeStatus);
            book2UserRepository.save(book2User);
        } else if (nonNull(book2User) && book2User.getBook2Type().getTypeStatus().equals(TypeStatus.PAID)) {
            book2User.getBook2Type().setTypeStatus(typeStatus);
            book2UserRepository.save(book2User);
        } else {
            Book2Type book2Type = new Book2Type();
            Book2User newBook2User = new Book2User();
            book2Type.setTypeStatus(typeStatus);
            newBook2User.setBook(book);
            newBook2User.setUser(user);
            newBook2User.setBook2Type(book2Type);
            book2UserRepository.save(newBook2User);
        }
    }



    public void removeFromBook2User(Book book, BookstoreUser user) {
        Book2User book2User = book2UserRepository.findByUserIdAndBookId(user.getId(), book.getId());
        if (nonNull(book2User)) {
            book2UserRepository.delete(book2User);
        }
    }


    @MethodDurationLoggable(className = "BookService", timeThreshold = 1500)
    public List<Book> getCartBooks(Integer id) {
        return bookRepository.getCartBooks(id);
    }

    @MethodDurationLoggable(className = "BookService", timeThreshold = 500)
    public List<Book> getPostponedBooks(Integer id) {
        return bookRepository.getPostponedBooks(id);
    }

}

