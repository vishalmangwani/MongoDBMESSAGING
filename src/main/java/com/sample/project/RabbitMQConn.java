package com.sample.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.QueueingConsumer;

public class RabbitMQConn {
	private static String routingKey="test";
	private static Connection connection;
	private static  Channel channel;
	public static void connectAndBuildExchange(String exchangeName) throws Exception 
	{
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    factory.setPort(5672);
	    factory.setUsername("guest");
	    factory.setPassword("guest");
	    connection = factory.newConnection();
	    channel = connection.createChannel(); 
	    channel.exchangeDeclare(exchangeName,"direct",true);
	}
	
	public static  void closeConnection() throws Exception{
		channel.close();
		connection.close();
	}
	
	public static boolean createQueue(String queueName,String exchangeName) throws Exception {
			try{
				channel.queueDeclare(queueName, true, false, false,null);
				channel.queueBind(queueName, exchangeName, routingKey);
				return true;
				}
				catch(Exception e1){e1.printStackTrace();return false;}
		}
	
	public static byte[] getAllMessage(String queueName) throws Exception{
		boolean autoAck = false;
		GetResponse response = channel.basicGet(queueName, autoAck);
		if (response == null) {
		    return null;
		} else {
		    byte[] body = response.getBody();
		    return body;
		}
	}
	
	public static boolean postMessage(String message, String exchangeName) throws Exception{
		byte[] messageBodyBytes = message.getBytes();
		System.out.println("exchngname="+exchangeName);
		try{
			channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public static List<String> readAllMessages(String queueName) throws Exception{
		List<String> ls=new ArrayList<>();
		channel.basicQos(100);
		Consumer consumer = new DefaultConsumer(channel) {
			  @Override
			  public void handleDelivery(String consumerTag, Envelope envelope,
			                             AMQP.BasicProperties properties, byte[] body)
			      throws IOException {
			    String message = new String(body, "UTF-8");
			    ls.add(message);
			  }
			};
			
			System.out.println(ls);
			return ls;
	}
	
}
