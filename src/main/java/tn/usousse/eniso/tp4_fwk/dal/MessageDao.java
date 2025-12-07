//exercice 2

package tn.usousse.eniso.tp4_fwk.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tn.usousse.eniso.tp4_fwk.entity.Message;
import tn.usousse.eniso.tp4_fwk.entity.User;
import tn.usousse.eniso.tp4_fwk.model.MessageDTO;
import tn.usousse.eniso.tp4_fwk.repository.MessageRepository;
import tn.usousse.eniso.tp4_fwk.repository.UserRepository;

import java.util.concurrent.TimeUnit;

@Repository
public class MessageDao {
    
    @Autowired
    private MessageRepository messageRepo;
    
    @Autowired
    private UserRepository userRepo;
    
    @Transactional
    public boolean ajouterMessage(MessageDTO dto) {
        try {
            User sender = getOrCreate(dto.getAuteur());
            User receiver = getOrCreate(dto.getDestinataire());
            
            Message msg = new Message(dto.getContenu(), sender, receiver);
            msg.setDelivered(false);
            messageRepo.save(msg);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Transactional
    public MessageDTO recupererMessage(String destinataire, long timeout) throws InterruptedException {
        long endTime = System.currentTimeMillis() + timeout;
        
        while (System.currentTimeMillis() < endTime) {
            User user = userRepo.findByUsername(destinataire).orElse(null);
            if (user != null) {
                Message msg = messageRepo.findFirstByReceiverAndDeliveredFalseOrderByTimestampAsc(user).orElse(null);
                if (msg != null) {
                    msg.setDelivered(true);
                    messageRepo.save(msg);
                    return toDTO(msg);
                }
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }
        return null;
    }
    
    @Transactional
    public MessageDTO recupererMessageBloquant(String destinataire) throws InterruptedException {
        while (true) {
            User user = userRepo.findByUsername(destinataire).orElse(null);
            if (user != null) {
                Message msg = messageRepo.findFirstByReceiverAndDeliveredFalseOrderByTimestampAsc(user).orElse(null);
                if (msg != null) {
                    msg.setDelivered(true);
                    messageRepo.save(msg);
                    return toDTO(msg);
                }
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
    
    private User getOrCreate(String username) {
        return userRepo.findByUsername(username)
            .orElseGet(() -> userRepo.save(new User(username)));
    }
    
    private MessageDTO toDTO(Message msg) {
        return new MessageDTO(
            msg.getReceiver().getUsername(),
            msg.getSender().getUsername(),
            msg.getContent()
        );
    }
    
    public long countMessagesEnAttente(String destinataire) {
        return userRepo.findByUsername(destinataire)
            .map(user -> messageRepo.countByReceiverAndDeliveredFalse(user))
            .orElse(0L);
    }
}



//exercice 1
/**package tn.usousse.eniso.tp4_fwk.dal;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import tn.usousse.eniso.tp4_fwk.model.MessageDTO;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDao {
    
    private HashMap<String, LinkedBlockingQueue<MessageDTO>> filesMessages;
    
    @PostConstruct  
    public void init() {
        filesMessages = new HashMap<>();
        System.out.println("MessageDao initialisé");
    }
    
    public boolean ajouterMessage(MessageDTO message) {
        try {
            String destinataire = message.getDestinataire();
            
            LinkedBlockingQueue<MessageDTO> fileDestinataire = filesMessages.get(destinataire);
            if (fileDestinataire == null) {
                fileDestinataire = new LinkedBlockingQueue<>();
                filesMessages.put(destinataire, fileDestinataire);
                System.out.println("Création file pour " + destinataire);
            }
            
            boolean success = fileDestinataire.offer(message);
            System.out.println("Message ajouté pour " + destinataire + 
                             " (succès: " + success + ")");
            return success;
            
        } catch (Exception e) {
            System.err.println("Erreur ajouterMessage: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public MessageDTO recupererMessage(String destinataire, long timeoutMs) throws InterruptedException {
        LinkedBlockingQueue<MessageDTO> fileDestinataire = filesMessages.get(destinataire);
        if (fileDestinataire == null) {
            fileDestinataire = new LinkedBlockingQueue<>();
            filesMessages.put(destinataire, fileDestinataire);
        }
        MessageDTO message = fileDestinataire.poll(timeoutMs, java.util.concurrent.TimeUnit.MILLISECONDS);
        return message;
    }

    public MessageDTO recupererMessageBloquant(String destinataire) throws InterruptedException {
        LinkedBlockingQueue<MessageDTO> fileDestinataire = filesMessages.get(destinataire); 
        if (fileDestinataire == null) {
            fileDestinataire = new LinkedBlockingQueue<>();
            filesMessages.put(destinataire, fileDestinataire);
        }
        MessageDTO message = fileDestinataire.take();
        return message;
    }
}
*/