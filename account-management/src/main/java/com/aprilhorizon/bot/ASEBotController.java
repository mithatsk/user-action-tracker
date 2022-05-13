package com.aprilhorizon.bot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ASEBotController {

    public ASEBotController() {}

    @PostMapping("/bot/replicateActions")
    @ResponseStatus(HttpStatus.OK)
    public void replicateActions() throws Exception {
        ASEBot bot = new ASEBot();
        bot.replicateActions();
    }
}
