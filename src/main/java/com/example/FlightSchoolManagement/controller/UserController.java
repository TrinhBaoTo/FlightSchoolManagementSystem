package com.example.FlightSchoolManagementSystem;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    // User Storage: Map ID-name
    private Map<Integer, String> users = new HashMap<>();

    // GET to retrieve all users
    @GetMapping("/users")
    public Map<Integer, String> getUsers(){
        return users;
    }

    // GET: retrieve a specific user by ID
    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable Integer id) {
        return users.get(id);
    }

    // POST: add a new user
    @PostMapping
    public String addUser(@RequestParam Integer id, @RequestParam String name) {
        users.put(id, name);
        return "User added successfully";
    }

    // PUT: update an existing user
    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable Integer id, @RequestParam String name) {
        if (users.containsKey(id)) {
            users.put(id, name);
            return "User updated successfully";
        } else {
            return "User not found";
        }
    }

    // DELETE: delete a user by ID
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Integer id) {
        if (users.containsKey(id)) {
            users.remove(id);
            return "User deleted successfully";
        } else {
            return "User not found";
        }
    }

    // TRACE: testing purposes (echo server)
    // Failed Test
    @RequestMapping(value = "/trace", method = RequestMethod.TRACE)
    public String echoServer(@RequestBody String body) {
        return body;
    }

    // OPTIONS: get supported HTTP methods
    @RequestMapping(value = "/options", method = RequestMethod.OPTIONS)
    public String getSupportedMethods() {
        return "GET, POST, PUT, DELETE, TRACE, OPTIONS, CONNECT, HEAD";
    }

    // HEAD: retrieve only the headers of a resource
    @RequestMapping(value = "/head", method = RequestMethod.HEAD)
    public ResponseEntity<Void> getUserHeaders() {
        // Add new header to return
        HttpHeaders headers = new HttpHeaders();
        headers.add("NEW-header", "xxxxxxx");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    // Does not support CONNECT but have PATCH - apply partial modifications to a resource.
}
