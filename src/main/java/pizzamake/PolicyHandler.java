package pizzamake;

import pizzamake.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    MakeRepository makeRepository;

//    @StreamListener(KafkaProcessor.INPUT)
//    public void onStringEventListener(@Payload String eventString){
//
//    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrdered_Make(@Payload Ordered ordered){

        System.out.println("##### listener Make : " + ordered.toJson());

        if(ordered.isMe()){
            Make make = new Make();
            make.setOrderId(ordered.getId());
            make.setPizzaname(ordered.getPizzaname());
            make.setQty(ordered.getQty());
            make.setStatus("WAITING");
            makeRepository.save(make);
        }

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCanceled_Cancel(@Payload OrderCanceled orderCanceled){

        System.out.println("##### listener Cancel : " + orderCanceled.toJson());

        if(orderCanceled.isMe()){
            Make make = makeRepository.findByOrderId(orderCanceled.getId());
            if(make != null) {
                make.setOrderId(orderCanceled.getId());
                make.setStatus("CANCELLED");
                makeRepository.save(make);
            }
        }
    }

}
