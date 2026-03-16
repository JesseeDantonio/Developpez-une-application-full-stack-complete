package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.in.UserCreateDTO;
import com.openclassrooms.mddapi.dto.out.UserDTO;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    // Route GET /api/users
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Récupère tous les utilisateurs.
     * @return La réponse HTTP avec la liste des utilisateurs
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Récupère un utilisateur par son ID.
     * @param id L'ID de l'utilisateur
     * @return La réponse HTTP avec les détails de l'utilisateur
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    /**
     * Crée un nouvel utilisateur.
     * @param userDto Les données du nouvel utilisateur
     * @return La réponse HTTP avec les détails de l'utilisateur créé
     */
    @PostMapping
    public ResponseEntity<UserCreateDTO> createUser(@RequestBody UserCreateDTO userDto) {
        UserCreateDTO createdUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Met à jour un utilisateur existant.
     * @param id L'ID de l'utilisateur à mettre à jour
     * @param userDto Les données de l'utilisateur mis à jour
     * @return La réponse HTTP avec les détails de l'utilisateur mis à jour
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserCreateDTO userDto) {
        UserDTO updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Supprime un utilisateur.
     * @param id L'ID de l'utilisateur à supprimer
     * @return La réponse HTTP
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
