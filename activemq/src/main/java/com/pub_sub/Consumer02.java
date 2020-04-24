package com.pub_sub;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 发布/订阅 模式
 *
 * 一对多，一个消息生产者生产的消息可以供多个消费者消费
 *
 * 消息的消费者
 */
public class Consumer02 {
    public static void main(String[] args) {
        Connection connection = null;
        Session session = null;
        MessageProducer producer =null ;
        //创建一个连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.6:61616");
        try {
            //创建一个连接
            connection = connectionFactory.createConnection();
            //设置客户端ID,每个消费者的客户端ID都不相同，名称随意
            connection.setClientID("pub_sub_topic_c02");
            //启动连接
            connection.start();
            //创建一个session
            //第一个参数代表是否支持事务，第二个参数表示应答方式，这里选择自动应答
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建一个主题
            Topic pub_sub_topic = session.createTopic("pub_sub_topic");
            //创建一个消息消费者
            MessageConsumer consumer = session.createDurableSubscriber(pub_sub_topic,"topic_c02_02");
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