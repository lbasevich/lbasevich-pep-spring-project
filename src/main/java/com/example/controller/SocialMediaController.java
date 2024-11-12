package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }


    @PostMapping("register")
    public ResponseEntity<Account> registration (@RequestBody Account account){

        if (account.getUsername().length()<1 || account.getPassword().length()<4){
            return ResponseEntity.status(400)
            .body(null);
        }                                                    
    
        Account createdAccount = accountService.registerService (new Account (account.getUsername(), account.getPassword()));

        if (createdAccount != null) {
            return ResponseEntity.status(200)
                .body(createdAccount);
        }
        else   
            return ResponseEntity.status(409)
                .body(createdAccount);

    }


    @PostMapping("login")
    public ResponseEntity<Account> login (@RequestBody Account account){                                                 
    
        Account loginAccount = accountService.loginService (new Account (account.getAccountId(), account.getUsername(), account.getPassword()));

        if (loginAccount != null) {
            return ResponseEntity.status(200)
                .body(loginAccount);
        }
        else   
            return ResponseEntity.status(401)
                .body(loginAccount);

    }


    @PostMapping("messages")
    public ResponseEntity<Message> addMessage (@RequestBody Message message){

        if (message.getMessageText().length()<1 || message.getMessageText().length()>255){
            return ResponseEntity.status(400)
            .body(null);
        }                                                    
    
        Message createdMessage = messageService.createMessageService (new Message (message.getPostedBy(), message.getMessageText(), message.getTimePostedEpoch()));

        if (createdMessage != null) {
            return ResponseEntity.status(200)
                .body(createdMessage);
        }
        else   
            return ResponseEntity.status(400)
                .body(createdMessage);

    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages (){
                                                   
        List <Message> allMessages = messageService.getAllMessagesService();

        return ResponseEntity.status(200)
                .body(allMessages);
        }

    @GetMapping("messages/{messageId}")
        public ResponseEntity<Message> getMessageById (@PathVariable int messageId){   
            Message message = messageService.getMessageByIdService(messageId);
    
            return ResponseEntity.status(200)
                    .body(message);
            }
    @DeleteMapping("messages/{messageId}")
            public ResponseEntity<Integer> deleteMessageById (@PathVariable int messageId){   
                
                Integer deleteResponse = messageService.deleteMessageByIdService(messageId);
        
                return ResponseEntity.status(200)
                        .body(deleteResponse);
                }
    @PatchMapping("messages/{messageId}")
            public ResponseEntity<Integer> updateMessageById (@PathVariable int messageId, @RequestBody Message message){
                if (message.getMessageText().length()<1 || message.getMessageText().length()>255)
                    return ResponseEntity.status(400)
                            .body(null);                     
                    
                Integer updateResponse = messageService.updateMessageByIdService(messageId, message.getMessageText());

                if (updateResponse == 1)
                    return ResponseEntity.status(200)
                            .body(updateResponse);
                    
                else
                    return ResponseEntity.status(400)
                        .body(updateResponse);
                }
                

    @GetMapping("accounts/{accountId}/messages")
                public ResponseEntity<List<Message>> getAllMessageByAccountId (@PathVariable int accountId){
                    
                    List <Message> allMessages = messageService.getAllMessageByAccountIdService(accountId);

                        return ResponseEntity.status(200)
                                .body(allMessages);
                        
                }
}