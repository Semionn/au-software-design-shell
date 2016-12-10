using System;
using System.Collections.Generic;
using System.Windows.Forms;
using NLog;
using RabbitMQChatProj.Controls;

namespace RabbitMQChatProj
{
    public partial class ChatForm : Form
    {
        private static readonly string QUEUE_NAME = "chat";
        private static readonly Logger logger = LogManager.GetCurrentClassLogger();
        private Dictionary<string, IChat> connectedChats = new Dictionary<string, IChat>();

        public ChatForm()
        {
            InitializeComponent();
        }

        private void ServerChatForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            foreach (IChat chat in connectedChats.Values)
            {
                chat.close();
            }
        }

        private void buttonStartServer_Click(object sender, EventArgs e)
        {
            tryConnect();
        }

        private void tryConnect()
        {
            string serverAddr = textBoxServerAddress.Text;
            if (serverAddr == "")
            {
                MessageBox.Show("Empty address!", "Warning");
                return;
            }
            if (connectedChats.ContainsKey(serverAddr))
            {
                MessageBox.Show(string.Format("Connection to address '{0}' already established", serverAddr), "Warning");
                return;
            }
            ChatClientPanel chatPanel = new ChatClientPanel(QUEUE_NAME) ;
            IChat chat = chatPanel.Chat;
            try
            {
                chat.connect(serverAddr);
                connectedChats.Add(serverAddr, chat);
                TabPage tp = new TabPage(serverAddr);
                tp.Controls.Add(chatPanel);
                chatPanel.Anchor = AnchorStyles.Right | AnchorStyles.Bottom | AnchorStyles.Top | AnchorStyles.Left;
                chatPanel.Size = tp.Size;
                tabControlMain.TabPages.Add(tp);
            }
            catch (Exception ex)
            {
                string message = string.Format("Somethig went wrong during connection to rabbitmq server: {0}", ex.Message);
                logger.Error(message);
                MessageBox.Show(message, "Error!");
            }
        }

        private void textBoxServerAddress_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                tryConnect();
            }
        }
    }
}
