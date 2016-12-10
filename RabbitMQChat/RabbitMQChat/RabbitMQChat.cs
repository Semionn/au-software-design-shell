using System.Text;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using NLog;

namespace RabbitMQChatProj
{
    /// <summary>
    /// Class for chatting through RabbitMQ. It allows to send broadcast messages to connected message queue
    /// Every message would be signed with provided username
    /// </summary>
    public class RabbitMQChat : IChat
    {
        public delegate void PrintMessage(string message);
        
        private static readonly Logger logger = LogManager.GetCurrentClassLogger();
        private readonly ConnectionFactory factory;
        private readonly string queueName = null;
        private IConnection connection = null;
        private IModel channel = null;

        private string username = "Anon";
        private PrintMessage printMessageMethod = null;
        
        private bool _connectedToServer = false;
        public bool connectedToServer { get { return _connectedToServer; } }

        /// <summary>
        /// Constructor of RabbitMQChat
        /// </summary>
        /// <param name="queueName"> name of queue (like chat room) </param>
        /// <param name="printMessageMethod"> callback, which will be called after receiving incoming message</param>
        public RabbitMQChat(string queueName, PrintMessage printMessageMethod, ConnectionFactory factory = null)
        {
            this.queueName = queueName;
            this.printMessageMethod = printMessageMethod;
            if (factory == null)
            {
                factory = new ConnectionFactory();
            }
            this.factory = factory;
        }

        /// <summary>
        /// Changes current username
        /// </summary>
        /// <param name="username"></param>
        public void setName(string username)
        {
            this.username = username;
        }

        private string BuildMessage(string message)
        {
            string res = string.Format("{0}: {1}", username, message);
            return res;
        }

        /// <summary>
        /// Connects to the MQ server. If connection already established, the method returns false - true otherwise
        /// </summary>
        /// <param name="serverAddress">ip address or hostname of MQ server</param>
        /// <returns></returns>
        public bool connect(string serverAddress)
        {
            if (connectedToServer)
            {
                return false;
            }
            close();
            factory.HostName = serverAddress;
            connection = factory.CreateConnection();
            channel = connection.CreateModel();
            channel.QueueDeclare(queue: queueName,
                                 durable: false,
                                 exclusive: false,
                                 autoDelete: false,
                                 arguments: null);

            var consumer = new EventingBasicConsumer(channel);
            consumer.Received += (model, ea) =>
            {
                var body = ea.Body;
                var message = Encoding.UTF8.GetString(body);
                logger.Info("Receive message with hash: {0}", message.GetHashCode());

                printMessageMethod(message);
            };
            channel.BasicConsume(queue: queueName,
                                 noAck: true,
                                 consumer: consumer);

            _connectedToServer = true;
            logger.Info("Start listening at queue: {0}", queueName);
            return true;
        }

        /// <summary>
        /// Sends message to connected MQ. If connections doesn't established, returns false - true otherwise
        /// </summary>
        /// <param name="message">message to send</param>
        /// <returns></returns>
        public bool sendMessage(string message)
        {
            if (channel == null)
            {
                return false;
            }
            
            var body = Encoding.UTF8.GetBytes(message);

            for (int i = 0; i < channel.ConsumerCount(queueName); i++)
            {
                channel.BasicPublish(exchange: "",
                                        routingKey: queueName,
                                        basicProperties: null,
                                        body: body);
            }
            return true;
        }

        /// <summary>
        /// Closes connections, if exists.
        /// Should be called after the chat session for correct work.
        /// </summary>
        public void close()
        {
            if (channel != null)
            {
                channel.Close();
                channel = null;
                logger.Info("Stop listening at queue: {0}", queueName);
            }
            if (connection != null)
            {
                connection.Close();
                logger.Info("Connection to MQ '{0}:{1}' closed'", connection.Endpoint.HostName, connection.Endpoint.Port);
                connection = null;
            }
        }
    }
}
