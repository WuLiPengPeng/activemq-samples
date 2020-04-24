package com.p2p;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * p2p 点对点模式
 *
 * 一对一，一个生产者生产的消息只能有一个消息消费者消费，消费后就没了
 *
 * 消息的生产者
 */
public class Producer {
    public static  void main (String [] args){
        Connection connection = null;
        Session session = null;
        MessageProducer producer =null ;
        //创建一个连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.6:61616");
        try {
            //创建一个连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建一个session
            //第一个参数代表是否支持事务，第二个参数表示应答方式，这里选择自动应答
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建一个消息队列
            Queue text_queue = session.createQueue("p2p_queue");
            //创建一个消息生产者
            producer = session.createProducer(text_queue);
            //创建一个文本消息
            TextMessage textMessage = session.createTextMessage("Hello ActiveMQ !");
            //发送消息
            producer.send(textMessage);

        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            if(producer != null){
                try {
                    producer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if(session != null){
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
