using System;
using System.Windows.Forms;

namespace RabbitMQChatProj.Controls
{
    public partial class ChatClientPanel : UserControl
    {
        private readonly IChat _chat = null;
        public IChat Chat { get { return _chat; } }

        public ChatClientPanel(string queueName)
        {
            InitializeComponent();
            _chat = new RabbitMQChat(queueName, (message) => {
                this.BeginInvoke(
                (MethodInvoker)delegate
                {
                    textBoxChat.Text += message + Environment.NewLine;
                });
            });
        }

        private string pullMessage()
        {
            string res = string.Format("{0}: {1}", textBoxUsername.Text, textBoxMessage.Text);
            textBoxMessage.Text = "";
            return res;
        }

        private void buttonSend_Click(object sender, EventArgs e)
        {
            if (!Chat.sendMessage(pullMessage()))
            {
                MessageBox.Show("Something went wrong: message has not been sent");
            }
        }

        private void textBoxUsername_TextChanged(object sender, EventArgs e)
        {
            Chat.setName(textBoxUsername.Text);
        }
    }
}
