package com.pub_sub;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 发布/订阅 模式
 *
 * 一对多，一个消息生产者生产的消息可以供多个消费者消费
 *
 * 此模式时，消费者必须是存活的，如果生产消息的时候，消费者没有存活，是无法订阅，无法消费的
 * 换言之，消费者想要消费消息，必须比生产者早启动，否则当生产者发布主题后再去订阅，是消费不到的
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
            //创建一个主题
            Topic pub_sub_topic = session.createTopic("pub_sub_topic");
            //创建一个消息生产者
            producer = session.createProducer(pub_sub_topic);
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
