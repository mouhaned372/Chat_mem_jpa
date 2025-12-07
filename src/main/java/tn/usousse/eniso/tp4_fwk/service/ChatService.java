package tn.usousse.eniso.tp4_fwk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.usousse.eniso.tp4_fwk.dal.MessageDao;
import tn.usousse.eniso.tp4_fwk.model.MessageDTO;

@Service
public class ChatService {
    
    @Autowired
    private MessageDao messageDao;
    
    public boolean envoyerMessage(MessageDTO message) {
        return messageDao.ajouterMessage(message);
    }
    
    public MessageDTO recevoirMessage(String destinataire, long timeout) throws InterruptedException {
        return messageDao.recupererMessage(destinataire, timeout);
    }
    
    public MessageDTO recevoirMessageBloquant(String destinataire) throws InterruptedException {
        return messageDao.recupererMessageBloquant(destinataire);
    }
}