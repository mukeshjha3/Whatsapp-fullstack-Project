package com.whatsapp.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Data
public class SendMessageRequest {

	private Integer userid;
	private Integer chatId;
	private String content;
	
}
