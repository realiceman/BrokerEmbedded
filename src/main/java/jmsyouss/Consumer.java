package jmsyouss;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Code:");
		String code = scanner.nextLine();
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			Connection connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//Destination destination = session.createQueue("cnam.queue");
			Destination destination = session.createTopic("cnam.topic");
		    MessageConsumer consumer = session.createConsumer(destination,"code='"+code+"'"); 
		    consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					if(message instanceof TextMessage) {
					    try {	
						TextMessage textmessage = (TextMessage) message;
							System.out.println("reception du message : "+textmessage.getText());
						} catch (JMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			});
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
