namespace RabbitMQChatProj
{
    partial class ChatForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.label4 = new System.Windows.Forms.Label();
            this.textBoxServerAddress = new System.Windows.Forms.TextBox();
            this.buttonStartServer = new System.Windows.Forms.Button();
            this.tabControlMain = new System.Windows.Forms.TabControl();
            this.SuspendLayout();
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(12, 15);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(78, 13);
            this.label4.TabIndex = 8;
            this.label4.Text = "Server address";
            // 
            // textBoxServerAddress
            // 
            this.textBoxServerAddress.Location = new System.Drawing.Point(12, 31);
            this.textBoxServerAddress.Name = "textBoxServerAddress";
            this.textBoxServerAddress.Size = new System.Drawing.Size(116, 20);
            this.textBoxServerAddress.TabIndex = 9;
            this.textBoxServerAddress.Text = "localhost";
            this.textBoxServerAddress.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textBoxServerAddress_KeyUp);
            // 
            // buttonStartServer
            // 
            this.buttonStartServer.Location = new System.Drawing.Point(134, 30);
            this.buttonStartServer.Name = "buttonStartServer";
            this.buttonStartServer.Size = new System.Drawing.Size(74, 20);
            this.buttonStartServer.TabIndex = 13;
            this.buttonStartServer.Text = "Connect";
            this.buttonStartServer.UseVisualStyleBackColor = true;
            this.buttonStartServer.Click += new System.EventHandler(this.buttonStartServer_Click);
            // 
            // tabControlMain
            // 
            this.tabControlMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.tabControlMain.Location = new System.Drawing.Point(12, 70);
            this.tabControlMain.Name = "tabControlMain";
            this.tabControlMain.SelectedIndex = 0;
            this.tabControlMain.Size = new System.Drawing.Size(400, 325);
            this.tabControlMain.TabIndex = 10;
            // 
            // ChatForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(424, 407);
            this.Controls.Add(this.buttonStartServer);
            this.Controls.Add(this.tabControlMain);
            this.Controls.Add(this.textBoxServerAddress);
            this.Controls.Add(this.label4);
            this.MinimumSize = new System.Drawing.Size(440, 445);
            this.Name = "ChatForm";
            this.Text = "Chat";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.ServerChatForm_FormClosed);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox textBoxServerAddress;
        private System.Windows.Forms.Button buttonStartServer;
        private System.Windows.Forms.TabControl tabControlMain;
    }
}

