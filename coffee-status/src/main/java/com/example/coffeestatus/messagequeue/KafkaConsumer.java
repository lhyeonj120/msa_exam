package com.example.coffeestatus.messagequeue;

import com.example.coffeestatus.mapper.CoffeeMapper;
import com.example.coffeestatus.vo.CoffeeStatusVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaConsumer {

    @Autowired
    private CoffeeMapper coffeeMapper;

    @KafkaListener(topics = "kosa-kafka-test")
    public void processMessage(String kafkaMessage){
        System.out.println("kafka Message : " + kafkaMessage);
        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try{
            // kafkaMessage 문자열을 mapper를 이용하여 key, value 형식으로 전달하도록
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        CoffeeStatusVO vo = new CoffeeStatusVO();
        vo.setOrderNumber(map.get("orderNumber").toString());
        vo.setCoffeeName(map.get("coffeeName").toString());
        vo.setCoffeeCount((Integer)map.get("coffeeCount"));
        vo.setCustomerName(map.get("customerName").toString());

        // DB 전달
        coffeeMapper.insertCoffeeOrderStatus(vo);
    }

}
