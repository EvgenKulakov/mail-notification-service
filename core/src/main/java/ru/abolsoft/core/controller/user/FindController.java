package ru.abolsoft.core.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.abolsoft.core.entity.Account;
import ru.abolsoft.core.entity.ImageMetadata;
import ru.abolsoft.core.service.AccountService;
import ru.abolsoft.core.service.ImageMetadataService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FindController {

    @Autowired
    private ImageMetadataService imageMetadataService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/images")
    public List<ImageMetadata> getImages(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Long size,
            @RequestParam(required = false) LocalDate uploadDate,
            Principal principal) {

        Account currentAccount = accountService.getAccountByLogin(principal.getName());

        return imageMetadataService.getAllImagesByAccount(currentAccount.getId(), sortBy, size, uploadDate);
    }
}
