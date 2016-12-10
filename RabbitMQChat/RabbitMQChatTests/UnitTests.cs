using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using RabbitMQ.Client;

namespace RabbitMQChatTests
{
    [TestClass]
    public class UnitTests
    {
        [TestMethod]
        public void TestConnection()
        {
            IModel channelMock = Mock.Of<IModel>();
            IConnection connectionMock = Mock.Of<IConnection>(d => d.CreateModel() == channelMock);
            ConnectionFactory factoryMock = Mock.Of<ConnectionFactory>(d => d.CreateConnection() == connectionMock);
            string queueName = "test";
            var chat = new RabbitMQChatProj.RabbitMQChat(queueName, message => { }, factoryMock);

            var hostname = "testhostname";
            Assert.IsTrue(chat.connect(hostname));
            Mock.Get(channelMock).Verify(channel => channel.QueueDeclare(queueName, false, false, false, null));
            Mock.Get(connectionMock).Verify(connection => connection.CreateModel());
            Mock.Get(factoryMock).Verify(factory => factory.CreateConnection());
            Assert.AreEqual(factoryMock.HostName, hostname);
        }

        // And so on... 
    }
}
