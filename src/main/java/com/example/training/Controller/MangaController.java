package com.example.training.Controller;

import com.example.training.Entity.Manga;
import com.example.training.Repository.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MangaController {

    @Autowired
    MangaRepository mangaRepository;

    @GetMapping("/manga")
    public List<Manga> findAll (){
        return mangaRepository.findAll();
    }

    @GetMapping("/manga/{id}")
    public Manga findSingleManga (@PathVariable int id){
        return mangaRepository.findById(id).get();

    }

    @PostMapping("/manga")
    public ResponseEntity<String> addNewManga (@RequestBody Manga manga){
        try{
            mangaRepository.save(manga);
            return ResponseEntity.status(HttpStatus.OK).body("created");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/manga/{id}")
    @ResponseBody
    public ResponseEntity<String> updateManga (@RequestBody Manga mangaData, @PathVariable int id){

        try{
            Manga manga = mangaRepository.findById(id).get();
            manga.setName(mangaData.getName());
            manga.setAuthor(mangaData.getAuthor());
            manga.setGenre(mangaData.getGenre());
            manga.setNote(mangaData.getNote());
            mangaRepository.save(manga);
            return ResponseEntity.status(HttpStatus.OK).body("updated");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/manga/{id}")
    public ResponseEntity<String> removeManga (@PathVariable int id){
        try{
            mangaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("deleted");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
