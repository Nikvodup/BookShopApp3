package com.example.data.Genre;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepo genreRepo;

    public GenreService(GenreRepo genreRepo) {
        this.genreRepo = genreRepo;
    }

    public Map<Genre.GenreType, List<Genre>> getGenreMap() {
        List<Genre> genres = genreRepo.findAll();

        if (!genres.isEmpty()) {
            return genres.stream().collect(Collectors.groupingBy(Genre::getGenreType));
        }
        return new HashMap<>();
    }
}
