package tn.usousse.eniso.tp4_fwk.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.usousse.eniso.tp4_fwk.model.MessageDTO;
import tn.usousse.eniso.tp4_fwk.service.ChatService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/chat")
public class ChatController {
    
    @Autowired
    private ChatService chatService;
    
    @PostMapping("/send")
    public ResponseEntity<String> envoyerMessage(@RequestBody MessageDTO message) {
        try {
            boolean succes = chatService.envoyerMessage(message);
            
            if (succes) {
                return ResponseEntity.ok("{\"status\": \"Message envoyé avec succès\"}");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Échec de l'envoi du message\"}");
            }
            
        } catch (Exception e) {
            System.err.println("Erreur dans envoyerMessage : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\": \"Erreur serveur: " + e.getMessage() + "\"}");
        }
    }
    
    @PostMapping("/receive/{destinataire}")
    public ResponseEntity<MessageDTO> recevoirMessage(
        @PathVariable String destinataire,
        @RequestParam(defaultValue = "30000") long timeout) throws InterruptedException {
    
        MessageDTO message = chatService.recevoirMessage(destinataire, timeout);
        return message != null 
           ? ResponseEntity.ok(message) 
           : ResponseEntity.noContent().build();
    }
    
    
    
    @GetMapping("/receive/{destinataire}")
    public ResponseEntity<MessageDTO> recevoirMessageBloquant(
        @PathVariable String destinataire) throws InterruptedException {
        MessageDTO message = chatService.recevoirMessageBloquant(destinataire);
        return ResponseEntity.ok(message);
    }
    
}