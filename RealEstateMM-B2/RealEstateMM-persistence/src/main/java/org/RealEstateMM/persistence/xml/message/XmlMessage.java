package org.RealEstateMM.persistence.xml.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
public class XmlMessage {

	private String senderPseudo;
	private String recipientPseudo;
	private String messageContent;
	private boolean isUnread;

	@XmlElement(name = "senderPseudo")
	public void setSenderPseudo(String senderPseudo) {
		this.senderPseudo = senderPseudo;
	}

	public String getSenderPseudo() {
		return senderPseudo;
	}
	
	@XmlElement(name = "recipientPseudo")
	public void setRecipientPseudo(String recipientPseudo) {
		this.recipientPseudo = recipientPseudo;
	}

	public String getRecipientPseudo() {
		return recipientPseudo;
	}
	
	@XmlElement(name = "messageContent")
	public void setMessageContent(String messageContent){
		this.messageContent = messageContent;
	}
	
	public String getMessageContent(){
		return messageContent;
	}
	
	@XmlElement(name = "isUnread")
	public void setIsUnread(boolean isUnread){
		this.isUnread = isUnread;
	}
	
	public boolean getIsUnread(){
		return isUnread;
	}
}
