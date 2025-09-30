package com.booking.sup.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

  @GetMapping("/")
  public String getMainPage(Model model) {
    return "booking.html";
  }
}
