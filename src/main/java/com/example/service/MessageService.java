package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;

    }

    public Message createMessageService(Message message){

        Optional<Account> optionalAccount = accountRepository.findById(message.getPostedBy());
        if(optionalAccount.isEmpty()){
            return null;
        }

        else {
            return messageRepository.save(message);
        }
    }

    public List <Message> getAllMessagesService(){
            return messageRepository.findAll();
        }
    
    public Message getMessageByIdService(int messageId){
        Optional <Message> optionalMessage = messageRepository.findById(messageId);

        if (optionalMessage.isPresent())
            return optionalMessage.get();
        else
            return null;

        }
    public Integer deleteMessageByIdService(int messageId){
            Optional <Message> optionalMessage = messageRepository.findById(messageId);
    
            if (optionalMessage.isPresent()){
                messageRepository.deleteById(messageId);
                return 1;
            }
            else
                return null;
    
            }

    public Integer updateMessageByIdService(int messageId, String messageText){
                Optional <Message> optionalMessage = messageRepository.findById(messageId);
        
                if (optionalMessage.isPresent()){
                    optionalMessage.get().setMessageText(messageText);
                    messageRepository.save(optionalMessage.get());
                    return 1;
                }
                else
                    return 2;
        
                }
    public List <Message> getAllMessageByAccountIdService(int accountId){
                    return messageRepository.findAllByPostedBy(accountId).get();
                }
}

    // public void updateGrocery(long id, Grocery replacement){
    //     //findById returns a type Optional<Grocery>. This helps the developer avoid null pointer
    //     //exceptions. We can use the method .get() to convert an Optional<Grocery> to Grocery.
    //     Optional<Grocery> optionalGrocery = groceryRepository.findById(id);
    //     if(optionalGrocery.isPresent()){
    //         Grocery grocery = optionalGrocery.get();
    //         grocery.setName(replacement.getName());
    //         groceryRepository.save(grocery);
    //     }