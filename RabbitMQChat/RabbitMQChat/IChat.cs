using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RabbitMQChatProj
{
    public interface IChat
    {
        /// <summary>
        /// Changes current username
        /// </summary>
        /// <param name="username"></param>
        void setName(string username);

        /// <summary>
        /// Connects to the MQ. If connection already established, the method returns false - true otherwise
        /// </summary>
        /// <param name="serverAddress">ip address or hostname of MQ server</param>
        /// <returns></returns>
        bool connect(string serverAddress);

        /// <summary>
        /// Sends message to connected MQ. If connections doesn't established, returns false - true otherwise
        /// </summary>
        /// <param name="message">message to send</param>
        /// <returns></returns>
        bool sendMessage(string message);

        /// <summary>
        /// Closes connections, if exists.
        /// Should be called after the chat session for correct work.
        /// </summary>
        void close();
    }
}
