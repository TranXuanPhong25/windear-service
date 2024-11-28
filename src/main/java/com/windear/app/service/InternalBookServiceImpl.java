package com.windear.app.service;

import com.windear.app.dto.AddInternalBookRequestDTO;
import com.windear.app.dto.InternalBookDTO;
import com.windear.app.entity.*;
import com.windear.app.exception.BookNotFoundException;
import com.windear.app.exception.IsbnExistsException;
import com.windear.app.primarykey.BookGenreId;
import com.windear.app.repository.InternalBookRepository;
import com.windear.app.repository.PopularBookRepository;
import com.windear.app.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InternalBookServiceImpl implements InternalBookService {
    private final InternalBookRepository internalBookRepository;
    private final ReviewRepository reviewRepository;
    private final ExternalBookService externalBookService;
    private final PopularBookService popularBookService;
    private final PopularBookRepository popularBookRepository;
    private final BookGenreService bookGenreService;
    private final GenreService genreService;

    @Autowired
    public InternalBookServiceImpl(InternalBookRepository bookRepository,
                                   @Qualifier("externalBookProxy") ExternalBookService externalBookService,
                                   ReviewRepository reviewRepository, PopularBookService popularBookService,
                                   PopularBookRepository popularBookRepository, BookGenreService bookGenreService,
                                   GenreService genreService) {
        this.internalBookRepository = bookRepository;
        this.externalBookService = externalBookService;
        this.reviewRepository = reviewRepository;
        this.popularBookService = popularBookService;
        this.popularBookRepository = popularBookRepository;
        this.bookGenreService = bookGenreService;
        this.genreService = genreService;
    }

    @Override
    public List<InternalBookDTO> findAll() {
        List<InternalBook> books = internalBookRepository.findAll();
        List<InternalBookDTO> booksDto = new ArrayList<>();
        for(InternalBook it : books) {
            booksDto.add(convertToDTO(it));
        }
        return booksDto;
    }

    @Override
    public AddInternalBookRequestDTO findById(Integer id) {
        Optional<InternalBook> book = internalBookRepository.findById(id);
        if (book.isPresent()) {
            double rating = 0;
            Double averageRating = reviewRepository.getAverageRatingOfBookById(id);
            if (averageRating != null) {
                rating = averageRating;
            }
            book.get().setRating(rating);
            popularBookService.updateScore(id, 5);
            AddInternalBookRequestDTO bookDto = new AddInternalBookRequestDTO();
            List<BookGenre> bookGenres = bookGenreService.findAllByBookId(id);
            StringBuilder genres = new StringBuilder();
            for(int i = 0; i < bookGenres.size(); i++) {
                if(i < bookGenres.size() - 1) {
                    genres.append(genreService.findById(bookGenres.get(i).getBookGenreId().getGenreId()).getName()).append(',');
                } else {
                    genres.append(genreService.findById(bookGenres.get(i).getBookGenreId().getGenreId()).getName());
                }
            }
            bookDto.setGenres(genres.toString());
            bookDto.setInternalBook(book.get());
            return bookDto;
        } else {
            throw new BookNotFoundException("Book with id not found: " + id);
        }
    }

    @Override
    @Transactional
    public InternalBook add(AddInternalBookRequestDTO book) {
        if (externalBookService.isIsbnExist(book.getInternalBook().getIsbn13()) ||
                internalBookRepository.existsByIsbn13(book.getInternalBook().getIsbn13())) {
            throw new IsbnExistsException("Book with isbn: "
                    + book.getInternalBook().getIsbn13() + " already exists.");
        }
        book.getInternalBook().setAddDate(LocalDate.now());
        InternalBook bookFromDb = internalBookRepository.save(book.getInternalBook());
        if(book.getGenres().isEmpty()) {
            return bookFromDb;
        }
        String[] genreIds = book.getGenres().split(",");
        for (String genreId : genreIds) {
            BookGenre bookGenre = new BookGenre();
            BookGenreId bookGenreId = new BookGenreId(bookFromDb.getId(), Integer.parseInt(genreId.trim()));
            bookGenre.setBookGenreId(bookGenreId);
            bookGenreService.add(bookGenre);
        }
        return bookFromDb;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (findById(id) != null) {
            internalBookRepository.deleteById(id);
            Optional<PopularBook> popularBook = popularBookRepository.findById(id);
            if (popularBook.isPresent()) {
                popularBookRepository.deleteById(id);
            }
        } else {
            throw new BookNotFoundException("Book with title not found: " + id);
        }
    }

    @Override
    public List<InternalBook> findTop10ByReleaseDate() {
        return internalBookRepository.findTop10ByOrderByReleaseDateDesc();
    }

    @Override
    public long getBookInLast30Day() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusDays(30);
        return internalBookRepository.countBooksInLast30Days(startDate);
    }

    @Override
    public InternalBook update(AddInternalBookRequestDTO requestDTO) {
        InternalBook bookFromDB = findById(requestDTO.getInternalBook().getId()).getInternalBook();
        InternalBook updatedBook = requestDTO.getInternalBook();
        if (updatedBook.getTitle() != null) {
            bookFromDB.setTitle(updatedBook.getTitle());
        }
        if (updatedBook.getAuthor() != null) {
            bookFromDB.setAuthor(updatedBook.getAuthor());
        }
        if (updatedBook.getReleaseDate() != null) {
            bookFromDB.setReleaseDate(updatedBook.getReleaseDate());
        }
        if (updatedBook.getRating() > 0) {
            bookFromDB.setRating(updatedBook.getRating());
        }
        if (updatedBook.getDescription() != null) {
            bookFromDB.setDescription(updatedBook.getDescription());
        }
        if (updatedBook.getIsbn10() != null) {
            bookFromDB.setIsbn10(updatedBook.getIsbn10());
        }
        if (updatedBook.getIsbn13() != null) {
            bookFromDB.setIsbn13(updatedBook.getIsbn13());
        }
        if (updatedBook.getImageUrl() != null) {
            bookFromDB.setImageUrl(updatedBook.getImageUrl());
        }
        if (updatedBook.getAuthorDescription() != null) {
            bookFromDB.setAuthorDescription(updatedBook.getAuthorDescription());
        }
        if (updatedBook.getPublisher() != null) {
            bookFromDB.setPublisher(updatedBook.getPublisher());
        }
        if (updatedBook.getFormat() != null) {
            bookFromDB.setFormat(updatedBook.getFormat());
        }
        if (updatedBook.getLanguage() != null) {
            bookFromDB.setLanguage(updatedBook.getLanguage());
        }
        if (updatedBook.getNumPages() != null) {
            bookFromDB.setNumPages(updatedBook.getNumPages());
        }

        String genres = requestDTO.getGenres();
        List<BookGenre> bookGenresFromDb = bookGenreService.findAllByBookId(requestDTO.getInternalBook().getId());
        for (BookGenre it : bookGenresFromDb) {
            bookGenreService.delete(it.getBookGenreId());
        }
        if (!genres.isEmpty()) {

            String[] updateGenreIds = genres.split(",");
            for (String it : updateGenreIds) {
                BookGenre newBookGenre = new BookGenre(new BookGenreId(requestDTO.getInternalBook().getId(), Integer.parseInt(it)));
                bookGenreService.add(newBookGenre);
            }
        }
        return internalBookRepository.save(bookFromDB);
    }

    @Override
    public InternalBookDTO convertToDTO(InternalBook book) {
        InternalBookDTO dto = new InternalBookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublisher(book.getPublisher());
        dto.setAddDate(book.getAddDate());
        dto.setReleaseDate(book.getReleaseDate());
        dto.setImageUrl(book.getImageUrl());
        return dto;
    }
}
