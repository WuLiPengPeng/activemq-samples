package com.p2p;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * p2p 点对点模式
 *
 * 一对一，一个生产者生产的消息只能有一个消息消费者消费，消费后就没了
 *
 * 消息的消费者
 */
public class Consumer {
    public static void main(String[] args) {
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
            //创建一个消息消费者
            MessageConsumer consumer = session.createConsumer(text_queue);

            //接收消息 (如果此时队列中没有消息可以消费，那么程序会被阻塞在这里)
            Message msg = consumer.receive();

            //获取数据
            if(msg instanceof TextMessage){
                TextMessage testMsg = (TextMessage) msg;
                System.out.println("消息内容是："+testMsg.getText());
            }
            System.out.println("=================");
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
