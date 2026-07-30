package com.example.demo;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public String register(@RequestParam String item) {
    return userService.registerUser(item);
  }

  @PostMapping("/registerCustomer")
  ResponseEntity<String> addUser(@Valid @RequestBody Customer customer) {
    userService.registerUser(customer.getFirstName() + " --- " + customer.getLastName());
    return ResponseEntity.ok("User is valid");
  }

  @GetMapping("/greeting")
  public Greeting greeting(@RequestParam(defaultValue = "Woorld") String name) {
    return new Greeting(counter.incrementAndGet(), template.formatted(name));
  }

  @GetMapping("/allusernames")
  public List<String> allusernames() {
    return userService.allUserNames();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });
    return errors;
  }
}
