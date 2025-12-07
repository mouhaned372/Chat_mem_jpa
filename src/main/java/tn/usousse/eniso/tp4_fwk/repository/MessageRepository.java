package tn.usousse.eniso.tp4_fwk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.usousse.eniso.tp4_fwk.entity.Message;
import tn.usousse.eniso.tp4_fwk.entity.User;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findFirstByReceiverAndDeliveredFalseOrderByTimestampAsc(User receiver);
    long countByReceiverAndDeliveredFalse(User receiver);
}