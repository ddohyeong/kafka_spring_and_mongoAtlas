package tmt.mongo_atlas_test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class Producer {
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	private static final String TOPIC = "sharos-events";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String key, String value) {
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC, key, value);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info(String.format("\n\n Produced event to topic %s: key = %-10s value = %s \n\n", TOPIC, key, value));
			}

			@Override
			public void onFailure(Throwable ex) {
				ex.printStackTrace();
			}
		});


	}
}
