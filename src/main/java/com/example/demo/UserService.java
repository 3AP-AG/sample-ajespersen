package com.example.demo;

import java.util.List;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

  @Autowired private MailService mailService;
  @Autowired private UserRepository userRepository;

  public MailService getMailService() {
    return mailService;
  }

  public UserRepository getUserRepository() {
    return userRepository;
  }

  public String registerUser(String name) {
    System.out.println("registerUser " + name);
    userRepository.save(new Customer("first " + name, "last " + name));
    return name;
  }

  public List<String> allUserNames() {
    Iterable<Customer> all = userRepository.findAll();
    var res =
        StreamSupport.stream(all.spliterator(), false)
            .map(x -> x.getFirstName() + " <-> " + x.getLastName());
    return res.toList();
  }
}
