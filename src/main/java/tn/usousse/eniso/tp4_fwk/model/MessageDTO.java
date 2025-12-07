package tn.usousse.eniso.tp4_fwk.model;

public class MessageDTO {
    private String destinataire;  
    private String auteur;       
    private String contenu;  
    
    public MessageDTO() {
    }
    
    
    public MessageDTO(String destinataire, String auteur, String contenu) {
        this.destinataire = destinataire;
        this.auteur = auteur;
        this.contenu = contenu;
    }
    
  
    
    public String getDestinataire() {
        return destinataire;
    }
    
    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }
    
    public String getAuteur() {
        return auteur;
    }
    
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
    
    public String getContenu() {
        return contenu;
    }
    
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    
    @Override
    public String toString() {
        return "MessageDTO{" +
                "destinataire='" + destinataire + '\'' +
                ", auteur='" + auteur + '\'' +
                ", contenu='" + contenu + '\'' +
                '}';
    }
}