package com.fslk.hairroomapi.controllers;

import com.fslk.hairroomapi.entities.Customer;
import com.fslk.hairroomapi.entities.Hairdresser;
import com.fslk.hairroomapi.exception.ResourceNotFoundException;
import com.fslk.hairroomapi.repositories.CustomerRepository;
import com.fslk.hairroomapi.repositories.HairdresserRepository;
import com.fslk.hairroomapi.service.HairService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class HairdresserController {
    private static String UPLOADED_FOLDER = "F://temp//";
    @Autowired
    HairdresserRepository repository;
    @Autowired
    CustomerRepository custRepository;
    @Autowired
    ServletContext context;
    @Autowired
    HairService hairService;


    @GetMapping("/hairdressers")
    public List<Hairdresser> getAllhairdressers() {
        System.out.println("Get all hairdressers...");
        List<Hairdresser> hairdressers = new ArrayList<>();
        repository.findAll().forEach(hairdressers::add);

        return hairdressers;
    }

    @PostMapping("/hairdressers")
    public ResponseEntity<Hairdresser> createHairdresser(@RequestBody Hairdresser hairdresser) {
        try {
            Hairdresser _hairdresser = repository
                    .save(new Hairdresser(null, hairdresser.getFirstname(), hairdresser.getLastname(), hairdresser.getPhone(),
                            hairdresser.getPhoto()));
            return new ResponseEntity<>(_hairdresser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@PostMapping("/hairdressers")
    public ResponseEntity<Message> createHairdresser(@RequestParam("file") MultipartFile file,
                                                     @RequestParam("hairdresser") String hairdresser) throws Exception {
        System.out.println("Ok .............");
        Hairdresser hairdr = new ObjectMapper().readValue(hairdresser, Hairdresser.class);
        boolean isExit = new File(context.getRealPath(System.getProperty("user.home") + "/Desktop/Bureau/JEE-Angular/hairRoom/photoHairRoom/")).exists();
        if (!isExit) {
            new File(context.getRealPath(System.getProperty("user.home") + "/Desktop/Bureau/JEE-Angular/hairRoom/photoHairRoom/")).mkdir();
            System.out.println("mk dir.............");
        }
        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
        File serverFile = new File(context.getRealPath(System.getProperty("user.home") + "/Desktop/Bureau/JEE-Angular/hairRoom/photoHairRoom/" + File.separator + newFileName));
        try {
            System.out.println("image");
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
        hairdr.setPhoto(newFileName);
        Hairdresser hair = repository.save(hairdr);
        if (hair != null) {
            return new ResponseEntity<Message>(new Message(""), HttpStatus.OK);
        } else {
            return new ResponseEntity<Message>(new Message("Hairdresser not saved"), HttpStatus.BAD_REQUEST);
        }
    }*/

    @GetMapping("/hairdressers/{id}")
    public ResponseEntity<Hairdresser> getHairdresserById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Hairdresser Hairdresser = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categorie not found for this id :: " + id));
        return ResponseEntity.ok().body(Hairdresser);
    }

    @GetMapping(value = "/hairdressers/photo/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhoto(@PathVariable("id") Long id) throws Exception {
        Hairdresser hair = repository.findById(id).get();
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Desktop/Dev/hairRoom/photoHairRoom/" + hair.getPhoto()));
    }

    @GetMapping(value = "/hairdressers/hairPhoto/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Desktop/Dev/hairRoom/photoHairRoom/" + fileName));
    }
    @GetMapping("//hairdressers/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = hairService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    /*@PostMapping(value = "/hairdressers/uploadPhoto/{fileName")
    public byte[] uploadPhoto(@RequestParam("file") MultipartFile file, @PathVariable String fileName) throws Exception{
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Desktop/Dev/hairRoom/photoHairRoom/" + fileName));
    }*/

    @DeleteMapping("/hairdressers/{id}")
    public Map<String, Boolean> deleteHairdresser(@PathVariable(value = "id") Long HairdresserId)
            throws ResourceNotFoundException {
        Hairdresser Hairdresser = repository.findById(HairdresserId)
                .orElseThrow(() -> new ResourceNotFoundException("Hairdresser not found  id :: " + HairdresserId));
        repository.delete(Hairdresser);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @DeleteMapping("/hairdressers/delete")
    public ResponseEntity<String> deleteAllhairdressers() {
        System.out.println("Delete All hairdressers...");
        repository.deleteAll();
        return new ResponseEntity<>("All hairdressers have been deleted!", HttpStatus.OK);
    }

    @PutMapping("/hairdressers/{id}")
    public ResponseEntity<Hairdresser> updateHairdresser(@PathVariable("id") long id, @RequestBody Hairdresser hairdres) {
        System.out.println("Update Hairdresser with ID = " + id + "...");
        Optional<Hairdresser> HairdresserInfo = repository.findById(id);
        if (HairdresserInfo.isPresent()) {
            Hairdresser hairdresser = HairdresserInfo.get();
            hairdresser.setFirstname(hairdres.getFirstname());
            hairdresser.setLastname(hairdres.getLastname());
            hairdresser.setPhone(hairdres.getPhone());
            hairdresser.setPhoto(hairdres.getPhoto());
            return new ResponseEntity<>(repository.save(hairdresser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
/*
    @GetMapping(value = "/hairdressers/{idHaidr}/customers", produces = "application/json")
    public List<Customer> getCustomerByIdHairdresser(@PathVariable Long idHaidr) {
        return custRepository.findByHairdresser(idHaidr);
    }*/

    @PostMapping(value = "/hairdressers/uploadPhoto/{id}")
    public void uploadPhoto(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws Exception{
        Hairdresser hairdresser = repository.findById(id).get();
        hairdresser.setPhoto(id + ".png");
        // eleve.setPhotoName(file.getOriginalFilename());
        Files.write(Paths.get(System.getProperty("user.home") + "/Desktop/Bureau/JEE-Angular/hairRoom/photoHairRoom/" +
                hairdresser.getPhoto()), file.getBytes());
        repository.save(hairdresser);
    }

    @PostMapping("/hairdressers/uploadSimplePhoto") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:new-hairdresser";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(System.getProperty("user.home") + "/Desktop/Bureau/JEE-Angular/hairRoom/photoHairRoom/" + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/new-hairdresser";
    }

}
